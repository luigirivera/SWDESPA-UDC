package ultimatedesignchallenge.Secretary;

import java.time.LocalDate;
import java.util.List;
import java.util.Observable;

import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.AppointmentService;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SlotService;

public class SecretaryModel extends Observable {
	private Secretary secretary;
	private AppointmentService asv;
	private ClientService csv;
	private DoctorService dsv;
	private SlotService ssv;

	public SecretaryModel(Secretary secretary) {
		this.secretary = secretary;
		this.asv = new AppointmentService();
		this.csv = new ClientService();
		this.dsv = new DoctorService();
		this.ssv = new SlotService();
	}

	public Secretary getSecretary() {
		return secretary;
	}

	public void setSecretary(Secretary secretary) {
		this.secretary = secretary;
	}

	public List<Slot> getAllSlots() {
		return ssv.getAll();
	}
	
	public List<Slot> getFreeSlots(Doctor doctor, LocalDate date){
		return ssv.getFree(doctor, date);
	}
	
	public List<Slot> getAllDoctorAppointments(LocalDate date){
		return ssv.getAllDoctorAppointments(date);
	}
	
	public List<Client> getAllDoctorAppointmentsClients(LocalDate date){
		return ssv.getAllDoctorAppointmentsClients(date);
	}
	
	public List<Doctor> getAllDoctorAppointmentsDoctors(LocalDate date){
		return ssv.getAllDoctorAppointmentsDoctors(date);
	}
	
	public List<Client> getAppointmentClientsList(Doctor doctor, LocalDate date){
		return ssv.getAppointmentClientsList(doctor, date);
	}
	
	public List<Slot> getAppointmentAgendaList(Doctor doctor, LocalDate date){
		return ssv.getAppointmentAgendaList(doctor, date);
	}
	
	public List<Doctor> getAllDoctors() {
		return dsv.getAll();
	}
	
	public List<Client> getAllClients(){
		return csv.getAll();
	}
	
	public int getAppointmentId(Slot slot) {
		return ssv.getAppointmentID(slot);
	}
	
	public Client getClient(int clientId) {
		return csv.getClient(clientId);
	}
	
	public int getClientViaAptId(int appointmentId) {
		return csv.getClientViaAptID(appointmentId);
	}
	
	public void deleteAppointment(Appointment apt) {
		asv.deleteAppointment(apt);
	}
}
