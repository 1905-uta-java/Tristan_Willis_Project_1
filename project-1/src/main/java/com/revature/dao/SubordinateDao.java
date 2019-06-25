package com.revature.dao;

import java.util.List;

import com.revature.models.Employee;

public interface SubordinateDao {
	public List<Employee> getSubordinates(int id);
	public List<Employee> getManagers(int id);
	public int addManagementPair(int emp, int man);
	public int removeManagementPair(int emp, int man);
}
