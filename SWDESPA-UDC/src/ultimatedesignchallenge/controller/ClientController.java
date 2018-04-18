package ultimatedesignchallenge.controller;

import java.util.List;

import ultimatedesignchallenge.Client.ClientModel;
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;

public class ClientController {
	private ClientModel model;
	
	public ClientController(ClientModel model) {
		this.model = model;
	}

	public void transformToAppointment(List<Slot> slots, Doctor doctor) {
		
		Appointment apt = new Appointment();
		
		apt.setClient(model.getClient());
		apt.setDoctor(doctor);
		apt.setSlots(slots);
		
		model.addAppointment(apt);
	}
	
//	public static void main(String[] args) {
//		Slot slot = new Slot();
//		slot.setId(25);
//		slot.setStart(LocalDateTime.of(2018, 4, 17, 0, 0));
//		slot.setEnd(LocalDateTime.of(2018, 4, 17, 0, 30));
//		List<Slot> slots = new ArrayList<Slot>();
//		slots.add(slot);
//		
//		Doctor doctor = new Doctor();
//		doctor.setColor("FF0000");
//		doctor.setDoctorId(1);
//		doctor.setId(1);
//		
//		Client client = new Client();
//		client.setClientId(1);
//		client.setFirstname("Jordan");
//		client.setLastname("Jordan");
//		client.setId(3);
//		client.setUsername("c1");
//		
//		ClientService cs = new ClientService();
//		ClientController cc = new ClientController(client, cs);
//		
//		cc.transformToAppointment(slots, doctor);
//		
//		Appointment apt = new Appointment();
//		apt.setClient(client);
//		apt.setDoctor(doctor);
//		apt.setId(9);
//		apt.setSlots(slots);
//		
////		AppointmentService sTemp = new AppointmentService();
////		sTemp.deleteAppointment(apt);
//	}
}
