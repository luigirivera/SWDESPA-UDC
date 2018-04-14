package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.model.User;

public class SecretaryService {
	
	public Secretary getAll() {
		
		Secretary sec = new Secretary();
		
		Connection conn = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Secretary.TABLE + " INNER JOIN" + User.TABLE
				+ " ON " + User.COL_USERID + " = " + Secretary.COL_USERID;
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			//only one secretary eh
			if(rs.next()) {
				sec = toSecretary(rs);
			}
			
			ps.close();
			rs.close();
			
			System.out.println("[SECRETARY] SELECT SUCCESS!");
			
		}catch(SQLException e) {
			System.out.println("[SECRETARY] SELECT FAILED!");
			e.printStackTrace();
		}
		
		return sec;
	}
	
	public Secretary getSecretary(int id) {
		Secretary result = null;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Secretary.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Secretary.COL_USERID
				+ " WHERE " + Secretary.COL_USERID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = toSecretary(rs);
			
			ps.close();
			rs.close();
			
			System.out.println("[SECRETARY] GET SECRETARY SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[SECRETARY] GET SECRETARY FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Secretary getSecretary(String username, String password) {
		Secretary result = null;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Secretary.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Secretary.COL_USERID
				+ " WHERE " + Secretary.COL_USERNAME + " = ?"
				+ " AND " + Secretary.COL_PASSWORD + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				result = toSecretary(rs);
			
			ps.close();
			rs.close();
			
			System.out.println("[SECRETARY] GET SECRETARY SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[SECRETARY] GET SECRETARY FAILED");
			e.printStackTrace();
		}
		
		return result;
	}

	private Secretary toSecretary(ResultSet rs) throws SQLException {
		
		Secretary sec = new Secretary();
		
		sec.setId(rs.getInt(Secretary.COL_USERID));
		sec.setSecretaryId(rs.getInt(Secretary.COL_SECRETARYID));
		sec.setUsername(rs.getString(User.COL_USERNAME));
		sec.setFirstname(rs.getString(User.COL_FIRSTNAME));
		sec.setLastname(rs.getString(User.COL_LASTNAME));
		
		return sec;
	}
	
}
