package ultimatedesignchallenge.Secretary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import ultimatedesignchallenge.model.Secretary;
import ultimatedesignchallenge.view.CalendarFramework;
import ultimatedesignchallenge.view.DoctorList;

public class SecretaryView extends CalendarFramework{
	private static final long serialVersionUID = 1L;
	private Secretary secretary;
	
	public SecretaryView(Secretary secretary) {
		super("Central Calendar Census - " + secretary.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.secretary = secretary;
		
		constructorGen("Clinic Secretary");
		instantiate();
		init();
		initListeners();
		update();
	}
	
	private void instantiate() {
		createPanel.setCreateName(new JTextField());
		createPanel.setDoctors(new JComboBox<String>());
		doctorList = new DoctorList();
		
		calendarPanel.setDoctors(new JToggleButton("Doctors"));
		calendarPanel.add(calendarPanel.getDoctors());
		calendarPanel.getDoctors().setBounds(10, 500, 250,50);
		
		notifyDoctor = new JMenuItem("Notify Doctor");
		notifyClient = new JMenuItem("Notify Client");
	}
	
	private void init() {
		popup.add(notifyDoctor);
		popup.add(notifyClient);
		
		createPanel.add(createPanel.getCreateName());		
		createPanel.getCreateName().setBounds(10, 30, 620, 40);
		
		createPanel.add(createPanel.getDoctors());
		createPanel.getDoctors().setBounds(390, 90, 120, 40);
		
		cancelAll = new JMenuItem("Cancel All Meetings");
		
		popup.add(update);
		popup.add(cancel);
		popup.add(cancelAll);
		
		createPanel.getSave().addActionListener(new saveCreateBtnListener());
		dayPanel.getDayTable().addMouseListener(new dayTableMouseListener());
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