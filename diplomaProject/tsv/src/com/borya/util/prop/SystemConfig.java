package com.borya.util.prop;

public class SystemConfig extends AbstractProperty{
	
   
	private SystemConfig (){
    	prop = super.load(SystemConfig.class, "/conf/conf.properties");
    }
    
    public static final SystemConfig getInstance(){
    	return SystemConfigHolder.instance;
    }
    
    private static final class SystemConfigHolder{
    	 private static SystemConfig instance = new SystemConfig();
    }
}
