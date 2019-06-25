package com.revature.models;

import java.io.Serializable;

public class Report implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1598720767953105793L;
	private int reportID;
	private int employeeID;
	private String employeeName;
	private int amount;
	private String desc;
	private int isAccepted;
	private int whoResolved;
	private String resolvedName;
	public Report() {
		
	}
	public Report(int reportID, int employeeID, int amount, int isAccepted, int whoResolved, String desc) {
		this.reportID = reportID;
		this.employeeID = employeeID;
		this.amount = amount;
		this.isAccepted = isAccepted;
		this.whoResolved = whoResolved;
		this.desc = desc;
	}
	public Report(int reportID, int employeeID, String employeeName, int amount, int isAccepted, int whoResolved, String resolvedName, String desc) {
		this.reportID = reportID;
		this.employeeID = employeeID;
		this.amount = amount;
		this.isAccepted = isAccepted;
		this.whoResolved = whoResolved;
		this.desc = desc;
		this.employeeName = employeeName;
		this.resolvedName = resolvedName;
	}
	public int getReportID() {
		return reportID;
	}
	public void setReportID(int reportID) {
		this.reportID = reportID;
	}
	public int getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(int isAccepted) {
		this.isAccepted = isAccepted;
	}
	public int getWhoResolved() {
		return whoResolved;
	}
	public void setWhoResolved(int whoResolved) {
		this.whoResolved = whoResolved;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getResolvedName() {
		return resolvedName;
	}
	public void setResolvedName(String resolvedName) {
		this.resolvedName = resolvedName;
	}
}
