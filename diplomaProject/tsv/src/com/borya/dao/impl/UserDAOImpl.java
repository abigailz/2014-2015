package com.borya.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.borya.dao.Callable;
import com.borya.dao.UserDAO;
import com.borya.model.db.User;

public class UserDAOImpl extends AbstractBaseDAO implements UserDAO{

	Logger log = Logger.getLogger(getClass());
	
	@Override
	public User queryByName(final String name) {
		return (User)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select id,uid,type,pwd,createTime,tmsi from tb_user where userName='"+name+"'";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				if(rs != null && rs.next()){
					User info = new User();
					info.setId(rs.getInt("id"));
					info.setUid(rs.getString("uid"));
					info.setType(rs.getInt("type"));
					info.setUserName(name);
					info.setPwd(rs.getString("pwd"));
					info.setCreateTime(rs.getLong("createTime"));
					info.setTmsi(rs.getString("tmsi"));
					
					return info;
				}
				
				log.warn("DB no found user. userName="+name);
				return null;
			}
		});
	}

	@Override
	public User queryByUid(final String uid) {
		return (User)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select id,uid,type,pwd,createTime,tmsi,userName from tb_user where uid='"+uid+"'";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				if(rs != null && rs.next()){
					User info = new User();
					info.setId(rs.getInt("id"));
					info.setUid(uid);
					info.setType(rs.getInt("type"));
					info.setUserName(rs.getString("userName"));
					info.setPwd(rs.getString("pwd"));
					info.setCreateTime(rs.getLong("createTime"));
					info.setTmsi(rs.getString("tmsi"));
					
					return info;
				}
				
				log.warn("DB no found user. uid="+uid);
				return null;
			}
		});
	}



	@Override
	public Boolean saveTmsi(final String name,final String tmsi) {
		return (Boolean)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "update tb_user set tmsi = '"+tmsi+"' where userName='"+name+"'";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				int num = pst.executeUpdate();
				if(num != 1){
					log.warn("DB update "+name+",影响的数据栏: "+num);
				}
				log.debug("DB 影响的数据栏: "+num);
				return true;
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list(final int type,final int start,final int size) {
		return (List<User>)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select id,uid,userName,type,createTime,tmsi from tb_user "
					+" where type='"+type+"' limit "+start+","+size;
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				List<User> list = new ArrayList<User>(32);
				if(rs != null){
					while(rs.next()){
						User info = new User();
						info.setId(rs.getInt("id"));
						info.setUid(rs.getString("uid"));
						info.setType(rs.getInt("type"));
						info.setUserName(rs.getString("userName"));
						info.setCreateTime(rs.getLong("createTime"));
						info.setTmsi(rs.getString("tmsi"));
						info.setType(type);
						list.add(info);
					}
				}
				
				log.debug("DB found user(type= "+type+") size "+list.size());
				return list;
			}
		});
	}

}