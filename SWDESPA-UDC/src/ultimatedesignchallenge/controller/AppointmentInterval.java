package ultimatedesignchallenge.controller;

import java.time.LocalDateTime;

import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;

public class AppointmentInterval implements SlotC{
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Doctor doctor;
	private Client client;
	
	public AppointmentInterval(LocalDateTime startTime, LocalDateTime endTime, Doctor doctor, Client client) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.doctor = doctor;
		this.client = client;
	}

	@Override
	public Doctor doctor() {
		// TODO Auto-generated method stub
		return doctor;
	}

	@Override
	public LocalDateTime startTime() {
		// TODO Auto-generated method stub
		return startTime;
	}

	@Override
	public LocalDateTime endTime() {
		// TODO Auto-generated method stub
		return endTime;
	}

	@Override
	public Client client() {
		// TODO Auto-generated method stub
		return client;
	}
	
	
	
}
