package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.model.*;
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
		this.dsv = dsv;
		this.ssv = ssv;
		this.csv = csv;
	}
	
	public boolean checkLogin(String username, String password) {

		System.out.println(username);
		System.out.println(password);
		
		if((model=dsv.getDoctor(username, password))!=null) {
			new DoctorView((Doctor)model);
			return true;
		}
		if((model=csv.getClient(username, password))!=null) {
			new ClientView((Client)model);
			return true;
		}
		if((model=ssv.getSecretary(username, password))!=null){
			new SecretaryView((Secretary)model);
			return true;
		}
		return false;
	}
}
