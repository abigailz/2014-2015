package com.tsv.model;

import com.tsv.closure.Constant;
import com.tsv.util.MD5;

public class Token {

	private int type;
	private String userName;
	private String pwd;
	private String tmsi;
	private String model;/** 手机型号 + 版本号  **/ 
	private String version;
	
	public Token(String userName,String pwd,String tmsi){
		this.model = Constant.MobileInfo.Build_MODEL+""+Constant.MobileInfo.VERSION_RELEASE;
		this.version = Constant.AppInfo.version;
		this.type = Constant.AppInfo.type;
		this.userName = userName;
		this.pwd = pwd;
		this.tmsi = tmsi;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getTmsi() {
		return tmsi;
	}
	public void setTmsi(String tmsi) {
		this.tmsi = tmsi;
	}
	
	public final String toData(){
		StringBuilder sb = new StringBuilder(100);
		sb.append("1"+","+userName+","+MD5.MD5ForString(pwd)+","+tmsi+","+model+","+version);
		return sb.toString();
	}

	@Override
	public String toString() {
		return "Token [type=" + type + ", userName=" + userName + ", pwd="
				+ pwd + ", tmsi=" + tmsi + ", model=" + model + ", version="
				+ version + "]";
	}
	
}
