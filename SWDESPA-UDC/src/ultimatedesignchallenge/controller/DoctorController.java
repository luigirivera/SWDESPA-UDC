package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;
import java.util.List;

import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.Slot_Doc;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SlotService;
import ultimatedesignchallenge.services.Slot_DocService;

public class DoctorController {
	private Doctor doctor;
	private DoctorService dsv;
	private Slot_DocService sdocService;
	private Slot_Doc temp;
	
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
		temp = new Slot_Doc();
		sdocService = new Slot_DocService();
		
		for (Slot slotc : slots) {
			System.out.println(slotc);
			service.addSlotC(slotc);
			temp.setSlotId(service.getId(slotc));
			temp.setDoctorId(doctor.getDoctorId());
			sdocService.addSlot_Doc(temp);
		}
		return false; //change this
	}
}
