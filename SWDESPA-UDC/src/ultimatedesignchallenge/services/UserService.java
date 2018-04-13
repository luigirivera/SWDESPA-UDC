package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.User;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.model.Client;

public class UserService {
	
	public List<User> getAll()
	{
		List<User> users = new ArrayList<User>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + User.TABLE;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				users.add(toUser(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] SELECT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[USER] SELECT FAILED");
			e.printStackTrace();
		}
		
		return users;
	}
	
	private User toUser(ResultSet rs) throws SQLException {
		User user = new User();
		
		user.setUSERid(rs.getInt(User.COL_USERID));
		user.setUsername(rs.getString(User.COL_USERNAME));
		user.setPassword(rs.getString(User.COL_PASSWORD));
		user.setFirstname(rs.getString(User.COL_FIRSTNAME));
		user.setLastname(rs.getString(User.COL_LASTNAME));
		
		return user;
	}
	
	public User getUser(String username, String password) {
		
		Connection cnt = CalendarDB.getConnection();
		
		User user = new User();
		
		System.out.println(username + "  " +password);
		
		String query = "SELECT * FROM " + User.TABLE + " WHERE " + User.COL_USERNAME + " = ? AND " + User.COL_PASSWORD + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				user = toUser(rs);

		}catch(SQLException e) {
			System.out.println("[USER] SELECT FAILED");
			e.printStackTrace();
		}
		
		return user;
	}
	
	public boolean ifGuest(int id)
	{
		boolean result = false;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + User.TABLE + " WHERE " + User.COL_USERID + " = ? AND " + User.COL_USERNAME + " IS NULL";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				result = true;
			
			ps.close();
			rs.close();
				
			System.out.println("[USER] CHECK GUEST SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[USER] CHECK GUEST FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean ifDoctor(int id) { // assuming this id is userid
		boolean result = false;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + User.TABLE + " WHERE " + User.COL_USERID + " IN (SELECT " + Doctor.COL_USERID + " FROM " + Doctor.TABLE + " WHERE " + Doctor.COL_USERID + "= ?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				result = true;
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] CHECK DOCTOR SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[USER] CHECK DOCTOR FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean ifSecretary(int id) { // assuming this id is userid
		boolean result = false;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Secretary.TABLE + " WHERE " + Secretary.COL_USERID + " IN (SELECT " + Secretary.COL_USERID + " FROM " + Secretary.TABLE + " WHERE " + Secretary.COL_USERID + "= ?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				result = true;
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] CHECK SECRETARY SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[USER] CHECK SECRETARY FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public boolean ifClient(int id) { // assuming this id is userid
		boolean result = false;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Client.TABLE + " WHERE " + Client.COL_USERID + " IN (SELECT " + Client.COL_USERID + " FROM " + Client.TABLE + " WHERE " + Client.COL_USERID + "= ?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				result = true;
			
			ps.close();
			rs.close();
			
			System.out.println("[USER] CHECK CLIENT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[USER] CHECK CLIENT FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
}
