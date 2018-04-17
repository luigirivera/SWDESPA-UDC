package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import ultimatedesignchallenge.CalendarDB;
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.User;

public class ClientService {

	public List<Client> getAll()
	{
		List<Client> clients = new ArrayList<Client>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Client.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Client.COL_USERID;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				clients.add(toClient(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[CLIENT] SELECT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[CLIENT] SELECT FAILED");
			e.printStackTrace();
		}
		
		return clients;
	}
	
	public Client getGuest(int id)
	{
		Client result = null;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Client.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Client.COL_USERID
				+ " WHERE " + User.COL_USERNAME + " IS NULL"
				+ " AND " + Client.COL_USERID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				result = toClient(rs);
			
			ps.close();
			rs.close();
				
			System.out.println("[CLIENT] GET GUEST SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[CLIENT] GET GUEST FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Client getClient(int id) { // assuming this id is userid
		Client result = null;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Client.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Client.COL_USERID
				+ " WHERE " + User.COL_USERNAME + " IS NOT NULL"
				+ " AND " + User.COL_USERID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				result = toClient(rs);
			
			ps.close();
			rs.close();
			
			System.out.println("[CLIENT] GET CLIENT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[CLIENT] GET CLIENT FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Client getClient(String username, String password) {
		Client result = null;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Client.TABLE + " INNER JOIN " + User.TABLE 
				+ " ON " + User.COL_USERID + " = " + Client.COL_USERID
				+ " WHERE " + User.COL_USERNAME + " = ?"
				+ " AND " + User.COL_PASSWORD + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				result = toClient(rs);
			
			ps.close();
			rs.close();
			
			System.out.println("[CLIENT] GET CLIENT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[CLIENT] GET CLIENT FAILED");
			e.printStackTrace();
		}
		
		return result;
	}
	
	private Client toClient(ResultSet rs) throws SQLException{
		Client client = new Client();
		
		client.setId(rs.getInt(User.COL_USERID));
		client.setClientId(rs.getInt(Client.COL_CLIENTID));
		client.setUsername(rs.getString(User.COL_USERNAME));
		client.setFirstname(rs.getString(User.COL_FIRSTNAME));
		client.setLastname(rs.getString(User.COL_LASTNAME));
		
		return client;
	}
}
