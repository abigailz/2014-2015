package com.borya.core.timer.impl;

import java.util.TimerTask;

import com.borya.core.timer.Task;

public abstract class AbstractTimerTask extends TimerTask{


	private Task task;
	
	public AbstractTimerTask(Task task){
		this.task = task;
	}
	
	@Override
	public final void run() {
		execute();
	}

	private final void execute() {
		task.execute();
	}
}