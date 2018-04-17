package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;
import java.util.List;

import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SlotService;

public class DoctorController {
	private Doctor doctor;
	private DoctorService dsv;
	
	public DoctorController(Doctor doctor, DoctorService dsv) {
		this.doctor = doctor;
		this.dsv = dsv;
	}
	
	public boolean createFree(LocalDateTime start, LocalDateTime end) {
		System.out.println("yes!!!!");
		System.out.println(start);
		System.out.println(end);
		SlotBuilder builder = new SlotBuilder();
		SlotService service = new SlotService();
		List<Slot> slots = builder.buildSlots(start, end);
		for (Slot slotc : slots) {
			System.out.println(slotc);
			service.addSlotC(slotc);
		}
		return false; //change this
	}
}
