package com.tsv.closure;

public class Constant {

	public static class AppInfo{
		public static int type = 1;
		public static String version = "v1.2.0";
	}
	
	public static class MobileInfo{
		public static String VERSION_RELEASE;
		public static String Build_MODEL;
	}
	
	public static class UserInfo{
		public static String USERNAME = "13881702448";
		public static String PWD = "PWD";
		public static String TMSI = "0";
		public static String TYPE = "1";
	}

	public static String KEY = "A3LL91HH";
	public static String PROTOCOL = "http://";
	public static int PORT = 8080;
	public static String HOST  = "192.168.10.162";

	public static class HttpUrl{
		public static String http_header = PROTOCOL + HOST + ":" + PORT;

		public static String upload_url = http_header + "/tsv/c/addpic";
		public static String user_login_url = http_header+"/tsv/c/user/login";
		public static String user_list_url = http_header+"/tsv/c/user/list";
		
		
		public static String question_list_url = http_header+"/tsv/c/question/list";
		public static String question_get_url = http_header+"/tsv/c/question/get";
		public static String question_add_url = http_header+"/tsv/c/question/add";
		public static String question_addpic_url = http_header+"/tsv/c/question/addpic";
		
		public static String question_deal_url = http_header+"/tsv/c/question/deal";
		public static String question_dealpic_url = http_header+"/tsv/c/question/dealpic";
		
		public static String question_allocate_url = http_header+"/tsv/c/question/allocate";
		
		public static String question_download_url = http_header+"/tsv/c/question/download";

		public static String question_close_url = http_header+"/tsv/c/question/close";
		
	}
	
}
