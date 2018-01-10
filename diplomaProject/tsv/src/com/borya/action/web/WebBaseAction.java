package com.borya.action.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.borya.action.BaseActionSupport;

@SuppressWarnings("serial")
abstract class WebBaseAction extends BaseActionSupport {

	Log log = LogFactory.getLog(getClass());
		
	public Object doPrepare(String host){
		return null;
	}
	
	public Object prepare(String host) {
		Object object = doPrepare(host);
		if(object == null){
			//out(StatusCode.PARAM_ERROR,"参数错误");
			//return null;
		}
		
		return object;
	}
	
	
	/******************************************************************
	 *                       权限校验
	 *****************************************************************/
	
//	public UserRelevantEntity getUserRelevantEntity(){
//		return (UserRelevantEntity)getSession().getAttribute(Constants.USER_ALL);
//	}
	
	/******************************************************************
	 *                       重写函数 返回消息
	 *****************************************************************/
	
	protected void out(String str){
		log.info("OUT :"+str);
		super.out(str);
	}
	
}
