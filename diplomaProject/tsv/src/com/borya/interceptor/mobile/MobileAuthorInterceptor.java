package com.borya.interceptor.mobile;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.borya.action.mobile.Preparable;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptorUtil;

/**
 * client拦截器
 * @author Administrator
 *
 */
public class MobileAuthorInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(getClass());
	protected String logLevel;
	
	public MobileAuthorInterceptor(){
		this.excludeMethods = Collections.emptySet();
	    this.includeMethods = Collections.emptySet();
	}
	
	@Override
	protected boolean applyInterceptor(ActionInvocation invocation) {
		String method = invocation.getProxy().getMethod();
		
		boolean applyMethod = MethodFilterInterceptorUtil.applyMethod(this.excludeMethods, this.includeMethods, method);
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
		ActionProxy actionProxy = invocation.getProxy();
		//ActionConfig config = actionProxy.getConfig();
		
		String namespace = actionProxy.getNamespace();
		Object action = invocation.getAction();
		
		//Map<String,Object> sessions = ac.getSession();  
		Map<String,Object> parameters = ac.getParameters();
		//Map<String,String> config_params = config.getParams();
		
		
		// url: /ems/c/company/init
		if(request.getRequestURI().indexOf("/c/") < 0){
			log.error("Unkonw request. ["+request.getRemoteHost()
					+"] "+request.getRequestURI());
			return null;
		}
		
		// 登录接口不拦截
//		if(request.getRequestURI().indexOf("/c/member/login") >= 0){
//			return invocation.invoke();
//		}
		// 校验token是否存在多个
		String []tokens = request.getParameterValues("token");
		if(tokens != null && tokens.length > 1){
			return null;
		}
		
		final String host = request.getRemoteHost();
		final String token = request.getParameter("token");
		// 校验TOKEN
		if(action instanceof Preparable){
			Object obj =((Preparable)action).prepare(host,token);
			if(obj == null){
				log.warn("Error request.url="+request.getRequestURI());
				log.warn("Error request.token="+token);
				return null;
			}
			
			if(obj instanceof Object[]){
				Object[] obj_tmp = (Object[])obj;
				// Member
				request.setAttribute("member", obj_tmp[0]);
				// AuthenticationToken
				request.setAttribute("auth", obj_tmp[1]);
			}
			else{
				// ERROR REQUEST
				log.warn("Error request.["+host+"] token="+token);
				return null;
			}
		}
		
		String key;
		Object val;
		for(Map.Entry<String, Object> entry : parameters.entrySet()){
			key = entry.getKey();
			val = entry.getValue();
			
			if(val instanceof String[]){
				String[] val_array = (String[])val;
				// 相同名称的参数只有一个
				if(val_array.length == 1){
					//System.out.println("key="+key+" ,val="+val_array[0]);
					continue;
				}
				
				// 相同名称的参数有多个
//				for(String str :(String[])val){
//					
//				}
				// [qqq, 123]
				//Arrays.toString((String[])val);
				continue;
			}
			
			if(val instanceof Object[]){
				System.out.println("key="+key);
				for(Object obj :(Object[])val){
					System.out.println("val="+obj);
				}
				continue;
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
}
