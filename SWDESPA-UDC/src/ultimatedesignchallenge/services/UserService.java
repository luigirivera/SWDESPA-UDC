package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import ultimatedesignchallenge.CalendarDB;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.User;

public class UserService {
	
	/*public List<User> getAll()
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
	}*/
public boolean addGuest(Client client) {
		
		Connection cnt = CalendarDB.getConnection();
		int tempo = -1;
		
		String query = "INSERT INTO " + User.TABLE + " VALUES (?, ?, ?, ?, ?)";
		String query2 = "SELECT LAST_INSERT_ID() FROM " + User.TABLE;
		String query3 = "INSERT INTO " + Client.TABLE + " VALUES(?, ?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs;
			ps.setNull(1, Types.NULL);
			ps.setNull(2, Types.NULL);
			ps.setNull(3, Types.NULL);
			ps.setString(4, client.getFirstname());
			ps.setString(5, client.getLastname());
			
			
			ps.executeUpdate();
			
			
			ps = cnt.prepareStatement(query2);
			
			rs = ps.executeQuery();
			
			if(rs.next())
				tempo = rs.getInt(1);
			
			
			ps = cnt.prepareStatement(query3);
			ps.setNull(1, Types.NULL);
			ps.setInt(2, tempo);
			

			ps.close();
			
			System.out.println("Success!");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error!");
			return false;
		}
		return true;
	}
}
