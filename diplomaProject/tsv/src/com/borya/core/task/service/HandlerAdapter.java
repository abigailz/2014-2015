package com.borya.core.task.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HandlerAdapter implements Handler{

	Log log = LogFactory.getLog(getClass());
	
	public void init(Processor processor) throws Exception {
		// Empty handler
	}
	
	public void onSentComPlete(Processor processor, Object message){
		// Empty handler
	}
	
}
