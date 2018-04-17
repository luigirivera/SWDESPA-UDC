package ultimatedesignchallenge.model;

import java.time.LocalDateTime;
import java.util.List;

public class Slot {

	/*
	 * private int SLOTid; private java.sql.Date start; private java.sql.Date end;
	 * private int APPOINTMENTid; private int RECURRINGid;
	 */

	private int id;
	private LocalDateTime start;
	private LocalDateTime end;
	private boolean isRecurring;

	public static final String TABLE = "SLOT";
	public static final String COL_SLOTID = TABLE+".SLOTid";
	public static final String COL_START = TABLE+".start";
	public static final String COL_END = TABLE+".end";
	public static final String COL_APPOINTMENTID = TABLE+".APPOINTMENTid";
	public static final String COL_RECURRINGID = TABLE+".RECURRINGid";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isRecurring() {
		return isRecurring;
	}

	public void setIsRecurring(boolean isRecurring) {
		this.isRecurring = isRecurring;
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

	@Override
	public String toString() {
		return String.format("%d %s to %s", id, start.toString(), end.toString());
	}
}

