package com.borya.interceptor;
import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;


public class ContextListener implements ServletContextListener {
	
	Log log = LogFactory.getLog(getClass());
	
	public void contextDestroyed(ServletContextEvent event) {
		try {

		} catch (Exception e) {
			log.debug(e.getMessage(), e);
		}
	}

	public void contextInitialized(ServletContextEvent sc) {
		try {
			String path = this.getClass().getResource("/").getPath();
			File file = new File(path);
			path = file.getPath();
			String log4jdir = new File(file.getParent()).getParent();
			
			System.out.println("path=" + path);
			System.out.println("log4jdir="+log4jdir);
			
			System.setProperty("log4jdir", log4jdir);
			String log4jfile = path + File.separator +"log4j.xml";
			//  加载xml文件
			DOMConfigurator.configure(log4jfile);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
	}

}