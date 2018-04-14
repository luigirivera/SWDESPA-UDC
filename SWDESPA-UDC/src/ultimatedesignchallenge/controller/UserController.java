package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.model.User;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SecretaryService;
//just got all of the them, we can change it later if you guys dont wanna do this
import ultimatedesignchallenge.view.ClientView;
import ultimatedesignchallenge.view.DoctorView;
import ultimatedesignchallenge.view.SecretaryView;

public class UserController {
	private DoctorService dsv;
	private SecretaryService ssv;
	private ClientService csv;
	private User model;
	
	public void checkLogin(String username, String password) {

		if((model=dsv.getDoctor(username, password))!=null) {
			new DoctorView(model);
		}
		else if((model=ssv.getSecretary(username, password))!=null){
			new SecretaryView(model);
		}
		else if((model=csv.getClient(username, password))!=null) {
			new ClientView(model);
		}
	}
}
