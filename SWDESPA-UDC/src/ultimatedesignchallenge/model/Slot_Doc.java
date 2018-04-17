package ultimatedesignchallenge.model;

public class Slot_Doc {
	
	protected int slotId;
	protected int doctorId;

	public static final String TABLE = "SLOT_DOC";
	public static final String COL_SLOTID = TABLE+".SLOTid";
	public static final String COL_DOCTORID = TABLE+".DOCTORid";
	
	public int getSlotId() {
		return slotId;
	}
	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	
}
