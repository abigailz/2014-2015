package com.borya.util.prop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


abstract class AbstractProperty {

	static Logger log = Logger.getLogger(AbstractProperty.class);
	protected Properties prop = null;
	// "GBK"
	public static final String CHARSET = "UTF-8";
	
	protected AbstractProperty(){
		
	}
	/**
	 * 
	 * @param cl 类的原型
	 * @param file_relative_path 文件相对项目的路径 
	 * 		eg: /conf/conf.properties
	 * @return
	 */
	public static Properties load(Class<?> cl,String file_relative_path) {
		if(cl == null || file_relative_path == null){
			return null;
		}
    	try {
			InputStream in = cl.getResourceAsStream(file_relative_path);
			BufferedReader buffer = new BufferedReader(
					new InputStreamReader(in,CHARSET));  
			Properties prop = new Properties();
			prop.load(buffer);
	        in.close();
	        return prop;
		} catch (Exception e) {
			log.error("Load "+file_relative_path+" error:"+e.getMessage(),e);
		}
    	return null;
    }
	
	// 根据key读取value
	public String readValue(String key) {
		try {
			String value = prop.getProperty(key);
			return value;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 *  读取properties的全部信息
	 */
	public void readAllProperties() {
		try {
			Enumeration<?> en = prop.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = prop.getProperty(key);
				System.out.println(key + Property);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public String getString(String propertyName) {
    	return getProperty(propertyName);
    }
    
    public String getProperty(String propertyName){
    	try {
    		String val = prop.getProperty(propertyName.trim());
    		if(val == null){
    			return "";
    		}
			return new String(val.getBytes(),CHARSET).trim();
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
		}
    	return "";
    }
    
    protected Map<String,String> buildProperties(Properties properties){
		Map<String,String> map = new HashMap<String, String>();
		Enumeration<Object> en = properties.keys();   
		String name = null;
		String path = null;
		while(en.hasMoreElements()){   
            name = en.nextElement().toString();   
            path = properties.getProperty(name);   
            map.put(name.trim(), path.trim());   
        }  
        return map;
	}
    
    public static Properties readProperties(String name){
		InputStream in = null;
		try {
			in = new FileInputStream(name);
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (FileNotFoundException e) {
			log.error("load file[" + name + "] failed! " + e);
			return null;
		} catch (IOException e) {
			log.error(":load file[" + name + "] failed! " + e);
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}
	
	public static Properties readProperties(InputStream in){
		try {
			Properties properties = new Properties();
			properties.load(in);
			return properties;
		} catch (IOException e) {
			log.error(":load file failed! " + e);
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}
    /**
     * 获取配置文件的url类型的属性值,并以"\"结尾
     * @param propertyName
     * @return
     */
    public String getURL(String propertyName){
    	String str = getString(propertyName);
    	if(! str.endsWith(File.separator)){
    		str += File.separator;
    	}
    	return str;
    }
    
	public int getInteger(String key) {
		return Integer.valueOf(getProperty(key));
	}
	
	public float getFloat(String key){
		return Float.valueOf(getProperty(key));
	}

	public long getLong(String key){
		return Long.valueOf(getProperty(key));
	}
	
	public boolean getBoolean(String key){
		return Boolean.valueOf(getProperty(key));
	}
	
    /**
     * 格式化字符串
     * eg:
     * 		str = "我是{0},我来自{1},今年{2}岁";
     * 		format(str, "中国人","北京",22));
     * @param pattern
     * @param arguments
     * @return
     */
    public static String format(String pattern, Object ... arguments){
    	return MessageFormat.format(pattern, arguments);
    }
    
}
