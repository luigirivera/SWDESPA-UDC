package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;

import ultimatedesignchallenge.model.Slot;

public class Slot30MinBuilder implements SlotBuilder{

	private Slot slot;
	
	public Slot30MinBuilder() {
		
		this.slot = new Slot();
	}

	@Override
	public void build30MinSlot(LocalDateTime start, LocalDateTime end) {
		
		slot.setStart(start);
		slot.setEnd(end);
		
	}
	
	public Slot getSlot() {
		
		return slot;
	}

}
