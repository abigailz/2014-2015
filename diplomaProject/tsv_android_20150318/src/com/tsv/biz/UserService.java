package com.tsv.biz;

import java.util.List;

import com.tsv.model.db.User;

public abstract interface UserService {

	String login(String context);
	
	List<User> list(int type,int pageSize,int size);
	
	void download(String qid,String url);
	
}
