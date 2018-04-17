package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.Client.ClientView;
import ultimatedesignchallenge.Doctor.DoctorView;
import ultimatedesignchallenge.Secretary.SecretaryView;
import ultimatedesignchallenge.model.*;
import ultimatedesignchallenge.model.User;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SecretaryService;
import ultimatedesignchallenge.services.SlotService;

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
			new DoctorView((Doctor)model, new DoctorController((Doctor)model, dsv), new SlotController(new SlotService()));
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
