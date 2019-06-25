package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.ReportDao;
import com.revature.dao.ReportDaoImpl;
import com.revature.models.Report;

public class ReportDelegate {
	ReportDao reps = new ReportDaoImpl();
	public boolean createReport(int empid, int amt, String desc) {
		Report r = new Report(0, empid, amt, 0, 0, desc);
		return (reps.addReport(r) == 1);
	}
	
	public void getPendReps(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String emp_id = request.getParameter("emp_id");
		
		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		
		if(id == null) {
			response.sendError(400, "No ID Supplied");
		}
		else if(id.matches("^\\d+$")) {
			if(emp_id == null) {
				List<Report> rep = reps.getPendingReports(Integer.parseInt(id));
				if(rep == null) {
					response.sendError(404, "No employee with given ID, which makes no sense.");
				}
				else {
					pw.write(om.writeValueAsString(rep));
					pw.close();
					response.setStatus(200);
				}
			}
			else if(emp_id.matches("^\\d+$")) {
				List<Report> rep = reps.getReportsForEmp(Integer.parseInt(emp_id));
				if(rep == null) {
					response.sendError(404, "No employee with given ID");
				}
				else {
					pw.write(om.writeValueAsString(rep));
					pw.close();
					response.setStatus(200);
				}
			}
		}
		else {
			response.sendError(400, "Invalid ID");
		}
	}
	
	public void getPastReps(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		ObjectMapper om = new ObjectMapper();
		PrintWriter pw = response.getWriter();
		
		if(id == null) {
			response.sendError(400, "No ID Supplied");
		}
		else if(id.matches("^\\d+$")) {
			List<Report> rep = reps.getPastReports(Integer.parseInt(id));
			if(rep == null) {
				response.sendError(404, "No employee with given ID, which makes no sense.");
			}
			else {
				pw.write(om.writeValueAsString(rep));
				pw.close();
				response.setStatus(200);
			}
		}
		else {
			response.sendError(400, "Invalid ID");
		}
	}
	
	public void acceptDenyRep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rep_id = request.getParameter("rep_id");
		String man_id = request.getParameter("emp_id");
		String accept = request.getParameter("accepted");
		if(rep_id.matches("^\\d+$") && man_id.matches("^\\d+$")) {
			int rid = Integer.parseInt(rep_id);
			int mid = Integer.parseInt(man_id);
			Report rep = reps.getReportById(rid);
			rep.setWhoResolved(mid);
			System.out.println(accept);
			if(accept.equals("TRUE")) {
				System.out.println("Accepted.");
				rep.setIsAccepted(1);
			}
			else {
				System.out.println("Denied.");
				rep.setIsAccepted(0);
			}
			if(reps.editReport(rep) == 1) {
				response.setStatus(200);
			}
			else {
				response.setStatus(400);
			}
		}
		else {
			response.sendError(400, "One of the IDs is invalid.");
		}
	}
}
