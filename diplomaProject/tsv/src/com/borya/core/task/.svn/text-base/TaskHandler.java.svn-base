package com.borya.core.task;

import java.util.Timer;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import com.borya.core.task.impl.PushTimerTask;
import com.borya.core.timer.Task;
import com.borya.core.timer.TimerHandler;
import com.borya.core.timer.TimerTaskHandler;

public class TaskHandler {
	
	private Logger log = Logger.getLogger(getClass());
	private Task task;
	
	public TaskHandler(Task t){
		log.debug("Create TaskHandler instance.");
		this.task = t;
	}
	
	private Task createTask(){
		return task;
	}
	
	public final TimerHandler getTimerHandler() {
		return new TimerHandler() {
			public Timer getTimer() {
				return new Timer("BT-TaskManager");
			}
		};
	}
	
	public final TimerTaskHandler getTimerTaskHandler() {
		return new TimerTaskHandler() {
			public TimerTask getTimerTask() {
				return new PushTimerTask(createTask());
			}
		};
	}
}