package com.borya.dao;

import com.borya.model.QuestionEntrty;
import com.borya.model.db.Question;
import com.borya.model.db.QuestionLog;

public abstract interface QuestionDAO {

	Question queryByQid(String qid);
	
	Object[] query(String uid,int start,int size);
	
	Object[] query(String uid,int start,int size,int state);
	
	QuestionEntrty get(String uid,String qid);
	
	Boolean save(Question question);
	
	Boolean save(QuestionLog questionLog);
	
	QuestionLog queryByQid(String qid,int state);
	
	Boolean update(String qid,int state,QuestionLog ques_log);
	
	Boolean updateQuestionState(String qid,int state);
}
