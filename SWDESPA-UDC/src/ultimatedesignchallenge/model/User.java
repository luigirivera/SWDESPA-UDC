package ultimatedesignchallenge.model;

public class User {
	private int USERid;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	
	public static final String TABLE = "USER";
	public static final String COL_USERID = "USERid";
	public static final String COL_USERNAME = "username";
	public static final String COL_PASSWORD = "password";
	public static final String COL_FIRSTNAME = "firstname";
	public static final String COL_LASTNAME = "lastname";
	
	public int getUSERid() {
		return USERid;
	}
	
	public void setUSERid(int uSERid) {
		USERid = uSERid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
}
