package ultimatedesignchallenge;

import designchallenge2.model.CalendarDB;
import ultimatedesignchallenge.controller.UserController;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SecretaryService;
import ultimatedesignchallenge.view.LoginView;

public class UDCDriver {
	public static void main(String[] args) {
		CalendarDB.getConnection();
		
		DoctorService dsv = new DoctorService();
		SecretaryService ssv = new SecretaryService();
		ClientService csv = new ClientService();
		
		UserController uc = new UserController(dsv, ssv, csv);
		
		new LoginView(uc);
	}

}
