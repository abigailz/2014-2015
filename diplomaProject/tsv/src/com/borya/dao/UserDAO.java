package com.borya.dao;

import java.util.List;

import com.borya.model.db.User;

public abstract interface UserDAO {

	User queryByName(String name);
	
	User queryByUid(String uid);
	
	Boolean saveTmsi(String name,String tmsi);
	
	List<User> list(int type,int start,int size);
}
