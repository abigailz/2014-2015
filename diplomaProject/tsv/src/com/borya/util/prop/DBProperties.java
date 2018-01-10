package com.borya.util.prop;

/**
 * DB 配置文件读取
 * @author Administrator
 *
 */
public class DBProperties extends AbstractProperty{
	
   
	private DBProperties (){
    	prop = super.load(DBProperties.class, "/conf/proxool.properties");
    }
    
    public static final DBProperties getInstance(){
    	return DBPropertiesHolder.instance;
    }
    
    private static final class DBPropertiesHolder{
    	 private static DBProperties instance = new DBProperties();
    }

}
