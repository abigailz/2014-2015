package com.borya.action.mobile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.borya.action.BaseActionSupport;
import com.borya.authc.AuthenticationToken;
import com.borya.model.db.User;
import com.borya.util.code.StatusCode;

abstract class MobileBaseAction extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(getClass());
	
	public MobileBaseAction(){
		// to do nothing
	}
	
	abstract Object doPrepare(String host,String token);
	
	public Object prepare(String host,String token) {
		Object object = doPrepare(host,token);
		if(object == null){
			out(StatusCode.PARAM_ERROR,"参数错误");
			return null;
		}
		
		if(object instanceof User){
			out(StatusCode.PASSWORD_ERROR,"密码错误");
			return null;
		}
		// 顶号
		if (object instanceof String) {
			out(StatusCode.TMSI_NO_MATCH, "你的账号在其他(终端)上被登录");
			return null;
		}
		return object;
	}
	
	public User getUser(){
		return (User)getRequest().getAttribute("member");
	}
	
	public AuthenticationToken getAuthenticationToken(){
		return (AuthenticationToken)getRequest().getAttribute("auth");
	}
	
	/******************************************************************
	 *                       重写函数 返回消息
	 *****************************************************************/
	
	protected void out(String str){
		//log.info("OUT :"+str);
		super.out(str);
	}
	
	protected void out(Integer code,String msg){
		String str = "{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";
		out(str);
	}
	/****
	 * 
	 * @param code
	 * @param msg
	 */
	protected void out(StatusCode code,String msg){
		String str = "{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";
		out(str);
	}
	
}
