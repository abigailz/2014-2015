package com.borya.authc;

import java.io.Serializable;

public interface AuthenticationToken extends Serializable {

	public String getTransformedTOKEN();
	// getPrincipal
	public String getUsername();

	public String getTMSI();
    /**
     * Returns the {@link #getPassword() password} byte array.
     *
     * @return the {@link #getPassword() password} byte array.
     * @see AuthenticationToken#getCredentials()
     */
	//getCredentials
    public Object getPassword();
}
