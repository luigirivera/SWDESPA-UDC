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
	
	public UserController (DoctorService dsv, SecretaryService ssv, ClientService csv) {
		dsv = new DoctorService();
		ssv = new SecretaryService();
		csv = new ClientService();
	}
	
	public boolean checkLogin(String username, String password) {

		if((model=dsv.getDoctor(username, password))!=null) {
			new DoctorView(model);
			return true;
		}
		else if((model=ssv.getSecretary(username, password))!=null){
			new SecretaryView(model);
			return true;
		}
		else if((model=csv.getClient(username, password))!=null) {
			new ClientView(model);
			return true;
		}
		return false;
	}
}
