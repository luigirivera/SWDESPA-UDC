package ultimatedesignchallenge;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.view.LoginView;

public class UDCDriver {
	public static void main(String[] args) {
		CalendarDB.getConnection();
		new LoginView();
	}

}
