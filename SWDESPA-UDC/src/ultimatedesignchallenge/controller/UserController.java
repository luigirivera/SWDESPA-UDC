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
			DoctorController controller = new DoctorController(model);
			DoctorView view = new DoctorView(model, controller);
			model.addObserver(view);
			controller.startThread();
			
			return true;
		}
		if((user=csv.getClient(username, password))!=null) {
			//new ClientView((Client)model);
			ClientModel model = new ClientModel((Client)user);
			ClientController controller = new ClientController(model);
			ClientView view = new ClientView(model, controller);
			model.addObserver(view);
			controller.startThread();
			
			return true;
		}
		if((user=ssv.getSecretary(username, password))!=null){
			//new SecretaryView((Secretary)model, new SecretaryController((Secretary)model, ssv));
			SecretaryModel model = new SecretaryModel((Secretary)user);
			SecretaryController controller = new SecretaryController(model);
			SecretaryView view = new SecretaryView(model, controller);
			model.addObserver(view);
			controller.startThread();
			
			return true;
		}
		return false;
	}
}
