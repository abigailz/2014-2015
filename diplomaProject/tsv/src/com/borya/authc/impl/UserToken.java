package com.borya.authc.impl;

import com.borya.authc.HostAuthenticationToken;
import com.borya.authc.RememberMeAuthenticationToken;


public class UserToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String transformedTOKEN;
    private String username;
    private String tmsi;
    /**
     * The password, in char[] format
     */
    private String password;

    /**
     * Whether or not 'rememberMe' should be enabled for the corresponding login attempt;
     * default is <code>false</code>
     */
    private boolean rememberMe = false;

    /**
     * The location from where the login attempt occurs, or <code>null</code> if not known or explicitly
     * omitted.
     */
    private String host;
    
	@Override
	public boolean isRememberMe() {
		return rememberMe;
	}

	@Override
	public String getHost() {
		return host;
	}

	/***********************************************************************/
	
	// 手机类型,username,pwd,tmsi,手机型号组成，version
	public UserToken(String host,String decryptToken,String[] tokenArray){
		this.host = host;
		this.transformedTOKEN = decryptToken;
		this.username = tokenArray[1];
		this.password = tokenArray[2];
		this.tmsi = tokenArray[3];
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTMSI() {
		return tmsi;
	}

	public void setTMSI(String tmsi) {
		this.tmsi = tmsi;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String getTransformedTOKEN() {
		return transformedTOKEN;
	}
	
}
