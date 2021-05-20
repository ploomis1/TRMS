package com.revature.data;

import java.util.List;

import com.revature.beans.User;
import com.revature.data.factory.Log;

/*
 * Update User
 * Get User
 * Add User
 * get Users
 * delete user?
 * 
 * 
 * 
 */
@Log
public interface UserDAO {
	
	public void createUserTable()throws Exception;
	public User getUser(int id)throws Exception;
	public void addUser(User user)throws Exception;
	public List<User> getUsers()throws Exception;
	public void updateUser(User user)throws Exception;
	public void deleteUser(User user)throws Exception;

}
