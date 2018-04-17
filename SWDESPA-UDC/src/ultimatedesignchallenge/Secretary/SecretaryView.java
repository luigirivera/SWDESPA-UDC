package ultimatedesignchallenge.Secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import ultimatedesignchallenge.controller.DoctorController;
import ultimatedesignchallenge.controller.SecretaryController;
import ultimatedesignchallenge.controller.SlotController;
import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.model.Slot;
import ultimatedesignchallenge.view.CalendarFramework;
import ultimatedesignchallenge.view.DoctorList;

public class SecretaryView extends CalendarFramework{
	private static final long serialVersionUID = 1L;
	private Secretary secretary;
	private SecretaryController controller;
	private SlotController slotController;
	
	public SecretaryView(Secretary secretary, SecretaryController controller, SlotController slotController) {
		super("Central Calendar Census - " + secretary.getFirstname());
		
//		this.model = model;
		this.secretary = secretary;
		this.controller = controller;
		this.slotController = slotController;
		
		constructorGen("Clinic Secretary");
		initListeners();
		init();
		update();
	}
	
	private void init() {
		
		
		createPanel.setCreateName(new JTextField());
		createPanel.add(createPanel.getCreateName());		
		createPanel.getCreateName().setBounds(10, 30, 620, 40);
		
		createPanel.setDoctors(new JComboBox<String>());
		createPanel.add(createPanel.getDoctors());
		createPanel.getDoctors().setBounds(390, 90, 120, 40);
		
		doctorList = new DoctorList();
		calendarPanel.setDoctors(new JToggleButton("Doctors"));
		calendarPanel.add(calendarPanel.getDoctors());
		calendarPanel.getDoctors().setBounds(10, 500, 250,50);
		
		cancelAll = new JMenuItem("Cancel All Meetings");
		notifyDoctor = new JMenuItem("Notify Doctor");
		notifyClient = new JMenuItem("Notify Client");
		popup.add(update);
		popup.add(notifyDoctor);
		popup.add(notifyClient);
		popup.add(cancel);
		popup.add(cancelAll);
		
		createPanel.getSave().addActionListener(new saveCreateBtnListener());
		dayPanel.getDayTable().addMouseListener(new dayTableMouseListener());
		dayPanel.getAgendaTable().addMouseListener(new agendaTableMouseListener());
		weekPanel.getWeekTable().addMouseListener(new weekTableMouseListener());
		weekPanel.getAgendaTable().addMouseListener(new weekAgendaTableMouseListener());
		cancel.addActionListener(new cancelListener());
	}
	
	@Override
	public void update() {
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
		List<Slot> myFree = slotController.getAllDoctorAppointments(LocalDate.of(yearToday, monthToday+1, dayToday));
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
	}
	
	private void refreshWeekView()
	{
		//TODO:
		//clear calendar rows
		//use this -> clearAgenda(weekPanel.modelAgendaTable);
		//check filter for which doctor/s
		//get time slots. color unoccupied slots white. color non-appointment slots (slots non of the doctors set) as gray //Custom TableRenderer only for week can be used
		//get all appointments
		//display it in the weekTable
		//display appointments in agenda table in order of the days and time //Custom TableRenderer only for week agenda can be used
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
			//TODO:
			//get selected appointment/s
			//delete those appointments from the DB
			update();
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
		update();
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