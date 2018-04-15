package ultimatedesignchallenge.Doctor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import ultimatedesignchallenge.controller.DoctorController;
import ultimatedesignchallenge.controller.SlotController;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.view.CalendarFramework;
import ultimatedesignchallenge.view.CalendarObserver;

public class DoctorView extends CalendarFramework implements CalendarObserver{
	private static final long serialVersionUID = 1L;
	private Doctor doctor;
	private DoctorController controller;
	private SlotController slotController;
	
	public DoctorView(Doctor doctor, DoctorController controller, SlotController slotController) {
		super("Doctor Calendar - " + doctor.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.doctor = doctor;
		this.controller = controller;
		this.slotController = slotController;
		
		constructorGen("Doctor");
		init();
		initListeners();
//		update();
	}
	
	private void init() {
		calendarPanel.getCreate().setText("Set Appointment Slot");
		createPanel.getSave().addActionListener(new saveCreateBtnListener());
	}
	
	@Override
	public void update() {
		List<Slot> myFree = slotController.getFree(doctor, 
				LocalDate.of(yearToday, monthToday+1, dayToday));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.of(0, 0));
		for (int i=0; i<48 ; i++) {
			for (Slot s : myFree) {
				if (count.equals(s.getStart())){
					dayPanel.getDayTable().setValueAt(s, i, 1);
				}
			}
			count = count.plusMinutes(30);
		}
		
		//grab necessary data
		
		//grab necessary data
		dayPanel.refreshDayTable();//method does not exist
		dayPanel.refreshAgendaTable();//method does note exist
		weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);
		weekPanel.refreshAgendaTable();//method does not exist
		monthPanel.refreshCalendar(monthToday, yearToday, validCells);
		monthPanel.refreshAgendaTable();//method does not exist
		calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		
	}
	
	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//saveCreation(); we dont know what this is so we commented it out
			// to do: add created slot to database, set appointment ID based on appointment name
			controller.createFree(
					LocalDateTime.of(LocalDate.parse(startDate.getText()), startTime.getItemAt(startTime.getSelectedIndex())), 
					LocalDateTime.of(LocalDate.parse(startDate.getText()), endTime.getItemAt(endTime.getSelectedIndex())));
			update();
			//if(recurringAppRB.isSelected())
				// to do: also set recurringID 
		}
	}
	


}
