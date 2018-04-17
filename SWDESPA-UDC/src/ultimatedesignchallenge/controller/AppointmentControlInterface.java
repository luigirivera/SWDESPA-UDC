package ultimatedesignchallenge.controller;

import java.util.List;

import ultimatedesignchallenge.model.Appointment;

public interface AppointmentControlInterface {
	public void addAppointment(Appointment appointment);
	public void deleteAppointment(int appointmentID);
	public void deleteAllRecurringAppointment(int recurringID);
	public void deleteSomeRecurringAppointment(int recurringID);
	public Appointment getAppointment(int appointmentID);
	public List<Appointment> getAllAppointments();
	public List<Appointment> getAllDoctorAppointments(int doctorID);
	public List<Appointment> getAllClientAppointments(int clientID);
	public List<Appointment> getAllOtherAppointments(int clientID);
	public void editAppointment(Appointment appointment);
}
