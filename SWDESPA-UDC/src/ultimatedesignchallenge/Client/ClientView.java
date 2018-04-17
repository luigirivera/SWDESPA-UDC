package ultimatedesignchallenge.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import ultimatedesignchallenge.controller.SlotBuilder;
import ultimatedesignchallenge.controller.SlotController;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.view.CalendarFramework;
import ultimatedesignchallenge.view.DayAgendaTableRenderer;
import ultimatedesignchallenge.view.DayTableRenderer;
import ultimatedesignchallenge.view.DoctorList;
import ultimatedesignchallenge.view.WeekAgendaTableRenderer;
import ultimatedesignchallenge.view.WeekTableRenderer;
import ultimatedesignchallenge.services.SlotService;
import ultimatedesignchallenge.model.Slot;

public class ClientView extends CalendarFramework{
	private static final long serialVersionUID = 1L;
	private Client client;
	private SlotService slotService;
	private SlotController slotController;
	
	public ClientView(Client client){
		super("Client Calendar - " + client.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.client = client;
		
		constructorGen("Client");
		init();
		initListeners();
		update();
		
	}
	
	private void init()
	{
		createPanel.setDoctors(new JComboBox<String>());
		createPanel.add(createPanel.getDoctors());
		createPanel.getDoctors().setBounds(390, 90, 120, 40);
		
		doctorList = new DoctorList();
		calendarPanel.setDoctors(new JToggleButton("Doctors"));
		calendarPanel.add(calendarPanel.getDoctors());
		calendarPanel.getDoctors().setBounds(10, 10, 250, 40);
		
		setAppointment = new JMenuItem("Set Appointment");
		cancelAll = new JMenuItem("Cancel All Meetings");
		
		popup.add(setAppointment);
		popup.add(update);
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
		System.out.println(monthToday + " " + yearToday + " " + dayToday);
		//this is a nullpointerexception but in doctorview it is not. idek why. please help me and my stupidity - pdlr
		List<Slot> freeThis = slotController.getCFree(LocalDate.of(yearToday, monthToday+1, dayToday));
		LocalDateTime count = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.of(0, 0));
		
		for (int i = 0; i < 48; i++) {
			dayPanel.getDayTable().setValueAt(null, i, 1);
			for (Slot s : freeThis) {
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
		
//		TODO: updating agenda
//		List<Slot> agendaList = slotController.getAppointmentAgendaList(doctor, 
//				LocalDate.of(yearToday, monthToday+1, dayToday));
//		List<Client> clientList = slotController.getAppointmentClientsList(doctor, 
//				LocalDate.of(yearToday, monthToday+1, dayToday));
//		
//		for (Slot s : agendaList) {
//			int i = 0;
//			//System.out.println(s.getStart().getHour());
//			//System.out.println(s.getStart().getMinute());
//			//System.out.println(s.getStart().getSecond());
//			
//			Client c = clientList.get(i);
//			
//			String temp = "Client: " + c.getLastname() + ", " + c.getFirstname();
//			dayPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp});
//			i++;
//		}
		
		//TODO:
		//supposedly clears calendar rows
		//use this -> clearAgenda(dayPanel.modelAgendaTable);
		//check filter for which doctor/s
		//get slots that the doctor/s have set available, all of them
		//color available slots on the time column. color depends on doctor //Custom TableRenderer only for day can be used
		//get all of my appointments in color
		//get all other appointments in redacted
		//display it in the dayTable //Custom TableRenderer only for day can be used
		//display occupied slots in agenda table, colored and redacted //Custom TableRenderer only for day agenda can be used
		
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
		//TODO: refreshWeekAgendaTable(Calendar cal){}
		
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
		
		//TODO:
		//clear calendar rows
		//use this -> clearAgenda(weekPanel.modelAgendaTable);
		//check filter for which doctor/s
		//get time slots. color unoccupied slots white. color non-appointment slots (slots non of the doctors set) as black //Custom TableRenderer only for week can be used
		//get all of my appointments in color //Custom TableRenderer only for week can be used
		//get all other appointments in redacted //Custom TableRenderer only for week can be used
		//display it in the weekTable
		//display appointments in agenda table in order of the days and time, colored and redacted //Custom TableRenderer only for week agenda can be used
	
		weekPanel.getAgendaTable().setDefaultRenderer(weekPanel.getAgendaTable().getColumnClass(0), new WeekTableRenderer());
		weekPanel.getAgendaTable().setDefaultRenderer(weekPanel.getAgendaTable().getColumnClass(0), new WeekAgendaTableRenderer());
	}
	
	private void refreshWeekViewByColumn(Calendar cal, int day)
	{	
		int tempY = cal.get(Calendar.YEAR);
		int tempM = cal.get(Calendar.MONTH)+1;
		int tempD = cal.get(Calendar.DATE);
		//System.out.println(tempY);
		//System.out.println(tempM);
		//System.out.println(tempD);
		
		List<Slot> myFree = slotController.getCFree(LocalDate.of(tempY, tempM, tempD));
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
	
	private void refreshWeekAgendaTable(Calendar cal) {
//		int tempY = cal.get(Calendar.YEAR);
//		int tempM = cal.get(Calendar.MONTH)+1;
//		int tempD = cal.get(Calendar.DATE);
//		//System.out.println(tempY);
//		//System.out.println(tempM);
//		//System.out.println(tempD);
//		
//		List<Slot> agendaList = slotController.getAppointmentAgendaList(doctor, 
//				LocalDate.of(tempY, tempM, tempD));
//		List<Client> clientList = slotController.getAppointmentClientsList(doctor, 
//				LocalDate.of(tempY, tempM, tempD));
//		
//		for (Slot s : agendaList) {
//			int i = 0;
//			Client c = clientList.get(i);
//			String temp = "Client: " + c.getLastname() + ", " + c.getFirstname();
//			weekPanel.getModelAgendaTable().addRow(new Object[]{s.getStart(), s.getEnd(), temp});
//			i++;
//		}
	}
	
	class updateAppointment implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class setAppointment implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JPanel panel = new JPanel();
			JRadioButton recurring = new JRadioButton("Recurring");
			JComboBox<String> recurrence = new JComboBox<String>();
			
			panel.add(recurring);
			panel.add(recurrence);
			
			recurrence.setVisible(false);
			recurrence.addItem("1 Week");
			recurrence.addItem("2 Weeks");
			recurrence.addItem("3 Weeks");
			recurrence.addItem("4 Weeks");
			
			recurring.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean toggle = recurring.isSelected();
					
					recurrence.setVisible(toggle);
					recurrence.setEnabled(toggle);
					
				}
				
			});
			int result = JOptionPane.showConfirmDialog(null, panel, "Set Appointment", JOptionPane.OK_CANCEL_OPTION);
			//TODO: set the appointment based on values
		}
		
	}
	
	class doctorListListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			doctorList.getDoctorList().getSelectedValuesList();
			update();
			
		}
	}
	
	class dayTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getDayTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if there is a selected item*/)
			{
				//TODO:
				/*if(this is NOT a recurring meeting)
				 *	disable cancelAll
				 *else
				 *	enable cancelAll
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
				 *	disable cancelAll
				 *else
				 *	enable cancelAll
				 *
				 */
					popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
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
				 *	disable cancelAll
				 *else
				 *	enable cancelAll
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
			int row = weekPanel.getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& if there is a selected item*/)
			{
				//TODO:
				/*if(this is NOT a recurring meeting)
				 *	disable cancelAll
				 *else
				 *	enable cancelAll
				 *
				 */
					popup.show(dayPanel.getDayTable(), arg0.getX(), arg0.getY());
			}
		}
	}
	
	class cancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO:
			//get selected appointment/s
			//delete those appointments from the DB
			update();
		}
	}


	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			SlotBuilder builder = new SlotBuilder();
			/*if(doctorsCBList.getSelectedItem() == "doctor1")
				slot = builder.buildAppointment(startTime.getSelectedItem().toString(), endTime.getSelectedItem().toString(), model.getFirstname());
			else
				slot = builder.buildDoc2Appointment(startTime.getSelectedItem().toString(), endTime.getSelectedItem().toString(), model.getFirstname());*/
			// to do: add created slot to database, set appointment ID based on appointment name
			
			//if(recurringAppRB.isSelected())
				// to do: also set recurringID 
			
			setAppointment();
		}
	}
	
	private void setAppointment() {
		LocalDateTime startDateTime, endDateTime;
		
		try {
			
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
			//create an Appointment object? and pass the client and doctor selected
			
			//if(recurringAppRB.isSelected())
				//set recurring appointment    controller.addTask(createName.getText(), startDateTime, some stuff to add);
			
			//else
				//set single appointment       controller.addEvent(createName.getText(), startDateTime, endDateTime);
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		toggleCreateView(false);
		update();
	}
}