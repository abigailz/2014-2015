package com.borya.server;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.borya.container.Module;
import com.borya.core.task.TaskManager;
import com.borya.core.userdata.UserDataManager;

public class Server {

	private Logger log = Logger.getLogger(getClass());

    private static Server instance;

    private String name;
    private String host;
    private Date startDate;
    private boolean initialized = false;
    private boolean started = false;
    private ClassLoader loader;
	private ExecutorService executors;
    /**
     * True if in setup mode
     */
    private boolean setupMode = true;

    private static final String STARTER_CLASSNAME = "com.borya.stater.ServerStarter";// do nothing
    private boolean shuttingDown;

    private Map<Class<?>, Module> modules = new LinkedHashMap<Class<?>, Module>();

    /**
     * Creates a server and starts it.
     * Through the reflection to obtain.
     */
    public Server() {
    	// We may only have one instance of the server running on the JVM
    	if (instance != null) {
    		log.error("A server is already running.");
    		throw new IllegalStateException("A server is already running");
    	}
    	log.info("Create ECSServer instance.");
    	instance = this;
    	start();
    	//executors = Executors.newSingleThreadExecutor();
    	executors = Executors.newFixedThreadPool(2);
    }
    
    public static Server getInstance() {
        return instance;
    }

    private void initialize() throws FileNotFoundException {
        name = "127.0.0.1".toLowerCase();
        try {
            host = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException ex) {
            log.warn("Unable to determine local hostname.", ex);
        }

        setupMode = false;

        loader = Thread.currentThread().getContextClassLoader();

        //CacheFactory.initialize();
        initialized = true;
    }

