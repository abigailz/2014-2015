package com.borya.closure;

import com.borya.util.prop.SystemConfig;


public abstract interface Constant {

	/******************** S2S **************************************/
	public String S2S_TOKEN = 
			SystemConfig.getInstance().getString("s2s.authorized.by.token");
	public String S2S_IP = 
			SystemConfig.getInstance().getString("s2s.authorized.by.ip");
	/***************************************************************/
	
	public static final String USER_HOST = "user_host";
	public static final String USER_UNIQUE_IDENTIFIER = "unique_identitifer";  
	
	public static final String AUTH_MARK = "AUTH";
	
	/*** 文件保存路径 **/
	public String FILE_PATH = 
			SystemConfig.getInstance().getString("file.save.path");
	
}

