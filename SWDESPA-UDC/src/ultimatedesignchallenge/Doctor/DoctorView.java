package ultimatedesignchallenge.Doctor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import ultimatedesignchallenge.controller.DoctorController;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.SlotService;
import ultimatedesignchallenge.view.CalendarFramework;
import ultimatedesignchallenge.view.CalendarObserver;
import ultimatedesignchallenge.view.DayAgendaTableRenderer;
import ultimatedesignchallenge.view.DayTableRenderer;
import ultimatedesignchallenge.view.WeekAgendaTableRenderer;
import ultimatedesignchallenge.view.WeekTableRenderer;

public class DoctorView extends CalendarFramework implements CalendarObserver{
	private static final long serialVersionUID = 1L;
	private Doctor doctor;
	private DoctorController controller;
	private SlotService slotService;
	
	public DoctorView(Doctor doctor, DoctorController controller) {
		super("Doctor Calendar - " + doctor.getFirstname());
		
//		this.model = model;
		this.doctor = doctor;
		this.controller = controller;
		slotService = new SlotService();
		
		constructorGen("Doctor");
		init();
		initListeners();
		update();
	}
	
	private void init() {
		topPanel.setCalendar(new JToggleButton("Calendar"));
		topPanel.setAgenda(new JToggleButton("Agenda"));
		topPanel.add(topPanel.getCalendar());
		topPanel.add(topPanel.getAgenda());
		topPanel.getCalendar().setBounds(780, 15, 80, 40);
		topPanel.getAgenda().setBounds(850, 15, 70, 40);
		topPanel.getButtonGroup().add(topPanel.getCalendar());
		topPanel.getButtonGroup().add(topPanel.getAgenda());
		
		calendarPanel.setCreate(new JButton("Set Appointment Slot"));
		calendarPanel.add(calendarPanel.getCreate());
		calendarPanel.getCreate().setBounds(10, 10, 250, 40);
		
		cancel.setText("Free Slot");
		
		popup.add(update);
		popup.add(cancel);
		
		update.addActionListener(new updateSlot());
		calendarPanel.getCreate().addActionListener(new createbtnListener());
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
		List<Slot> myFree = slotService.getFree(doctor, 
				LocalDate.of(yearToday, monthToday+1, dayToday));
		List<Slot> myTaken = slotService.getTakenDoctor(doctor, LocalDate.of(yearToday, monthToday+1, dayToday));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.of(0, 0));
		for (int i = 0; i < 48; i++) {
			dayPanel.getDayTable().setValueAt(null, i, 1);
			for (Slot s : myFree) {
				
				if (count.equals(s.getStart())){
					dayPanel.getDayTable().setValueAt(s, i, 1);
				}
			}
			for (Slot s : myTaken) {
				
				if (count.equals(s.getStart())){
					dayPanel.getDayTable().setValueAt(s, i, 1);
				}
			}
			count = count.plusMinutes(30);
		}
		
		clearAgenda(dayPanel.getModelAgendaTable());
		List<Slot> agendaList = slotService.getAppointmentAgendaList(doctor, 
				LocalDate.of(yearToday, monthToday+1, dayToday));
		List<Client> clientList = slotService.getAppointmentClientsList(doctor, 
				LocalDate.of(yearToday, monthToday+1, dayToday));

