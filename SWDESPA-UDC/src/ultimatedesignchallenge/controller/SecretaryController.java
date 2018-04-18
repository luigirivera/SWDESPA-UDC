package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;

import ultimatedesignchallenge.Doctor.DoctorModel;
import ultimatedesignchallenge.Secretary.SecretaryModel;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;

public class SecretaryController {
	/*private SecretaryService service;
	private <add the object of the model> model;*/
	
	private SecretaryModel model;
	
	public SecretaryController(SecretaryModel model) {
		this.model = model;
	}
	
	public boolean updateFree(Doctor doctor, Slot oldSlot, LocalDateTime newStart, LocalDateTime newEnd) {
		DoctorController dc = new DoctorController(new DoctorModel(doctor));
		
		return dc.updateFree(oldSlot, newStart, newEnd);
	}
}
