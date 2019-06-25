package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.models.Employee;

public class EmplDelegate {
	private EmployeeDao empl = new EmployeeDaoImpl();
	
	public boolean createEmpl(String login, String pass, String name, String email) {
		Employee e = new Employee(0, login, pass, name, email);
		return (empl.addEmployee(e) == 1);
	}
	
	public boolean updateEmpl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(id);
		String username = request.getParameter("SetUsername");
		System.out.println(username);
		String pass = request.getParameter("SetPassword");
		String opass = request.getParameter("OldPassword");
		String fullName = request.getParameter("SetFullName");
		System.out.println(fullName);
		String email = request.getParameter("SetEmail");
		if(id != null && id.matches("^\\d+$")) {
			Employee e = empl.getEmployeeById(Integer.parseInt(id));
			if(e.getPass().equals(opass)) {
				if(username != null && username != "")
					e.setLogin(username);
				if(fullName != null && fullName != "")
					e.setFullName(fullName);
				e.setEmail(email);
				if(pass != null && pass != "") 
					e.setPass(pass);
				if(empl.editEmployee(e) == 1)
					return true;
			}
			else {
				System.out.println("Not correct password.");
				System.out.println(e.getPass() + " VS " + opass);
			}
		}
		return false;
	}
	
	public void getEmpls(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(id);
		
		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		
		if(id == null) {
			List<Employee> emps = empl.getEmployees();
			pw.write(om.writeValueAsString(emps));
			pw.close();
		}
		else if(id.matches("^\\d+$")) {
			Employee emp = empl.getEmployeeById(Integer.parseInt(id));
			if(emp == null) {
				response.sendError(404, "No employee with given ID");
			}
			else {
				pw.write(om.writeValueAsString(emp));
				pw.close();
			}
		}
		else {
			response.sendError(400, "Invalid ID");
		}
	}
	
	public void getSubs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(id);
		
		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		
		if(id.matches("^\\d+$")) {
			List<Employee> subs = empl.getSubordinates(Integer.parseInt(id));
			if(subs == null) {
				response.sendError(404, "No employee with given ID");
			}
			else {
				pw.write(om.writeValueAsString(subs));
				pw.close();
			}
		}
		else {
			response.sendError(400, "Invalid ID");
		}
	}
}
