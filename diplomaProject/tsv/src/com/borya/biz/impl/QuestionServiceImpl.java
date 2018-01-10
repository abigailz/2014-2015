package com.borya.biz.impl;

import java.io.File;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.borya.authc.AuthenticationToken;
import com.borya.biz.QuestionService;
import com.borya.closure.Constant;
import com.borya.model.QuestionEntrty;
import com.borya.model.db.Question;
import com.borya.model.db.QuestionLog;
import com.borya.model.db.State;
import com.borya.model.db.Type;
import com.borya.model.db.User;
import com.borya.util.DateUtils;
import com.borya.util.FileUtils;
import com.borya.util.StringUtils;
import com.borya.util.code.StatusCode;

public class QuestionServiceImpl extends AbstractBaseService implements QuestionService{

	Logger log = Logger.getLogger(getClass());

	private final String[] file_prefix = {"officers_","staff_"};
	private final String[] fileder = {"officers","staff"};
	// 文件保存路径
	private String rootPath = Constant.FILE_PATH;
			
	@Override
	public Object list(User user, AuthenticationToken auth
			,final int pageSize
			,final int size,final int state
			,final long time) {
		if(pageSize < 0){
			log.warn("pageSize error."+pageSize);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		if(size < 0){
			log.warn("size error."+size);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		if(! State.exists(state)){
			log.warn("state error."+size);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		final int type = user.getType();
		if(type == Type.OFFICERS){
			return proecss_officers(user,pageSize,size,state,time);
		}
		if(type == Type.ASSIGN){
			return process_assign(user,pageSize,size,state,time);
		}
		if(type == Type.STAFF){
			return process_staff(user,pageSize,size,state,time);
		}
		
		log.warn("Unknow user type."+type);
		return StatusCode.PARAM_ERROR.toJSON();
	}

	private String proecss_officers(User user, int pageSize, int size, int state,long time) {
		final int start = (pageSize-1) * size;
		final String uid = user.getUid();
		if(state == State.Default.num()){
			Object[] array = getQuestionDAOImpl().query(uid,start,size);
			if(array == null){
				return StatusCode.SUCCESS.toDiyJson("data"
						, "{\"total\":\"0\",\"list\":[]}");
			}
			
			int count = (Integer) array[0];
			@SuppressWarnings("unchecked")
			List<Question> list = (List<Question>)array[1];
			StringBuilder sb = new StringBuilder(300);
			sb.append("{");
			sb.append("\"total\":\""+count+"\"");
			sb.append(",\"time\":\""+System.currentTimeMillis()+"\"");
			sb.append(",\"list\":[");
			int i = 0;
			for(Question ques : list){
				if(i != 0){
					sb.append(",");
				}
				
				i++;
				sb.append("{");
				sb.append("\"id\":\""+ques.getId()+"\"");
				sb.append(",\"qid\":\""+ques.getQid()+"\"");
				sb.append(",\"title\":\""+ques.getTitle()+"\"");
				sb.append(",\"uid\":\""+ques.getUid()+"\"");
				sb.append(",\"createTime\":\""+ques.getCreateTime()+"\"");
				sb.append(",\"url\":\""+ques.getUrl()+"\"");
				sb.append(",\"state\":\""+ques.getState()+"\"");
				sb.append(",\"loc\":\""+ques.getLoc()+"\"");
				sb.append(",\"desc\":\""+ques.getDesc()+"\"");
				sb.append("}");
			}
			sb.append("]");
			sb.append("}");
			return StatusCode.SUCCESS.toDiyJson("data"
					,sb.toString());
		}
		
		return process_queryByState(uid, start, size, state,time);
	}
	
	private String process_queryByState(String uid, int start, int size, int state,long time) {
		Object[] array = getQuestionDAOImpl().query(uid,start,size,state);
		if(array == null){
			return StatusCode.SUCCESS.toDiyJson("data"
					, "{\"total\":\"0\",\"list\":[]}");
		}
		
		int count = (Integer) array[0];
		@SuppressWarnings("unchecked")
		List<Question> list = (List<Question>)array[1];
		StringBuilder sb = new StringBuilder(300);
		sb.append("{");
		sb.append("\"total\":\""+count+"\"");
		sb.append(",\"time\":\""+System.currentTimeMillis()+"\"");
		sb.append(",\"list\":[");
		int i = 0;
		for(Question ques : list){
			if(i != 0){
				sb.append(",");
			}
			
			i++;
			sb.append("{");
			sb.append("\"id\":\""+ques.getId()+"\"");
			sb.append(",\"qid\":\""+ques.getQid()+"\"");
			sb.append(",\"title\":\""+ques.getTitle()+"\"");
			sb.append(",\"uid\":\""+ques.getUid()+"\"");
			sb.append(",\"createTime\":\""+ques.getCreateTime()+"\"");
			sb.append(",\"url\":\""+ques.getUrl()+"\"");
			sb.append(",\"state\":\""+ques.getState()+"\"");
			sb.append(",\"loc\":\""+ques.getLoc()+"\"");
			sb.append(",\"desc\":\""+ques.getDesc()+"\"");
			sb.append("}");
		}
		sb.append("]");
		sb.append("}");
		return StatusCode.SUCCESS.toDiyJson("data"
				,sb.toString());
	}
	
	/***
	 * 状态（1：未处理，2：已分配，3：处理完成，4：已关闭）
	 * @param user
	 * @param pageSize
	 * @param size
	 * @param state
	 * @return
	 */
	private String process_assign(User user, int pageSize, int size, int state,long time) {
		final int start = (pageSize -1) * size;
		final String uid = user.getUid();
		
		return process_queryByState(uid, start, size, state,time);
	}
	
	private String process_staff(User user, int pageSize, int size, int state,long time) {
		final int start = (pageSize -1) * size;
		final String uid = user.getUid();
		
		return process_queryByState(uid, start, size, state,time);
	}

	@Override
	public Object get(User user, AuthenticationToken auth, String qid) {
		if(qid == null || qid.trim().length() < 1){
			log.warn("Error qid "+qid);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		final String uid = user.getUid();
		QuestionEntrty entry = getQuestionDAOImpl().get(uid,qid);
		if(entry == null || entry.getQuestion() == null){
			log.warn("Not found question qid="+qid);
			return StatusCode.SUCCESS.toDiyJson("data", "{}");
		}
		
		Question ques = entry.getQuestion();
		User u = entry.getUser();
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		sb.append("\"question\":");
		sb.append("{");
		sb.append("\"id\":\""+ques.getId()+"\"");
		sb.append(",\"qid\":\""+ques.getQid()+"\"");
		sb.append(",\"title\":\""+ques.getTitle()+"\"");
		sb.append(",\"uid\":\""+ques.getUid()+"\"");
		sb.append(",\"createTime\":\""+ques.getCreateTime()+"\"");
		sb.append(",\"url\":\""+ques.getUrl()+"\"");
		sb.append(",\"state\":\""+ques.getState()+"\"");
		sb.append(",\"loc\":\""+ques.getLoc()+"\"");
		sb.append(",\"desc\":\""+ques.getDesc()+"\"");
		sb.append("}");
		
		sb.append(",\"user\":");
		sb.append("{");
		sb.append("\"userName\":\""+u.getUserName()+"\"");
		sb.append(",\"type\":\""+u.getType()+"\"");
		sb.append("}");
		
		sb.append("}");
		
		return StatusCode.SUCCESS.toDiyJson("data", sb.toString());
	}

	@Override
	public Object addpic(User user, AuthenticationToken auth,
			List<File> fileData, List<String> fileDataFileName) {
		if(fileData == null || fileData.get(0) == null){
			log.warn("Error File .");
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		File file = fileData.get(0);
		String fileName = fileDataFileName.get(0);
		
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		
		String path = null;
		if (rootPath.endsWith(File.separator)) {
			path = rootPath;// + Constants.FILE_PATH;
		} else {
			path = rootPath + File.separator;// + Constants.FILE_PATH;
		}
		
		path += fileder[0] + File.separator;
		fileName = file_prefix[0]+DateUtils.getDetailedTime() + suffix;

		log.info("Save file,fileName=" + fileName);

		FileUtils.mkDirectory(path);
		FileUtils.upload(fileName, path, file); 
		
//		{"code":"200","msg":"xxx","data":{"url":"xxx"}}
		return StatusCode.SUCCESS.toDiyJson("data", "{\"url\":\""+fileName+"\"}");
	}

	@Override
	public Object add(User user, AuthenticationToken auth, String context) {
		if(context == null || context.trim().length() < 5){
			log.warn("Error param ."+context);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		// {"title":"xx","uid":"","loc":"经度，维度","desc":"xxx"}
		JSONObject json = JSONObject.fromObject(context);
		Question question = new Question();
		question.setQid(StringUtils.randomNumberString(6));
		question.setCreateTime(System.currentTimeMillis());
		question.setDesc(json.getString("desc"));
		question.setLoc(json.getString("loc"));
		question.setState(State.Untreated.num());
		question.setTitle(json.getString("title"));
		question.setUid(user.getUid());
		question.setUrl(json.getString("url"));
		
		Boolean bool = getQuestionDAOImpl().save(question);
		if(bool == null){
			log.warn("DB connection exception ."+context);
			return StatusCode.SYSTEM_EXCEPTION.toJSON();
		}
		
		return StatusCode.SUCCESS.toJSON();
	}
	
	@Override
	public Object dealpic(User user, AuthenticationToken auth,
			List<File> fileData, List<String> fileDataFileName) {
		if(fileData == null || fileData.get(0) == null){
			log.warn("Error File .");
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		File file = fileData.get(0);
		String fileName = fileDataFileName.get(0);
		
		String suffix = fileName.substring(fileName.lastIndexOf("."));
		
		// 文件保存路径
		String rootPath = Constant.FILE_PATH;
		String path = null;
		if (rootPath.endsWith(File.separator)) {
			path = rootPath;// + Constants.FILE_PATH;
		} else {
			path = rootPath + File.separator;// + Constants.FILE_PATH;
		}
		
		path += fileder[1] + File.separator;;
		fileName = file_prefix[1]+DateUtils.getDetailedTime() + suffix;

		log.info("Save file,fileName=" + fileName);

		FileUtils.mkDirectory(path);
		FileUtils.upload(fileName, path, file); 
		
//		{"code":"200","msg":"xxx","data":{"url":"xxx"}}
		return StatusCode.SUCCESS.toDiyJson("data", "{\"url\":\""+fileName+"\"}");
	}

	@Override
	public Object deal(User user, AuthenticationToken auth, String context) {
		if(context == null || context.trim().length() < 5){
			log.warn("Error param ."+context);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		// {"title":"xx","uid":"","loc":"经度，维度","desc":"xxx"}
		JSONObject json = JSONObject.fromObject(context);
		QuestionLog question = new QuestionLog();
		question.setQid(StringUtils.randomNumberString(6));
		question.setCreateTime(System.currentTimeMillis());
		question.setDesc(json.getString("desc"));
		question.setLoc(json.getString("loc"));
		question.setState(State.Complete.num());
		question.setUid(user.getUid());
		question.setCreator(user.getUid()+"_"+user.getUserName());
		question.setUrl(json.getString("url"));
		
		Boolean bool = getQuestionDAOImpl().save(question);
		if(bool == null){
			log.warn("DB connection exception ."+context);
			return StatusCode.SYSTEM_EXCEPTION.toJSON();
		}
		
		log.debug("Save question success."+context);
		return StatusCode.SUCCESS.toJSON();
	}

	@Override
	public Object allocate(User user, AuthenticationToken auth, String context) {
		if(context == null || context.trim().length() < 5){
			log.warn("Error param ."+context);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		//{"qid":"","uid":"","desc":""}
		JSONObject json = JSONObject.fromObject(context);
		final String uid = json.getString("uid");
		final String qid = json.getString("qid");
		
		User u = getUserDAOImpl().queryByUid(uid);
		if(u == null){
			log.warn("DB not found user .uid="+uid);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		if(u.getType() != Type.STAFF){
			log.warn("User type not match.uid="+uid);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		Question ques = getQuestionDAOImpl().queryByQid(qid);
		if(ques == null){
			log.warn("DB not found user .uid="+uid);
			return StatusCode.PARAM_ERROR.toJSON();
		}
		
		int state = ques.getState();
		if(state == State.UNTREATED){
			QuestionLog ques_log = new QuestionLog();
			ques_log.setCreateTime(System.currentTimeMillis());
			ques_log.setDesc("");
			ques_log.setLoc("");
			ques_log.setQid(qid);
			ques_log.setState(State.Allocated.num());
			ques_log.setCreator(user.getUid()+"_"+user.getUserName());
			ques_log.setUid(uid);
			ques_log.setUrl("");
			
			boolean bool = getQuestionDAOImpl().update(qid, state,ques_log);
			if(bool){
				log.debug("allocate question success.qid="+qid);
				return StatusCode.SUCCESS.toJSON();
			}
			
			log.debug("allocate question failed.qid="+qid);
			return StatusCode.SYSTEM_EXCEPTION.toJSON();
		}
		
		log.warn("Unknow state.qid="+qid+",state="+state);
		return StatusCode.SUCCESS.toJSON();
	}

	@Override
	public Object download(User user, AuthenticationToken auth, String qid,String url) {
		if(url == null){
			log.warn("Params error."+url);
			return StatusCode.PARAM_ERROR;
		}
		
		// TODO 权限校验
		String path = null;
		if (rootPath.endsWith(File.separator)) {
			path = rootPath;// + Constants.FILE_PATH;
		} else {
			path = rootPath + File.separator;// + Constants.FILE_PATH;
		}
		
		//		file_prefix = {"officers_","staff_"};
		if(url.startsWith(file_prefix[0])){
			path += fileder[0] + File.separator;
			path += url;
			return path;
		}
		
		if(url.startsWith(file_prefix[1])){
			path += fileder[1] + File.separator;
			path += url;
			return path;
		}
		
		log.warn("Params error,unknow url,"+url);
		return StatusCode.PARAM_ERROR;
	}
	
}