package ultimatedesignchallenge.model;

import java.time.LocalDateTime;

public class Slot {
	
	/*private int SLOTid;
	private java.sql.Date start;
	private java.sql.Date end;
	private int APPOINTMENTid;
	private int RECURRINGid;*/
	
	private int id;
	private LocalDateTime start;
	private LocalDateTime end;
	
	public static final String TABLE = "SLOT";
	public static final String COL_SLOTID = "SLOTid";
	public static final String COL_START = "start";
	public static final String COL_END = "end";
	public static final String COL_APPOINTMENTID = "APPOINTMENTid";
	public static final String COL_RECURRINGID = "RECURRINGid";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
	
	
	
	
	
}
