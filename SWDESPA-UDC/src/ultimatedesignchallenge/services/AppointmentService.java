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
				+ " SET " + Slot.COL_APPOINTMENTID + " = ?"
				+ " WHERE " + Slot.COL_SLOTID + " = ?"
				+ " AND " + Slot.COL_APPOINTMENTID + " IS NULL";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setNull(1, Types.NULL);
			ps.setInt(2, appointment.getDoctor().getDoctorId());
			ps.setInt(3, appointment.getClient().getClientId());
			
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
				
				ps.setInt(1, appointment.getId());
				ps.setInt(2, slot.getId());
				
				ps.close();
			}
			
			System.out.println("[APPOINTMENT] ADD SUCCESS");
		}catch(SQLException e) {
			System.out.println("[APPOINTMENT] ADD FAILED");
			e.printStackTrace();
		}
	}
	
	public void deleteAppointment(int id)
	{
		Connection cnt = CalendarDB.getConnection();
		
		String query = "DELETE FROM " + Appointment.TABLE
				+ " WHERE " + Appointment.COL_APPOINTMENTID + " = ?";
		
		try {
			PreparedStatement ps = cnt.prepareStatement(query);
			
			ps.setInt(1, id);
			
			ps.executeUpdate();
			
			ps.close();
			
			System.out.println("[APPOINTMENT] DELETE SUCCESS");
		}catch(SQLException e) {
			System.out.println("[APPOINTMENT] DELETE FAILED");
			e.printStackTrace();
		}
	}
}
