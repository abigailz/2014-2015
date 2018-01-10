package com.borya.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;
import com.borya.dao.Callable;
import com.borya.database.DbConnectionManager;

abstract class AbstractBaseDAO {

	private Logger log = Logger.getLogger(getClass());
	
	protected Connection getConnection(){
		return DbConnectionManager.getConnection();
	}
	
	protected Object execute(Callable able) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			return able.exec(conn, pst, rs);
		}catch(Exception ex){
			log.error(ex.getMessage(),ex);
			return null;
		}finally{
			close(rs, pst, conn);
		}
	}
	
	protected boolean executeBatch(List<String> sql_list) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			for(String sql :sql_list){
				stmt.addBatch(sql);
			}
			stmt.executeBatch();
			conn.commit();
			return true;
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
			return false;
		} finally {
			close(stmt,conn);
		}
	}
	
	protected void close(Connection con) {
		DbConnectionManager.close(con);
	}
	
	protected void close(Statement stmt, Connection con) {
		DbConnectionManager.close(stmt, con);
	}
	
	protected void close(ResultSet rs,Statement stmt) {
		DbConnectionManager.close(rs, stmt);
	}
	
	protected void close(ResultSet rs,Statement stmt, Connection con) {
		DbConnectionManager.close(rs, stmt, con);
	}
	
}
