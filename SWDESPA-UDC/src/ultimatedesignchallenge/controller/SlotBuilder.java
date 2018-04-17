package ultimatedesignchallenge.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.services.DoctorService;

public class SlotBuilder {

    public List<Slot> buildSlots(LocalDateTime startTime, LocalDateTime endTime) { //change if needed (slotC because its the slot in controller)
		List<Slot> slots = new ArrayList<Slot>();
		while (startTime.isBefore(endTime)) {
			Slot tempSlot = new Slot();
			tempSlot.setStart(startTime);
			tempSlot.setEnd(startTime.plusMinutes(30));
			tempSlot.setIsRecurring(false);
			slots.add(tempSlot);
			startTime = startTime.plusMinutes(30);
		}
		return slots;
	}
	
	public List<Slot> buildRecurringSlots(LocalDateTime startTime, LocalDateTime endTime) { //change if needed (slotC because its the slot in controller)
		List<Slot> slots = new ArrayList<Slot>();
		while (startTime.isBefore(endTime)) {
			Slot tempSlot = new Slot();
			tempSlot.setStart(startTime);
			tempSlot.setEnd(startTime.plusMinutes(30));
			tempSlot.setIsRecurring(true);
			slots.add(tempSlot);
			startTime = startTime.plusMinutes(30);
		}
		return slots;
	}
}
