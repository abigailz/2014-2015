import encrypt.EncryptUtil;
public class HttpTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String token = "1,13881702448,pwd,0,sanxin9001,v1.3.0";
		token = EncryptUtil.encrypt("A3LL91HH", token);
		System.out.println("token="+token);
		String url = "";
		String postdata = "";
		// 登录
		url="http://192.168.1.108:8080/tsv/c/user/login?token="+token;
		postdata = "{\"loc\":\"101,203\"}";
		
//		// 获取问题列表 
//		// 状态（1：未处理，2：已分配，3：处理完成，4：已关闭）
//		url = "http://192.168.1.108:8080/tsv/c/question/list?token="+token+"&pageSize=1&size=10&state=1&time=0";
//		postdata = null;
//		
//		// 获取某个问题的所有信息
//		url = "http://192.168.10.162:8080/tsv/c/question/get?token="+token+"&qid=qid2";
//		postdata = null;
//		
//		//
//		url = "http://192.168.10.162:8080/tsv/c/question/add?token="+token;
//		postdata = "{\"title\":\"test\",\"loc\":\"20,20\",\"url\":\"officers_xxx.jpg\",\"desc\":\"xxx\"}";
//		
//		
//		url = "http://192.168.10.162:8080/tsv/c/question/deal?token="+token;
//		postdata = "{\"title\":\"test\",\"loc\":\"20,20\",\"url\":\"staff_xxx.jpg\",\"desc\":\"xxx\"}";
//		
//		
//		url = "http://192.168.10.162:8080/tsv/c/user/list?token="+token+"&type=2&pageSize=1&size=10";
//		postdata = null;
//		
//		
//		url = "http://192.168.10.162:8080/tsv/c/question/allocate?token="+token;
//		postdata = "{\"qid\":\"038213\",\"uid\":\"00000\",}";
		
		String str = HTTPUtils.sendPost(url, postdata);
		System.out.println(str);

	}

}
