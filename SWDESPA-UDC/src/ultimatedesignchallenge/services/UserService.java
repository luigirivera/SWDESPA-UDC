package ultimatedesignchallenge.services;

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
}
