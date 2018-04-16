package ultimatedesignchallenge.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CreatePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JButton save, discard;
	JTextField createName;
	JRadioButton recurring;
	JLabel TOLabelTime;
	JComboBox<String> doctors, recurrence;
	JComboBox<Integer> month, year, day;
	JComboBox<LocalTime> startTime, endTime;
	
	
	public CreatePanel()
	{
		super(null);
		setVisible(false);
		instantiate();
		initialize();
		initListeners();
	}
	
	private void instantiate()
	{
		List<Integer> years = new ArrayList<Integer>();
		
		for(int i = 100; i >0; i--)
			years.add(Year.now().minusYears(i).getValue());
		
		years.add(Year.now().getValue());
		
		for(int i = 1; i <=100; i++)
			years.add(Year.now().plusYears(i).getValue());
		
		Integer[] yearsA = years.toArray(new Integer[years.size()]);
		
		Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};

		
		
		month = new JComboBox<Integer>(months);
		year = new JComboBox<Integer>(yearsA);
		day = new JComboBox<Integer>();
		
		TOLabelTime = new JLabel("to");
		
		save = new JButton("Save");
		discard = new JButton("Discard");
		recurring = new JRadioButton("Recurring");
		
		startTime = new JComboBox<LocalTime>();
		endTime = new JComboBox<LocalTime>();
		
		recurrence = new JComboBox<String>();
	}
	
	private void initialize()
	{
		add(month);
		add(day);
		
		add(year);
		add(startTime);
		add(endTime);
		add(recurring);
		add(save);
		add(discard);
		add(TOLabelTime);
		add(recurrence);
		
		
		recurrence.setVisible(false);
		
		TOLabelTime.setFont(new Font("Arial", Font.BOLD, 15));
		
		recurrence.addItem("Every Week");
		recurrence.addItem("Every 2 Weeks");
		recurrence.addItem("Every 3 Weeks");
		recurrence.addItem("Every Month");
		
		LocalTime tmpTime = LocalTime.of(0, 0);
		for(int i=0 ; i<48 ; i++) {
			startTime.addItem(tmpTime);
			endTime.addItem(tmpTime);
			tmpTime = tmpTime.plusMinutes(30);
		}
		
		recurring.setBounds(130, 90, 90, 50);
		recurrence.setBounds(250, 90, 120, 40);
		
		month.setBounds(90, 140, 40, 40);
		day.setBounds(140, 140, 40, 40);
		year.setBounds(190,140, 60, 40);
		startTime.setBounds(380, 140, 70, 40);
		TOLabelTime.setBounds(460, 140, 20, 40);
		endTime.setBounds(480, 140, 70, 40);
		
		save.setBounds(10, 200, 300, 40);
		discard.setBounds(330, 200, 300, 40);
		
		setToday();

	}
	
	void setToday()
	{
		LocalDateTime now = LocalDateTime.now();
		month.setSelectedItem(now.getMonth().getValue());
		year.setSelectedItem(now.getYear());
		
		day.removeAllItems();
		
		for(int i = 1; i <=now.getMonth().length(Year.isLeap(now.getYear())); i++)
			day.addItem(i);
		
		day.setSelectedItem(now.getDayOfMonth());
	}
	
	private void initListeners()
	{
		recurring.addActionListener(new recurringListener());
		month.addActionListener(new checkDaysListener());
		year.addActionListener(new checkDaysListener());
	}
	
	class recurringListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean toggle = recurring.isSelected();
			
			recurrence.setVisible(toggle);
			recurrence.setEnabled(toggle);
			
		}
		
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

	public JButton getSave() {
		return save;
	}

	public void setSave(JButton save) {
		this.save = save;
	}

	public JButton getDiscard() {
		return discard;
	}

	public void setDiscard(JButton discard) {
		this.discard = discard;
	}

	public JTextField getCreateName() {
		return createName;
	}

	public void setCreateName(JTextField createName) {
		this.createName = createName;
	}

	public JRadioButton getRecurring() {
		return recurring;
	}

	public void setRecurring(JRadioButton recurring) {
		this.recurring = recurring;
	}

	public JLabel getTOLabelTime() {
		return TOLabelTime;
	}

	public void setTOLabelTime(JLabel tOLabelTime) {
		TOLabelTime = tOLabelTime;
	}

	public JComboBox<String> getDoctors() {
		return doctors;
	}

	public void setDoctors(JComboBox<String> doctors) {
		this.doctors = doctors;
	}

	public JComboBox<String> getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(JComboBox<String> recurrence) {
		this.recurrence = recurrence;
	}

	public JComboBox<Integer> getMonth() {
		return month;
	}

	public void setMonth(JComboBox<Integer> month) {
		this.month = month;
	}

	public JComboBox<Integer> getYear() {
		return year;
	}

	public void setYear(JComboBox<Integer> year) {
		this.year = year;
	}

	public JComboBox<Integer> getDay() {
		return day;
	}

	public void setDay(JComboBox<Integer> day) {
		this.day = day;
	}

	public JComboBox<LocalTime> getStartTime() {
		return startTime;
	}

	public void setStartTime(JComboBox<LocalTime> startTime) {
		this.startTime = startTime;
	}

	public JComboBox<LocalTime> getEndTime() {
		return endTime;
	}

	public void setEndTime(JComboBox<LocalTime> endTime) {
		this.endTime = endTime;
	}

}
