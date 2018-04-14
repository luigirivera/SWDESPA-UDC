package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;

import ultimatedesignchallenge.services.DoctorService;

public class DoctorController {
	private DoctorService dsv;
	
	public DoctorController(DoctorService dsv) {
		this.dsv = dsv;
	}
	
	public boolean createFree(LocalDateTime start, LocalDateTime end) {
		
		return false; //change this
	}
}
