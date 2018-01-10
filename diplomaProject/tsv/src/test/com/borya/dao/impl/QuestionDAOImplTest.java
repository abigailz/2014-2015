package test.com.borya.dao.impl;

import junit.framework.Assert;

import org.junit.Test;

import com.borya.dao.QuestionDAO;
import com.borya.dao.impl.QuestionDAOImpl;
import com.borya.model.QuestionEntrty;
import com.borya.model.db.Question;
import com.borya.model.db.QuestionLog;

public class QuestionDAOImplTest {

	private QuestionDAO questionDAOImpl = new QuestionDAOImpl();
	
	@Test
	public void test_query(){
		Object[] array = questionDAOImpl.query("00000",0,10);
		System.out.println(array[0]);
		Assert.assertNotNull(array);
	}
	
	@Test
	public void test_query_state(){
		Object[] array = questionDAOImpl.query("00000",0,10,1);
		System.out.println(array[0]);
		Assert.assertNotNull(array);
	}
	
	@Test
	public void test_get(){
		QuestionEntrty entry = questionDAOImpl.get("00000","qid");
		System.out.println(entry);
		Assert.assertNotNull(entry);
	}
	
	@Test
	public void test_save(){
		Question question = new Question();
		question.setCreateTime(System.currentTimeMillis());
		question.setDesc("desc");
		question.setLoc("23,34");
		question.setQid("test");
		question.setState(1);
		question.setTitle("title");
		question.setUid("123456");
		question.setUrl("");
		
		Boolean bool = questionDAOImpl.save(question);
		System.out.println(bool);
		Assert.assertTrue(bool);
	}
	
	@Test
	public void test_saveLog(){
		QuestionLog ques = new QuestionLog();
		ques.setDesc("desc");
		ques.setLoc("23,34");
		ques.setQid("test");
		ques.setState(1);
		ques.setUid("123456");
		ques.setUrl("");
		ques.setCreateTime(System.currentTimeMillis());
		ques.setCreator("creator");
		
		Boolean bool = questionDAOImpl.save(ques);
		System.out.println(bool);
		Assert.assertTrue(bool);
	}
}
