package ultimatedesignchallenge.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ultimatedesignchallenge.CalendarDB;
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Recurring;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.Slot_Doc;
import ultimatedesignchallenge.services.AppointmentService;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.SlotService;
import ultimatedesignchallenge.services.Slot_DocService;

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
//		//cc.transformToAppointment(slots, doctor);
//		
//		Appointment apt = new Appointment();
//		apt.setClient(client);
//		apt.setDoctor(doctor);
//		apt.setId(9);
//		apt.setSlots(slots);
//		
//		AppointmentService sTemp = new AppointmentService();
//		sTemp.deleteAppointment(apt);
//	}
}
