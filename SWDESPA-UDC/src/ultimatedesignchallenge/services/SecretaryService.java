package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.Secretary;

public class SecretaryService {
	
	public Secretary getSecretary() {
		
		Secretary sec = new Secretary();
		
		Connection conn = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Secretary.TABLE;
		
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

	private Secretary toSecretary(ResultSet rs) throws SQLException {
		
		Secretary sec = new Secretary();
		
		sec.setSECRETARYid(rs.getInt(Secretary.COL_SECRETARYID));
		
		return sec;
	}
	
}
