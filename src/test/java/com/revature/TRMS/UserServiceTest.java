/**
 * 
 */
package com.revature.TRMS;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.beans.TRF;
import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.UserDAO;
import com.revature.data.UserDAOImpl;
import com.revature.services.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	public UserDAOImpl dao;
	public User user;
	public User superv;
	public User benco;
	public UserServiceImpl service;
	public List<User> users;
	
	@Before
	public void setUpUser() {
		this.service = new UserServiceImpl(mock(UserDAOImpl.class));
		this.dao = mock(UserDAOImpl.class);
		
		user=new User();
		user.setId(0);
		user.setDepartmentid(0);
		user.setName("Jack");
		user.setPassword("password");
		user.setPendingReim(0);
		user.setReimbursementTotalForYear(0.0);
		user.setRole(Role.EMPLOYEE);
		user.setSupervisorid(1);
		
		superv = new User();
		superv.setId(1);
		superv.setDepartmentid(0);
		superv.setName(null);
		superv.setPassword(null);
		superv.setPendingReim(0);
		superv.setReimbursementTotalForYear(0);
		superv.setRole(Role.HEAD);
		superv.setSupervisorid(2);
		
		
		benco = new User();
		benco.setId(2);
		benco.setDepartmentid(1);
		benco.setName(null);
		benco.setPassword(null);
		benco.setPendingReim(0);
		benco.setReimbursementTotalForYear(0);
		benco.setRole(Role.BENCO);
		benco.setSupervisorid(3);
		users= new ArrayList<User>();
		users.add(benco);
	}

	@After
	public void tearDown() {
		service=null;
		dao=null;
		user=null;
		superv=null;
		benco=null;
		users=null;
	}
	
	@Test
	public void addUserCallsDAO() throws Exception {
		when(dao.getUser(user.getId())).thenReturn(user);
		service.addUser(user);
		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(dao).addUser(captor.capture());
		final User test=captor.getValue();
		assertEquals("",user,test);
		
	}
	@Test
	public void updateUserCallsDAO() throws Exception {
		//when(dao.getUser(user.getId())).thenReturn(user);
		service.updateUser(user);
		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(dao).updateUser(captor.capture());
		final User test=captor.getValue();
		assertEquals("",user,test);
	}
	@Test
	public void getSupervisorReturnsSuperID() throws Exception {
		when(dao.getUser(user.getId())).thenReturn(user);
		int supervi=service.getSupervisor(user.getId());
//		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//		verify(dao).addUser(captor.capture());
//		final User test=captor.getValue();
		assertEquals("",superv.getId(),supervi);
	}
	@Test
	public void getReviewersretrunsListOfUsers() throws Exception{
		//when(dao.getUser(user.getId())).thenReturn(user);
		service.addUser(user);
		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		//verify(dao).addUser(captor.capture());
		final User test=captor.getValue();
		assertEquals("",user,test);
	}
	@Test
	public void getBencoForReviewReturnsBenco() throws Exception{
		when(dao.getUsers()).thenReturn(users);
		int test =service.getBencoForReview(user.getId());
//		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//		verify(dao).addUser(captor.capture());
//		final User test=captor.getValue();
		assertEquals("",benco.getId(),test);
	}
	@Test
	public void getRoleReturnsRole() throws Exception {
		when(dao.getUser(user.getId())).thenReturn(user);
	Enum test=	service.getRole(user.getId());
//		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//		verify(dao).updateUser(captor.capture());
//		final User test=captor.getValue();
		assertEquals("",user.getRole(),test);
	}
	@Test
	public void loginReturnsTrue() throws Exception {
		when(dao.getUser(user.getId())).thenReturn(user);
		User test =service.login(user.getId(),user.getPassword());
//		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
//		verify(dao).updateUser(captor.capture());
//		final User test=captor.getValue();
		assertEquals("",user,test);
	}
	@Test
	public void updateRoleUpdatesRole() throws Exception {
		when(dao.getUser(user.getId())).thenReturn(user);
		boolean tests=service.updateRole(user.getId(),Role.CEO);
		final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		verify(dao).updateUser(captor.capture());
		final User test=captor.getValue();
		assertEquals("",Role.CEO,test.getRole());
	}
	@Test
	public void getUserReturnsUser() throws Exception {
		when(dao.getUser(user.getId())).thenReturn(user);
		User test=service.getUser(user.getId());
		assertEquals("",user,test);
		
	}
	@Test
	public void getUsersReturnsUsers() throws Exception {
		when(dao.getUsers()).thenReturn(users);
		List<User> test=service.getUsers();
		assertEquals("",users,test);
		
		
	}

}
