package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ultimatedesignchallenge.CalendarDB;
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Doctor;
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
	
	public List<Slot> getFree(LocalDate date) {
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE
				+ " WHERE DATE(" + Slot.COL_START + ") = ?"
				+ " AND " + Slot.COL_APPOINTMENTID + " IS NULL"
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
	
	public List<Slot> getFree(Doctor doctor, LocalDate date) {
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE
				+ " WHERE DATE(" + Slot.COL_START + ") = ?"
				+ " AND " + Slot.COL_APPOINTMENTID + " IS NULL"
				+ " AND (SELECT COUNT(*) FROM SLOT_DOC WHERE"
				+ " SLOT_DOC.SLOTid = " + Slot.COL_SLOTID
				+ " AND SLOT_DOC.DOCTORid = ?) = 1"
				+ " ORDER BY " + Slot.COL_START;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setDate(1, Date.valueOf(date));
			ps.setInt(2, doctor.getDoctorId());
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
	
	public List<Slot> getTaken(Appointment appointment){
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE
				+ " WHERE " + Slot.COL_APPOINTMENTID
				+ " = ?"
				+ " ORDER BY " + Slot.COL_START;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, appointment.getId());
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
	
	public void addSlotC(Slot slot) {
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "INSERT INTO " + Slot.TABLE + " VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setNull(1, Types.NULL);
			ps.setTimestamp(2, Timestamp.valueOf(slot.getStart()));
			ps.setTimestamp(3, Timestamp.valueOf(slot.getEnd()));
			ps.setNull(4, Types.NULL);
			ps.setNull(5, Types.NULL);
			
			ps.executeUpdate();
			
			ps.close();
			// cnt.close();
			System.out.println("Success!");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error!");
		}
	}
	
	/*public static void main(String[] args) {
		DoctorService ds = new DoctorService();
		SlotService ss = new SlotService();
		SlotBuilder sb = new SlotBuilder();
		LocalDate dtoday = LocalDate.now();
		LocalTime start = LocalTime.of(1, 0);
		LocalTime end = LocalTime.of(2, 0);
		Slot slot = new Slot();
		slot.setStart(LocalDateTime.of(dtoday, start));
		slot.setEnd(LocalDateTime.of(dtoday, end));
		ss.addSlotC(slot);
		System.out.println(":D");
		
		
//		System.out.println(ss.getAll());
//		System.out.println(ss.getFree(LocalDate.of(2018, 4, 14)));
//		System.out.println(ss.getFree(ds.getDoctor(2), LocalDate.of(2018, 4, 14)));
	}*/
}