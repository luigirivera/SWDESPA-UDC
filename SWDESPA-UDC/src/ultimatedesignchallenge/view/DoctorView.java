package ultimatedesignchallenge.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import designchallenge2.view.CalendarObserver;
import ultimatedesignchallenge.controller.DoctorController;
import ultimatedesignchallenge.model.Doctor;

public class DoctorView extends CalendarFramework implements CalendarObserver{
	private static final long serialVersionUID = 1L;
	private Doctor doctor;
	private DoctorController controller;
	
	public DoctorView(Doctor doctor, DoctorController controller) {
		super("Doctor Calendar - " + doctor.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.doctor = doctor;
		this.controller = controller;
		
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
			// to do: add created slot to database, set appointment ID based on appointment name
			controller.createFree(
					LocalDateTime.of(LocalDate.parse(startDate.getText()), startTime.getItemAt(startTime.getSelectedIndex())), 
					LocalDateTime.of(LocalDate.parse(startDate.getText()), endTime.getItemAt(endTime.getSelectedIndex())));
			//if(recurringAppRB.isSelected())
				// to do: also set recurringID 
		}
	}
	


}
