package com.borya.core.task.component.push.notify;

import java.util.concurrent.Executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.borya.core.task.service.AbstractService;
import com.borya.core.task.service.Handler;
import com.borya.core.task.service.Processor;

public class NotifyService extends AbstractService{

	Log log = LogFactory.getLog(getClass());
	private Processor processor;
	
	/*** 上次执行的时间 ,毫秒 ***/
	private long lastTime;
	/*** 执行的时间间隔 ,毫秒***/
	private final long timeInterval;// = 1 * 60 * 1000;
	
	public NotifyService(Executor executor) {
		super(executor);
		
		//timeInterval = SystemConfig.getInstance().getLong("socket.notify.interval");
		timeInterval = 1 * 60 * 1000;
		Handler handler = new NotifyHandler();
		processor = new NotifyProcessor(handler);
	}
	
	public void execute() {
		final long curTime = System.currentTimeMillis();
		// 这次不应该执行
		if((lastTime+timeInterval) > curTime){
			return;
		}
		
		lastTime = curTime;
		super.execute();
	}
	
	protected void dispose0(){
		if(! processor.isEnd()){
			log.debug("Execute logic.");
			return;
		}

		processor.process();
		return;
	}
	
}