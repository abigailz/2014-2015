package com.tsv.model.db;

/**
 * 问题实体
 * @author Administrator
 *
 */
public class Question {

	private int id;
	private String qid;
	private String title;
	private String uid;
	private long createTime;
	private long closeTime;
	private String url;
	private int state;
	private String loc;
	private String desc;
	
	public Question(){
		closeTime = 0L;
	}
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getCloseTime() {
		return closeTime;
	}
	public void setCloseTime(long closeTime) {
		this.closeTime = closeTime;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", qid=" + qid + ", title=" + title
				+ ", uid=" + uid + ", createTime=" + createTime
				+ ", closeTime=" + closeTime + ", url=" + url + ", state="
				+ state + ", loc=" + loc + ", desc=" + desc + "]";
	}
}
