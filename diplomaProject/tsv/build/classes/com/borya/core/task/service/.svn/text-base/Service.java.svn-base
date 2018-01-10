package com.borya.core.task.service;


public abstract interface Service {

	void init();
	
	void execute();
	 /**
     * Releases any resources allocated by this service.  Please note that
     * this method might block as long as there are any sessions managed by
     * this service.
     */
    void dispose();
	
	 /**
     * Returns the handler which will handle all connections managed by this service.
     */
    Handler getHandler();

    /**
     * Sets the handler which will handle all connections managed by this service.
     */
    void setHandler(Handler handler);
    
}
