package com.borya.biz.impl;

import org.apache.log4j.Logger;

import com.borya.authc.AuthenticationToken;
import com.borya.authc.impl.UserToken;
import com.borya.dao.QuestionDAO;
import com.borya.dao.UserDAO;
import com.borya.dao.impl.QuestionDAOImpl;
import com.borya.dao.impl.UserDAOImpl;
import com.borya.model.db.User;
import com.borya.util.encrypt.EncryptUtil;

abstract class AbstractBaseService {

	Logger log = Logger.getLogger(getClass());
	
	protected UserDAO userDAOImpl;
	protected QuestionDAO questionDAOImpl;
	
	protected AbstractBaseService(){
		userDAOImpl = new UserDAOImpl();
		questionDAOImpl = new QuestionDAOImpl();
	}
	
	public UserDAO getUserDAOImpl() {
		return userDAOImpl;
	}

	public QuestionDAO getQuestionDAOImpl() {
		return questionDAOImpl;
	}

	/*******************************************************************
	 *
	 *******************************************************************/
	/**
	 * token 解析 、校验
	 * 
	 * @param token
	 * @return NULL
	 */
	public AuthenticationToken authenticate(String host, String token) {
		if (token == null) {
			return null;
		}

		String decryptToken;
		try {
			decryptToken = EncryptUtil.decrypt(token);
		} catch (Exception e) {
			return null;
		}

		if (decryptToken == null) {
			return null;
		}

		String tokenArray[] = decryptToken.split(",");
		if (tokenArray == null || tokenArray.length < 6) {
			return null;
		}

		// 手机类型,username,pwd,tmsi,手机型号组成，version
		return new UserToken(host, decryptToken, tokenArray);
	}

	/***
	 * client token 校验
	 * 
	 * @param host
	 * @param token
	 * @return
	 */
	public Object prepare(String host, String token) {
		if(token == null){
			log.warn("Token is null.");
			return null;
		}
		
		return prepare_Common(host,token);
	}
	
	private Object prepare_Common(String host,String token){
		AuthenticationToken auth = authenticate(host, token);
		if (auth == null) {
			log.warn("Error data.["+host+"] token="+token);
			return null;
		}

		User user = getUserDAOImpl().queryByName(auth.getUsername());
		if (user == null) {
			log.warn("User not found.["+host+"] "+auth.getUsername());
			return null;
		}
		
		if(!user.getPwd().equalsIgnoreCase((String)auth.getPassword())){
			log.warn("User password error.["+host+"] "+auth.getUsername());
			return user;
		}
		
		if("0".equals(auth.getTMSI())){
			String tmsi = System.currentTimeMillis()+"";
			Boolean bool = getUserDAOImpl().saveTmsi(auth.getUsername(), tmsi);
			if(bool){
				user.setTmsi(tmsi);
				return (new Object[] { user, auth });
			}
			// TODO db exception
			return "TMSI";
		}
		// 比较TMSI 判断是否被顶号
  		if (!auth.getTMSI().equals(user.getTmsi())) {
  			log.warn("User TMSI error.["+host+"] "+auth.getUsername());
			return "TMSI";
		}
		return (new Object[] { user, auth });
	}
	
}