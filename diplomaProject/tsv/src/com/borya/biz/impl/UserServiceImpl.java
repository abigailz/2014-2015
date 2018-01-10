package com.borya.biz.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.borya.authc.AuthenticationToken;
import com.borya.biz.UserService;
import com.borya.model.db.State;
import com.borya.model.db.Type;
import com.borya.model.db.User;
import com.borya.util.code.StatusCode;

public class UserServiceImpl extends AbstractBaseService implements UserService{

	Logger log = Logger.getLogger(getClass());

	public UserServiceImpl(){

	}
	/**************************************************/

	@Override
	public Object login(User user, AuthenticationToken auth, String context) {
		if(context == null || context.trim().length() < 5){
			log.warn("Paramter error."+context);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		JSONObject json = JSONObject.fromObject(context);
		String loc = json.getString("loc");
		// TODO SAVE LOC TO DB
		log.debug("[LOC] "+loc);
		
		return StatusCode.SUCCESS.toDiyJson("data"
				, "{\"tmsi\":\""+user.getTmsi()+"\",\"type\":\""+user.getType()+"\"}");
	}

	@Override
	public void logout(User user, AuthenticationToken auth, String context) {
		log.debug("[LOGOUT] "+context);
		return;
	}

	@Override
	public Object list(User user, AuthenticationToken auth, int type,
			int pageSize, int size) {
		if(pageSize < 1){
			log.warn("Paramter(pageSize) error."+pageSize);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		if(size < 1){
			log.warn("Paramter(size) error."+size);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		if(! Type.exists(type)){
			log.warn("Paramter(type) error."+type);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		final int start = (pageSize - 1) * size;
		
		List<User> list = getUserDAOImpl().list(type,start,size);
		if(list == null){
			log.warn("DB connection exception.");
			return StatusCode.SUCCESS.toDiyJson("data", "[]");
		}
		
//		{"id":"xx","uid":"xx","userName":"x","type":"","createTime":""}
		StringBuilder sb = new StringBuilder(300);
		sb.append("[");
		int i =0;
		for(User u :list){
			if(i != 0){
				sb.append(",");
			}
			i++;
			sb.append("{");
			sb.append("\"id\":\""+u.getId()+"\"");
			sb.append(",\"uid\":\""+u.getUid()+"\"");
			sb.append(",\"userName\":\""+u.getUserName()+"\"");
			sb.append(",\"type\":\""+u.getType()+"\"");
			sb.append(",\"createTime\":\""+u.getCreateTime()+"\"");
			sb.append(",\"uid\":\""+u.getUid()+"\"");
			sb.append(",\"type\":\""+u.getType()+"\"");
			sb.append("}");
			continue;
		}
		sb.append("]");
		
		return StatusCode.SUCCESS.toDiyJson("data", sb.toString());
	}
	
	@Override
	public Object close(User user, AuthenticationToken auth, String context) {
		if(context == null || context.trim().length() < 5){
			log.warn("Paramter error."+context);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		// {"qid":"xx","loc":"经度，维度"}
		JSONObject json = JSONObject.fromObject(context);
		final String qid = json.getString("qid");
		
		boolean bool = 
				getQuestionDAOImpl().updateQuestionState(qid, State.Close.num());
		if(bool){
			log.debug("Close question success.qid="+qid);
			StatusCode.SUCCESS.toJSON();
		}
		
		return null;
	}

}
