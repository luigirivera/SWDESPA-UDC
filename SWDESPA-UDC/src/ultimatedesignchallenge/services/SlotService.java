package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.model.Slot;

public class SlotService {

	public List<Slot> getAll()
	{
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				slots.add(toSlot(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[SLOT] SELECT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[SLOT] SELECT FAILED");
			e.printStackTrace();
		}
		
		return slots;
	}
	
	private Slot toSlot(ResultSet rs) throws SQLException{
		Slot slot = new Slot();
		
		slot.setSLOTid(rs.getInt(Slot.COL_SLOTID));
		slot.setStart(rs.getDate(Slot.COL_START));
		slot.setEnd(rs.getDate(Slot.COL_END));
		slot.setAPPOINTMENTid(rs.getInt(Slot.COL_APPOINTMENTID));
		slot.setRECURRINGid(rs.getInt(Slot.COL_RECURRINGID));
		
		return slot;
	}
	
	
}
