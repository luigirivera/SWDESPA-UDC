package ultimatedesignchallenge.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import designchallenge2.view.CalendarObserver;
import ultimatedesignchallenge.controller.SlotBuilder;
import ultimatedesignchallenge.controller.SlotC;
import ultimatedesignchallenge.model.Doctor;

public class DoctorView extends CalendarFramework implements CalendarObserver{
	private static final long serialVersionUID = 1L;
	private Doctor doctor;
	
	public DoctorView(Doctor doctor) {
		super("Doctor Calendar - " + doctor.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.doctor = doctor;
		
		instantiate();
		constructorGen("Doctor");
		init();
		initListeners();
	}
	
	private void instantiate() {
		
		
	}
	
	private void init() {
		save.addActionListener(new saveCreateBtnListener());
	}
	
	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//saveCreation(); we dont know what this is so we commented it out
			SlotBuilder builder = new SlotBuilder();
			SlotC slot = builder.buildDoctorAvailable(startTime.getSelectedItem().toString(), endTime.getSelectedItem().toString());
			// to do: add created slot to database, set appointment ID based on appointment name
			
			//if(recurringAppRB.isSelected())
				// to do: also set recurringID 
		}
	}
	


}
