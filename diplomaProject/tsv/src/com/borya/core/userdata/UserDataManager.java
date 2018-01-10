package com.borya.core.userdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.borya.container.BasicModule;


public class UserDataManager extends BasicModule{

	Logger log = LoggerFactory.getLogger(getClass());
	private static UserDataManager instance;
	
	
	public UserDataManager(){
		super("UserDataManager");
		
		instance = this;
		log.debug("Create UserDataManager instance.");
	}
	
	public static final UserDataManager getInstance(){
		return instance;
	}
	
}
