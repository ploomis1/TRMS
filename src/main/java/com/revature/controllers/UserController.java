package com.revature.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.factory.BeanFactory;
import com.revature.data.factory.Log;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;

import io.javalin.http.Context;
@Log
public class UserController {
	private static UserService us = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	private static final Logger log = LogManager.getLogger(UserController.class);
	/*
	 * Need to login
	 * Need to logout
	 * Need to create new users
	 * Need to delete new users?
	 * Need to update users
	 */

	public static void createUser(Context ctx) {//need this

		//maybe print out json format, then recieve it back?
		User newUser = new User();
		newUser.setName(ctx.formParam("name"));
		newUser.setId(Integer.valueOf(ctx.formParam("id"))); 
		newUser.setPassword(ctx.formParam("password")); 
		newUser.setRole(User.Role.valueOf(ctx.formParam("role")));
		newUser.setDepartmentid(Integer.valueOf(ctx.formParam("department")));
		newUser.setSupervisorid(Integer.valueOf(ctx.formParam("superid")));
		newUser.setPendingReim(0);
		newUser.setReimbursementTotalForYear(0);
		
		
		boolean added = us.addUser(newUser);
		if (added) {
			ctx.json(newUser);
			//log.trace("User registered Rest",u);
			ctx.status(201);
			return;
		}
		//log.trace("Something went wrong");
		ctx.status(500);
		
	}
	
	public static void getUsers(Context ctx) {
		ctx.json(us.getUsers());
	}
	public static void deleteUser(Context ctx) {////work in progress, maybe for future
		User u = ctx.sessionAttribute("User");
		if(u==null||!u.getRole().equals(Role.CEO)) {//maybe check if they are head?
			log.trace("User not logged in or not CEO");
			ctx.status(403);
			return;
		}
		//possibly just get id from front end, then in service call get user, then delete user
		User newUser = new User();
		//int id =Integer.valueOf(ctx.formParam("id"));
		newUser.setId(Integer.valueOf(ctx.formParam("id"))); 
		newUser.setRole(User.Role.valueOf(ctx.formParam("role")));
		newUser.setDepartmentid(Integer.valueOf(ctx.formParam("department")));
		newUser.setSupervisorid(Integer.valueOf(ctx.formParam("superid")));
		//boolean deleted = us.deleteUser(newUser); us.deleteUser(id);
	}
	public static void login(Context ctx) {// need this
		if (ctx.sessionAttribute("User") != null) {
			log.trace("User logged in");
			ctx.status(204);
			return;
		}
		int id = Integer.valueOf(ctx.formParam("id"));
		String password = ctx.formParam("password");
		User newUser = us.login(id, password);
		if (newUser != null) {
			log.trace("User logged in",newUser);
			ctx.sessionAttribute("User", newUser);
			ctx.json(newUser);
			ctx.status(201);
			return;
		}
	}
	public static void logout(Context ctx) {//need this
		ctx.req.getSession().invalidate();
	}
	public static void updateUserSup(Context ctx) {//future
		User u = ctx.sessionAttribute("User");
		if(u==null) {//maybe check if they are head?
			log.trace("User not logged in");
			ctx.status(403);
			return;
		}
		int id = Integer.valueOf(ctx.formParam("id"));
		int superid = Integer.valueOf(ctx.formParam("superid"));
		us.updateSupervisor(id, superid);//need to check if worked
		
	}
	public static void updateUserDeptAndSuper(Context ctx) {//future
		User u = ctx.sessionAttribute("User");
		if(u==null) {//maybe check if they are head?
			log.trace("User not logged in");
			ctx.status(403);
			return;
		}
		int id = Integer.valueOf(ctx.formParam("id"));
		int superid = Integer.valueOf(ctx.formParam("superid"));
		int deptid = Integer.valueOf(ctx.formParam("departmentid"));
		us.updateSupervisor(id, superid);//need to check if worked
		//need to update department
	}
	
	public static void updateUserRoleAndSuper(Context ctx) {//future
		User u = ctx.sessionAttribute("User");
		if(u==null) {//maybe check if they are head?
			log.trace("User not logged in");
			ctx.status(403);
			return;
		}
		int id = Integer.valueOf(ctx.formParam("id"));
		int superid = Integer.valueOf(ctx.formParam("superid"));
		//User.Role.valueOf(ctx.formParam("role"); get role
		us.updateSupervisor(id, superid);//need to check if worked
		//update role
	}
	public static void updateUserPassword(Context ctx) {//future
		User u = ctx.sessionAttribute("User");
		if(u==null) {//maybe check if they are head?
			log.trace("User not logged in");
			ctx.status(403);
			return;
		}
		String name = ctx.formParam("name");
		String password = ctx.formParam("password");
		int id = Integer.valueOf(ctx.pathParam("userid"));
		//update user
	}
	
}
