package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Employee;
import com.revature.models.ConnectionUtil;

public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public List<Employee> getEmployees() {
		// TODO Auto-generated method stub
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM employee";
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
				employees.add(new Employee(empid, login, pass, full_name, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}

	@Override
	public List<Employee> getSubordinates(int id) {
		List<Employee> employees = new ArrayList<>();
		String sql = "SELECT * FROM employee E JOIN reportsTo M ON E.employeeID = M.employeeID WHERE M.managerID = "+id;
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
				employees.add(new Employee(empid, login, pass, full_name, email));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employees;
	}
	
	@Override
	public Employee getEmployeeById(int id) {
		// TODO Auto-generated method stub
		Employee emp = new Employee();
		String sql = "SELECT * FROM employee WHERE EmployeeID = " + id;
		
		try(
				Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
				){
			rs.next();
			emp.setEmployeeID(id);
			emp.setLogin(rs.getString("Login"));
			emp.setPass(rs.getString("Pass"));
			emp.setFullName(rs.getString("FullName"));
			emp.setEmail(rs.getString("Email"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

	@Override
	public int addEmployee(Employee e) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO employee(Login, Pass, FullName, Email) VALUES (?, ?, ?, ?)";

		try(
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				){
			ps.setString(1, e.getLogin());
			ps.setString(2, e.getPass());
			ps.setString(3, e.getFullName());
			ps.setString(4, e.getEmail());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException er) {
			er.printStackTrace();
		}
		return -1;
	}

	@Override
	public int editEmployee(Employee e) {
		// TODO Auto-generated method stub
		String sql = "UPDATE employee SET Login = ?, Pass = ?, FullName = ?, Email = ? WHERE EmployeeID = ?";

		try( 
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)
						){
			ps.setString(1, e.getLogin());
			ps.setString(2, e.getPass());
			ps.setString(3, e.getFullName());
			ps.setString(4, e.getEmail());
			ps.setInt(5, e.getEmployeeID());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException er) {
			er.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteEmployee(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM employee WHERE EmployeeID = "+id;

		try(
				Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement()){
			s.execute(sql);
			return s.getUpdateCount();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Employee getAuthEmployee(String login, String pass) {
		// TODO Auto-generated method stub
		Employee emp = new Employee();
		String sql = "SELECT * FROM employee WHERE Login = ? AND Pass = ?";
		
		try(
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				){
			ps.setString(1, login);
			ps.setString(2, pass);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if(rs.next()) {
				emp.setEmployeeID(rs.getInt("EmployeeID"));
				emp.setLogin(rs.getString("Login"));
				emp.setPass(rs.getString("Pass"));
				emp.setFullName(rs.getString("FullName"));
				emp.setEmail(rs.getString("Email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emp;
	}

}
