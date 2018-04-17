package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
	
	public boolean createFreeRecurring(LocalDateTime start, LocalDateTime end, int option) {
		
		SlotBuilder builder = new SlotBuilder();
		SlotService service = new SlotService();
		List<Slot> slots = new ArrayList<Slot>();
		
//		System.out.println("\n\n\n Local Date Start "+start.toLocalDate());
//		System.out.println("\n\n\n Local Date after a week"+start.toLocalDate().plusWeeks(1));
//		System.out.println("\n Local Date End " +start.toLocalDate());
		
		for(int i = 0; i <= option + 1; i++) {
			slots.addAll(builder.buildSlots(start, end));
			start = start.plusWeeks(1);
			end = end.plusWeeks(1);
		}
		
		temp = new Slot_Doc();
		sdocService = new Slot_DocService();
		
		for (Slot slotc : slots) {
			System.out.println(slotc);
			service.addSlotC(slotc);
			temp.setSlotId(service.getId(slotc));
			temp.setDoctorId(doctor.getDoctorId());
			sdocService.addSlot_Doc(temp);
		}
		
		return false; //i dont understand why the one above is false but ill do the same
	}
	
}
