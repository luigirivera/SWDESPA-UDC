package ultimatedesignchallenge.model;

public class Secretary extends User {
	private int secretaryId;

	public static final String TABLE = "SECRETARY";
	public static final String COL_SECRETARYID = "SECRETARYid";

	public int getSecretaryId() {
		return secretaryId;
	}

	public void setSecretaryId(int secretaryId) {
		this.secretaryId = secretaryId;
	}

}
