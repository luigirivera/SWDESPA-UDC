package ultimatedesignchallenge.model;

public class Client extends User {
	private int clientId;

	public static final String TABLE = "CLIENT";
	public static final String COL_CLIENTID = TABLE+".CLIENTid";
	public static final String COL_USERID = TABLE+".USERid"
;
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

}
