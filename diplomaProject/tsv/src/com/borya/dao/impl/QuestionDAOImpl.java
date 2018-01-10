package com.borya.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.borya.dao.Callable;
import com.borya.dao.QuestionDAO;
import com.borya.model.QuestionEntrty;
import com.borya.model.db.Question;
import com.borya.model.db.QuestionLog;
import com.borya.model.db.User;

public class QuestionDAOImpl extends AbstractBaseDAO implements QuestionDAO{

	Logger log = Logger.getLogger(getClass());

	@Override
	public Question queryByQid(final String qid) {
		return (Question)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select id,qid,title,uid,createTime,url,state,`desc`,loc from tb_question "
						+" where qid='"+qid+"'";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				if(rs != null && rs.next()){
					Question ques = new Question();
					ques.setId(rs.getInt("id"));
					ques.setQid(rs.getString("qid"));
					ques.setTitle(rs.getString("title"));
					ques.setUid(rs.getString("uid"));
					ques.setCreateTime(rs.getLong("createTime"));
					ques.setUrl(rs.getString("url"));
					ques.setState(rs.getInt("state"));
					ques.setDesc(rs.getString("desc"));
					ques.setLoc(rs.getString("loc"));
					
					return ques;
				}
				
				log.warn("Not found question.qid="+qid);
				return null;
			}
		});
	}

	@Override
	public Object[] query(final String uid,final int start,final int size) {
		return (Object[])execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select count(1) from tb_question where uid='"+uid+"' ";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] array = new Object[2];
				int count = 0;
				if(rs != null && rs.next()){
					count = rs.getInt(1);
					array[0] = count;
				}
				
				if(count == 0){
					array[1] = new ArrayList<Question>();
					return array;
				}
				
				close(rs,pst);
				
				sql = "select id,qid,title,uid,createTime,url,state,`desc`,loc from tb_question "
						+" where uid='"+uid+"'  order by createTime asc limit "+start+","+size;
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				List<Question> list = new ArrayList<Question>(size);
				if(rs != null){
					while(rs.next()){
						Question ques = new Question();
						ques.setId(rs.getInt("id"));
						ques.setQid(rs.getString("qid"));
						ques.setTitle(rs.getString("title"));
						ques.setUid(uid);
						ques.setCreateTime(rs.getLong("createTime"));
						ques.setUrl(rs.getString("url"));
						ques.setState(rs.getInt("state"));
						ques.setDesc(rs.getString("desc"));
						ques.setLoc(rs.getString("loc"));
						
						list.add(ques);
						continue;
					}
				}
				log.debug("DB query data size = "+list.size());
				
				array[1] = list;
				return array;
			}
		});
	}
	
	@Override
	public Object[] query(final String uid
			,final int start,final int size,final int state) {
		return (Object[])execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select count(1) from tb_question where uid='"+uid+"' and state='"+state+"'";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object[] array = new Object[2];
				int count = 0;
				if(rs != null && rs.next()){
					count = rs.getInt(1);
					array[0] = count;
				}
				
				if(count == 0){
					array[1] = new ArrayList<Question>();
					return array;
				}
				
				close(rs,pst);
				
				sql = "select id,qid,title,uid,createTime,url,state,`desc`,loc from tb_question "
						+" where uid='"+uid+"' and state='"+state+"'  order by createTime asc limit "+start+","+size;
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				List<Question> list = new ArrayList<Question>(size);
				if(rs != null){
					while(rs.next()){
						Question ques = new Question();
						ques.setId(rs.getInt("id"));
						ques.setQid(rs.getString("qid"));
						ques.setTitle(rs.getString("title"));
						ques.setUid(uid);
						ques.setCreateTime(rs.getLong("createTime"));
						ques.setUrl(rs.getString("url"));
						ques.setState(rs.getInt("state"));
						ques.setDesc(rs.getString("desc"));
						ques.setLoc(rs.getString("loc"));
						
						list.add(ques);
						continue;
					}
				}
				log.debug("DB query data size = "+list.size());
				
				array[1] = list;
				return array;
			}
		});
	}

	
	@Override
	public QuestionEntrty get(final String uid,final String qid) {
		return (QuestionEntrty)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select q.id,q.qid,q.title,q.uid,q.createTime,q.url,q.state,q.`desc`,q.loc "
					+" ,u.userName,u.type"
					+" from tb_question q "
					+" inner join tb_user u"
					+" on q.uid = u.uid"
					+" where q.uid='"+uid+"' and q.qid='"+qid+"'";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				QuestionEntrty entry = new QuestionEntrty();
				if(rs != null && rs.next()){
					Question ques = new Question();
					ques.setId(rs.getInt("id"));
					ques.setQid(rs.getString("qid"));
					ques.setTitle(rs.getString("title"));
					ques.setUid(uid);
					ques.setCreateTime(rs.getLong("createTime"));
					ques.setUrl(rs.getString("url"));
					ques.setState(rs.getInt("state"));
					ques.setDesc(rs.getString("desc"));
					ques.setLoc(rs.getString("loc"));
					
					User user = new User();
					user.setUid(uid);
					user.setUserName(rs.getString("userName"));
					user.setType(rs.getInt("type"));
					
					entry.setQuestion(ques);
					entry.setUser(user);
				}
				
				return entry;
			}
		});
	}
	
	@Override
	public Boolean save(final Question ques) {
		return (Boolean)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "insert into tb_question(qid,title,uid,createTime,url,state,`desc`,loc,closeTime)"
						+"values("
						+"'"+ques.getQid()+"'"
						+",'"+ques.getTitle()+"'"
						+",'"+ques.getUid()+"'"
						+",'"+ques.getCreateTime()+"'"
						+",'"+ques.getUrl()+"'"
						+",'"+ques.getState()+"'"
						+",'"+ques.getDesc()+"'"
						+",'"+ques.getLoc()+"'"
						+",'"+ques.getCloseTime()+"'"
						+")";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				int count = pst.executeUpdate();
				log.debug("[SQL] 影响行数:"+count);
				
				return true;
			}
		});
	}

	@Override
	public Boolean save(final QuestionLog ques) {
		return (Boolean)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "insert into tb_question_log(qid,uid,creator,createTime,url,state,`desc`,loc)"
						+"values("
						+"'"+ques.getQid()+"'"
						+",'"+ques.getUid()+"'"
						+",'"+ques.getCreator()+"'"
						+",'"+ques.getCreateTime()+"'"
						+",'"+ques.getUrl()+"'"
						+",'"+ques.getState()+"'"
						+",'"+ques.getDesc()+"'"
						+",'"+ques.getLoc()+"'"
						+")";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				int count = pst.executeUpdate();
				log.debug("[SQL] 影响行数:"+count);
				
				return true;
			}
		});
	}

	@Override
	public QuestionLog queryByQid(final String qid,final int state) {
		return (QuestionLog)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "select id,qid,uid,creator,createTime,url,state,`desc`,loc"
						+"from tb_question_log where qid='"+qid+"' and state='"+state+"' ";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				if(rs != null && rs.next()){
					QuestionLog ques = new QuestionLog();
					ques.setId(rs.getInt("id"));
					ques.setQid(rs.getString("qid"));
					ques.setUid(rs.getString("uid"));
					ques.setCreator(rs.getString("creator"));
					ques.setCreateTime(rs.getLong("createTime"));
					ques.setUrl(rs.getString("url"));
					ques.setState(rs.getInt("state"));
					ques.setDesc(rs.getString("desc"));
					ques.setLoc(rs.getString("loc"));
					
					return ques;
				}
				
				log.warn("Not found question.qid="+qid);
				return null;
			}
		});
	}

	@Override
	public Boolean update(final String qid,final int state,final QuestionLog ques) {
		List<String> sql_list = new ArrayList<String>();
		sql_list.add("update tb_question set state='"+state+"' where qid='"+qid+"'");
		
		String sql = "insert into tb_question_log(qid,uid,creator,createTime,url,state,`desc`,loc)"
				+"values("
				+"'"+ques.getQid()+"'"
				+",'"+ques.getUid()+"'"
				+",'"+ques.getCreator()+"'"
				+",'"+ques.getCreateTime()+"'"
				+",'"+ques.getUrl()+"'"
				+",'"+ques.getState()+"'"
				+",'"+ques.getDesc()+"'"
				+",'"+ques.getLoc()+"'"
				+")";
		sql_list.add(sql);
		return executeBatch(sql_list);
	}

	@Override
	public Boolean updateQuestionState(final String qid,final int state) {
		return (Boolean)execute(new Callable() {
			
			@Override
			public Object exec(Connection conn, PreparedStatement pst, ResultSet rs)
					throws SQLException {
				String sql = "update tb_question set "
					+"state='"+state+"',closeTime='"+System.currentTimeMillis()+"' where qid='"+qid+"'";
				log.debug("[SQL] "+sql);
				pst = conn.prepareStatement(sql);
				int count = pst.executeUpdate();
				log.debug("[SQL] 影响行数:"+count);
				
				return true;
			}
		});
	}
	
	
}