package ultimatedesignchallenge.model;

public class Slot_Doc {
	public static final String TABLE = "SLOT_DOC";
	public static final String COL_SLOTID = "SLOTid";
	public static final String COL_DOCTORID = "DOCTORid";
	
	private String DocSlotID;
	private String DocID;
	
	public void setDocSlotID(String SlotID) {
		
		DocSlotID = SlotID;
	}
	
	public String getDocSlotID() {
		
		return DocSlotID;
	}
	
	public void setDocID(String DocID) {
		
		this.DocID = DocID;
	}
	
	public String getDocID() {
		
		return DocID;
	}
	
}
