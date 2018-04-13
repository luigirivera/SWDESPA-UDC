package ultimatedesignchallenge.model;

import java.util.List;

public class Appointment {
	/*
	 * private int APPOINTMENTid; private int DOCTORid; private int CLIENTid;
	 * private int RECURRINGid;
	 */

	private int id;
	private Client client;
	private Doctor doctor;
	private List<Slot> slots;

	public static final String TABLE = "APPOINTMENT";
	public static final String COL_APPOINTEMENTID = "APPOINTMENTid";
	public static final String COL_DOCTORID = "DOCTORid";
	public static final String COL_CLIENTID = "CLIENTid";
	public static final String COL_RECURRINGID = "RECURRINGid";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

}
