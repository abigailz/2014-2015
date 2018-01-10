package com.tsv.model.db;

public class QuestionLog {

	private int id;
	private String qid;
	private String uid;
	private String creator;
	private long createTime;
	private int state;
	private String loc;
	private String url;
	private String desc;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQid() {
		return qid;
	}
	public void setQid(String qid) {
		this.qid = qid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	@Override
	public String toString() {
		return "QuestionLog [id=" + id + ", qid=" + qid + ", uid=" + uid
				+ ", creator=" + creator + ", createTime=" + createTime
				+ ", state=" + state + ", loc=" + loc + ", url=" + url
				+ ", desc=" + desc + "]";
	}
}
