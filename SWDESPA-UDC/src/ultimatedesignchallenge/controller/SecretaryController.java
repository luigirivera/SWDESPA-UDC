package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;
import java.util.List;

import ultimatedesignchallenge.Client.ClientModel;
import ultimatedesignchallenge.Doctor.DoctorModel;
import ultimatedesignchallenge.Secretary.SecretaryModel;
import ultimatedesignchallenge.Secretary.SecretaryThread;
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;

public class SecretaryController {
	/*private SecretaryService service;
	private <add the object of the model> model;*/
	
	private SecretaryModel model;
	
	public SecretaryController(SecretaryModel model) {
		this.model = model;
	}
	
	public void startThread() {
		SecretaryThread st = new SecretaryThread(model);
		st.start();
	}
	
	public void transformToAppointment(Client client, List<Slot> slots, Doctor doctor) {
		ClientController cc = new ClientController(new ClientModel(client));
		cc.transformToAppointment(slots, doctor);
	}
	
	public void deleteAppointment(Appointment apt) {
		model.deleteAppointment(apt);
	}
	
	public boolean updateFree(Doctor doctor, Slot oldSlot, LocalDateTime newStart, LocalDateTime newEnd) {
		DoctorController dc = new DoctorController(new DoctorModel(doctor));
		
		return dc.updateFree(oldSlot, newStart, newEnd);
	}
}
