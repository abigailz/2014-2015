package com.tsv.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class TaskManager {

	private static ExecutorService executorService;
	
	private TaskManager(){
		if(executorService == null){
			executorService = Executors.newCachedThreadPool();
		}
	}
	
	private static final class TaskManagerHolder{
		private static TaskManager instance = new TaskManager();
	}
	
	public static final TaskManager getInstance(){
		return TaskManagerHolder.instance;
	}
	
	public final void execute(final Callback callback){
		 executorService.submit(new Runnable() {
	           public void run() {
	        	  Object obj = callback.exec();
	        	  // FIXME
	        	  System.out.println(obj);
	           }
		 });
		 return;
	}
	
}
