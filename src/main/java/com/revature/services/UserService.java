package com.revature.services;

import java.util.List;

import com.revature.beans.Department;
import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.factory.Log;
@Log
public interface UserService {
	/*
	 * Other things needed
	 * 
	 * 
	 * Methods needed
	 * Update employee
	 * Create employee
	 * get supervisor
	 * get department
	 * get roles
	 * get id
	 * update roles
	 * update department
	 * update supervisor
	 * 
	 * 
	 */
	
	public boolean addUser(User newUser);
	public User getUser(int id);
	public List<User> getUsers();
	public boolean updateUser(User user);
	public int getSupervisor(int id);
	public boolean updateSupervisor(int userid, int superid);
	public Department getDepartment(int id);
	public int getDepartmentID(int userid);
	public Enum<?> getRole(int id);
	public int getID(User user);
	public User login(int id, String password);
	public boolean updateRole(int id, Role role);
	public void createUserTable();
	public List<Integer> getReviewers(int userID);
	public int getBencoForReview(int id);
}
