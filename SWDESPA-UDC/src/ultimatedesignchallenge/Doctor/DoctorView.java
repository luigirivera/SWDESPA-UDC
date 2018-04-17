package ultimatedesignchallenge.Doctor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;
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
		this.doctor = doctor;
		this.controller = controller;
		this.slotController = slotController;
		
		constructorGen("Doctor");
		initListeners();
		init();
		update();
	}
	
	private void init() {
		calendarPanel.getCreate().setText("Set Appointment Slot");
		
		cancel.setText("Free Slot");
		
		popup.add(update);
		popup.add(cancel);
		
		createPanel.getSave().addActionListener(new saveCreateBtnListener());
		dayPanel.getDayTable().addMouseListener(new dayTableMouseListener());
		dayPanel.getAgendaTable().addMouseListener(new agendaTableMouseListener());
		weekPanel.getWeekTable().addMouseListener(new weekTableMouseListener());
		weekPanel.getAgendaTable().addMouseListener(new weekAgendaTableMouseListener());
		cancel.addActionListener(new cancelListener());
	}
	
	@Override
	public void update() {
		//TODO:
		//grab necessary data
		calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);
		changeLabel();
//		TODO: FULFILL THE STEPS
		refreshDayView();
		refreshWeekView();
	}
	
	private void refreshDayView()
	{
		List<Slot> myFree = slotController.getFree(doctor, 
				LocalDate.of(yearToday, monthToday+1, dayToday));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.of(0, 0));
		for (int i = 0; i < 48; i++) {
			dayPanel.getDayTable().setValueAt(null, i, 1);
			for (Slot s : myFree) {
				//System.out.println(s.getStart());
				//System.out.println(count);
				if (count.equals(s.getStart())){
					//System.out.println("changed");
					dayPanel.getDayTable().setValueAt(s, i, 1);
				}
			}
			count = count.plusMinutes(30);
		}
		
		// UPDATE AGENDA
		
		//TODO:
		//clear all rows
		//use this -> clearAgenda(dayPanel.getModelAgendaTable());
		//get slots that i have set available, all of them
		//display it in the dayTable
		//display occupied slots in agenda table
	}
	
	private void refreshWeekView()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(yearToday, monthToday, dayToday);
		cal.get(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		refreshWeekViewByColumn(cal, 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		refreshWeekViewByColumn(cal, 2);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		refreshWeekViewByColumn(cal, 3);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		refreshWeekViewByColumn(cal, 4);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		refreshWeekViewByColumn(cal, 5);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		refreshWeekViewByColumn(cal, 6);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		refreshWeekViewByColumn(cal, 7);
		
		// UPDATE AGENDA
		
		//TODO:
		//clear all rows
		//use this -> clearAgenda(weekPanel.getModelAgendaTable());
		//get slots that i have set available, all of them
		//display it in the weekTable
		//display appointments in agenda table in order of the days
	}
	

	protected void refreshWeekViewByColumn(Calendar cal, int day)
	{	
		int tempY = cal.get(Calendar.YEAR);
		int tempM = cal.get(Calendar.MONTH)+1;
		int tempD = cal.get(Calendar.DATE);
		System.out.println(tempY);
		System.out.println(tempM);
		System.out.println(tempD);
		
		List<Slot> myFree = slotController.getFree(doctor, 
				LocalDate.of(tempY, tempM, tempD));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(tempY, tempM, tempD), LocalTime.of(0, 0));
		for (int i = 0; i < 48 ; i++) {
			weekPanel.getWeekTable().setValueAt(null, i, day);
			for (Slot s : myFree) {
				if (count.equals(s.getStart())) {
					//System.out.println("changed!");
					//System.out.println(weekPanel.getWeekTable().getValueAt(i, 0));
							weekPanel.getWeekTable().setValueAt(s, i, day);
							//System.out.println(weekPanel.getWeekTable().getValueAt(i, day));
				}
			}
			count = count.plusMinutes(30);
		}
	}
	
	class dayTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getDayTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
			
		}
		
	}
	
	class agendaTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
		}
	}
	
	class weekTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = weekPanel.getWeekTable().getSelectedRow();
			int col = weekPanel.getWeekTable().getSelectedColumn();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
		}
	}
	
	class weekAgendaTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = weekPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());	
		}
	}
	
	class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO:
			//get selected slot
			//remove that slot from doctor's slots
			update();
		}
	}
	
	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			//TODO: add created slot to database, set appointment ID based on appointment name
						
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
=======
package ultimatedesignchallenge.Doctor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;
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
		this.doctor = doctor;
		this.controller = controller;
		this.slotController = slotController;
		
		constructorGen("Doctor");
		initListeners();
		init();
		update();
	}
	
	private void init() {
		calendarPanel.getCreate().setText("Set Appointment Slot");
		
		cancel.setText("Free Slot");
		
		popup.add(update);
		popup.add(cancel);
		
		createPanel.getSave().addActionListener(new saveCreateBtnListener());
		dayPanel.getDayTable().addMouseListener(new dayTableMouseListener());
		dayPanel.getAgendaTable().addMouseListener(new agendaTableMouseListener());
		weekPanel.getWeekTable().addMouseListener(new weekTableMouseListener());
		weekPanel.getAgendaTable().addMouseListener(new weekAgendaTableMouseListener());
		cancel.addActionListener(new cancelListener());
	}
	
	@Override
	public void update() {
		//TODO:
		//grab necessary data
		calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);
		changeLabel();
