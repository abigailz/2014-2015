package com.borya.authc;


public interface RememberMeAuthenticationToken extends AuthenticationToken {

	 /**
     * Returns {@code true} if the submitting user wishes their identity (principal(s)) to be remembered
     * across sessions, {@code false} otherwise.
     *
     * @return {@code true} if the submitting user wishes their identity (principal(s)) to be remembered
     *         across sessions, {@code false} otherwise.
     */
    boolean isRememberMe();
    
}
