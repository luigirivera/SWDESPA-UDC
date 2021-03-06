package ultimatedesignchallenge.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ultimatedesignchallenge.CalendarDB;
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Slot;

public class AppointmentService {
	private ClientService csv;
	private DoctorService dsv;
	private SlotService ssv;
	
	public AppointmentService() {
		this.csv = new ClientService();
		this.dsv = new DoctorService();
		this.ssv = new SlotService();
	}
	
	public List<Appointment> getAll()
	{
		List<Appointment> appointments = new ArrayList<Appointment>();
		
		Connection cnt = CalendarDB.getConnection();
		
		String query = "SELECT * FROM " + Appointment.TABLE;
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				appointments.add(toAppointment(rs));
			
			ps.close();
			rs.close();
			
			System.out.println("[APPOINTMENT] SELECT SUCCESS");	
		}catch(SQLException e) {
			System.out.println("[APPOINTMENT] SELECT FAILED");
			e.printStackTrace();
		}
		
		return appointments;
	}
	
	private Appointment toAppointment(ResultSet rs) throws SQLException {
		Appointment appointment = new Appointment();
		
		appointment.setId(rs.getInt(Appointment.COL_APPOINTMENTID));
		appointment.setDoctor(dsv.getDoctor(rs.getInt(Appointment.COL_DOCTORID)));
		appointment.setClient(csv.getClient(rs.getInt(Appointment.COL_CLIENTID)));
		appointment.setSlots(ssv.getTaken(appointment));
		
		return appointment;
	}
	
	public void addAppointment(Appointment appointment)
	{
		Connection cnt = CalendarDB.getConnection();
		String query = "INSERT INTO " + Appointment.TABLE + " VALUES(?,?,?,?)";
		String query2 = "SELECT LAST_INSERT_ID() FROM " + Appointment.TABLE;
		String query3 = "UPDATE " + Slot.TABLE
				+ " SET " + Slot.COL_APPOINTMENTID + " = ? "
				+ " WHERE " + Slot.COL_SLOTID + " = ? "
				+ " AND " + Slot.COL_APPOINTMENTID + " IS NULL";
		
		try {
			cnt.setAutoCommit(false);
			
			PreparedStatement ps = cnt.prepareStatement(query);
			System.out.println(appointment.getDoctor().getDoctorId() + " I LOVE AIKO");
			System.out.println(appointment.getClient().getClientId() + " I HATE AIKO");
			ps.setNull(1, Types.NULL);
			ps.setInt(2, appointment.getDoctor().getDoctorId());
			ps.setInt(3, appointment.getClient().getClientId());
			ps.setNull(4, Types.NULL);
			
			ps.executeUpdate();
			
			ps.close();
			
			ps = cnt.prepareStatement(query2);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				appointment.setId(rs.getInt(1));
			}
			
			ps.close();
			
			for (Slot slot : appointment.getSlots()) {
				ps = cnt.prepareStatement(query3);
				
				System.out.println(appointment.getId());
				System.out.println(slot.getId());
				ps.setInt(1, appointment.getId());
				ps.setInt(2, slot.getId());
				
				int result = ps.executeUpdate();
				
				ps.close();
				
				if (result==0)
					throw new SQLException("Slot does not exist");
			}
			
			System.out.println("[APPOINTMENT] ADD SUCCESS");
		}catch(SQLException e) {
			System.out.println("[APPOINTMENT] ADD FAILED");
			e.printStackTrace();
			try {
				cnt.rollback();
			} catch (SQLException ex) {}
		}finally {
			try {
				cnt.setAutoCommit(true);
			} catch (SQLException ex) {}
		}
	}
	
	public boolean isAppointment(Slot slot) {
		boolean flag = false;
		Connection cnt = CalendarDB.getConnection();
		String query = "SELECT " + Slot.COL_APPOINTMENTID + " FROM " + Slot.TABLE + " where " + Slot.COL_SLOTID + " = ?";
		ResultSet rs;
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1,  slot.getId());
			
			rs = ps.executeQuery();
			
			if(rs.next())
				flag = true;
			
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return flag;
	}
	
	public void deleteAppointment(Appointment appointment)
	{
		Connection cnt = CalendarDB.getConnection();
		
		String query = "DELETE FROM " + Appointment.TABLE
				+ " WHERE " + Appointment.COL_APPOINTMENTID + " = ?";
		String query2 = "UPDATE " + Slot.TABLE
				+ " SET " + Slot.COL_APPOINTMENTID + " = ? "
				+ " WHERE " + Slot.COL_SLOTID + " = ? "
				+ " AND " + Slot.COL_APPOINTMENTID + " IS NULL";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, appointment.getId());
			
			ps.executeUpdate();
			
			ps.close();
			
			for (Slot slot : appointment.getSlots()) {
				ps = cnt.prepareStatement(query2);
				
				System.out.println(appointment.getId());
				System.out.println(slot.getId());
				ps.setNull(1, Types.NULL);
				ps.setInt(2, slot.getId());
				
				int result = ps.executeUpdate();
				
				ps.close();
				
				if (result==0)
					throw new SQLException("Slot does not exist");
			}
			
			System.out.println("[APPOINTMENT] DELETE SUCCESS");
		}catch(SQLException e) {
			System.out.println("[APPOINTMENT] DELETE FAILED");
			e.printStackTrace();
		}
		
		
	}
}
