package ultimatedesignchallenge.model;

public abstract class User {
	protected int id;
	protected String username;
	protected String firstname;
	protected String lastname;

	public static final String TABLE = "USER";
	public static final String COL_USERID = TABLE + ".USERid";
	public static final String COL_USERNAME = TABLE + ".username";
	public static final String COL_PASSWORD = TABLE + ".password";
	public static final String COL_FIRSTNAME = TABLE + ".firstname";
	public static final String COL_LASTNAME = TABLE + ".lastname";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
