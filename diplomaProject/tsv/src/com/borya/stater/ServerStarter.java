package com.borya.stater;

import org.apache.log4j.Logger;

public class ServerStarter {

	private Logger log = Logger.getLogger(getClass());
	
	public ServerStarter(){
		// Ignore
	}
	
	public void start(){
		try {
			//Class.forName("com.borya.server.Server").newInstance();
		} catch (Exception e) {
			log.fatal("Server start failed.");
		}
	}
	
	public void stop(){
		
	}
	
}