		int i = 0;
		for (Slot s : agendaList) {
			//System.out.println(s.getStart().getHour());
			//System.out.println(s.getStart().getMinute());
			//System.out.println(s.getStart().getSecond());
			
			Client c = clientList.get(i);
			
			String temp = "Client: " + c.getLastname() + ", " + c.getFirstname();
			dayPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp});
			i++;
		}
		
		// UPDATE AGENDA
		
		//TODO:
		//clear all rows
		//use this -> clearAgenda(dayPanel.getModelAgendaTable());
		//get slots that i have set available, all of them
		//display it in the dayTable
		//display occupied slots in agenda table
		
		dayPanel.getDayTable().setDefaultRenderer(dayPanel.getDayTable().getColumnClass(0), new DayTableRenderer());
		dayPanel.getAgendaTable().setDefaultRenderer(dayPanel.getAgendaTable().getColumnClass(0), new DayAgendaTableRenderer());
	}
	
	private void refreshWeekView()
	{
		clearAgenda(weekPanel.getModelAgendaTable());
		
		Calendar cal = Calendar.getInstance();
		cal.set(yearToday, monthToday, dayToday);
		cal.get(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		refreshWeekViewByColumn(cal, 1);
		refreshWeekAgendaTable(cal);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		refreshWeekViewByColumn(cal, 2);
		refreshWeekAgendaTable(cal);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		refreshWeekViewByColumn(cal, 3);
		refreshWeekAgendaTable(cal);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		refreshWeekViewByColumn(cal, 4);
		refreshWeekAgendaTable(cal);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		refreshWeekViewByColumn(cal, 5);
		refreshWeekAgendaTable(cal);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		refreshWeekViewByColumn(cal, 6);
		refreshWeekAgendaTable(cal);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		refreshWeekViewByColumn(cal, 7);
		refreshWeekAgendaTable(cal);
		
		// UPDATE AGENDA
		
		//TODO:
		//clear all rows
		//use this -> clearAgenda(weekPanel.getModelAgendaTable());
		//get slots that i have set available, all of them
		//display it in the weekTable
		//display appointments in agenda table in order of the days
		
		weekPanel.getAgendaTable().setDefaultRenderer(weekPanel.getAgendaTable().getColumnClass(0), new WeekTableRenderer());
		weekPanel.getAgendaTable().setDefaultRenderer(weekPanel.getAgendaTable().getColumnClass(0), new WeekAgendaTableRenderer());
	}
	
	private void refreshWeekViewByColumn(Calendar cal, int day)
	{	
		List<Slot> slots = new ArrayList<Slot>();
		
		int tempY = cal.get(Calendar.YEAR);
		int tempM = cal.get(Calendar.MONTH)+1;
		int tempD = cal.get(Calendar.DATE);
		//System.out.println(tempY);
		//System.out.println(tempM);
		//System.out.println(tempD);
		
		slots.addAll(slotService.getFree(doctor, LocalDate.of(tempY, tempM, tempD)));
		slots.addAll(slotService.getTakenDoctor(doctor, LocalDate.of(yearToday, monthToday+1, dayToday)));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(tempY, tempM, tempD), LocalTime.of(0, 0));
		for (int i = 0; i < 48 ; i++) {
			weekPanel.getWeekTable().setValueAt(null, i, day);
			for (Slot s : slots) {
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
	
	private void refreshWeekAgendaTable(Calendar cal) {
		int tempY = cal.get(Calendar.YEAR);
		int tempM = cal.get(Calendar.MONTH)+1;
		int tempD = cal.get(Calendar.DATE);
		//System.out.println(tempY);
		//System.out.println(tempM);
		//System.out.println(tempD);
		
		List<Slot> agendaList = slotService.getAppointmentAgendaList(doctor, 
				LocalDate.of(tempY, tempM, tempD));
		List<Client> clientList = slotService.getAppointmentClientsList(doctor, 
				LocalDate.of(tempY, tempM, tempD));

		int i = 0;
		for (Slot s : agendaList) {
			Client c = clientList.get(i);
			String temp = "Client: " + c.getLastname() + ", " + c.getFirstname();
			weekPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp});
			i++;
		}
	}
	
	class updateSlot implements ActionListener{
		JComboBox<Integer> month, day, year;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Slot temp = new Slot();
			temp = (Slot)dayPanel.getDayTable().getValueAt(dayPanel.getDayTable().getSelectedRow(), dayPanel.getDayTable().getSelectedColumn());
			
			JPanel panel = new JPanel();
			JComboBox<LocalTime> startTime, endTime;
			
			startTime = new JComboBox<LocalTime>();
			endTime = new JComboBox<LocalTime>();
			
			List<Integer> years = new ArrayList<Integer>();
			
			for(int i = 100; i >0; i--)
				years.add(Year.now().minusYears(i).getValue());
			
			years.add(Year.now().getValue());
			
			for(int i = 1; i <=100; i++)
				years.add(Year.now().plusYears(i).getValue());
			
			Integer[] yearsA = years.toArray(new Integer[years.size()]);
			
			Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
			
			month = new JComboBox<Integer>(months);
			day = new JComboBox<Integer>();
			year = new JComboBox<Integer>(yearsA);
			
			month.addActionListener(new checkDaysListener());
			year.addActionListener(new checkDaysListener());
			
			panel.add(month);
			panel.add(day);
			panel.add(year);
			panel.add(startTime);
			panel.add(endTime);
			
			setToday();
			LocalTime tmpTime = LocalTime.of(0, 0);
			for(int i=0 ; i<48 ; i++) {
				startTime.addItem(tmpTime);
				endTime.addItem(tmpTime);
				tmpTime = tmpTime.plusMinutes(30);
			}
			
			int result = JOptionPane.showConfirmDialog(null, panel, "Update Slot", JOptionPane.OK_CANCEL_OPTION);
			
			if (result==JOptionPane.OK_OPTION) {
				LocalDateTime startDateTime = LocalDateTime.of(LocalDate.of(year.getItemAt(year.getSelectedIndex()), month.getItemAt(month.getSelectedIndex()),
						day.getItemAt(day.getSelectedIndex())), startTime.getItemAt(startTime.getSelectedIndex()));
				
				LocalDateTime endDateTime = LocalDateTime.of(LocalDate.of(year.getItemAt(year.getSelectedIndex()), month.getItemAt(month.getSelectedIndex()),
						day.getItemAt(day.getSelectedIndex())), endTime.getItemAt(endTime.getSelectedIndex()));
				
				controller.updateFree(temp, startDateTime, endDateTime);
			}
			update();
		}
		
		private void setToday()
		{
			LocalDateTime now = LocalDateTime.now();
			month.setSelectedItem(now.getMonth().getValue());
			year.setSelectedItem(now.getYear());
			
			day.removeAllItems();
			
			for(int i = 1; i <=now.getMonth().length(Year.isLeap(now.getYear())); i++)
				day.addItem(i);
			
			day.setSelectedItem(now.getDayOfMonth());
		}
		
		class checkDaysListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int daysC = Month.of((int)month.getSelectedItem()).length(Year.isLeap((int)year.getSelectedItem()));
				
				day.removeAllItems();
				
				for(int i = 1; i<= daysC; i++)
					day.addItem(i);
								
			}
			
		}
		
	}
	
	class createbtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleCreateView(true);
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
				popup.show(dayPanel.getAgendaTable(), arg0.getX(), arg0.getY());
		}
	}
	
	class weekTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = weekPanel.getWeekTable().getSelectedRow();
			int col = weekPanel.getWeekTable().getSelectedColumn();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(weekPanel.getWeekTable(), arg0.getX(), arg0.getY());
		}
	}
	
	class weekAgendaTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = weekPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if this slot is free*/)
				popup.show(weekPanel.getAgendaTable(), arg0.getX(), arg0.getY());
		}
	}
	
	class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			slotService = new SlotService();
			Slot temp = new Slot();
			temp = (Slot)dayPanel.getDayTable().getValueAt(dayPanel.getDayTable().getSelectedRow(), dayPanel.getDayTable().getSelectedColumn());
			
			slotService.deleteSlot(temp.getId());
			update();
		}
	}
	
	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {			
			//TODO: add created slot to database, set appointment ID based on appointment name
						
			try {
				if(createPanel.getRecurring().isSelected() == true) {
					controller.createFreeRecurring(LocalDateTime.of(LocalDate.of((int)createPanel.getYear().getSelectedItem(), (int)createPanel.getMonth().getSelectedItem(),
					(int)createPanel.getDay().getSelectedItem()), (LocalTime) createPanel.getStartTime().getSelectedItem()), LocalDateTime.of(LocalDate.of((int)createPanel.getYear().getSelectedItem(), (int)createPanel.getMonth().getSelectedItem(),
							(int)createPanel.getDay().getSelectedItem()), (LocalTime) createPanel.getEndTime().getSelectedItem()), createPanel.getRecurrence().getSelectedIndex());
					
					
					
				}else {
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
