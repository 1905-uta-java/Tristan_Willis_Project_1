package com.revature.dao;

import java.util.List;

import com.revature.models.Report;

public interface ReportDao {
	public List<Report> getReports();
	public List<Report> getPendingReports(int emp_id);
	public List<Report> getReportsForEmp(int emp_id);
	public List<Report> getPastReports(int emp_id);
	public Report getReportById(int id);
	public int addReport(Report r);
	public int editReport(Report r);
	public int deleteReport(int id);
}
