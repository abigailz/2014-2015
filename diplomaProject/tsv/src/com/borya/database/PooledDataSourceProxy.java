package com.borya.database;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

class PooledDataSourceProxy {

	private Logger log = Logger.getLogger(getClass());
//	private ComboPooledDataSource dataSource;
	
	private PooledDataSourceProxy(){
		log.debug("load file /conf/c3p0.properties");
		// 启动时 自动加载 c3p0.properties
//		dataSource = new ComboPooledDataSource("/conf/c3p0.properties");
	}

	public final static PooledDataSourceProxy getInstance(){
		return PooledDataSourceProxyContianer.instance;
	}
	
	public final DataSource getDataSource() {
//		return dataSource;
		return null;
	}

	private static class PooledDataSourceProxyContianer{
		static PooledDataSourceProxy instance = new PooledDataSourceProxy();
	}
	
}
