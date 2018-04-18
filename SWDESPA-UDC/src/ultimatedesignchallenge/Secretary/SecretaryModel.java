package ultimatedesignchallenge.Secretary;

import java.time.LocalDate;
import java.util.List;
import java.util.Observable;

import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SlotService;

public class SecretaryModel extends Observable {
	private Secretary secretary;
	private DoctorService dsv;
	private SlotService ssv;

	public SecretaryModel(Secretary secretary) {
		this.secretary = secretary;
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
}
