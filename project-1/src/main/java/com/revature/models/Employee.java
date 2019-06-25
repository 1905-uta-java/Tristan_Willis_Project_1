package com.revature.models;

import java.io.Serializable;

public class Employee implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5516418142911720696L;
	private int employeeID;
	private String login;
	private String pass;
	private String fullName;
	private String email;
	public Employee() {
		
	}
	public Employee(int id, String login, String pass, String fullName, String email) {
		this.employeeID = id;
		this.login = login;
		this.pass = pass;
		this.fullName = fullName;
		this.email = email;
	}
	
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
