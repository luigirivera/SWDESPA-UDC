package ultimatedesignchallenge.model;

public class Client extends User{
	private int CLIENTid;
	
	public static final String TABLE = "CLIENT";
	public static final String COL_CLIENTID = "CLIENTid";
	
	public int getCLIENTid() {
		return CLIENTid;
	}
	
	public void setCLIENTid(int cLIENTid) {
		CLIENTid = cLIENTid;
	}
}
