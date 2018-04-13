package ultimatedesignchallenge.model;

import java.sql.Date;

public class Slot {
	
	private int SLOTid;
	private java.sql.Date start;
	private java.sql.Date end;
	private int APPOINTMENTid;
	private int RECURRINGid;
	
	public static final String TABLE = "SLOT";
	public static final String COL_SLOTID = "SLOTid";
	public static final String COL_START = "start";
	public static final String COL_END = "end";
	public static final String COL_APPOINTMENTID = "APPOINTMENTid";
	public static final String COL_RECURRINGID = "RECURRINGid";
	
	public int getSLOTid() {
		return SLOTid;
	}
	public void setSLOTid(int sLOTid) {
		SLOTid = sLOTid;
	}
	public java.sql.Date getStart() {
		return start;
	}
	public void setStart(java.sql.Date start) {
		this.start = start;
	}
	public java.sql.Date getEnd() {
		return end;
	}
	public void setEnd(java.sql.Date end) {
		this.end = end;
	}
	public int getAPPOINTMENTid() {
		return APPOINTMENTid;
	}
	public void setAPPOINTMENTid(int aPPOINTMENTid) {
		APPOINTMENTid = aPPOINTMENTid;
	}
	public int getRECURRINGid() {
		return RECURRINGid;
	}
	public void setRECURRINGid(int rECURRINGid) {
		RECURRINGid = rECURRINGid;
	}
	
	
	
}
