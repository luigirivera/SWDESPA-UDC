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

		if(service.ifDoctor(model.getId())) {
			new DoctorView(model);
		}
		else if(service.ifSecretary(model.getId())){
			new SecretaryView(model);
		}
		else if(service.ifClient(model.getId())) {
			new ClientView(model);
		}
	}
}
