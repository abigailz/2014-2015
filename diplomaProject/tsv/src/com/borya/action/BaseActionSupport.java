package com.borya.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.borya.util.code.StatusCode;

public abstract class BaseActionSupport extends BaseAction {

	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(getClass());


//	/**
//	 * 页面做分页使用的接口
//	 * 
//	 */
//	protected void responseSuccessMsg(boolean result,int total,String jsonMsgList)
//	{
//		String str = JSONMessageUtils.composeData(result,total,jsonMsgList);
//		out(str);
//	}

	/****
	 * 
	 * @param code
	 * @param msg
	 */
	protected void out(StatusCode code,String msg){
		String str = "{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";
		out(str);
	}
	
	protected void out(StatusCode code) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("code", String.valueOf(code));
			response.setStatus(Integer.valueOf(code.num()));
			// PrintWriter out = response.getWriter();
			// out.close();
		} catch (Exception e) {
			log.error("outToCode is fail");
		}
	}

	// TODO: [common] 位置
	protected void out(String str) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.print(str);
			out.close();
		} catch (Exception e) {
			log.error("outToJson is fail. "+e.getMessage());
		}
	}
	
}
