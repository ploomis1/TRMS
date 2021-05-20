package com.revature.beans;

import java.io.Serializable;

public class User implements Serializable, Comparable<User>{
	//private static final long serialVersionUID = 5858452743693988617L;
	public static enum Role {
		EMPLOYEE, SUPERVISOR, HEAD, CEO, BENCO
	}
	private String name;
	private int id;
	private String password;
	private int departmentid;
	private Role role;
	private double pendingReim;
	private double reimbursementTotalForYear;//maybe need more?
	private int supervisorid; //(may not need this because of department info)
	//also if CEO no supervisor
	public int compareTo(User arg0) {
		return 0;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	public double getPendingReim() {
		return pendingReim;
	}
	public void setPendingReim(double pendingReim) {
		this.pendingReim = pendingReim;
	}

	public double getReimbursementTotalForYear() {
		return reimbursementTotalForYear;
	}
	public void setReimbursementTotalForYear(double reimbursementTotalForYear) {
		this.reimbursementTotalForYear = reimbursementTotalForYear;
	}
	public int getSupervisorid() {
		return supervisorid;
	}
	public void setSupervisorid(int supervisorid) {
		this.supervisorid = supervisorid;
	}


}
