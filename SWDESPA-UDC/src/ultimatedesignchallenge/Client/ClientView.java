package ultimatedesignchallenge.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import ultimatedesignchallenge.controller.SlotBuilder;
import ultimatedesignchallenge.controller.SlotC;
import ultimatedesignchallenge.model.Client;
import ultimatedesignchallenge.view.CalendarFramework;
import ultimatedesignchallenge.view.DoctorList;
import ultimatedesignchallenge.view.dayTableMouseListener;

public class ClientView extends CalendarFramework{
	private static final long serialVersionUID = 1L;
	private Client client;
	
	public ClientView(Client client){
		super("Client Calendar - " + client.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.client = client;
		
		constructorGen("Client");
		init();
		initListeners();
		
	}
	
	private void init()
	{
		createPanel.setDoctors(new JComboBox<String>());
		createPanel.add(createPanel.getDoctors());
		createPanel.getDoctors().setBounds(390, 90, 120, 40);
		
		cancelAll = new JMenuItem("Cancel All Meetings");
		doctorList = new DoctorList();
		
		calendarPanel.setDoctors(new JToggleButton("Doctors"));
		calendarPanel.add(calendarPanel.getDoctors());
		calendarPanel.getDoctors().setBounds(10, 500, 250,50);
		
		popup.add(update);
		popup.add(cancel);
		popup.add(cancelAll);
		
		createPanel.getSave().addActionListener(new saveCreateBtnListener());
		dayPanel.getDayTable().addMouseListener(new dayTableMouseListener());
	}
	
	@Override
	public void update() {
		//grab necessary data
		dayPanel.refreshDayTable();//method does not exist
		dayPanel.refreshAgendaTable();//method does note exist
		weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);
		weekPanel.refreshAgendaTable();//method does not exist
		monthPanel.refreshCalendar(monthToday, yearToday, validCells);
		monthPanel.refreshAgendaTable();//method does not exist
		calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		
	}
	
	class dayTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.getDayTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& modelDayTable.getValueAt(row, 1) instanceof CalendarItem*/)
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
			SlotBuilder builder = new SlotBuilder();
			SlotC slot;
			
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
			
			//grab doctor selected
			
			//create an Appointment object? and pass the client and doctor selected
			
			//if(recurringAppRB.isSelected())
				//set recurring appointment    controller.addTask(createName.getText(), startDateTime, some stuff to add);
			
			//else
				//set single appointment       controller.addEvent(createName.getText(), startDateTime, endDateTime);
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		//view.toggleCreateView(false);
		//view.update();
	}
}