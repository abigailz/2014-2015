
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class HTTPUtils {

	
	public static String sendPost(String url, String postdata) {
		return send(url, postdata,null);
	}
	/**
	 * 
	 * @param url
	 * @param postdata
	 * @return [0] cookieValue [1] value
	 */
	public static String send(String url, String postdata,Hashtable<String,String> headers) {
		InputStream is = null;
		BufferedReader in = null;
		String response = "";
		try {
			URL serverUrl = new URL(url);
			HttpURLConnection conn = 
					(HttpURLConnection) serverUrl.openConnection();
			conn.setRequestProperty("Content-type", "text/html");
			conn.setRequestProperty("token", "MD5");
			
			if(headers != null){
				for(Map.Entry<String, String> entry:headers.entrySet()){
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			
			if (postdata == null) {
				conn.setRequestMethod("GET");
				conn.setDoOutput(true);
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.getOutputStream().write(postdata.getBytes());
			}
			conn.connect();
			is = conn.getInputStream();

			in = new BufferedReader(new InputStreamReader(is));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			response = buffer.toString();
			is.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					in.close();
				}
				if (in != null) {
					is.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
			in = null;
			is = null;
		}
		return response;
	}

	public static String sendPostRequest(String url,
			List<NameValuePair> paramList) {
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url);
//			httpPost.setEntity(new UrlEncodedFormEntity(paramList));
			httpPost.setEntity(new UrlEncodedFormEntity(paramList,HTTP.UTF_8));    
			HttpResponse response = new DefaultHttpClient().execute(httpPost);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//httpPost.releaseConnection();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String url="http://api4.it.yuantel.net:10000/callconfservice/v1/startconf";
		String postdata = "{\"startconf_request\":{\"conftitle\":\"测试会议\",\"hostphone\":\"15008236758\",\"members\":[{\"mebid\":\"\",\"mode\":\"Talk\",\"name\":\"杨前程\",\"phoneno\":\"+8615982115540\"},{\"mebid\":\"\",\"mode\":\"Talk\",\"name\":\"小简\",\"phoneno\":\"+8617092800595\"},{\"mebid\":\"\",\"mode\":\"Talk\",\"name\":\"李婵\",\"phoneno\":\"+8613438932842\"}]},\"api_key\":\"dk1iaFJpelRCZXJJWm9pZTRFbUpEb3dUYnhjSWVSWk5GQ050YlYreUtDN09BbERLNytnemZ4VHhMNUhENU5UL1RmOUVPTXprOFUyeUNSRGFpWVBLZ3c9PQ\u003d\u003d\",\"api_version\":\"v1.0\",\"format\":\"json\",\"lang\":\"en\",\"serialno\":\"000000001402C912D3A98F987FF5C5C99A7D9858\",\"sessionid\":\"dc4a2d96-2820-42e4-a821-0f91e8a1e359\",\"sessionkey\":\"dc14be6d-58cb-4d5a-a9b0-3c7281ecef45\",\"sign\":\"176544C7F1EB8E7CD94B7AA2673F0A22\",\"time_stamp\":\"20141208160544343\"}";
		
		url = "http://192.168.10.162:8080/mnc/s/msg/send";
		postdata = "{\"users\":[\"user1\",\"user2\"],\"content\":\"待定\",\"expcount\":\"3\",\"validTime\":\"123456789\",\"companyId\":\"xxx\"}";

		url = "http://192.168.10.162:8080/bt/notify?token=MD5";
		postdata = "{\"format\":\"json\",\"cid\":\"wertyuio\",\"uid\":\"ogARWOM7bu7FXXd4QdIEM5_13881702448\",\"msgId\":\"rete\",\"osType\":\"2\",\"context\":{\"id\":\"xxx\"} }";
		
		
		url = "http://192.168.10.162:8080/mnc/c/user/bind?token=aFtruiq2avfnH72ck-Yy--VyF5JMjI66WuGCTxxKJdoEi5vfFNS_zA**&appId=ogARWOM7bu7FXXd4QdIEM5&uid=by0001";
		//postdata = "{\"cid\":\"ceshi app\",\"uid\":\"ogARWOM7bu7FXXd4QdIEM5_xxdf\",\"osType\":\"1\"}";
		postdata = "iV3ut5UjlcVBwJd7ChOrN-IQn6J-SoKR309Gs6Fk1vuZYrw1WtgECtq0aNXBnAbtCBkt86DSGOMbTzqbrACy6laeUXgpdCLR";
		
		url = "http://192.168.10.162:8080/mnc/c/user/pull?token=aFtruiq2avfnH72ck-Yy--VyF5JMjI66WuGCTxxKJdoEi5vfFNS_zA**&appId=ogARWOM7bu7FXXd4QdIEM5&uid=by0001&id=msgId&type=0";
		postdata = null;
		
		String str = sendPost(url, postdata);
		System.out.println(str);
	}
}
