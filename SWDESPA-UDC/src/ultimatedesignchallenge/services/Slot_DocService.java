package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ultimatedesignchallenge.CalendarDB;
import ultimatedesignchallenge.model.Slot_Doc;

public class Slot_DocService {

	public List<Slot_Doc> getAll()
	{
		List<Slot_Doc> docSlots = new ArrayList<Slot_Doc>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot_Doc.TABLE;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				docSlots.add(toSlot_Doc(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[SLOT_DOC] SELECT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[SLOT_DOC] SELECT FAILED");
			e.printStackTrace();
		}
		
		return docSlots;
	}
	
	public void addSlot_Doc(Slot_Doc docSlot) {
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "INSERT INTO " + Slot_Doc.TABLE + " VALUES (?, ?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, docSlot.getSlotId());
			ps.setInt(2, docSlot.getDoctorId());
			ps.executeUpdate();
			
			ps.close();
			// cnt.close();
			System.out.println("Success!");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error!");
		}
	}
	
	public void deleteSlot_Doc(int slotId, int doctorId) {
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "DELETE FROM " + Slot_Doc.TABLE + " WHERE "
				+ Slot_Doc.COL_SLOTID + " = ? AND "
				+ Slot_Doc.COL_DOCTORID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, slotId);
			ps.setInt(2, doctorId);
			ps.executeUpdate();
			
			ps.close();
			// cnt.close();
			System.out.println("Success!");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error!");
		}
	}
	
	private Slot_Doc toSlot_Doc(ResultSet rs) throws SQLException{
		Slot_Doc docSlot = new Slot_Doc();
		
		docSlot.setSlotId(rs.getInt(Slot_Doc.COL_SLOTID));
		docSlot.setDoctorId(rs.getInt(Slot_Doc.COL_DOCTORID));
		
		return docSlot;
	}
}
