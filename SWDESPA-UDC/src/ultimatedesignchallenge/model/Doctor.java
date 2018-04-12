package ultimatedesignchallenge.model;

public class Doctor extends User{
	private int DOCTORid;
	private String color;
	
	public static final String TABLE = "DOCTOR";
	public static final String COL_DOCTORID = "DOCTORid";
	public static final String COL_COLOR = "color";
	
	public int getDOCTORid() {
		return DOCTORid;
	}
	
	public void setDOCTORid(int dOCTORid) {
		DOCTORid = dOCTORid;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
}
