package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;
import ultimatedesignchallenge.model.Slot;

public interface SlotBuilder {

	public void build30MinSlot(LocalDateTime start, LocalDateTime end);
	
	public Slot getSlot();
	
}
