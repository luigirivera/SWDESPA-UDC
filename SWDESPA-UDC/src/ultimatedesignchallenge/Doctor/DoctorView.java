package ultimatedesignchallenge.Doctor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
		
		cancel.setText("Free Slot");
		
		popup.add(update);
		popup.add(cancel);
		
		createPanel.getSave().addActionListener(new saveCreateBtnListener());
		dayPanel.getDayTable().addMouseListener(new dayTableMouseListener());
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
		
		calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);	
		monthPanel.refreshCalendar(monthToday, yearToday, validCells);
//		TODO: FULFILL THE STEPS
		refreshDayView();
		refreshWeekView();
		refreshMonthView();

		
	}
	
	@Override
	protected void refreshDayView()
	{
		//clear all rows
		//get slots that i have set available, all of them
		//display it in the dayTable
		//display occupied slots in agenda table
	}
	
	@Override
	protected void refreshWeekView()
	{
		//clear all rows
		//get slots that i have set available, all of them
		//display it in the weekTable
		//display appointments in agenda table in order of the days
	}
	
	@Override
	protected void refreshMonthView()
	{
		//get all my appointments of the month
		//display it in the calendar
		//display appointments in agenda table in order of the days 
	}
	
	class dayTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getDayTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
			
			//update();
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent arg0) {}
		@Override
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			//saveCreation(); we dont know what this is so we commented it out
			// to do: add created slot to database, set appointment ID based on appointment name
						
			try {
				if(createPanel.getRecurring().isSelected() == true)
					System.out.println("boy");
				
				else {
					controller.createFree(LocalDateTime.of(LocalDate.of((int)createPanel.getYear().getSelectedItem(), (int)createPanel.getMonth().getSelectedItem(),
					(int)createPanel.getDay().getSelectedItem()), (LocalTime) createPanel.getStartTime().getSelectedItem()), LocalDateTime.of(LocalDate.of((int)createPanel.getYear().getSelectedItem(), (int)createPanel.getMonth().getSelectedItem(),
							(int)createPanel.getDay().getSelectedItem()), (LocalTime) createPanel.getEndTime().getSelectedItem()));
				}				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			toggleCreateView(false);
			update();
		}
	}
}
