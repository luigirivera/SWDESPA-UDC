package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.model.User;
import ultimatedesignchallenge.services.UserService;
import ultimatedesignchallenge.view.*; //just got all of the them, we can change it later if you guys dont wanna do this

public class UserController {
	private UserService service;
	private User model;
	
	public void checkLogin(String username, String password) {
		service = new UserService();
		model = service.getUser(username, password);

		if(service.ifDoctor(model.getUSERid())) {
			new DoctorView();
		}
		else if(service.ifSecretary(model.getUSERid())){
			new SecretaryView();
		}
		else if(service.ifClient(model.getUSERid())) {
			new ClientView();
		}
	}
}
