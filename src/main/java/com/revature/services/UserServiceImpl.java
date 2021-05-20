package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.Department;
import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.UserDAO;
import com.revature.data.UserDAOImpl;
import com.revature.data.factory.BeanFactory;
import com.revature.data.factory.Log;

@Log
public class UserServiceImpl implements UserService {
	private UserDAO ud;//= (UserDAO) BeanFactory.getFactory().get(UserDAO.class, UserDAOImpl.class);
	
	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	
	public UserServiceImpl() {
		ud=(UserDAO) BeanFactory.getFactory().get(UserDAO.class, UserDAOImpl.class);
	}
	public UserServiceImpl(UserDAOImpl udao) {
		ud=udao;
	}
	
	@Override
	public boolean addUser(User newUser) {
		//User u =getUser(newUser.getId());
		if(newUser==null) {
			//possibly create new id?
			return false;
		}
		//need to check if user exists
		//if not call DAO layer to add to database, catch exceptions
		try {
			ud.addUser(newUser);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	/*
	 * gets user that was updated in, maybe just gets field that were updated
	 * calls DAO to update user
	 * catch exceptions
	 * maybe return null
	 */
	@Override
	public boolean updateUser(User user) {
		try {
			ud.updateUser(user);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	/*
	 * takes in user id
	 * calls DAO to get user info
	 * returns superid
	 */
	@Override
	public int getSupervisor(int id) {
		try {
			User u = getUser(id);
			return u.getSupervisorid();
		}catch(Exception e) {
			return -1;
		}
		
		
	}
	/*
	 * 
	 */
	@Override
	public List<Integer> getReviewers(int userID) {
		List<Integer> reviewers = new ArrayList<Integer>();
		User use = getUser(userID);
		int superid = use.getSupervisorid();
		reviewers.add(superid);
		//check if super is CEO or Head, then skip to getting benco
		User u = getUser(superid);
		if(!u.getRole().equals(Role.HEAD)) {
		System.out.println("Head");
	}
		if(!u.getRole().equals(Role.CEO)&&!u.getRole().equals(Role.HEAD)) {
			 List<User> department=getUsers().stream().filter((t)-> t.getDepartmentid()==u.getDepartmentid()).collect(Collectors.toList());
			 for(User us:department) {
				 if(us.getRole().equals(Role.HEAD)) {
					 System.out.println(us.getId());
					 reviewers.add(us.getId());
				 }
			 }
		}
		reviewers.add(getBencoForReview(userID));
		
		return reviewers;
	}
	
	/*
	 * 
	 */
	@Override
	public int getBencoForReview(int id) {
		 Random rand = new Random();
	List<User> benco = getUsers().stream()
			.filter((t) -> t.getRole().equals(Role.BENCO))
			.collect(Collectors.toList());
	int random = rand.nextInt(benco.size());
	while(benco.get(random).getId()==id) {
		random = rand.nextInt(benco.size());
	}
		
		return benco.get(random).getId();
	}
	
	
	/*
	 * 
	 */
	@Override
	public boolean updateSupervisor(int userid, int superid) {
		try {
			User u = getUser(userid);
			u.setSupervisorid(superid);
			return updateUser(u);
		}catch(Exception e) {
			return false;
		}
	}
	/*
	 * 
	 */
	@Override
	public Department getDepartment(int id) {//maybe send back id
//		try {
//			User u = getUser(id);
//			return ds.getDepartment(u.getDepartmentid());
//		}catch (Exception e) {
//			return null;
//		}
		return null;
	}
	/*
	 * 
	 */
	@Override
	public int getDepartmentID(int userid) {
		try {
		User u = getUser(userid);
		return u.getDepartmentid();
	}catch (Exception e) {
		return -1;
	}
	}
	/*
	 * 
	 */
	@Override
	public Enum<?> getRole(int id) {
		try {
			User u = getUser(id);
			return u.getRole();
		}catch (Exception e) {
			return null;
		}
	}
	/*
	 * 
	 */
	@Override
	public int getID(User user) {
		// TODO Auto-generated method stub
		return 0;
	}
	/*
	 * 
	 */
	@Override
	public User login(int id, String password) {
		try {
			User u = getUser(id);
			if(u.getPassword().equals(password)) {
				return u;
			}
		}catch (Exception e) {
			return null;
		}

		return null;
	}
	/*
	 * 
	 */
	@Override
	public boolean updateRole(int id, Role role) {
		try {
			User u = getUser(id);
			if(!u.getRole().equals(role)) {
				u.setRole(role);
				return updateUser(u);
			}
		}catch (Exception e) {
			return false;
		}
		
		return false;
	}
	/*
	 * 
	 */
	@Override
	public User getUser(int id) {
		//returns user from DAO
		//catch exceptions
		try {
			return ud.getUser(id);
		}catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public void createUserTable() {
		try {
			ud.createUserTable();
		}catch (Exception e) {
			
		}
		
	}
	@Override
	public List<User> getUsers() {
		List<User> users= new ArrayList<User>();
		try {
			users = ud.getUsers();//maybe just return this?
		}catch (Exception e) {
			
		}
		return users;
	}



}
