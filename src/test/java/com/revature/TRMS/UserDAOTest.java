/**
 * 
 */
package com.revature.TRMS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.datastax.oss.driver.api.core.CqlSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.UserDAO;
import com.revature.data.UserDAOImpl;
import com.revature.data.factory.BeanFactory;
import com.revature.utils.CassandraUtil;

import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;

/**
 * @author bigdaddy
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {
	public UserDAOImpl dao;
	public User user;
	public CassandraUtil cass;
	
	@Mock
	public CqlSession session;
	
	@Before
	public void setUp() {
		dao= new UserDAOImpl(mock(CqlSession.class));
		
		user=new User();
		user.setId(0);
		user.setDepartmentid(0);
		user.setName("Jack");
		user.setPassword("password");
		user.setPendingReim(0);
		user.setReimbursementTotalForYear(0.0);
		user.setRole(Role.EMPLOYEE);
		user.setSupervisorid(1);
	}
	
	@After
	public void tearDown() {
		dao=null;
		cass=null;
		session=null;
		user=null;
	}
	
	@Test
	public void addUserCallsUtil() throws Exception {
		dao.addUser(user);
		verify(cass,times(1)).getSession();
	}
	
	@Test
	public void getUserCallsUtil() throws Exception {
		dao.getUser(0);
		verify(cass,times(1)).getSession();
	}
	
	@Test
	public void getUsersCallsUtil() throws Exception {
		dao.getUsers();
		verify(cass,times(1)).getSession();
	}
	
	@Test
	public void updateUserCallsUtil() throws Exception {
		dao.updateUser(user);
		verify(cass,times(1)).getSession();
	}


}
