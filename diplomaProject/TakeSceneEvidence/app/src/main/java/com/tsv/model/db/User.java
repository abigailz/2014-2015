package com.tsv.model.db;

/**
 * 用户实体类
 * @author Administrator
 *
 */
public class User {

	private int id;
	private String uid;
	private String tmsi;
	private String userName;
	private String pwd;
	private int type;
	private long createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getTmsi() {
		return tmsi;
	}
	public void setTmsi(String tmsi) {
		this.tmsi = tmsi;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", uid=" + uid + ", tmsi=" + tmsi
				+ ", userName=" + userName + ", pwd=" + pwd + ", type=" + type
				+ ", createTime=" + createTime + "]";
	}
	
}
