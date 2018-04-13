package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.Client;

public class ClientService {

	public List<Client> getAll()
	{
		List<Client> clients = new ArrayList<Client>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Client.TABLE;
		
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
	
	private Client toClient(ResultSet rs) throws SQLException{
		Client client = new Client();
		
		client.setCLIENTid(rs.getInt(Client.COL_CLIENTID));
		client.setUSERid(rs.getInt(Client.COL_USERID));
		client.setUsername(rs.getString(Client.COL_USERNAME));
		client.setPassword(rs.getString(Client.COL_PASSWORD));
		client.setFirstname(rs.getString(Client.COL_FIRSTNAME));
		client.setLastname(rs.getString(Client.COL_LASTNAME));
		
		return client;
	}
}
