package com.borya.biz;

import com.borya.authc.AuthenticationToken;
import com.borya.model.db.User;

public abstract interface UserService extends BaseService{

	Object login(User user,AuthenticationToken auth,String context);
	
	Object close(User user,AuthenticationToken auth,String context);
	
	void logout(User user,AuthenticationToken auth,String context);
	
	Object list(User user,AuthenticationToken auth,int type,int pageSize,int size);
}
