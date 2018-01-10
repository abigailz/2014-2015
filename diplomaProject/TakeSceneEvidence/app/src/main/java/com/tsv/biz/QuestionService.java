package com.tsv.biz;

import android.graphics.Bitmap;

import com.tsv.model.QuestionEntry;
import com.tsv.model.db.Question;

import org.json.JSONException;

import java.util.List;

public abstract interface QuestionService {

	boolean upload(String imageFile);
	
	String savePic(Bitmap bitmap, String path, String fileName);
	
	/**
	 * 获取问题列表 
	 * 状态（1：未处理，2：已分配，3：处理完成，4：已关闭）
	 * @param pageSize 页数 从1开始
	 * @param size 每页的条数
	 * @param state 问的状态
	 * @param time default :0
	 * @return
	 */
	List<Question> list(int pageSize, int size, int state, long time) throws JSONException;
	
	/**
	 * 获取单个信息
	 * @param qid
	 * @return
	 */
	QuestionEntry get(String qid) throws JSONException;
	
	/** 执勤人员提交问题 **/
	String addpic(String imageFile) throws JSONException;
	
	/***
	 * {"title":"xx","loc":"经度，维度","url":"xx","desc":"xxx"}
	 * @param context
	 * @return
	 */
	boolean add(String context) throws JSONException;
	
	/** 办事人员提交解决解决问题图片 **/
	String dealpic(String imageFile) throws JSONException;
	
	/***
	 * {"loc":"经度，维度","url":"xx","desc":"xxx"}
	 * @param context
	 * @return
	 */
	boolean deal(String context) throws JSONException;
	
	/** 分配人员分配问题 **/
	boolean allocate(String qid, String uid, String desc) throws JSONException;
	
	/** 执勤人员关闭问题 **/
	boolean close(String qid, String loc) throws JSONException;
	
}
