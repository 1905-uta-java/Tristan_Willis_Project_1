package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.ConnectionUtil;
import com.revature.models.Employee;

public class SubordinateDaoImpl implements SubordinateDao {

	@Override
	public List<Employee> getSubordinates(int id) {
		// TODO Auto-generated method stub
		List<Employee> subs = new ArrayList<>();
		String sql = "SELECT * FROM employee E INNER JOIN reportsto R ON E.employeeID = R.employeeID WHERE R.managerID = " + id;
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int empid = rs.getInt("EmployeeID");
				String login = rs.getString("Login");
				String pass = rs.getString("Pass");
				String full_name = rs.getString("FullName");
				String email = rs.getString("Email");
				subs.add(new Employee(empid, login, pass, full_name, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subs;
	}

	@Override
	public List<Employee> getManagers(int id) {
		// TODO Auto-generated method stub
		List<Employee> subs = new ArrayList<>();
		String sql = "SELECT * FROM employee E INNER JOIN reportsto R ON E.employeeID = R.managerID WHERE R.employeeID = " + id;
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int empid = rs.getInt("EmployeeID");
				String login = rs.getString("Login");
				String pass = rs.getString("Pass");
				String full_name = rs.getString("FullName");
				String email = rs.getString("Email");
				subs.add(new Employee(empid, login, pass, full_name, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subs;
	}

	@Override
	public int addManagementPair(int emp, int man) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO reportsto(employeeID, managerID) VALUES (?, ?)";
		try(
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				){
			ps.setInt(1, emp);
			ps.setInt(2, man);
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException er) {
			er.printStackTrace();
		}
		return -1;
	}

	@Override
	public int removeManagementPair(int emp, int man) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM reportsto WHERE EmployeeID = ? AND ManagerID = ?";
		
		try(
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				){
			ps.setInt(1, emp);
			ps.setInt(2, man);
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException er) {
			er.printStackTrace();
		}
		return -1;
	}

}
