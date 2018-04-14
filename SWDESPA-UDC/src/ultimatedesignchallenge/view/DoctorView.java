package ultimatedesignchallenge.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

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
		
		constructorGen("Doctor");
		init();
		doctorListInst();
		initListeners();
	}
	
	
	protected void doctorListInst() {
		doctorListFrame = new JFrame("Clinic Doctors");
		doctorListPanel = new JPanel();
		doctorsCBList = new JComboBox<String>();
		
		doctorListFrame.setResizable(false);
		
		doctors = new JToggleButton("Doctors");	
		
		scrollDoctorList = new JScrollPane(doctorList);
		
		doctorListFrame.setSize(420, 625);
		doctorListFrame.setLayout(null);
		doctorListPanel.setLayout(null);

		calendarPanel.add(doctors);
		createPanel.add(doctorsCBList);
		doctors.setBounds(10, 500, 250,50);
		doctorsCBList.setBounds(160, 120, 120, 40);
		doctorListPanel.setBounds(0, 0, doctorListFrame.getWidth(), doctorListFrame.getHeight());
		scrollDoctorList.setBounds(doctorListPanel.getBounds());
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
