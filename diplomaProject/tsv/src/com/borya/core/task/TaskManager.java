package com.borya.core.task;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;
import com.borya.container.BasicModule;
import com.borya.core.task.component.push.notify.NotifyService;
import com.borya.core.task.service.Service;
import com.borya.core.timer.Task;
import com.borya.util.TaskEngine;

public class TaskManager extends BasicModule {
	
	public Logger log = Logger.getLogger(getClass());
	private static TaskManager instance;
	private Timer timer;
	private TimerTask timerTask;
	// 定时器循环执行的间隔时间(毫秒)
	private final long timerCycleTime = 1 * 60 * 1000;
	
	private List<Service> services;
	private Executor executor;
	
	public final static TaskManager getInstance() {
		return instance;
	}
	
	public TaskManager() {
		super("TaskManager");
		
		executor = TaskEngine.getInstance().getExecutorService();
		services = new CopyOnWriteArrayList<Service>();
		
		initData();

		init();
		instance = this;
	}

	private void initData() {
		// 推送处理
		services.add(new NotifyService(executor));
	}

	public void init() {
		TaskHandler taskHandler = new TaskHandler(createTask());
		timerTask = taskHandler.getTimerTaskHandler().getTimerTask();
		timer = taskHandler.getTimerHandler().getTimer();
		
		//timer.scheduleAtFixedRate(timerTask, 5 * 1000L, timerCycleTime);
		
		TaskEngine.getInstance().scheduleAtFixedRate(timerTask, 5 * 1000L, timerCycleTime);
	}

	/**
	 * 创建任务，并执行任务
	 * @return
	 */
	private Task createTask() {
		return new Task() {
			private AtomicLong count = new AtomicLong(0L);
			public void execute() {
				process(count);
				count.getAndIncrement();
			}
			
			private void process(AtomicLong count){
				if(services == null || services.size() < 1){
					return;
				}
				
				for(Service service : services){
					service.execute();
				}
				return;
			}
		};
	}
	
	
	/**
	 * 停止关闭
	 */
	public final void stop() {
		try {
			if (timerTask != null) {
				timerTask.cancel();
			}

			if (timer != null) {
				timer.cancel();
			}
			
			services = null;
			executor = null;
		} catch (Exception e) {
			// to do nothing
		}
	}

}