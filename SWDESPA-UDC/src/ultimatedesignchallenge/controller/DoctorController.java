package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;
import java.util.List;

import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.services.DoctorService;

public class DoctorController {
	private Doctor doctor;
	private DoctorService dsv;
	
	public DoctorController(Doctor doctor, DoctorService dsv) {
		this.doctor = doctor;
		this.dsv = dsv;
	}
	
	public boolean createFree(LocalDateTime start, LocalDateTime end) {
		SlotBuilder builder = new SlotBuilder();
		List<SlotC> slots = builder.buildAvailable(start, end, doctor);
		for (SlotC slotc : slots) {
			System.out.println(slotc);
		}
		return false; //change this
	}
}