//		TODO: FULFILL THE STEPS
		refreshDayView();
		refreshWeekView();
	}
	
	private void refreshDayView()
	{
		List<Slot> myFree = slotController.getFree(doctor, 
				LocalDate.of(yearToday, monthToday+1, dayToday));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.of(0, 0));
		for (int i = 0; i < 48; i++) {
			dayPanel.getDayTable().setValueAt(null, i, 1);
			for (Slot s : myFree) {
				//System.out.println(s.getStart());
				//System.out.println(count);
				if (count.equals(s.getStart())){
					//System.out.println("changed");
					dayPanel.getDayTable().setValueAt(s, i, 1);
				}
			}
			count = count.plusMinutes(30);
		}
		
		// UPDATE AGENDA
		
		//TODO:
		//clear all rows
		//use this -> clearAgenda(dayPanel.getModelAgendaTable());
		//get slots that i have set available, all of them
		//display it in the dayTable
		//display occupied slots in agenda table
	}
	
	private void refreshWeekView()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(yearToday, monthToday, dayToday);
		cal.get(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		refreshWeekViewByColumn(cal, 1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		refreshWeekViewByColumn(cal, 2);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		refreshWeekViewByColumn(cal, 3);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		refreshWeekViewByColumn(cal, 4);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		refreshWeekViewByColumn(cal, 5);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		refreshWeekViewByColumn(cal, 6);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		refreshWeekViewByColumn(cal, 7);
		
		// UPDATE AGENDA
		
		//TODO:
		//clear all rows
		//use this -> clearAgenda(weekPanel.getModelAgendaTable());
		//get slots that i have set available, all of them
		//display it in the weekTable
		//display appointments in agenda table in order of the days
	}
	

	protected void refreshWeekViewByColumn(Calendar cal, int day)
	{	
		int tempY = cal.get(Calendar.YEAR);
		int tempM = cal.get(Calendar.MONTH)+1;
		int tempD = cal.get(Calendar.DATE);
		System.out.println(tempY);
		System.out.println(tempM);
		System.out.println(tempD);
		
		List<Slot> myFree = slotController.getFree(doctor, 
				LocalDate.of(tempY, tempM, tempD));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(tempY, tempM, tempD), LocalTime.of(0, 0));
		for (int i = 0; i < 48 ; i++) {
			weekPanel.getWeekTable().setValueAt(null, i, day);
			for (Slot s : myFree) {
				if (count.equals(s.getStart())) {
					//System.out.println("changed!");
					//System.out.println(weekPanel.getWeekTable().getValueAt(i, 0));
							weekPanel.getWeekTable().setValueAt(s, i, day);
							//System.out.println(weekPanel.getWeekTable().getValueAt(i, day));
				}
			}
			count = count.plusMinutes(30);
		}
	}
	
	class dayTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getDayTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
			
		}
		
	}
	
	class agendaTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
		}
	}
	
	class weekTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = weekPanel.getWeekTable().getSelectedRow();
			int col = weekPanel.getWeekTable().getSelectedColumn();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
		}
	}
	
	class weekAgendaTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = weekPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());	
		}
	}
	
	class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO:
			//get selected slot
			//remove that slot from doctor's slots
			update();
		}
	}
	
	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			//TODO: add created slot to database, set appointment ID based on appointment name
						
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
