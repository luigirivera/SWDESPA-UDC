package ultimatedesignchallenge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CalendarDB {
	private static String DRIVER_NAME = "com.mysql.jdbc.Driver";
	private static String URL = "jdbc:mysql://127.0.0.1:3306/";
	private static String USERNAME = "root";
	private static String PASSWORD = "agentblue";
	private static String DATABASE = "UDC";

	private static Connection connection = null;

	private CalendarDB() {
	}

	public static synchronized Connection getConnection() {
		if (connection == null) {
			try {
				Class.forName(DRIVER_NAME);
				connection = DriverManager.getConnection(URL + DATABASE + "?autoReconnect=true&useSSL=false",
						USERNAME, PASSWORD);
				System.out.println("[MYSQL] Connection successful");
			} catch (SQLException e) {
				System.out.println("[MYSQL] Connection failed!");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("[MYSQL] Connection failed!");
				e.printStackTrace();
			}
		}
		
		return connection;
	}
}
