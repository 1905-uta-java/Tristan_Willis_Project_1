package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.ConnectionUtil;
import com.revature.models.Report;

public class ReportDaoImpl implements ReportDao {

	@Override
	public List<Report> getReports() {
		// TODO Auto-generated method stub
		List<Report> reps = new ArrayList<>();
		String sql = "SELECT * FROM reimbursement";
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int repid = rs.getInt("ReimbursementID");
				int empid = rs.getInt("EmployeeID");
				int amount = rs.getInt("Amount");
				String desc = rs.getString("Descript");
				int accepted = rs.getInt("Accepted");
				int resid = rs.getInt("ResolvedBy");
				reps.add(new Report(repid, empid, amount, accepted, resid, desc));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reps;
	}
	
	@Override
	public List<Report> getPendingReports(int emp_id){
		List<Report> reps = new ArrayList<>();
		String sql = "SELECT R.*, E.fullname AS Employee, E2.fullname AS Manager " + 
				"FROM reimbursement R " + 
				"LEFT JOIN Employee E ON R.EmployeeID = E.EmployeeID " + 
				"LEFT JOIN Employee E2 ON R.ResolvedBy = E2.EmployeeID " + 
				"WHERE R.EmployeeID = "+emp_id+ " AND R.resolvedBy IS NULL " +
				"UNION " + 
				"SELECT R2.*, E3.fullname AS Employee, E4.fullname AS Manager " + 
				"FROM reimbursement R2 INNER JOIN reportsTo M ON R2.EmployeeID = M.EmployeeID " + 
				"LEFT JOIN Employee E3 ON R2.EmployeeID = E3.EmployeeID " + 
				"LEFT JOIN Employee E4 ON R2.ResolvedBy = E4.EmployeeID " + 
				"WHERE M.ManagerID = "+emp_id+" AND R2.resolvedBy IS NULL";
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int repid = rs.getInt("ReimbursementID");
				int empid = rs.getInt("EmployeeID");
				int amount = rs.getInt("Amount");
				String desc = rs.getString("Descript");
				int accepted = rs.getInt("Accepted");
				int resid = rs.getInt("ResolvedBy");
				String emp_name = rs.getString("Employee");
				String man_name = rs.getString("Manager");
				reps.add(new Report(repid, empid, emp_name, amount, accepted, resid, man_name, desc));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reps;
	}
	
	@Override
	public List<Report> getReportsForEmp(int emp_id){
		List<Report> reps = new ArrayList<>();
		String sql = "SELECT R.*, E.fullname AS Employee, E2.fullname AS Manager " + 
				"FROM reimbursement R " + 
				"LEFT JOIN Employee E ON R.EmployeeID = E.EmployeeID " + 
				"LEFT JOIN Employee E2 ON R.ResolvedBy = E2.EmployeeID " + 
				"WHERE R.EmployeeID = "+emp_id;
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int repid = rs.getInt("ReimbursementID");
				int empid = rs.getInt("EmployeeID");
				int amount = rs.getInt("Amount");
				String desc = rs.getString("Descript");
				int accepted = rs.getInt("Accepted");
				int resid = rs.getInt("ResolvedBy");
				String emp_name = rs.getString("Employee");
				String man_name = rs.getString("Manager");
				reps.add(new Report(repid, empid, emp_name, amount, accepted, resid, man_name, desc));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reps;
	}
	
	@Override
	public List<Report> getPastReports(int emp_id){
		List<Report> reps = new ArrayList<>();
		String sql = "SELECT R.*, E.fullname AS Employee, E2.fullname AS Manager " + 
				"FROM reimbursement R " + 
				"LEFT JOIN Employee E ON R.EmployeeID = E.EmployeeID " + 
				"LEFT JOIN Employee E2 ON R.ResolvedBy = E2.EmployeeID " + 
				"WHERE R.EmployeeID = "+emp_id+ " AND R.resolvedBy IS NOT NULL " +
				"UNION " + 
				"SELECT R2.*, E3.fullname AS Employee, E4.fullname AS Manager " + 
				"FROM reimbursement R2 INNER JOIN reportsTo M ON R2.EmployeeID = M.EmployeeID " + 
				"LEFT JOIN Employee E3 ON R2.EmployeeID = E3.EmployeeID " + 
				"LEFT JOIN Employee E4 ON R2.ResolvedBy = E4.EmployeeID " + 
				"WHERE M.ManagerID = "+emp_id+" AND R2.resolvedBy IS NOT NULL";
		
		try( 
			Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
			){
			while(rs.next()) {
				int repid = rs.getInt("ReimbursementID");
				int empid = rs.getInt("EmployeeID");
				int amount = rs.getInt("Amount");
				String desc = rs.getString("Descript");
				int accepted = rs.getInt("Accepted");
				int resid = rs.getInt("ResolvedBy");
				String emp_name = rs.getString("Employee");
				String man_name = rs.getString("Manager");
				reps.add(new Report(repid, empid, emp_name, amount, accepted, resid, man_name, desc));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reps;
	}

	@Override
	public Report getReportById(int id) {
		// TODO Auto-generated method stub
		Report rep = new Report();
		String sql = "SELECT * FROM reimbursement WHERE reimbursementID = " + id;
		
		try(
				Connection con = ConnectionUtil.getConnection();
				Statement s = con.createStatement();
				ResultSet rs = s.executeQuery(sql);
				){
			rs.next();
			rep.setReportID(id);
			rep.setEmployeeID(rs.getInt("EmployeeID"));
			rep.setAmount(rs.getInt("Amount"));
			rep.setIsAccepted(rs.getInt("Accepted"));
			rep.setWhoResolved(rs.getInt("ResolvedBy"));
			rep.setDesc(rs.getString("Descript"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rep;
	}

	@Override
	public int addReport(Report r) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO reimbursement(EmployeeID, Amount, Accepted, ResolvedBy, Descript) VALUES (?, ?, ?, NULL, ?)";
		try(
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				){
			ps.setInt(1, r.getEmployeeID());
			ps.setInt(2, r.getAmount());
			ps.setInt(3, r.getIsAccepted());
			ps.setString(4, r.getDesc());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException er) {
			er.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public int editReport(Report r) {
		// TODO Auto-generated method stub
		String sql = "UPDATE reimbursement SET EmployeeID = ?, Amount = ?, Accepted = ?, ResolvedBy = ?, Descript = ? WHERE reimbursementID = ?";

		try( 
				Connection con = ConnectionUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)
						){
			ps.setInt(1, r.getEmployeeID());
			ps.setInt(2, r.getAmount());
			ps.setInt(3, r.getIsAccepted());
			if(r.getWhoResolved() == 0)
				ps.setNull(4, Types.INTEGER);
			else
				ps.setInt(4, r.getWhoResolved());
			ps.setString(5, r.getDesc());
			ps.setInt(6, r.getReportID());
			ps.execute();
			return ps.getUpdateCount();
		}
		catch(SQLException er) {
			er.printStackTrace();
		}
		return -1;
	}

	@Override
	public int deleteReport(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM reimbursement WHERE reimbursementID = " + id;

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

}
