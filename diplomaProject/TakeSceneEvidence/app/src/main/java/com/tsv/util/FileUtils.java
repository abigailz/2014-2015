package com.tsv.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileUtils {

	/**
	 * �ϴ��ļ���Server�ķ���
	 * 
	 * @param actionUrl
	 *            ����url eg :"http://192.168.0.71:8086/HelloWord/myForm";
	 * @param uploadFile
	 *            �ļ���·�� eg:"/sdcard/image.JPG";
	 * @return
	 */
	public static boolean uploadFile(String actionUrl, String uploadFile) {
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			String newName = uploadFile.substring(uploadFile.lastIndexOf("/"));

			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* ����Input,Output ��ʹ��Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); /* ���ô��͵�method=POST */
			con.setRequestMethod("POST"); /* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary); /*
																 * ����
																 * DataOutputStream
																 */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file1\";filename=\"" + newName + "\"" + end);
			ds.writeBytes(end);

			/* ȡ���ļ���FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* ����ÿ��д��1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			while ((length = fStream.read(buffer)) != -1) { /*
															 * ������д��
															 * DataOutputStream
															 * ��
															 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			fStream.close();
			ds.flush();

			/* ȡ��Response���� */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			} /* ��Response��ʾ��Dialog */
			ds.close();
			/* �ر�DataOutputStream */

			// showDialog("�ϴ��ɹ�" + b.toString().trim());
			return true;
		} catch (Exception e) {
			// showDialog("�ϴ�ʧ��" + e);
			return false;
		}
	}

	/***
	 * ����ͼƬ��SD��
	 * 
	 * @param bitmap
	 * @param path
	 *            �ļ�·��
	 * @param fileName
	 *            �ļ����
	 * @return
	 */
	public static String savePicToSdcard(Bitmap bitmap, String path,
			String fileName) {
		if (bitmap == null) {
			return "";
		}

		String filePath = path + fileName;
		OutputStream os = null;
		try {
			File destFile = new File(filePath);
			os = new FileOutputStream(destFile);
			bitmap.compress(CompressFormat.JPEG, 100, os);
			os.flush();
			os.close();
		} catch (IOException e) {
			filePath = "";
		}
		return filePath;
	}

	public static String downFile(String urlstr) {
		StringBuffer sb = new StringBuffer();
		BufferedReader buffer = null;
		URL url = null;
		String line = null;
		try {
			// 创建一个URL对象
			url = new URL(urlstr);
			// 根据URL对象创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 使用IO读取下载的文件数据
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
