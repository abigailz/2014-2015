package com.borya.core.task.component.push.notify;

import org.apache.log4j.Logger;

import com.borya.core.task.service.Handler;
import com.borya.core.task.service.Processor;

public class NotifyProcessor implements Processor{
	
	Logger log = Logger.getLogger(getClass());
	private Handler handler;
	private volatile boolean isEnd;
	
	public NotifyProcessor(Handler handler) {
		this.handler = handler;
		isEnd = true;
	}
	
	public void init() {
		// TODO Auto-generated method stub
	}

	public final boolean isEnd() {
		return isEnd;
	}

	public void process(){
		if(!isEnd){
			return;
		}
		
		isEnd = false;
		handler();
		isEnd = true;
		
		handler.onSentComPlete(this, true);
	}
	
	/**
	 * 遍历所有IoSession,发送数据
	 * @return
	 */
	private boolean handler(){
		
		return true;
	}
	
	public void shutDown() {
		handler = null;
	}
	
}