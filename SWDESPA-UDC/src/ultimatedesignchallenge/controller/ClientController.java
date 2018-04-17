package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.AppointmentService;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.SlotService;

public class ClientController {
	private Client client;
	private ClientService clientService;
	private SlotService slotService;
	
	private AppointmentService aptService;
	
	public ClientController(Client client, ClientService clientService) {
		this.client = client;
		this.clientService = clientService;
	}

	public void transformToAppointment(List<Slot> slots, Doctor doctor) {
		slotService = new SlotService();
		aptService = new AppointmentService();
		
		Appointment apt = new Appointment();
		
		apt.setClient(client);
		apt.setDoctor(doctor);
		apt.setSlots(slots);
		
		aptService.addAppointment(apt);
	}
	
	public static void main(String[] args) {
		Slot slot = new Slot();
		slot.setId(17);
		slot.setStart(LocalDateTime.of(2018, 4, 17, 0, 0));
		slot.setEnd(LocalDateTime.of(2018, 4, 17, 0, 30));
		List<Slot> slots = new ArrayList<Slot>();
		slots.add(slot);
		
		Doctor doctor = new Doctor();
		doctor.setColor("FF0000");
		doctor.setDoctorId(1);
		doctor.setId(1);
		
		Client client = new Client();
		client.setClientId(1);
		client.setFirstname("Jordan");
		client.setLastname("Jordan");
		client.setId(3);
		client.setUsername("c1");
		
		ClientService cs = new ClientService();
		ClientController cc = new ClientController(client, cs);
		
		cc.transformToAppointment(slots, doctor);
	}
}
