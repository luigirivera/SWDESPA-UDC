package ultimatedesignchallenge.controller;

import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.services.SecretaryService;

public class SecretaryController {
	/*private SecretaryService service;
	private <add the object of the model> model;*/
	
	private Secretary secretary;
	private SecretaryService ssv;
	
	public SecretaryController(Secretary secretary, SecretaryService ssv) {
		this.secretary = secretary;
		this.ssv = ssv;
	}
}
