package ultimatedesignchallenge.model;

public class Appointment extends Slot {
	private int APPOINTMENTid;
	private int DOCTORid;
	private int CLIENTid;
	private int RECURRINGid;
	
	public static final String TABLE = "APPOINTMENT";
	public static final String COL_APPOINTEMENTID = "APPOINTMENTid";
	public static final String COL_DOCTORID = "DOCTORid";
	public static final String COL_CLIENTID = "CLIENTid";
	public static final String COL_RECURRINGID = "RECURRINGid";
	
	public int getAPPOINTMENTid() {
		return APPOINTMENTid;
	}
	
	public void setAPPOINTMENTid(int aPPOINTMENTid) {
		APPOINTMENTid = aPPOINTMENTid;
	}
	
	public int getDOCTORid() {
		return DOCTORid;
	}
	
	public void setDOCTORid(int dOCTORid) {
		DOCTORid = dOCTORid;
	}
	
	public int getCLIENTid() {
		return CLIENTid;
	}
	
	public void setCLIENTid(int cLIENTid) {
		CLIENTid = cLIENTid;
	}
	
	public int getRECURRINGid() {
		return RECURRINGid;
	}
	
	public void setRECURRINGid(int rECURRINGid) {
		RECURRINGid = rECURRINGid;
	}
}
