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
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.User;

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
	
	public List<Slot> getAll(int clientId)
	{
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "select SLOTid, start, end, " + Slot.COL_RECURRINGID + " from ("+ Slot.TABLE + " inner join " + Appointment.TABLE + " on " + Slot.COL_APPOINTMENTID + " = " + Appointment.COL_APPOINTMENTID +") " 
				+ "inner join " + Client.TABLE + " on " + Client.COL_CLIENTID + " = " + Appointment.COL_CLIENTID 
				+ " where USERid = ? ";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, clientId);
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
	
	public int getAppointmentID(Slot slot) {
		int temp = -1;
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT " + Slot.COL_APPOINTMENTID + " FROM " + Slot.TABLE + " WHERE " + Slot.COL_SLOTID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, slot.getId());
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				temp = rs.getInt(1);
			
			ps.close();
			rs.close();
			
			System.out.println("[SLOT] SELECT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[SLOT] SELECT FAILED");
			e.printStackTrace();
		}
		
		return temp;
	}
	
	public List<Slot> getTakenDoctor(Doctor doctor, LocalDate date) {
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE
				+ " WHERE DATE(" + Slot.COL_START + ") = ?"
				+ " AND " + Slot.COL_APPOINTMENTID + " IS NOT NULL"
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
	
	public List<Slot> getAllDoctorAppointments(LocalDate date) {
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE + " INNER JOIN " + Appointment.TABLE 
				+ " ON " + Slot.COL_APPOINTMENTID + " = " + Appointment.COL_APPOINTMENTID
				+ " WHERE DATE(" + Slot.COL_START + ") = ?";
		
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

	public List<Client> getAllDoctorAppointmentsClients(LocalDate date) {
		List<Client> clients = new ArrayList<Client>();
		
		Connection cnt = CalendarDB.getConnection();

		String query = "SELECT * FROM " + Slot.TABLE + " INNER JOIN " + Appointment.TABLE 
				+ " ON " + Slot.COL_APPOINTMENTID + " = " + Appointment.COL_APPOINTMENTID
				+ " INNER JOIN " + Client.TABLE + " ON " + Appointment.COL_CLIENTID + " = " + Client.COL_CLIENTID
				+ " INNER JOIN " + User.TABLE + " ON " + Client.COL_USERID + " = " + User.COL_USERID
				+ " WHERE DATE(" + Slot.COL_START + ") = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setDate(1, Date.valueOf(date));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				clients.add(toClient(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[SLOT] SELECT SUCCESS");	
		} catch(SQLException e) {
			System.out.println("[SLOT] SELECT FAILED");
			e.printStackTrace();
		}
		
		return clients;
	}
	
	public List<Doctor> getAllDoctorAppointmentsDoctors(LocalDate date) {
		List<Doctor> doctors = new ArrayList<Doctor>();
		
		Connection cnt = CalendarDB.getConnection();

		String query = "SELECT * FROM " + Slot.TABLE + " INNER JOIN " + Appointment.TABLE 
				+ " ON " + Slot.COL_APPOINTMENTID + " = " + Appointment.COL_APPOINTMENTID
				+ " INNER JOIN " + Doctor.TABLE + " ON " + Appointment.COL_DOCTORID + " = " + Doctor.COL_DOCTORID
				+ " INNER JOIN " + User.TABLE + " ON " + Doctor.COL_USERID + " = " + User.COL_USERID
				+ " WHERE DATE(" + Slot.COL_START + ") = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setDate(1, Date.valueOf(date));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				doctors.add(toDoctor(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[SLOT] SELECT SUCCESS");	
		} catch(SQLException e) {
			System.out.println("[SLOT] SELECT FAILED");
			e.printStackTrace();
		}
		
		return doctors;
	}
	
	public List<Slot> getAppointmentAgendaList(Doctor doctor, LocalDate date) {
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE + " INNER JOIN " + Appointment.TABLE 
				+ " ON " + Slot.COL_APPOINTMENTID + " = " + Appointment.COL_APPOINTMENTID
				+ " WHERE Appointment.DOCTORid = ?"
				+ " AND " + " DATE(" + Slot.COL_START + ") = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, doctor.getDoctorId());
			ps.setDate(2, Date.valueOf(date));
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
	
	public List<Slot> getAllAppointmentsJoinedSlots() {
		List<Slot> slots = new ArrayList<Slot>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE + " INNER JOIN " + Appointment.TABLE 
				+ " ON " + Slot.COL_APPOINTMENTID + " = " + Appointment.COL_APPOINTMENTID;
		
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
	
	public List<Client> getAppointmentClientsList(Doctor doctor, LocalDate date)
	{
		List<Client> clients = new ArrayList<Client>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Slot.TABLE + " INNER JOIN " + Appointment.TABLE 
				+ " ON " + Slot.COL_APPOINTMENTID + " = " + Appointment.COL_APPOINTMENTID
				+ " INNER JOIN " + Client.TABLE + " ON " + Appointment.COL_CLIENTID + " = " + Client.COL_CLIENTID
				+ " INNER JOIN " + User.TABLE + " ON " + Client.COL_USERID + " = " + User.COL_USERID
				+ " WHERE Appointment.DOCTORid = ?"
				+ " AND " + " DATE(" + Slot.COL_START + ") = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setInt(1, doctor.getDoctorId());
			ps.setDate(2, Date.valueOf(date));
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
	
	private Client toClient(ResultSet rs) throws SQLException {
		Client client = new Client();
		
		client.setFirstname(rs.getString(User.COL_FIRSTNAME));
		client.setLastname(rs.getString(User.COL_LASTNAME));
		
		return client;
	}
	
	private Doctor toDoctor(ResultSet rs) throws SQLException {
		Doctor doctor = new Doctor();
		
		doctor.setId(rs.getInt(Doctor.COL_USERID));
		doctor.setDoctorId(rs.getInt(Doctor.COL_DOCTORID));
		doctor.setColor(rs.getString(Doctor.COL_COLOR));
		doctor.setUsername(rs.getString(Doctor.COL_USERNAME));
		doctor.setFirstname(rs.getString(Doctor.COL_FIRSTNAME));
		doctor.setLastname(rs.getString(Doctor.COL_LASTNAME));
		
		return doctor;
	}
	
	private Slot toSlot(ResultSet rs) throws SQLException{
		Slot slot = new Slot();
		
		slot.setId(rs.getInt(Slot.COL_SLOTID));
		slot.setStart(rs.getTimestamp(Slot.COL_START).toLocalDateTime());
		slot.setEnd(rs.getTimestamp(Slot.COL_END).toLocalDateTime());
		if(rs.getInt(Slot.COL_RECURRINGID) == 0) {
			slot.setIsRecurring(false);
		}
		else{
			slot.setIsRecurring(true);
		}
		
		return slot;
	}
	
	public boolean addSlotC(Slot slot) {
		
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
			return false;
		}
		return true;
	}
	
	public boolean addSlotC(Slot slot, int recurringID) {
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "INSERT INTO " + Slot.TABLE + " VALUES (?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setNull(1, Types.NULL);
			ps.setTimestamp(2, Timestamp.valueOf(slot.getStart()));
			ps.setTimestamp(3, Timestamp.valueOf(slot.getEnd()));
			ps.setNull(4, Types.NULL);
			ps.setInt(5, recurringID);
			
			ps.executeUpdate();
			
			ps.close();
			// cnt.close();
			System.out.println("Success!");
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Error!");
			return false;
		}
		return true;
	}
	
	public Slot getId(Slot slot) {
		Connection cnt = CalendarDB.getConnection();
		
		Slot slotTemp = null;
		String query = "select * from " + Slot.TABLE + " where " + Slot.COL_START + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ps.setString(1, slot.getStart().toString());
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			slotTemp = (toSlot(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[SLOT] ID SELECTION SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[SLOT] ID SELECTION FAILED");
			e.printStackTrace();
		}
		
		return slotTemp;
	}
	
	
	
	public void deleteSlot(int SlotID) {
        //get connection
        Connection cnt = CalendarDB.getConnection();

        //create query
        String query = "DELETE FROM " + Slot.TABLE + " WHERE " + Slot.COL_SLOTID + " = ?";

        try {
            //create prepared statement
            PreparedStatement ps = cnt.prepareStatement(query);

            //prepare the values
            ps.setInt(1, SlotID);

            //execute the update
            ps.executeUpdate();

            //close resources
            ps.close();

            System.out.println("[SLOT] DELETE SUCCESS!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("[SLOT] DELETE FAILED!");
            e.printStackTrace();
        }

    }
	
	public boolean isFree(Slot slot) {
		boolean result = false;
		
        //get connection
        Connection cnt = CalendarDB.getConnection();

        //create query
        String query = "SELECT * FROM " + Slot.TABLE + " WHERE "
        		+ Slot.COL_SLOTID + " = ? AND " + Slot.COL_APPOINTMENTID + " IS NULL";

        try {
            //create prepared statement
            PreparedStatement ps = cnt.prepareStatement(query);

            //prepare the values
            ps.setInt(1, slot.getId());

            //execute the update
            ResultSet rs = ps.executeQuery();
            
            if (rs.next())
            		result = true;

            //close resources
            rs.close();
            ps.close();

            System.out.println("[SLOT] SELECT SUCCESS!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("[SLOT] SELECT FAILED!");
            e.printStackTrace();
        }
        
        return result;
	}
	
	public static void main(String[] args) {
		/*
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
 * 
		SlotService ss = new SlotService();
		Slot slot = new Slot();
		slot.setId(45);
		System.out.println(ss.getAppointmentID(slot));
		*/
	}
}