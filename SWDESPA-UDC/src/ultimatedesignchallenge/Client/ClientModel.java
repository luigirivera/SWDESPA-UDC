package ultimatedesignchallenge.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.Observable;

import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.AppointmentService;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SlotService;

public class ClientModel extends Observable {
	private Client client;
	private AppointmentService asv;
	private ClientService csv;
	private DoctorService dsv;
	private SlotService ssv;

	public ClientModel(Client client) {
		this.client = client;
		this.asv = new AppointmentService();
		this.csv = new ClientService();
		this.dsv = new DoctorService();
		this.ssv = new SlotService();
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public List<Doctor> getAllDoctors(){
		return dsv.getAll();
	}
	
	public List<Slot> getFree(Doctor doctor, LocalDate date) {
		return ssv.getFree(doctor, date);
	}
	
	public List<Slot> getAllSlots(int clientId){
		return ssv.getAll(clientId);
	}
	
	public int getAppointmentId(Slot slot) {
		return ssv.getAppointmentID(slot);
	}
	
	public void addAppointment(Appointment appointment) {
		asv.addAppointment(appointment);
	}
	
	public void deleteAppointment(Appointment appointment) {
		asv.deleteAppointment(appointment);
	}
	
	public List<Appointment> getAllAppointments(){
		return asv.getAll();
	}
	
	public List<Slot> getAllAppointmentsJoinedSlots(){
		return ssv.getAllAppointmentsJoinedSlots();
	}

}
