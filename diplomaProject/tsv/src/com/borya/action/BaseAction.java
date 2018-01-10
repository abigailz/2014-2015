package com.borya.action;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public abstract class BaseAction extends ActionSupport implements Action,
		Serializable {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getLog(getClass());

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return request;
	}

	/**
	 * response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * session
	 * 
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * ServletActionContext.getRequest().setAttribute(attName, attValue);
	 * 
	 * @param attName
	 * @param attValue
	 */
	public void setReqAtt(String attName, Object attValue) {
		ServletActionContext.getRequest().setAttribute(attName, attValue);
	}

	/**
	 * put object to session scope
	 * ActionContext.getContext().getSession().put(attName, attValue);
	 * 
	 * @param attName
	 * @param attValue
	 */
	public void setSessAtt(String attName, Object attValue) {
		ActionContext.getContext().getSession().put(attName, attValue);
	}

	/**
	 * get object to request scope
	 * ServletActionContext.getRequest().getAttribute(attName);
	 * 
	 * @param attName
	 * @return
	 */
	public Object getReqAtt(String attName) {
		return ServletActionContext.getRequest().getAttribute(attName);
	}

	/**
	 * get object to session scope
	 * ActionContext.getContext().getSession().get(attName);
	 * 
	 * @param attName
	 * @return
	 */
	public Object getSessAtt(String attName) {
		return ActionContext.getContext().getSession().get(attName);
	}

	/**
	 * ServletActionContext.getRequest().getParameter(paramName)
	 * 
	 * @param paramName
	 * @return
	 */
	public String getReqParam(String paramName) {
		String value = null;
		value = getRequest().getParameter(paramName);
		try {
			value = new String(value.getBytes("iso8859-1"), "utf-8");
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return value;
	}

	protected int getInt(String paramName){
		return Integer.valueOf(getReqParam(paramName));
	}
	
	public String getHost() {
		return getRequest().getRemoteHost();
	}

	/**
	 * ServletActionContext.getRequest().getParameterValues(paramName)
	 * 
	 * @param paramName
	 * @return
	 */
	public String[] getReqParams(String paramName) {
		return ServletActionContext.getRequest().getParameterValues(paramName);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getPostData(HttpServletRequest request) {
		String line = "";
		Map map = request.getParameterMap();
		Set set = map.entrySet();
		Map.Entry[] entry = (Map.Entry[]) set.toArray(new Map.Entry[0]);
		for (int i = 0, k = entry.length; i < k; i++) {
			line += entry[i].getKey();
			String value[] = (String[]) entry[i].getValue();
			for (int j = 0, t = value.length; j < t; j++) {
				line += value[j];
			}
		}
		return line;
	}

	/**
	 * 获取二进制的报文体，并转换成字符串
	 */
	public String getContext() throws IOException {
		HttpServletRequest request = getRequest();

		// // HTTP 流长度
		// int size = request.getContentLength();
		byte[] buffer = new byte[1024 * 1024];

		ServletInputStream in = request.getInputStream();
		int length = in.read(buffer);
		String encode = request.getCharacterEncoding();

		if (length > 0) {
			byte[] data = new byte[length];
			if (data.length > 0) {
				System.arraycopy(buffer, 0, data, 0, length);
			}
			return new String(data, encode);
		}
		return getPostData(request);
	}

}