package com.tsv.biz.impl;

import android.graphics.Bitmap;
import android.util.Log;

import com.tsv.biz.QuestionService;
import com.tsv.closure.Constant;
import com.tsv.model.QuestionEntry;
import com.tsv.model.db.Question;
import com.tsv.model.db.User;
import com.tsv.thread.Callback;
import com.tsv.thread.TaskManager;
import com.tsv.util.FileUtils;
import com.tsv.util.code.StatusCode;
import com.tsv.util.http.FormFile;
import com.tsv.util.http.HTTPUtils;
import com.tsv.util.http.HttpRequester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl extends AbstrctService implements QuestionService {

	protected String tag = "Question";
	
	public boolean upload(final String imageFile){
		TaskManager.getInstance().execute(new Callback() {
			@Override
			public Object exec() {
				try {
					String imageType = imageFile.substring(imageFile.lastIndexOf("."));
					final FormFile formFile = new FormFile(System.currentTimeMillis() + imageType
							,new File(imageFile),"FileData","application/octet-stream");
					HttpRequester.post(Constant.HttpUrl.upload_url, null,
                            new FormFile[]{formFile});
				}
				catch (Exception e) {
					Log.d(tag, "upload exeception :" + e.getMessage());
				}
				return null;
			}
		});
		
		return true;
	}

	@Override
	public String savePic(Bitmap bitmap, String path, String fileName) {
		Log.d(path, "Save pic." + path + fileName);
		return FileUtils.savePicToSdcard(bitmap, path, fileName);
	}

	@Override
	public List<Question> list(int pageSize, int size, int state, long time) throws JSONException {
		String url = Constant.HttpUrl.question_list_url+"?token="+getEncryptToken()+"&pageSize="+pageSize+"&size="+size+"&state="+state+"&time="+0;
		String str = HTTPUtils.sendPost(url, null);
		System.out.println(str);
		if(str == null || str.trim().length() < 5){
			return null;
		}
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");
		switch(Integer.valueOf(code)){
		case StatusCode.PARAM_ERROR:
			// TODO
			return null;
		case StatusCode.SUCCESS:
			JSONObject data = json.getJSONObject("data");
			int total = data.getInt("total");
			if(total < 1){
				return null;
			}
			
			JSONArray array = data.getJSONArray("list");
			List<Question> list = new ArrayList<Question>();
			JSONObject obj;
			for(int i = 0,j= array.length();i<j;i++){
				obj = array.getJSONObject(i);
				Question ques = new Question();
				ques.setId(obj.getInt("id"));
				ques.setQid(obj.getString("qid"));
				ques.setTitle(obj.getString("title"));
				ques.setUid(obj.getString("uid"));
				ques.setCreateTime(obj.getLong("createTime"));
				ques.setUrl(obj.getString("url"));
				ques.setState(obj.getInt("state"));
				ques.setLoc(obj.getString("loc"));
				ques.setDesc(obj.getString("desc"));
				list.add(ques);
				continue;
			}
			
			return list;
		default:
			System.out.println("Unknow param code."+code);
			return null;
		}
	}

	@Override
	public QuestionEntry get(String qid) throws JSONException {
		String url = Constant.HttpUrl.question_get_url+"?token="+getEncryptToken()+"&qid="+qid;
		String str = HTTPUtils.sendPost(url, null);
		System.out.println(str);
		if(str == null || str.trim().length() < 5){
			return null;
		}		
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");

		switch(Integer.valueOf(code)){
		case StatusCode.PARAM_ERROR:
			// TODO
			return null;
		case StatusCode.SUCCESS:
			JSONObject data = json.getJSONObject("data");
			JSONObject user_obj = data.getJSONObject("user");
			JSONObject question_obj = data.getJSONObject("question");
			
			QuestionEntry entry = new QuestionEntry();
			
			Question ques = new Question();
			ques.setId(question_obj.getInt("id"));
			ques.setQid(question_obj.getString("qid"));
			ques.setTitle(question_obj.getString("title"));
			ques.setCreateTime(question_obj.getLong("createTime"));
			ques.setUrl(question_obj.getString("url"));
			ques.setState(question_obj.getInt("state"));
			ques.setLoc(question_obj.getString("loc"));
			ques.setDesc(question_obj.getString("desc"));
			
			User user = new User();
			user.setUserName(user_obj.getString("userName"));
			user.setType(user_obj.getInt("type"));
			
			entry.setQuestion(ques);
			entry.setUser(user);
			return entry;
		default:
			return null;
		}
	}

	@Override
	public String addpic(final String imageFile) throws JSONException {
		String imageType = imageFile.substring(imageFile.lastIndexOf("."));
		final FormFile formFile = new FormFile(System.currentTimeMillis() + imageType
				,new File(imageFile),"FileData","application/octet-stream");
		String url = Constant.HttpUrl.question_addpic_url+"?token="+getEncryptToken();
		String str;
		try {
			str = HttpRequester.post(url, null, new FormFile[] { formFile });
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			return null;
		}
		// {"code":"200","msg":"xxx","data":{"url":"xxx"}}
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");
		
		switch(Integer.valueOf(code)){
		case StatusCode.SUCCESS:
			JSONObject data = json.getJSONObject("data");
			return data.getString("url");
		case StatusCode.PARAM_ERROR:
			// TODO
			return null;
		default:
			return null;
		}
	}

	@Override
	public boolean add(String context) throws JSONException {
		String url = Constant.HttpUrl.question_add_url+"?token="+getEncryptToken();
		String str = HTTPUtils.sendPost(url, null);
		System.out.println(str);
		if(str == null || str.trim().length() < 5){
			System.out.println("Params error. "+context);
			return false;
		}		
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");
		switch(Integer.valueOf(code)){
		case StatusCode.SUCCESS:
			
			return true;
		default:
			return false;
		}
	}

	@Override
	public String dealpic(String imageFile) throws JSONException {
		String imageType = imageFile.substring(imageFile.lastIndexOf("."));
		final FormFile formFile = new FormFile(System.currentTimeMillis() + imageType
				,new File(imageFile),"FileData","application/octet-stream");
		String url = Constant.HttpUrl.question_dealpic_url+"?token="+getEncryptToken();
		String str;
		try {
			str = HttpRequester.post(url, null, new FormFile[] { formFile });
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			return null;
		}
		// {"code":"200","msg":"xxx","data":{"url":"xxx"}}
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");
		
		switch(Integer.valueOf(code)){
		case StatusCode.SUCCESS:
			JSONObject data = json.getJSONObject("data");
			return data.getString("url");
		case StatusCode.PARAM_ERROR:
			// TODO
			return null;
		default:
			return null;
		}
	}

	@Override
	public boolean deal(String context) throws JSONException {
		String url = Constant.HttpUrl.question_deal_url+"?token="+getEncryptToken();
		String str = HTTPUtils.sendPost(url, null);
		System.out.println(str);
		if(str == null || str.trim().length() < 5){
			System.out.println("Params error. "+context);
			return false;
		}		
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");
		switch(Integer.valueOf(code)){
		case StatusCode.SUCCESS:
			
			return true;
		default:
			return false;
		}
	}

	
	@Override
	public boolean allocate(String qid, String uid, String desc) throws JSONException {
		String context = "{\"qid\":\""+qid+"\",\"uid\":\""+uid+"\",\"desc\":\""+desc+"\"}";
		String url = Constant.HttpUrl.question_allocate_url+"?token="+getEncryptToken();
		String str = HTTPUtils.sendPost(url, context);
		// {"code":"200","msg":"xxx"}
		System.out.println(str);
		if(str == null || str.trim().length() < 5){
			return false;
		}		
		
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");
		switch(Integer.valueOf(code)){
		case StatusCode.SUCCESS:
			return true;
		default:
			System.out.println("Unknow param code."+code);
			return false;
		}
	}

	@Override
	public boolean close(String qid, String loc) throws JSONException {
		String context = "{\"qid\":\""+qid+"\",\"loc\":\""+loc+"\"}";
		String url = Constant.HttpUrl.question_close_url+"?token="+getEncryptToken();
		String str = HTTPUtils.sendPost(url, context);
		// {"code":"200","msg":"success","data":{"tmsi":"1426124776691","type":"1"}}
		JSONObject json = new JSONObject(str.trim());
		final String code = json.getString("code");
		switch(Integer.valueOf(code)){
		case StatusCode.SUCCESS:
			return true;
		default:
			System.out.println("Unknow param code."+code);
			return false;
		}
	}
	
	
}