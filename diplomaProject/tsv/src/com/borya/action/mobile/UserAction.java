package com.borya.action.mobile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.borya.authc.AuthenticationToken;
import com.borya.biz.UserService;
import com.borya.biz.impl.UserServiceImpl;
import com.borya.model.db.User;
import com.borya.util.code.StatusCode;

public class UserAction extends MobileBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(getClass());
	
	private UserService userServiceImpl;
	public UserAction(){
		userServiceImpl = new UserServiceImpl();
	}
	
	public UserService getUserServiceImpl() {
		return userServiceImpl;
	}
	
	/**************************************************************/
	
	public Object doPrepare(String host, String token) {
		return getUserServiceImpl().prepare(host, token);
	}

	public String login(){
		try{
			// {"loc":"经度，维度"}
			String context = getContext();
			log.info("RECV : ["+getHost()+"] "+context);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();

			Object object = getUserServiceImpl().login(user, auth, context);
			if(object instanceof String){
				log.info("OUT :"+object);
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
	public String close(){
		try{
			String context = getContext();
			
			log.info("RECV : ["+getHost()+"] "+context);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();

			Object object = getUserServiceImpl().close(user, auth,context);
			if(object instanceof String){
				log.info("OUT :"+object);
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
	public String logout(){
		try{
			String context = getContext();
			log.info("RECV : ["+getHost()+"] "+context);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();

			getUserServiceImpl().logout(user, auth, context);
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		log.info("OUT :"+StatusCode.SUCCESS.toJSON());
		out(StatusCode.SUCCESS.toJSON());
		return null;
	}
	
	public String list(){
		try{
			int type = getInt("type");
			int pageSize = getInt("pageSize");
			int size = getInt("size");
			
			log.info("RECV : ["+getHost()+"] type="+type+",pageSize="+pageSize+",size="+size);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();

			Object object = getUserServiceImpl().list(user, auth, type,pageSize,size);
			if(object instanceof String){
				log.info("OUT :"+object);
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
}