package ultimatedesignchallenge.Doctor;

import java.time.LocalDate;
import java.util.List;
import java.util.Observable;

import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.model.Slot_Doc;
import ultimatedesignchallenge.services.SlotService;
import ultimatedesignchallenge.services.Slot_DocService;

public class DoctorModel extends Observable {
	private Doctor doctor;
	private SlotService ssv;
	private Slot_DocService sdsv;

	public DoctorModel(Doctor doctor) {
		this.doctor = doctor;
		this.ssv = new SlotService();
		this.sdsv = new Slot_DocService();
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
	public List<Slot> getFree(LocalDate date){
		return ssv.getFree(doctor, date);
	}
	
	public List<Slot> getTaken(LocalDate date){
		return ssv.getTakenDoctor(doctor, date);
	}
	
	public List<Slot> getAppointmentAgendaList(LocalDate date){
		return ssv.getAppointmentAgendaList(doctor, date);
	}
	
	public List<Client> getAppointmentClientList(LocalDate date){
		return ssv.getAppointmentClientsList(doctor, date);
	}
	
	public boolean isFree(Slot slot) {
		return ssv.isFree(slot);
	}
	
	public boolean addSlot(Slot slot) {
		return ssv.addSlotC(slot);
	}
	
	public boolean addSlot(Slot slot, int recurringId) {
		return ssv.addSlotC(slot, recurringId);
	}
	
	public void addSlot_Doc(Slot_Doc docSlot) {
		sdsv.addSlot_Doc(docSlot);
	}
	
	public void deleteSlot(int slotId) {
		ssv.deleteSlot(slotId);
	}
	
	public Slot getId(Slot slot) {
		return ssv.getId(slot);
	}

}
