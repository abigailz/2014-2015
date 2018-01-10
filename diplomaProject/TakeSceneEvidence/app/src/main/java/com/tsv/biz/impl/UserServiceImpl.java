package com.tsv.biz.impl;


import com.tsv.biz.UserService;
import com.tsv.closure.Constant;
import com.tsv.model.db.User;
import com.tsv.util.FileUtils;
import com.tsv.util.code.StatusCode;
import com.tsv.util.http.HTTPUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl extends AbstrctService implements UserService{

	@Override
	public String login(final String context) {
		String url = Constant.HttpUrl.user_login_url+"?token="+getEncryptToken();
		String str = HTTPUtils.sendPost(url, context);
		// {"code":"200","msg":"success","data":{"tmsi":"1426404046718","type":"1"}}
		System.out.println(str);
		if(str != null && str.trim().length() > 5){
			JSONObject json = new JSONObject(str.trim());
			final String code = json.getString("code");
			switch(Integer.valueOf(code)){
			case StatusCode.PARAM_ERROR:
				// TODO 跳转到登录界面
//				Intent intent = new Intent(this,dest.activity);
//				intent.putExtra("result", "请求参数错误");
//				startActivity(intent);
				break;
			case StatusCode.SUCCESS:
				JSONObject data = json.getJSONObject("data");
				Constant.UserInfo.TMSI = data.getString("tmsi");
				Constant.UserInfo.TYPE = data.getString("type");
				break;
			default:
				System.out.println("Unknow param code."+code);
				break;
			}
		
		}		
		return "";
	}

	@Override
	public List<User> list(int type, int pageSize, int size) {
		String url = Constant.HttpUrl.user_list_url+"?token="+getEncryptToken()+"&type="+type+"&pageSize="+pageSize+"&size="+size;
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
			List<User> list = new ArrayList<User>();
			JSONObject obj;
			for(int i = 0,j= array.length();i<j;i++){
				obj = array.getJSONObject(i);
				
				User user = new User();
				user.setId(obj.getInt("id"));
				user.setUid(obj.getString("uid"));
				user.setUserName(obj.getString("userName"));
				user.setType(obj.getInt("type"));
				user.setCreateTime(obj.getLong("createTime"));
				
				list.add(user);
				continue;
			}
			
			return list;
		default:
			System.out.println("Unknow param code."+code);
			return null;
		}
	}

	@Override
	public void download(String qid, String url) {
		String url_str = Constant.HttpUrl.question_download_url+"?token="+getEncryptToken()+"&qid="+qid+"&url="+url;

		String str = FileUtils.downFile(url_str);
		
		
	}
	
	
}