package ultimatedesignchallenge.controller;

import java.util.List;

import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.services.AppointmentService;

public class AppointmentController implements AppointmentControlInterface {
	private AppointmentService service;
	private Appointment model;
	
	@Override
	public void addAppointment(Appointment appointment) {
		service.addAppointment(appointment);
	}
	@Override
	public void deleteAppointment(int appointmentID) {
		service.deleteAppointment(appointmentID);
		
	}
	@Override
	public void deleteAllRecurringAppointment(int recurringID) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteSomeRecurringAppointment(int recurringID) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Appointment getAppointment(int appointmentID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Appointment> getAllAppointments() {
		return service.getAllAppointments();
	}
	@Override
	public List<Appointment> getAllDoctorAppointments(int doctorID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Appointment> getAllClientAppointments(int clientID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Appointment> getAllOtherAppointments(int clientID) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void editAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		
	}
}
