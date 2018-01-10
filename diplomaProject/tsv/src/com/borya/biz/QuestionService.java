package com.borya.biz;

import java.io.File;
import java.util.List;

import com.borya.authc.AuthenticationToken;
import com.borya.model.db.User;

public abstract interface QuestionService extends BaseService{

	Object list(User user,AuthenticationToken auth,int pageSize,int size,int state,long lasttime);

	Object get(User user,AuthenticationToken auth,String qid);
	
	/*** 执勤人员提交问题文件上传 ***/
	Object addpic(User user,AuthenticationToken auth,
			List<File> fileData,List<String> fileDataFileName);
	
	Object add(User user,AuthenticationToken auth,String context);
	
	
	Object dealpic(User user,AuthenticationToken auth,
			List<File> fileData,List<String> fileDataFileName);
	
	Object deal(User user,AuthenticationToken auth,String context);

	Object allocate(User user,AuthenticationToken auth,String context);
	
	Object download(User user,AuthenticationToken auth,String qid,String url);
}
