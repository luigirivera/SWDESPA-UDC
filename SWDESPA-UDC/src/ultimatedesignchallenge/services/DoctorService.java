package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.Doctor;

public class DoctorService {
	
	public List<Doctor> getAll()
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Doctor.TABLE;
		
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
	
	private Doctor toDoctor(ResultSet rs) throws SQLException {
		Doctor doctor = new Doctor();
		
		doctor.setDOCTORid(rs.getInt(Doctor.COL_DOCTORID));
		doctor.setColor(rs.getString(Doctor.COL_COLOR));
		doctor.setUSERid(rs.getInt(Doctor.COL_USERID));
		doctor.setUsername(rs.getString(Doctor.COL_USERNAME));
		doctor.setPassword(rs.getString(Doctor.COL_PASSWORD));
		doctor.setFirstname(rs.getString(Doctor.COL_FIRSTNAME));
		doctor.setLastname(rs.getString(Doctor.COL_LASTNAME));
		
		return doctor;
	}
}
