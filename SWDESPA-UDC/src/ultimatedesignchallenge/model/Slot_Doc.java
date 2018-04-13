package ultimatedesignchallenge.model;

public class Slot_Doc extends Slot{
	public static final String TABLE = "SLOT_DOC";
	public static final String COL_SLOTID = "SLOTid";
	public static final String COL_DOCTORID = "DOCTORid";
	
	private String DOCTORid;

	public String getDOCTORid() {
		return DOCTORid;
	}

	public void setDOCTORid(String dOCTORid) {
		DOCTORid = dOCTORid;
	}
}
