package ultimatedesignchallenge.Secretary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.util.Observable;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

//github.com/luigirivera/SWDESPA-UDC.git
import ultimatedesignchallenge.controller.SecretaryController;
import ultimatedesignchallenge.model.Appointment;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.services.AppointmentService;
import ultimatedesignchallenge.services.ClientService;
//github.com/luigirivera/SWDESPA-UDC.git
import ultimatedesignchallenge.view.CalendarFramework;
import ultimatedesignchallenge.view.DayAgendaTableRenderer;
import ultimatedesignchallenge.view.DoctorList;
import ultimatedesignchallenge.view.WeekAgendaTableRenderer;

public class SecretaryView extends CalendarFramework{
	private static final long serialVersionUID = 1L;
	private SecretaryModel model;
	private SecretaryController controller;
	
	private Doctor doctor;
	
	public SecretaryView(SecretaryModel model, SecretaryController controller) {
		super("Central Calendar Census - " + model.getSecretary().getFirstname());
		
		this.model = model;
		this.controller = controller;
		
		constructorGen("Clinic Secretary");
		init();
		initListeners();
		update(null, null);
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
		
		createPanel.setCreateName(new JTextField());
		createPanel.add(createPanel.getCreateName());		
		createPanel.getCreateName().setBounds(10, 30, 620, 40);
		
		createPanel.setDoctors(new JComboBox<String>());
		createPanel.add(createPanel.getDoctors());
		createPanel.getDoctors().setBounds(390, 90, 120, 40);
		
		doctorList = new DoctorList();
		calendarPanel.setDoctors(new JToggleButton("Doctors"));
		calendarPanel.add(calendarPanel.getDoctors());
		calendarPanel.getDoctors().setBounds(10, 10, 250, 40);
		
		setAppointment = new JMenuItem("Set Appointment");
		cancelAll = new JMenuItem("Cancel All Meetings");
		notifyDoctor = new JMenuItem("Notify Doctor");
		notifyClient = new JMenuItem("Notify Client");
		
		popup.add(setAppointment);
		popup.add(update);
		popup.add(notifyDoctor);
		popup.add(notifyClient);
		popup.add(cancel);
		popup.add(cancelAll);
		
		update.addActionListener(new updateAppointment());
		setAppointment.addActionListener(new setAppointment());
		doctorList.getDoctorList().addMouseListener(new doctorListListener());
		dayPanel.getDayTable().addMouseListener(new dayTableMouseListener());
		dayPanel.getAgendaTable().addMouseListener(new agendaTableMouseListener());
		weekPanel.getWeekTable().addMouseListener(new weekTableMouseListener());
		weekPanel.getAgendaTable().addMouseListener(new weekAgendaTableMouseListener());
		cancel.addActionListener(new cancelListener());
	}
	
