package ultimatedesignchallenge.model;

public class Secretary extends User{
	private int SECRETARYid;
	
	public static final String TABLE = "SECRETARY";
	public static final String COL_SECRETARYID = "SECRETARYid";
	
	public int getSECRETARYid() {
		return SECRETARYid;
	}
	
	public void setSECRETARYid(int sECRETARYid) {
		SECRETARYid = sECRETARYid;
	}
}
