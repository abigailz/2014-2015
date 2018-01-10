package com.borya.interceptor.web;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.borya.action.web.Preparable;
import com.borya.closure.Constant;
import com.borya.util.code.StatusCode;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptorUtil;

public class WebAuthorInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(getClass());
	protected String logLevel;
	
	public WebAuthorInterceptor(){
		if(excludeMethods == null){
			this.excludeMethods = Collections.emptySet();
		}
		if(includeMethods == null){
			this.includeMethods = Collections.emptySet();
		}
	}
	
	@Override
	protected boolean applyInterceptor(ActionInvocation invocation) {
		String method = invocation.getProxy().getMethod();
		
		boolean applyMethod = 
				MethodFilterInterceptorUtil.applyMethod(
						this.excludeMethods, 
						this.includeMethods, method);
		if ((log.isDebugEnabled()) && (!(applyMethod))) {
			log.debug("Skipping Interceptor... Method [" + method + "] found in exclude list.");
		}
		
		//return super.applyInterceptor(invocation);
		return applyMethod;
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (applyInterceptor(invocation)) {
			return doIntercept(invocation);
		}
		    
		return invocation.invoke();
	}

	@Override
	protected String doIntercept(ActionInvocation invocation) 
			throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request= ServletActionContext.getRequest();
//				(HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
		//Locale locale = ac.getLocale();
		//ActionConfig config = actionProxy.getConfig();
		ActionProxy actionProxy = invocation.getProxy();
		//Map<String,Object> parameters = ac.getParameters();
		Map<String,Object> sessions = ac.getSession();  
		
		String namespace = actionProxy.getNamespace();
		Object action = invocation.getAction();
		String url = request.getRequestURI();
		String host = request.getRemoteHost();
		
		// url: /ems/w/company/init
		if(url.indexOf("/w/") < 0){
			log.error("["+host+"] Unkonw request. "+request.getRequestURI());
			return null;
		}
		// 登录接口不拦截
		if(url.indexOf("/w/user/send") >= 0){
			return invocation.invoke();
		}
		
		String host_session = (String)sessions.get(Constant.USER_HOST);
		String identifier = (String)sessions.get(Constant.USER_UNIQUE_IDENTIFIER);

		if(identifier == null
				|| !host.equals(host_session)){
			log.debug("["+host + "] Session invalid Or kicked.");
			out(StatusCode.SESSION_OUT_OF_DATE,"页面失效",ServletActionContext.getResponse());
			return null;
			//ServletActionContext.getResponse().sendRedirect("/ems/login.html");
			//return "login";
		}
		
		if(action instanceof Preparable){
			// com.borya.action.web.CompanyAction@aac61
			Object obj =((Preparable)action).prepare(host);
			if(obj == null){
				// TODO 后续响应处理
			}else{
				
			}
		}
		
		/*** 记录业务的执行时间**/
		long startTime = System.currentTimeMillis();
		String result = invocation.invoke();
		long executionTime = System.currentTimeMillis() - startTime;
		
		invokeUnderTiming(actionProxy,namespace,executionTime);
	    
		return result;
	}
	
	private void invokeUnderTiming(ActionProxy actionProxy,String namespace,long executionTime){
		StringBuilder message = new StringBuilder(100);
	    message.append("Executed action [");
	    if ((namespace != null) && (namespace.trim().length() > 0)) {
	    	message.append(namespace).append("/");
	    }
	    message.append(actionProxy.getActionName());
	    message.append("!");
	    message.append(actionProxy.getMethod());
	    message.append("] took ").append(executionTime).append(" ms.");

	    doLog(log, message.toString());
	}
	
	protected void doLog(Log log, String message){
		if (this.logLevel == null) {
			log.info(message);
			return;
	    }

	    if ("debug".equalsIgnoreCase(this.logLevel)){
	    	log.debug(message);
	    }
	    else if ("info".equalsIgnoreCase(this.logLevel)){
	    	log.info(message);
	    }
	    else if ("warn".equalsIgnoreCase(this.logLevel)){
	    	log.warn(message);
	    }
	    else if ("error".equalsIgnoreCase(this.logLevel)){
	    	log.error(message);
	    }
	    else if ("fatal".equalsIgnoreCase(this.logLevel)){
	    	log.fatal(message);
	    }
	    else if ("trace".equalsIgnoreCase(this.logLevel)){
	    	log.trace(message);
	    }
	    else{
	    	log.error("LogLevel [" + this.logLevel + "] is not supported");
	    }
	}
	
	private void out(StatusCode code,String msg,HttpServletResponse response) {
		String str = "{\"code\":"+code+",\"msg\":\""+msg+"\"}";
		out(str,response);
	}
	
	private void out(String str,HttpServletResponse response) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			out.print(str);
			out.close();
		} catch (Exception e) {
			log.error("out fail"+e.getMessage());
		}
	}
}

