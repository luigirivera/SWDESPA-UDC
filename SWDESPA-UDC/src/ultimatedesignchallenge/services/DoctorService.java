package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.model.User;

public class DoctorService {
	
	public List<Doctor> getAll()
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Secretary.TABLE + " INNER JOIN" + User.TABLE
				+ " ON " + User.COL_USERID + " = " + Secretary.COL_USERID;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				doctors.add(toDoctor(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[DOCTOR] SELECT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[DOCTOR] SELECT FAILED");
			e.printStackTrace();
		}
		
		return doctors;
	}
	
	public Doctor getDoctor(int id) {
		Doctor result = null;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Doctor.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Doctor.COL_USERID
				+ " WHERE " + Doctor.COL_USERID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = toDoctor(rs);
			
			ps.close();
			rs.close();
			
			System.out.println("[DOCTOR] GET DOCTOR SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[DOCTOR] GET DOCTOR FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Doctor getDoctor(String username, String password) {
		Doctor result = null;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Doctor.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Doctor.COL_USERID
				+ " WHERE " + User.COL_USERNAME + " = ?"
				+ " AND " + User.COL_PASSWORD + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				result = toDoctor(rs);
			
			ps.close();
			rs.close();
			
			System.out.println("[DOCTOR] GET DOCTOR SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[DOCTOR] GET DOCTOR FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	private Doctor toDoctor(ResultSet rs) throws SQLException {
		Doctor doctor = new Doctor();
		
		doctor.setId(rs.getInt(Doctor.COL_USERID));
		doctor.setDoctorId(rs.getInt(Doctor.COL_DOCTORID));
		doctor.setColor(rs.getString(Doctor.COL_COLOR));
		doctor.setUsername(rs.getString(Doctor.COL_USERNAME));
		doctor.setFirstname(rs.getString(Doctor.COL_FIRSTNAME));
		doctor.setLastname(rs.getString(Doctor.COL_LASTNAME));
		
		return doctor;
	}
}
