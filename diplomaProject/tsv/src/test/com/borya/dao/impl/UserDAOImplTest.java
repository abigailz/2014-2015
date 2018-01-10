package test.com.borya.dao.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.borya.dao.UserDAO;
import com.borya.dao.impl.UserDAOImpl;
import com.borya.model.db.User;

public class UserDAOImplTest {

	private UserDAO userDAOImpl = new UserDAOImpl();
	
	@Test
	public void test_queryByName() {
		User user = userDAOImpl.queryByName("13881702448");
		System.out.println(user);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void test_saveTmsi() {
		Boolean bool = userDAOImpl.saveTmsi("13881702448", "123445678");
		System.out.println(bool);
		Assert.assertTrue(bool);
	}
	
	@Test
	public void test_list() {
		List<User> list = userDAOImpl.list(2, 0, 10);
		System.out.println(list.size());
		Assert.assertNotNull(list);
	}
	
}
