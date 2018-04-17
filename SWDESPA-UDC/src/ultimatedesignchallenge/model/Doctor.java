package ultimatedesignchallenge.model;

import java.util.List;

public class Doctor extends User {
	private int doctorId;
	private String color; // hex string
	private List<Slot> slots;

	public static final String TABLE = "DOCTOR";
	public static final String COL_DOCTORID = TABLE + ".DOCTORid";
	public static final String COL_COLOR = TABLE + ".color";
	public static final String COL_USERID = TABLE + ".USERid";

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}

}
