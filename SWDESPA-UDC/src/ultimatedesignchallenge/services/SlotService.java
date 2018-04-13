package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
	
	public List<Slot> getOn(LocalDate date) {
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE
				+ " WHERE DATE(" + Slot.COL_START + ") = ?"
				+ " ORDER BY " + Slot.COL_START;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setDate(1, Date.valueOf(date));
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
		
		slot.setId(rs.getInt(Slot.COL_SLOTID));
		slot.setStart(rs.getTimestamp(Slot.COL_START).toLocalDateTime());
		slot.setEnd(rs.getTimestamp(Slot.COL_END).toLocalDateTime());
		
		return slot;
	}
	
	public static void main(String[] args) {
		SlotService ss = new SlotService();
		System.out.println(ss.getOn(LocalDate.of(2018, 4, 14)));
	}
}
