package com.borya.core.task.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public abstract class AbstractService implements Service{

	Log log = LogFactory.getLog(getClass());
	
	/** 
     * The unique number identifying the Service. It's incremented
     * for each new IoService created.
     */
    private static final AtomicInteger id = new AtomicInteger();

    /** 
     * The thread name built from the IoService inherited 
     * instance class name and the IoService Id 
     **/
    private final String threadName;

    /**
     * The associated executor, responsible for handling execution of I/O events.
     */
    protected final Executor executor;

    /**
     * A flag used to indicate that the local executor has been created
     * inside this instance, and not passed by a caller.
     * 
     * If the executor is locally created, then it will be an instance
     * of the ThreadPoolExecutor class.
     */
    private final boolean createdExecutor;
    /**
     * A lock object which must be acquired when related resources are
     * destroyed.
     */
    protected final Object disposalLock = new Object();

    private volatile boolean disposing;

    private volatile boolean disposed;
    /**
     * The IoHandler in charge of managing all the I/O Events. It is 
     */
    private Handler handler;

    public AbstractService(Executor executor) {
    	if (executor == null) {
            this.executor = Executors.newCachedThreadPool();
            createdExecutor = true;
        } else {
            this.executor = executor;
            createdExecutor = false;
        }
    	
        threadName = getClass().getSimpleName() + '-' + id.incrementAndGet();
    }
    
    
    public void init(){
    	
    }
    
    /**
     * {@inheritDoc}
     */
    public final void setHandler(Handler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler cannot be null");
        }
        
        this.handler = handler;
    }
    
    /**
     * {@inheritDoc}
     */
    public final Handler getHandler() {
        return handler;
    }
    
    public void execute() {
        execute(new Runnable() {
			public void run() {
				dispose0();
			}
		}, null);
    }

    protected final void execute(Runnable worker, String suffix) {
        String actualThreadName = threadName;
        if (suffix != null) {
            actualThreadName = actualThreadName + '-' + suffix;
        }
        executor.execute(new NamePreservingRunnable(worker, actualThreadName));
    }
    
    /**
     * {@inheritDoc}
     */
    public final void dispose() {
        dispose(false);
    }

    /**
     * {@inheritDoc}
     */
    public final void dispose(boolean awaitTermination) {
        if (disposed) {
            return;
        }

        synchronized (disposalLock) {
            if (!disposing) {
                disposing = true;

                try {
                    dispose0();
                } catch (Exception e) {
                   log.error(e.getMessage(),e);
                }
            }
        }

        if (createdExecutor) {
            ExecutorService e = (ExecutorService) executor;
            e.shutdownNow();
            if (awaitTermination) {

                //Thread.currentThread().setName();

                try {
                    log.debug("awaitTermination on {"+this
                    		+"} called by thread=[{"+Thread.currentThread().getName()+"}]");
                    e.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
                    log.debug("awaitTermination on {"+this+"} finished");
                } catch (InterruptedException e1) {
                    log.warn("awaitTermination on [{"+this+"}] was interrupted");
                    // Restore the interrupted status
                    Thread.currentThread().interrupt();
                }
            }
        }
        disposed = true;
    }
    
    public class NamePreservingRunnable implements Runnable {

        /** The runnable name */
        private final String newName;

        /** The runnable task */
        private final Runnable runnable;

        /**
         * Creates a new instance of NamePreservingRunnable.
         *
         * @param runnable The underlying runnable
         * @param newName The runnable's name
         */
        public NamePreservingRunnable(Runnable runnable, String newName) {
            this.runnable = runnable;
            this.newName = newName;
        }

        /**
         * Run the runnable after having renamed the current thread's name 
         * to the new name. When the runnable has completed, set back the 
         * current thread name back to its origin. 
         */
        public void run() {
            Thread currentThread = Thread.currentThread();
            String oldName = currentThread.getName();

            if (newName != null) {
                setName(currentThread, newName);
            }

            try {
                runnable.run();
            } finally {
                setName(currentThread, oldName);
            }
        }

        /**
         * Wraps {@link Thread#setName(String)} to catch a possible {@link Exception}s such as
         * {@link SecurityException} in sandbox environments, such as applets
         */
        private void setName(Thread thread, String name) {
            try {
                thread.setName(name);
            } catch (SecurityException se) {
                if (log.isWarnEnabled()) {
                    log.warn("Failed to set the thread name.", se);
                }
            }
        }
    }
    
    
    /**
     * Implement this method to release any acquired resources.  This method
     * is invoked only once by {@link #dispose()}.
     */
    protected abstract void dispose0();
}