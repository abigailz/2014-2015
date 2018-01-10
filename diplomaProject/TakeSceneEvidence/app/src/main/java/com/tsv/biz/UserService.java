package com.tsv.biz;

import com.tsv.model.db.User;

import java.util.List;

public abstract interface UserService {

	String login(String context);
	
	List<User> list(int type, int pageSize, int size);
	
	void download(String qid, String url);
	
}
