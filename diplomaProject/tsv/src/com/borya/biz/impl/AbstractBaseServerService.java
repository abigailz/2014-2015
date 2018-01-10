package com.borya.biz.impl;

import com.borya.util.prop.SystemConfig;

abstract class AbstractBaseServerService extends AbstractBaseService{

	// 服务器鉴权token、ip
	private static final String AUTHORIZED_IP = 
			SystemConfig.getInstance().getString("authorized.by.ip");;
	private static final String AUTHORIZED_TOKEN = 
			SystemConfig.getInstance().getString("authorized.by.token");;
	
	protected AbstractBaseServerService(){
		SystemConfig.getInstance().getString("authorized.by.ip");
	}
	
	/**
	 * Server 鉴权
	 */
	public Object prepare(String host, String token) {
		// 校验TOKEN 、IP
		if(AUTHORIZED_IP.contains(host) && AUTHORIZED_TOKEN.contains(token)){
			log.debug(host+" authorized success.");
			return "success";
		}
		
		log.warn(host+" authorized failed.");
		return null;
	}
	
}
