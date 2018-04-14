package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;

import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;

public interface SlotC {
	public Doctor doctor();
	public LocalDateTime startTime();
	public LocalDateTime endTime();
	public Client client();
	
}
