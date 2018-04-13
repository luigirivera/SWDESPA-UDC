package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.User;

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
				
			System.out.println("[USER] CHECK GUEST SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[USER] CHECK GUEST FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
}
