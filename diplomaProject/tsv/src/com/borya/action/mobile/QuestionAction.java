package com.borya.action.mobile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.borya.authc.AuthenticationToken;
import com.borya.biz.QuestionService;
import com.borya.biz.impl.QuestionServiceImpl;
import com.borya.model.db.User;
import com.borya.util.code.StatusCode;

public class QuestionAction extends MobileBaseAction implements Preparable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(getClass());
	
	private QuestionService questionServiceImpl;
	
	// 文件上传
	private List<File> FileData;
	private List<String> FileDataFileName; 
	private List<String> FileDataContentType;
//	private String savePath;
	
	public QuestionAction(){
		questionServiceImpl = new QuestionServiceImpl();
	}
	
	public QuestionService getQuestionServiceImpl() {
		return questionServiceImpl;
	}
	public List<File> getFileData() {
		return FileData;
	}
	public void setFileData(List<File> filedata) {
		FileData = filedata;
	}
	public List<String> getFileDataFileName() {
		return FileDataFileName;
	}
	public void setFileDataFileName(List<String> filedataFileName) {
		FileDataFileName = filedataFileName;
	}
	public List<String> getFileDataContentType() {
		return FileDataContentType;
	}
	public void setFileDataContentType(List<String> filedataContentType) {
		FileDataContentType = filedataContentType;
	}


	/**************************************************************/
	
	public Object doPrepare(String host, String token) {
		return getQuestionServiceImpl().prepare(host, token);
	}

	public String list(){
		try{
			int pageSize = getInt("pageSize");
			int size = getInt("size");
			int state = getInt("state");
			long time = Long.valueOf(getReqParam("time"));
			log.info("RECV : ["+getHost()+"] pageSize="+pageSize+",size="+size+",state="+state);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().list(user, auth, pageSize, size, state,time);
			if(object instanceof String){
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}

	public String get(){
		try{
			String qid = getReqParam("qid");
			log.info("RECV : ["+getHost()+"] qid="+qid);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().get(user, auth, qid);
			if(object instanceof String){
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}

	/**
	 * 执勤人员提交问题
	 * @return
	 */
	public String addpic(){
		try{
			log.info("RECV : ["+getHost()+"] FileNames="+FileDataFileName);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().addpic(user, auth, FileData,FileDataFileName);
			if(object instanceof String){
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
	public String add(){
		try{
			String context = getContext();
			log.info("RECV : ["+getHost()+"] "+context);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().add(user, auth, context);
			if(object instanceof String){
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
	public String dealpic(){
		try{
			log.info("RECV : ["+getHost()+"] FileNames="+FileDataFileName);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().dealpic(user, auth, FileData,FileDataFileName);
			if(object instanceof String){
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
	
	public String deal(){
		try{
			String context = getContext();
			log.info("RECV : ["+getHost()+"] "+context);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().deal(user, auth, context);
			if(object instanceof String){
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
	/**
	 * 分配人员分配任务
	 * @return
	 */
	public String allocate(){
		try{
			String context = getContext();
			log.info("RECV : ["+getHost()+"] "+context);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().allocate(user, auth, context);
			if(object instanceof String){
				out((String)object);
				return null;
			}
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR, "参数错误");
		return null;
	}
	
	public String download(){
		try{
			String qid = getReqParam("qid");
			String url = getReqParam("url");
			log.info("RECV : ["+getHost()+"] qid="+qid+",url="+url);
			
			User user = getUser();
			AuthenticationToken auth = getAuthenticationToken();
			
			Object object = getQuestionServiceImpl().download(user, auth, qid,url);
			if(object instanceof StatusCode){
				out((StatusCode)object);
				return null;
			}
			if(object instanceof String){
				write((String)object,super.getResponse());
				return null;
			}
			return null;
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
		}
		
		out(StatusCode.PARAM_ERROR);
		return null;
	}
	
	private void write(String imgPath,HttpServletResponse response)
			throws IOException {
		InputStream in = null;
		try {
			File file = new File(imgPath);
			if (file.exists()) {
				response.setStatus(200);
			}
			// 一次读多个字节
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			in = new FileInputStream(file);
			ServletOutputStream out = response.getOutputStream();
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				out.write(tempbytes, 0, byteread);
			}
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
		} finally {
			if(in != null){
				in.close();
			}
		}
	}
}