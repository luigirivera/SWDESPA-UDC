package ultimatedesignchallenge.model;

public class Doctor extends User {
	private String color; //hex string

	public static final String TABLE = "DOCTOR";
	public static final String COL_DOCTORID = "DOCTORid";
	public static final String COL_COLOR = "color";

	public Doctor(int id) {
		super(id);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
