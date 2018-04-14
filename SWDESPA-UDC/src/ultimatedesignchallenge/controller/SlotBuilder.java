package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;

import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;

public class SlotBuilder {

	public SlotC buildAvailable(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor) { //change if needed (slotC because its the slot in controller)
		SlotC slot;
		slot = new AvailableInterval(startTime, endTime, doctor);
		return slot;
	}
	
	public SlotC buildAppointment(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor, Client client) {
		SlotC slot;
		slot = new AppointmentInterval(startTime, endTime, doctor, client);
		return slot;
	}
	
}
