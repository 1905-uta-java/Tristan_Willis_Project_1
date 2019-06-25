package com.revature.dao;

import java.util.List;

import com.revature.models.Employee;

public interface EmployeeDao {
	public List<Employee> getEmployees();
	public List<Employee> getSubordinates(int id);
	public Employee getEmployeeById(int id);
	public int addEmployee(Employee e);
	public int editEmployee(Employee e);
	public int deleteEmployee(int id);
	public Employee getAuthEmployee(String login, String pass);
}
