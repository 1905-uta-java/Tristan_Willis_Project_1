package com.revature.p1test;

import org.junit.Test;

import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.dao.ReportDao;
import com.revature.dao.ReportDaoImpl;
import com.revature.dao.SubordinateDao;
import com.revature.dao.SubordinateDaoImpl;
import com.revature.models.Employee;
import com.revature.models.Report;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReportTest {
	EmployeeDao ed = new EmployeeDaoImpl();
	ReportDao rd = new ReportDaoImpl();
	SubordinateDao sd = new SubordinateDaoImpl();
	
	@Test
	public void empDaoTest() {
		Employee e = new Employee(0, "UNITTEST", "UNITTEST", "UNITTEST", "UNIT@TEST.com");
		assertEquals(ed.addEmployee(e), 1);
		e = ed.getAuthEmployee("UNITTEST", "UNITTEST");
		assertEquals(ed.editEmployee(e), 1);
		assertEquals(e.getEmail(), "UNIT@TEST.com");
		assertEquals(ed.deleteEmployee(e.getEmployeeID()), 1);
		assertNotNull(ed.getEmployees());
		assertNotNull(ed.getEmployeeById(1));
		assertNotNull(ed.getSubordinates(1));
	}
	
	@Test
	public void repDaoTest() {
		Report r = new Report(0, 1, 100, 0, 0, "UNITTEST");
		assertEquals(rd.addReport(r), 1);
		List<Report> reps = rd.getReportsForEmp(1);
		r = reps.get(reps.size()-1);
		assertEquals(rd.editReport(r), 1);
		assertEquals(r.getAmount(), 100);
		assertEquals(rd.deleteReport(r.getReportID()), 1);
		assertNotNull(rd.getReports());
		assertNotNull(rd.getPastReports(1));
		assertNotNull(rd.getPendingReports(1));
		assertNotNull(rd.getReportById(1));
	}
	
	@Test
	public void subDaoTest() {
		assertEquals(sd.addManagementPair(4, 3), 1);
		assertEquals(sd.removeManagementPair(4, 3), 1);
		assertNotNull(sd.getManagers(2));
		assertNotNull(sd.getSubordinates(1));
	}
}
