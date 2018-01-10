package com.borya.action.mobile;

import java.io.File;

import com.opensymphony.xwork2.ActionSupport;

public class BannerAction extends ActionSupport{//extends WebBaseAction implements Preparable{
	private static final long serialVersionUID = 1L;
	
	
	private File Filedata;
	private String FiledataFileName; 
	private String FiledataContentType;

	public File getFiledata() {
		return Filedata;
	}
	public void setFiledata(File filedata) {
		Filedata = filedata;
	}
	public String getFiledataFileName() {
		return FiledataFileName;
	}
	public void setFiledataFileName(String filedataFileName) {
		FiledataFileName = filedataFileName;
	}
	public String getFiledataContentType() {
		return FiledataContentType;
	}
	public void setFiledataContentType(String filedataContentType) {
		FiledataContentType = filedataContentType;
	}

	public String saveImage(){
		try {
			System.out.println(Filedata + ":" + FiledataFileName + ":" + FiledataContentType);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return null;
	}
	

}
