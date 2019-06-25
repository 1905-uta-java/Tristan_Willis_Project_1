package com.revature.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.servlets.AuthDelegate;
import com.revature.servlets.EmplDelegate;
import com.revature.servlets.ReportDelegate;

/**
 * Servlet implementation class RequestHelper
 */
public class RequestHelper {
    
	AuthDelegate ad = new AuthDelegate();
	EmplDelegate ed = new EmplDelegate();
	ReportDelegate rd = new ReportDelegate();
	 /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestHelper() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		if(uri.startsWith("/api/")) {
			String token = request.getHeader("Authorization");
			if(!ad.isAuthorized(token)){
				response.sendRedirect(request.getContextPath() + "/static/index");
			}
			
			String which = uri.substring(5);
			switch(which) {
			case "emps":
				ed.getEmpls(request, response);
				break;
			case "subs":
				ed.getSubs(request, response);
				break;
			case "pendreps":
				rd.getPendReps(request, response);
				break;
			case "pastreps":
				rd.getPastReps(request, response);
				break;
			default:
				response.sendError(404, "Unsupported api.");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		switch(uri) {
		case "/login":
			String username = request.getParameter("LoginUsername");
			String password = request.getParameter("LoginPassword");
			String auth = ad.authenticate(username, password);
			if(auth == null) {
				response.setStatus(401); // 401 Unauthorized
			} 
			else {
				response.setStatus(200);
				response.setHeader("Authorization", auth);
			}
			break;
		case "/newuser":
			String login = request.getParameter("CreateUsername");
			String pass = request.getParameter("CreatePassword");
			String passc = request.getParameter("ConfirmPassword");
			String fullname = request.getParameter("SetFullName");
			String email = request.getParameter("SetEmail");
			if(pass.equals(passc)) {
				if(ed.createEmpl(login, passc, fullname, email)) {
					response.setStatus(200);
				}
				else {
					response.setStatus(400);
				}
			}
			else {
				response.setStatus(400);
			}
			break;
		case "/createReport":
			String token = request.getHeader("Authorization");
			if(!ad.isAuthorized(token)){
				response.sendRedirect("../static/index");
			}
			int id = Integer.parseInt(token.split(":")[0]);
			String input = request.getParameter("Amount");
			if(input.matches("^\\d+$")) {
				int amt = Integer.parseInt(input);
				String desc = request.getParameter("Descript");
				if(rd.createReport(id, amt, desc)) {
					response.setStatus(200);
				}
				else
					response.setStatus(400);
			}
			else
				response.setStatus(400);
			break;
		default:
			response.setStatus(404);
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		if(uri.startsWith("/api/")) {
			String token = request.getHeader("Authorization");
			if(!ad.isAuthorized(token)){
				response.sendRedirect("../static/index");
			}
			
			String which = uri.substring(5);
			switch(which) {
			case "updateEmp":
				if(ed.updateEmpl(request, response)) {
					String username = request.getParameter("SetUsername");
					String password = request.getParameter("SetPassword");
					String auth = ad.authenticate(username, password);
					if(auth == null) {
						response.setStatus(401);
					} 
					else {
						response.setStatus(200);
						response.setHeader("Authorization", auth);
					}
				}
				else {
					response.setStatus(400);
				}
				break;
			case "acceptDenyRep":
				rd.acceptDenyRep(request, response);
				break;
			default:
				response.sendError(404, "Unsupported api.");
			}
		}
		else {
			response.setStatus(404);
		}
	}
}
