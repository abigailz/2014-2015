package com.borya.core.task.impl;

import com.borya.core.timer.Task;
import com.borya.core.timer.impl.AbstractTimerTask;

public class PushTimerTask extends AbstractTimerTask{
	Task task;
	
	public PushTimerTask(Task task) {
		super(task);
		this.task = task;
	}

}
