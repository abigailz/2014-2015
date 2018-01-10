package com.tsv.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


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
			System.out.println(" HTTP SEND : URL=" + url+" ,data=" + postdata);
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
			System.out.println(" HTTP SEND : " + e.getMessage());
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
			httpPost.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
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
	
}