    /**
     * Finish the setup process. Because this method is meant to be called from inside
     * the Admin console plugin, it spawns its own thread to do the work so that the
     * class loader is correct.
     */
    public void finishSetup() {
        if (!setupMode) {
            return;
        }
        Thread finishSetup = new Thread() {
            @Override
			public void run() {
                try {
                    // First load all the modules so that modules may access other modules while
                    // being initialized
                    loadModules();
                    // Initize all the modules
                    initModules();
                    // Start all the modules
                    startModules();
                }catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                    shutdownServer();
                }
            }
        };
        // Use the correct class loader.
        finishSetup.setContextClassLoader(loader);
        finishSetup.start();
        // We can now safely indicate that setup has finished
        setupMode = false;
    }

    public void start() {
        try {
            initialize();

            startDate = new Date();
            // If the server has already been setup then we can start all the server's modules
            if (!setupMode) {
                // First load all the modules so that modules may access other modules while
                // being initialized
                loadModules();
                // Initize all the modules
                initModules();
                
                // Start all the modules
                startModules();
            }
            
            started = true;
            
            initConnectListener();
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            shutdownServer();
        }
    }

	private void initConnectListener() {
		// TODO Auto-generated method stub
		//ConnectEventDispatcher.addListener(new CCSServerListenerImpl());
	}
	
	private void loadModules() {
        // Load boot modules
		
    	// Load core modules
        loadModule(UserDataManager.class.getName());
        loadModule(TaskManager.class.getName());
        
        // Load standard modules
        
    }

    /**
     * Loads a module.
     *
     * @param module the name of the class that implements the Module interface.
     */
    private void loadModule(String module) {
        try {
            Class<?> modClass = loader.loadClass(module);
            Module mod = (Module) modClass.newInstance();
            this.modules.put(modClass, mod);
        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(),e);
        }
    }

    private void initModules() {
        for (Module module : modules.values()) {
            boolean isInitialized = false;
            try {
                module.initialize(this);
                isInitialized = true;
            }
            catch (Exception e) {
                e.printStackTrace();
                // Remove the failed initialized module
                this.modules.remove(module.getClass());
                if (isInitialized) {
                    module.stop();
                    module.destroy();
                }
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * <p>Following the loading and initialization of all the modules
     * this method is called to iterate through the known modules and
     * start them.</p>
     */
    private void startModules() {
    	boolean started = false;
        for (Module module : modules.values()) {
            try {
                module.start();
            }
            catch (Exception e) {
                if (started && module != null) {
                    module.stop();
                    module.destroy();
                }
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * Restarts the server and all it's modules only if the server is restartable. Otherwise do
     * nothing.
     */
    public void restart() {
        if (isStandAlone() && isRestartable()) {
            try {
//                Class wrapperClass = Class.forName(WRAPPER_CLASSNAME);
//                Method restartMethod = wrapperClass.getMethod("restart", (Class []) null);
//                restartMethod.invoke(null, (Object []) null);
            }
            catch (Exception e) {
                log.error("Could not restart container", e);
            }
        }
    }

    /**
     * Restarts the HTTP server only when running in stand alone mode. The restart
     * process will be done in another thread that will wait 1 second before doing
     * the actual restart. The delay will give time to the page that requested the
     * restart to fully render its content.
     */
    public void restartHTTPServer() {
        Thread restartThread = new Thread() {
            @Override
			public void run() {
                if (isStandAlone()) {
                    try {
                        //  TODO Add 
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        restartThread.setContextClassLoader(loader);
        restartThread.start();
    }

    /**
     * Stops the server only if running in standalone mode. Do nothing if the server is running
     * inside of another server.
     */
    public final void stop() {
    	initialized = false;
        // Only do a system exit if we're running standalone
        if (isStandAlone()) {
            // if we're in a wrapper, we have to tell the wrapper to shut us down
            if (isRestartable()) {
                try {
//                    Class wrapperClass = Class.forName(WRAPPER_CLASSNAME);
//                    Method stopMethod = wrapperClass.getMethod("stop", Integer.TYPE);
//                    stopMethod.invoke(null, 0);
                }
                catch (Exception e) {
                    log.error("Could not stop container", e);
                }
            }else {
                shutdownServer();
                Thread shutdownThread = new ShutdownThread();
                shutdownThread.setDaemon(true);
                shutdownThread.start();
            }
        }else {
            // Close listening socket no matter what the condition is in order to be able
            // to be restartable inside a container.
            shutdownServer();
        }
    }

    public boolean isSetupMode() {
        return setupMode;
    }

    public boolean isRestartable() {
        try {
//          Class.forName(WRAPPER_CLASSNAME);
        	return true;
        }catch (Exception e) {
        	log.error(e.getMessage(),e);
            return false;
        }
    }

    public boolean isStandAlone() {
        try {
            Class.forName(STARTER_CLASSNAME);
            return true;
        }catch (ClassNotFoundException e) {
        	log.error(e.getMessage(),e);
           return false;
        }
    }

    /**
     * <p>A thread to ensure the server shuts down no matter what.</p>
     * <p>Spawned when stop() is called in standalone mode, we wait a few
     * seconds then call system exit().</p>
     *
     * @author Iain Shigeoka
     */
    private class ShutdownThread extends Thread {

        /**
         * <p>Shuts down the JVM after a 5 second delay.</p>
         */
        @Override
		public void run() {
            try {
                Thread.sleep(5000);
                // No matter what, we make sure it's dead
                System.exit(0);
            }catch (InterruptedException e) {
                // Ignore.
            }
        }
    }

    /**
     * Makes a best effort attempt to shutdown the server
     */
    private void shutdownServer() {
        shuttingDown = true;
        
        // Shutdown the task engine.
        //TaskEngine.getInstance().shutdown();

        // If we don't have modules then the server has already been shutdown
        if (modules.isEmpty()) {
            return;
        }
        // Get all modules and stop and destroy them
        for (Module module : modules.values()) {
            module.stop();
            module.destroy();
        }
        modules.clear();
        stopExecutor();
        
        log.info("CCS stopped");
        System.exit(0);
        return;
    }
    
    /**
     * Returns true if the server is being shutdown.
     *
     * @return true if the server is being shutdown.
     */
    public boolean isShuttingDown() {
        return shuttingDown;
    }
    
    public final TaskManager getTaskManager(){
    	return (TaskManager)modules.get(TaskManager.class);
    }
    
    public final UserDataManager getUserDataManager(){
    	return (UserDataManager) modules.get(UserDataManager.class);
    }
    
    /**
     * Returns whether or not the server has been started.
     * 
     * @return whether or not the server has been started.
     */
    public boolean isStarted() {
        return started;
    }
    
    public final void execute(Runnable runnable){
    	if(executors == null){
    		log.warn("ExecutorService is null.");
    		return;
    	}
    	executors.submit(runnable);
    }
    
    private void stopExecutor(){
    	if(executors == null){
    		log.warn("ExecutorService is null.");
    		return;
    	}
    	executors.shutdown();
    }
   
}
