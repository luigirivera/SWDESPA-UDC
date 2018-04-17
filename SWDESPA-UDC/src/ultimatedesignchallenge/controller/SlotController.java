package ultimatedesignchallenge.controller;

import java.time.LocalDate;
import java.util.List;

import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.SlotService;

public class SlotController {
	private SlotService service;
	private Slot model;
	
	public SlotController(SlotService service)
	{
		this.service = service;
	}
	
	public List<Slot> getFree(Doctor doctor, LocalDate date)
	{
		return service.getFree(doctor, date);
	}
	
	public List<Slot> getAllDoctorAppointments(LocalDate date)
	{
		return service.getAllDoctorAppointments(date);
	}
	
	public List<Slot> getAppointmentAgendaList(Doctor doctor, LocalDate date)
	{
		return service.getAppointmentAgendaList(doctor, date);
	}
	
	public List<Slot> getTaken(Doctor doctor, LocalDate date){
		return service.getTakenDoctor(doctor, date);
	}
	
	public List<Client> getAppointmentClientsList(Doctor doctor, LocalDate date)
	{
		return service.getAppointmentClientsList(doctor, date);
	}
}