	@Override
	public void update(Observable o, Object arg) {
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
		try {
			if(doctorList.getDoctorList().getSelectedValue().equals("All")) {
				System.out.println("ok!");
				List<Slot> slots = new ArrayList<Slot>();
				//slots.addAll(slotService.getAllDoctorAppointments(LocalDate.of(yearToday, monthToday+1, dayToday))); this can be good for if we start filtering
				slots.addAll(model.getAllSlots());
				LocalDateTime count = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.of(0, 0));
				for (int i = 0; i < 48; i++) {
					dayPanel.getDayTable().setValueAt(null, i, 1);
					for (Slot s : slots) {
						//System.out.println(s.getStart());
						//System.out.println(count);
						if (count.equals(s.getStart())){
							//System.out.println("changed");
							dayPanel.getDayTable().setValueAt(s, i, 1);
						}
					}
					count = count.plusMinutes(30);
				}
				
				clearAgenda(dayPanel.getModelAgendaTable());
				List<Slot> agendaList = model.getAllDoctorAppointments(LocalDate.of(yearToday, monthToday+1, dayToday));
				List<Client> allClients = model.getAllDoctorAppointmentsClients(LocalDate.of(yearToday, monthToday+1, dayToday));
				List<Doctor> allDoctors = model.getAllDoctorAppointmentsDoctors(LocalDate.of(yearToday, monthToday+1, dayToday));

				int i = 0;
				for (Slot s : agendaList) {
					Client c = allClients.get(i);
					Doctor d = allDoctors.get(i);
					String temp = "Doctor: " + d.getLastname() + ", " + d.getFirstname();
					String temp2 = "Client: " + c.getLastname() + ", " + c.getFirstname();
					String temp3 = temp + "; " + temp2;
					dayPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp3});
					i++;
				}
			} else {
				List<Doctor> doctors = model.getAllDoctors();
				for(Doctor d : doctors) {
					String temp = d.getLastname() + ", " + d.getFirstname();
					
					if(doctorList.getDoctorList().getSelectedValue().equals(temp)) {
						System.out.println("ok!");
						List<Slot> slots = new ArrayList<Slot>();
						slots.addAll(model.getAppointmentAgendaList(d, LocalDate.of(yearToday, monthToday+1, dayToday)));
						slots.addAll(model.getFreeSlots(d, LocalDate.of(yearToday, monthToday+1, dayToday)));
						LocalDateTime count = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.of(0, 0));
						for (int i = 0; i < 48; i++) {
							dayPanel.getDayTable().setValueAt(null, i, 1);
							for (Slot s : slots) {
								//System.out.println(s.getStart());
								//System.out.println(count);
								if (count.equals(s.getStart())){
									//System.out.println("changed");
									dayPanel.getDayTable().setValueAt(s, i, 1);
								}
							}
							count = count.plusMinutes(30);
						}
						
						clearAgenda(dayPanel.getModelAgendaTable());
						List<Slot> agendaList = model.getAppointmentAgendaList(d, 
								LocalDate.of(yearToday, monthToday+1, dayToday));
						List<Client> clientList = model.getAppointmentClientsList(d, 
								LocalDate.of(yearToday, monthToday+1, dayToday));

						int i = 0;
						for (Slot s : agendaList) {
							//System.out.println(s.getStart().getHour());
							//System.out.println(s.getStart().getMinute());
							//System.out.println(s.getStart().getSecond());
							
							Client c = clientList.get(i);
							
							String temp2 = "Client: " + c.getLastname() + ", " + c.getFirstname();
							dayPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp2});
							i++;
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			System.out.println("Nothing pressed yet!");
		}
		
		
		//TODO:
		//clear calendar rows
		//use this -> clearAgenda(dayPanel.modelAgendaTable);
		//check filter for which doctor/s
		//get all time slots
		//color available slots on the time column. color depends on doctor. white if none //Custom TableRenderer only for day can be used
		//get all appointments
		//display it in the dayTable
		//display appointments in agenda table //Custom TableRenderer only for day agenda can be used
		
		//PS: not sure about this on the bottom
		/*for (int row = 0 ; row < modelDayTable.getRowCount() ; row++) {
			modelDayTable.setValueAt("", row, 1);
			for (CalendarItem item : dayItems) {
				LocalDateTime tmpStartTime = item.getStart();
				LocalDateTime tmpEndTime = item.getEnd();
				LocalDateTime tmpTableTime = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), (LocalTime)modelDayTable.getValueAt(row, 0));
				if((tmpStartTime.equals(tmpTableTime) || tmpStartTime.isBefore(tmpTableTime)) &&
						tmpEndTime.isAfter(tmpTableTime)) {
					modelDayTable.setValueAt(item, row, 1);
					break;
				}
			}
		}*/
		
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
		
		try {
			if(doctorList.getDoctorList().getSelectedValue().equals("All")) {
				//System.out.println("ok!");
				slots.addAll(model.getAllSlots());
				LocalDateTime count = LocalDateTime.of(LocalDate.of(tempY, tempM, tempD), LocalTime.of(0, 0));
				for (int i = 0; i < 48; i++) {
					weekPanel.getWeekTable().setValueAt(null, i, day);
					for (Slot s : slots) {
						//System.out.println(s.getStart());
						//System.out.println(count);
						if (count.equals(s.getStart())){
							//System.out.println("changed");
							weekPanel.getWeekTable().setValueAt(s, i, day);
						}
					}
					count = count.plusMinutes(30);
				}
			}
			
			else {
				List<Doctor> doctors = model.getAllDoctors();
				for(Doctor d : doctors) {
					String temp = d.getLastname() + ", " + d.getFirstname();
					
					if(doctorList.getDoctorList().getSelectedValue().equals(temp)) {
						//System.out.println("ok!");
						slots.addAll(model.getAppointmentAgendaList(d, LocalDate.of(tempY, tempM, tempD)));
						slots.addAll(model.getFreeSlots(d, LocalDate.of(tempY, tempM, tempD)));
						LocalDateTime count = LocalDateTime.of(LocalDate.of(tempY, tempM, tempD), LocalTime.of(0, 0));
						for (int i = 0; i < 48; i++) {
							weekPanel.getWeekTable().setValueAt(null, i, day);
							for (Slot s : slots) {
								//System.out.println(s.getStart());
								//System.out.println(count);
								if (count.equals(s.getStart())){
									//System.out.println("changed");
									weekPanel.getWeekTable().setValueAt(s, i, day);
								}
							}
							count = count.plusMinutes(30);
						}
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("Nothing pressed yet!");
		}
		
		/* slots.addAll(slotService.getFree(doctor, LocalDate.of(tempY, tempM, tempD)));
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
		} */
		
		
	}
	
	private void refreshWeekAgendaTable(Calendar cal) {
		int tempY = cal.get(Calendar.YEAR);
		int tempM = cal.get(Calendar.MONTH)+1;
		int tempD = cal.get(Calendar.DATE);
		//System.out.println(tempY);
		//System.out.println(tempM);
		//System.out.println(tempD);
		
		try {
			if(doctorList.getDoctorList().getSelectedValue().equals("All")) {
				List<Slot> allAppointments = model.getAllDoctorAppointments(LocalDate.of(tempY, tempM, tempD));
				List<Client> allClients = model.getAllDoctorAppointmentsClients(LocalDate.of(tempY, tempM, tempD));
				List<Doctor> allDoctors = model.getAllDoctorAppointmentsDoctors(LocalDate.of(tempY, tempM, tempD));

				int i = 0;
				for (Slot s : allAppointments) {
					Client c = allClients.get(i);
					Doctor d = allDoctors.get(i);
					String temp = "Doctor: " + d.getLastname() + ", " + d.getFirstname();
					String temp2 = "Client: " + c.getLastname() + ", " + c.getFirstname();
					String temp3 = temp + "; " + temp2;
					weekPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp3});
					i++;
				}
			}
			
			else {
				List<Doctor> doctors = model.getAllDoctors();
				for(Doctor d : doctors) {
					String temp = d.getLastname() + ", " + d.getFirstname();
					
					if(doctorList.getDoctorList().getSelectedValue().equals(temp)) {
						List<Slot> agendaList = model.getAppointmentAgendaList(d, 
								LocalDate.of(tempY, tempM, tempD));
						List<Client> clientList = model.getAppointmentClientsList(d, 
								LocalDate.of(tempY, tempM, tempD));

						int i = 0;
						for (Slot s : agendaList) {
							Client c = clientList.get(i);
							String temp2 = "Client: " + c.getLastname() + ", " + c.getFirstname();
							weekPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp2});
							i++;
						}
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println("Nothing pressed yet!");
		}
	}
	
	class DayTableRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		private int TimeToRowNumber(String eTime) {

	        switch (eTime) {

	            case "0:30":
	                return 1;

	            case "1:00":
	                return 2;

	            case "1:30":
	                return 3;

	            case "2:00":
	                return 4;

	            case "2:30":
	                return 5;

	            case "3:00":
	                return 6;

	            case "3:30":
	                return 7;

	            case "4:00":
	                return 8;

	            case "4:30":
	                return 9;

	            case "5:00":
	                return 10;

	            case "5:30":
	                return 11;

	            case "6:00":
	                return 12;

	            case "6:30":
	                return 13;

	            case "7:00":
	                return 14;

	            case "7:30":
	                return 15;

	            case "8:00":
	                return 16;

	            case "8:30":
	                return 17;

	            case "9:00":
	                return 18;

	            case "9:30":
	                return 19;

	            case "10:00":
	                return 20;

	            case "10:30":
	                return 21;

	            case "11:00":
	                return 22;

	            case "11:30":
	                return 23;

	            case "12:00":
	                return 24;

	            case "12:30":
	                return 25;

	            case "13:00":
	                return 26;

	            case "13:30":
	                return 27;

	            case "14:00":
	                return 28;

	            case "14:30":
	                return 29;

	            case "15:00":
	                return 30;

	            case "15:30":
	                return 31;

	            case "16:00":
	                return 32;

	            case "16:30":
	                return 33;

	            case "17:00":
	                return 34;

	            case "17:30":
	                return 35;

	            case "18:00":
	                return 36;

	            case "18:30":
	                return 37;

	            case "19:00":
	                return 38;

	            case "19:30":
	                return 39;

	            case "20:00":
	                return 40;

	            case "20:30":
	                return 41;

	            case "21:00":
	                return 42;

	            case "21:30":
	                return 43;

	            case "22:00":
	                return 44;

	            case "22:30":
	                return 45;

	            case "23:00":
	                return 46;

	            case "23:30":
	                return 47;

	            //if time is 0:00    
	            default:
	                return 0;
	        }
	    }
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
				int row, int column) {
			
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			
			if(column == 0)
			{
				//TODO:
				setHorizontalAlignment(SwingConstants.LEFT);
				setBackground(Color.WHITE);
				
				/*	if(there is a doctor who has the slot)
				 * 		if(>1 doctor share)
				 * 			setBackground(Color.ORANGE);
				 * 		else
				 * 			setBackground(color of the doctor)
				 * 	else
				 * 		setBackground(Color.BLACK);
				 */
			}
			
			if (table.getValueAt(row, column) != null && column == 1)
			{
				String sval = String.valueOf(table.getValueAt(row, column));
				
				String tempTime = sval.substring(sval.indexOf("T")+1, sval.indexOf(" ")+37);
				
				String sTime = sval.substring(sval.indexOf("T") + 1, sval.indexOf(" ")+17);
				String eTime = tempTime.substring(tempTime.indexOf("T")+1, tempTime.length());
				
				String TmonthAndDay = sval.substring(sval.indexOf("-")+1, sval.indexOf("T"));
				String[] monthAndDay = TmonthAndDay.split("-");
				
				String year = sval.substring(sval.indexOf(" ") +1, sval.indexOf("-"));
				String month = monthAndDay[0];
				String day = monthAndDay[1];
				
				String firstTime = String.valueOf(table.getValueAt(row, 0));
				
				
				//getDoctor
//				if(firstTime.equals(sTime))
//					setBackground(Color.GREEN);
//				else
//					setBackground(Color.WHITE);
				
				/*	if(table.getValueAt(row, column) == null)
				 * 		setBackground(Color.BLACK);
				 * 	if(there is appointment)
				 * 		setBackground(Color.firstdoctor.getColor());
				 * 	else
				 * 		setBackground(Color.WHITE)
				 */

				
			}
			else
				setBackground(Color.WHITE);
			
			
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}
	
	class WeekTableRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			
			if(column == 0)
			{
				setHorizontalAlignment(SwingConstants.LEFT);
			}
			else
			{
//				if(table.getValueAt(row, column) == null)
//					setBackground(Color.BLACK);
//				else
//					setBackground(Color.GREEN);
				
				/*	if(table.getValueAt(row, column) == null)
				 * 		setBackground(Color.BLACK);
				 * 	else if(there an appointment)
				 * 		setBackground(color of the doctor)
				 * 	else
				 * 		setBackground(Color.WHITE)
				 */
				
				//TODO:
				/* if(this slot is not set by any doctor)
				 * 	setBackground(Color.BLACK);
				 * else if(this slot is unoccupied)
				 * 	setBackground(Color.WHITE);
				 * else
				 * 	setBackground(color of doctor who has an appointment on this slot)
				 * 
				 */
			}
			
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}

	
	class updateAppointment implements ActionListener{

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
				
				if(!doctorList.getDoctorList().getSelectedValue().equals("All"))
					controller.updateFree(doctor, temp, startDateTime, endDateTime);
			}
			update(null, null);
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
	
	class setAppointment implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new JPanel();
			JRadioButton walkin = new JRadioButton("Walk In?");
			JComboBox<String> clients = new JComboBox<String>();
			
			panel.add(walkin);
			panel.add(clients);
			
			clients.setVisible(true);
			
			JPanel panel2 = new JPanel();
			JTextField name = new JTextField();
			name.setPreferredSize(new Dimension(200,30));
			panel2.add(name);
			List<Client> clientC = model.getAllClients();
			
			for(Client c : clientC)
				clients.addItem(c.getLastname() + ", " + c.getFirstname());
			
			walkin.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean toggle = !walkin.isSelected();
					
					clients.setVisible(toggle);
					clients.setEnabled(toggle);
					
				}
				
			});
			int result = JOptionPane.showConfirmDialog(null, panel, "Set Appointment", JOptionPane.OK_CANCEL_OPTION);
			
			if(result == JOptionPane.OK_OPTION)
			{
				if(walkin.isSelected())
				{
					int result2 = JOptionPane.showConfirmDialog(null, panel2, "Set Appointment", JOptionPane.OK_CANCEL_OPTION);
					if(!name.getText().trim().isEmpty())
					{
						//TODO: Appointments
					}
					else
						JOptionPane.showMessageDialog(null, "Empty name", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					//TODO: appointments
				}
					
			}
			
		}
		
	}
	
	class doctorListListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			String strDoctor = doctorList.getDoctorList().getSelectedValue();
			List<Doctor> doctors = new ArrayList<Doctor>();
			doctors = model.getAllDoctors();
			
			for(int i = 0; i < doctors.size(); i++) {
				if(!strDoctor.equalsIgnoreCase("All") && doctors.get(i).getFirstname().equals(strDoctor.split(", ")[1]) && doctors.get(i).getLastname().equals(strDoctor.split(", ")[0]))
					doctor = doctors.get(i);
			}
			
			update(null, null);
			
		}
	}
	
	class dayTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getDayTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& modelDayTable.getValueAt(row, 1) instanceof CalendarItem*/)
			{
				//TODO:
				/*if(this is NOT a recurring meeting)
				 *	cancellAll.setEnabled(false);
				 *else
				 *	cancellAll.setEnabled(true);
				 *
				 *if(client does not have account)
				 *	notifyClient.setEnabled(false);
				 *else
				 *	notifyClient.setEnabled(true);
				 *
				 *if(conflicting appointments && conflicting doctor availability because of filter)
				 *	setAppointemnt.setEnabled(false);
				 *else
				 *	setAppointemnt.setEnabled(true);
				 */
					popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
			}
		}
	}
	
	class agendaTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if there is a selected item*/)
			{
				//TODO:
				/*if(this is NOT a recurring meeting)
				 *	cancellAll.setEnabled(false);
				 *else
				 *	cancellAll.setEnabled(true);
				 *
				 *if(client does not have account)
				 *	notifyClient.setEnabled(false);
				 *else
				 *	notifyClient.setEnabled(true);
				 */
				popup.show(dayPanel.getAgendaTable(), arg0.getX(), arg0.getY());
			}	
		}	
	}
	
	class weekTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = weekPanel.getWeekTable().getSelectedRow();
			int col = weekPanel.getWeekTable().getSelectedColumn();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if there is a selected item*/)
			{
				//TODO:
				/*if(this is NOT a recurring meeting)
				 *	cancellAll.setEnabled(false);
				 *else
				 *	cancellAll.setEnabled(true);
				 *
				 *if(client does not have account)
				 *	notifyClient.setEnabled(false);
				 *else
				 *	notifyClient.setEnabled(true);
				 *
				 *if(conflicting appointments && conflicting doctor availability because of filter)
				 *	setAppointemnt.setEnabled(false);
				 *else
				 *	setAppointemnt.setEnabled(true);
				 */
					popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
			}
		}
	}
	
	class weekAgendaTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row =  weekPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if there is a selected item*/)
			{
				//TODO:
				/*if(this is NOT a recurring meeting)
				 *	cancellAll.setEnabled(false);
				 *else
				 *	cancellAll.setEnabled(true);
				 *
				 *if(client does not have account)
				 *	notifyClient.setEnabled(false);
				 *else
				 *	notifyClient.setEnabled(true);
				 */
					popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
			}	
		}
	}
	
	class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			Appointment apt = new Appointment();
			
			List<Slot> slots = new ArrayList<Slot>();
			ClientService clientService = new ClientService();
			int[] sRows = dayPanel.getDayTable().getSelectedRows();
			
			for(int i = 0; i < sRows.length; i++) {
				slots.add((Slot)dayPanel.getModelDayTable().getValueAt(sRows[i], 1));
			}
			
			apt.setDoctor(doctor);
			apt.setSlots(slots);
			apt.setId(model.getAppointmentId(slots.get(0)));
			apt.setClient(clientService.getClient(clientService.getClientViaAptID(apt.getId())));
			
			AppointmentService aptService = new AppointmentService();
			aptService.deleteAppointment(apt);
			
			//TODO:
			//get selected appointment/s
			//delete those appointments from the DB
			update(null, null);
		}
	}
	
	private void setAppointment() {
		LocalDateTime startDateTime, endDateTime;
		
		try {
			if(createPanel.getCreateName().getText().trim().isEmpty())
				throw new Exception("Please enter a name");
			
			startDateTime = LocalDateTime.of(LocalDate.of((int)createPanel.getYear().getSelectedItem(), (int)createPanel.getMonth().getSelectedItem(),
					(int)createPanel.getDay().getSelectedItem()), (LocalTime) createPanel.getStartTime().getSelectedItem());
			
			endDateTime = LocalDateTime.of(LocalDate.of((int)createPanel.getYear().getSelectedItem(), (int)createPanel.getMonth().getSelectedItem(),
					(int)createPanel.getDay().getSelectedItem()), (LocalTime) createPanel.getEndTime().getSelectedItem());
			
			
			System.out.println(startDateTime);
			System.out.println(endDateTime);
			
			//TODO:
			//grab doctor selected
			//check if doctor has that slot
			//check if that slot is free
			//create user object for the walk-in client
			
			//create an Appointment object? and pass the walk-in client and doctor selected
			
			//if(recurringAppRB.isSelected())
				//set recurring appointment    controller.addTask(createName.getText(), startDateTime, some stuff to add);
			
			//else
				//set single appointment       controller.addEvent(createName.getText(), startDateTime, endDateTime);
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		toggleCreateView(false);
		update(null, null);
	}

	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			setAppointment();
			/*SlotBuilder builder = new SlotBuilder();
			SlotC slot = builder.buildDoc1Available(startTime.getSelectedItem().toString(), endTime.getSelectedItem().toString());*/
			// to do: add created slot to database, set appointment ID based on appointment name
			
			//if(recurringAppRB.isSelected())
				// to do: also set recurringID 
		}
	}
	
}