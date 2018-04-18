package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.Client.ClientModel;
import ultimatedesignchallenge.Client.ClientView;
import ultimatedesignchallenge.Doctor.DoctorModel;
import ultimatedesignchallenge.Doctor.DoctorView;
import ultimatedesignchallenge.Secretary.SecretaryModel;
import ultimatedesignchallenge.Secretary.SecretaryView;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.model.User;
import ultimatedesignchallenge.services.ClientService;
import ultimatedesignchallenge.services.DoctorService;
import ultimatedesignchallenge.services.SecretaryService;

public class UserController {
	private DoctorService dsv;
	private SecretaryService ssv;
	private ClientService csv;
	private User user;
	
	public UserController (DoctorService dsv, SecretaryService ssv, ClientService csv) {
		this.dsv = dsv;
		this.ssv = ssv;
		this.csv = csv;
	}
	
	public boolean checkLogin(String username, String password) {

		System.out.println(username);
		System.out.println(password);
		
		if((user=dsv.getDoctor(username, password))!=null) {
			DoctorModel model = new DoctorModel((Doctor)user);
			DoctorView view = new DoctorView(model, new DoctorController(model));
			model.addObserver(view);
			return true;
		}
		if((user=csv.getClient(username, password))!=null) {
			//new ClientView((Client)model);
			ClientModel model = new ClientModel((Client)user);
			ClientView view = new ClientView(model, new ClientController(model));
			model.addObserver(view);
			return true;
		}
		if((user=ssv.getSecretary(username, password))!=null){
			//new SecretaryView((Secretary)model, new SecretaryController((Secretary)model, ssv));
			SecretaryModel model = new SecretaryModel((Secretary)user);
			SecretaryView view = new SecretaryView(model, new SecretaryController(model));
			model.addObserver(view);
			return true;
		}
		return false;
	}
}
