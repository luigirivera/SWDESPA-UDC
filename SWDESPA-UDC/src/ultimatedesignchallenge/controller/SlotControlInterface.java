package ultimatedesignchallenge.controller;

import java.util.List;

import ultimatedesignchallenge.model.Slot;

public interface SlotControlInterface {
	public boolean isFree(int slotID);
	public void occupySlot(int slotID, int appointmentID);
	public void freeSlot(int slotID, int appointmentID);
	public Slot getSlot(int slotID);
	public List<Slot> getDoctorSlots(int doctorID);
	public List<Slot> getAllSlots();
}
