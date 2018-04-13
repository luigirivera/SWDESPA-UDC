package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.model.User;

public interface UserControlInterface {
	public boolean isGuest(int userID);
	public boolean isDoctor(int userID);
	public boolean isSecretary(int userID);
	public boolean userExist(String username);
	public boolean attemptLogin(String username, String password);
}
