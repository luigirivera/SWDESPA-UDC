package ultimatedesignchallenge.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.services.DoctorService;

public class SlotBuilder {

	public List<SlotC> buildAvailable(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor) { //change if needed (slotC because its the slot in controller)
		List<SlotC> slots = new ArrayList<SlotC>();
		while (startTime.isBefore(endTime)) {
			slots.add(new AvailableInterval(startTime, startTime.plusMinutes(30), doctor));
			startTime = startTime.plusMinutes(30);
		}
		return slots;
	}
	
	public SlotC buildAppointment(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor, Client client) {
		SlotC slot;
		slot = new AppointmentInterval(startTime, endTime, doctor, client);
		return slot;
	}
	
	public static void main(String[] args) {
		DoctorService dsv = new DoctorService();
		SlotBuilder sb = new SlotBuilder();
		LocalDate datetoday = LocalDate.now();
		LocalTime start = LocalTime.of(13, 0);
		LocalTime end = LocalTime.of(15, 0);
		List<SlotC> buildAvailable = sb.buildAvailable(LocalDateTime.of(datetoday, start), LocalDateTime.of(datetoday, end), dsv.getDoctor(1));
		for (SlotC slotc : buildAvailable) {
			System.out.println(slotc);
		}
	}
	
}
