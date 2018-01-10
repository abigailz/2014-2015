package com.tsv.biz.impl;

import com.tsv.closure.Constant;
import com.tsv.model.Token;
import com.tsv.util.encrypt.EncryptUtil;

abstract class AbstrctService {

	protected String getToken(){
		Token token = new Token(Constant.UserInfo.USERNAME
				, Constant.UserInfo.PWD
				, Constant.UserInfo.TMSI);
		return token.toData();
	}
	
	
	protected String getEncryptToken(){
		String t = EncryptUtil.encrypt(Constant.KEY, getToken());
		return t;
	}
	
}
