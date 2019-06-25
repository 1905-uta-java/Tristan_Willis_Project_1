package com.revature.servlets;
import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.models.Employee;

public class AuthDelegate {
	
	private EmployeeDao empDao = new EmployeeDaoImpl(); 
	
	// used to process POST request to "/login" (RequestHelper processPost)
	public String authenticate(String user, String pass) {
		Employee e = empDao.getAuthEmployee(user, pass);
		System.out.println(e);
		if(e==null|| e.getLogin()==null) {
			return null;
		}
		return e.getEmployeeID()+":"+e.getFullName();
	}

	public boolean isAuthorized(String token) {
		if(token!= null && token.split(":").length == 2) {
			String id = token.split(":")[0];
			String name = token.split(":")[1];
			if(id.matches("^\\d+$")) {
				Employee e = empDao.getEmployeeById(Integer.parseInt(id));
				if(e != null && e.getFullName().equals(name)) {
					return true;
				}
				if(e!= null) {
					System.out.println("Emp name: " + e.getFullName());
					System.out.println("Auth name: " + name);
				}
				else {
					System.out.println("EMP IS NULL");
				}
			}
			else {
				System.out.println("Token doesn't mach regex.");
			}
		}
		return false;
	}
}
