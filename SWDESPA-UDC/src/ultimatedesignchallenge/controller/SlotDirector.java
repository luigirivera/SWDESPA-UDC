package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.model.Slot;

public class SlotDirector {
	
	private Slot30MinBuilder s30mbuilder;
	
	public SlotDirector(Slot30MinBuilder s30mbuilder) {
		
		this.s30mbuilder = s30mbuilder;
		
	}
	
	public Slot getSlot() {
		
		return this.s30mbuilder.getSlot();
	}

}
