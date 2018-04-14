package ultimatedesignchallenge.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ultimatedesignchallenge.controller.SlotBuilder;
import ultimatedesignchallenge.controller.SlotC;
import ultimatedesignchallenge.model.User;

public class ClientView extends CalendarFramework{
	private static final long serialVersionUID = 1L;
	private User model;
	
	public ClientView(User model){
		super("Client Calendar - " + model.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.model = model;
		
		constructorGen("Client");
		initListeners();
		init();
	}
	
	private void init()
	{
		save.addActionListener(new saveCreateBtnListener());
	}
	
	
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
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
		}
	}
	
	/*private void saveCreation() {
		Appointment appointment = new Appointment();
		String[] startDate = new String[3];
		LocalDateTime startDateTime, endDateTime;
		
		try {
			if(createName.getText().equals(createPlaceholderName) || createName.getText().isEmpty())
				throw new Exception("Please enter a name");
			if(this.startDate.getText().equals(createPlaceholderStartDate) || this.startDate.getText().isEmpty())
				throw new Exception("Please enter a starting date");
			startDate = this.startDate.getText().split("/");
			if(startDate.length !=3)
				throw new Exception("Invalid date format");
			startDateTime = LocalDateTime.of(LocalDate.of(Integer.valueOf(startDate[0]), 
					Integer.valueOf(startDate[1]), Integer.valueOf(startDate[2])), (LocalTime) startTime.getSelectedItem());
			
			endDateTime = LocalDateTime.of(LocalDate.of(Integer.valueOf(startDate[0]), 
					Integer.valueOf(startDate[1]), Integer.valueOf(startDate[2])), (LocalTime) endTime.getSelectedItem());
			System.out.println(startDateTime);
			System.out.println(endDateTime);
			
			appointment.setClient((Client)model);
			//appointment.s
			
			
			if(recurringAppRB.isSelected())
				//set recurring appointment    controller.addTask(createName.getText(), startDateTime, some stuff to add);
			
			//else
				//set single appointment       controller.addEvent(createName.getText(), startDateTime, endDateTime);
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		//view.toggleCreateView(false);
		//view.update();
	}*/
}