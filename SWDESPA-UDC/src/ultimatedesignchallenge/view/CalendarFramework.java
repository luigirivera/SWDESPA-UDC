package ultimatedesignchallenge.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

//NOTE: Remove comment at refreshTileEvents thanks - Louie

public abstract class CalendarFramework extends JFrame implements CalendarObserver {
	private static final long serialVersionUID = 1L;
	/**** Day Components ****/
	protected int yearBound, monthBound, dayBound, yearToday, monthToday, dayToday;
	protected String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	protected String[] monthsAbrev = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};


	/**** Added during the project ****/
	protected CellDataHolder validCells;
	protected JPopupMenu popup;
	protected JMenuItem cancel, notifyDoctor, notifyClient, cancelAll, update, setAppointment;	
	protected TopPanel topPanel;
	protected CalendarPanel calendarPanel;
	protected CreatePanel createPanel;
	protected DayPanel dayPanel;
	protected WeekPanel weekPanel;
	protected DoctorList doctorList;

	
	public CalendarFramework(String name) {
		super(name);
	}
	
	protected void constructorGen(String topLabel) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		setLayout(null);
		setSize(950, 700);
		
		
		commonInstantiate(topLabel);
		commonInit();
		calendarPanel.generateCalendar(validCells);
		weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);
		
		setResizable(false);
		setVisible(true);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.toggleAgendaView(false);
		this.toggleDayView(true);
	}
	
	private void commonInstantiate(String topLabel) {		
		calendarPanel = new CalendarPanel(this);
		topPanel = new TopPanel(topLabel);
		createPanel = new CreatePanel();
		dayPanel = new DayPanel();
		weekPanel = new WeekPanel();
		
		
		popup = new JPopupMenu();
		cancel = new JMenuItem("Cancel");
		update = new JMenuItem("Update");

		validCells = new CellDataHolder();	
	}
	
	private void commonInit() {
		
		add(calendarPanel);
		add(topPanel);
		add(createPanel);
		add(dayPanel);
		add(weekPanel);
		
		dayPanel.setVisible(true);
		weekPanel.setVisible(false);

		topPanel.setBounds(0,0,this.getWidth(), 70);
		calendarPanel.setBounds(0, 70, 270, 610);
		createPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		dayPanel.setBounds(createPanel.getBounds());
		weekPanel.setBounds(createPanel.getBounds());
	}
	
	public void refreshCurrentPage() {
		/*this.refreshCalendar(monthToday, yearToday, modelCalendarTable);
		this.refreshCalendar(monthToday, yearToday, modelMonthTable);
		this.refreshDay();
		this.refreshAgenda();*/
	}

	/* Added this */
	public void refreshTileEvents(int day, int row, int column) throws NullPointerException {
		/*EventStringFormatter esformatter = new HTMLEventMarkerFormatter();
		CellStringFormatter csformatter = new HTMLCellMarkerFormatter();*/

		//modelCalendarTable.setValueAt(csformatter.format(day,
		//		esformatter.formatEvents(LegacyEventConverter.convert(model.getItemsOn(flags, LocalDate.of(yearToday, monthToday+1, day))))), row, column);
	}
	
	public void refreshAgenda() {
		/*clearAgenda();
		for(int row = 0 ; row < dayItems.size() ; row++) {
			Object[] data = new Object[2];
			LocalDateTime tmpItemStart = dayItems.get(row).getStart();
			LocalDateTime tmpItemEnd = dayItems.get(row).getEnd();
			LocalDateTime todayMin = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.MIN);
			LocalDateTime todayMax = LocalDateTime.of(LocalDate.of(yearToday, monthToday+1, dayToday), LocalTime.MAX);
			if(tmpItemStart.isBefore(todayMin)) {
				tmpItemStart = todayMin.truncatedTo(ChronoUnit.MINUTES);
			}
			if(tmpItemEnd.isAfter(todayMax)) {
				tmpItemEnd = todayMax.truncatedTo(ChronoUnit.MINUTES);
			}
			
			data[0] = tmpItemStart.toLocalTime().toString() + " - " + tmpItemEnd.toLocalTime().toString();
			data[1] = dayItems.get(row);
			modelAgendaTable.addRow(data);
		}*/
	}
	
	protected void clearAgenda(DefaultTableModel model) {
		for (int row = model.getRowCount()-1 ; row >= 0 ; row--) {
			model.removeRow(row);
		}
	}
	
	protected void changeLabel()
	{
		String label = months[monthToday] + " " + dayToday + ", " + yearToday;
		if(topPanel.viewType.getSelectedItem().equals("Week"))
		{
			Calendar cal = Calendar.getInstance();
			cal.set(yearToday, monthToday, dayToday);
			cal.get(Calendar.WEEK_OF_YEAR);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			
			if(cal.get(Calendar.WEEK_OF_YEAR) == 1)
			{
				label = "Dec " + cal.get(Calendar.DATE) + ", " + cal.get(Calendar.YEAR) + " - ";
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				label += "Jan " + cal.get(Calendar.DATE) + ", " + cal.get(Calendar.YEAR);
			}
			else
			{
				label = monthsAbrev[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.DATE) + " - ";
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
				label += monthsAbrev[cal.get(Calendar.MONTH)] + " " + cal.get(Calendar.DATE) + ", " + yearToday;
			}	
		}
		
		topPanel.viewLabel.setText(label);
	}
	
	protected void initListeners() {
		calendarPanel.btnNext.addActionListener(new btnNext_Action());
		calendarPanel.btnPrev.addActionListener(new btnPrev_Action());
		calendarPanel.calendarTable.addMouseListener(new calendarTableMouseListener());
		
		createPanel.discard.addActionListener(new discardCreateBtnListener());

		topPanel.viewType.addActionListener(new calendarViewCBListener());
		topPanel.today.addActionListener(new todayButtonListener());
		
		try {
			topPanel.calendar.addActionListener(new dayToggleBtnListener());
			topPanel.agenda.addActionListener(new agendaToggleBtnListener());
			doctorList.addWindowListener(new doctorListWindowListener());
			calendarPanel.doctors.addActionListener(new toggleDoctorListListener());
		}catch(Exception e) {}
	}
	
	// ------------OVERRIDE METHODS------------//
	
	// ------------LISTENERS------------//
	
	class btnPrev_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (monthToday == 0) {
				monthToday = 11;
				yearToday -= 1;
			} else {
				monthToday -= 1;
			}
			calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		}
	}

	class btnNext_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (monthToday == 11) {
				monthToday = 0;
				yearToday += 1;
			} else {
				monthToday += 1;
			}
			calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		}
	}
	
	class calendarTableMouseListener extends MouseAdapter{

		@Override
		public void mouseClicked(MouseEvent evt) {
				int col = calendarPanel.calendarTable.getSelectedColumn();
				int row = calendarPanel.calendarTable.getSelectedRow();
				
				try {
					int day = validCells.getDayAtCell(row, col);
					dayToday = day;
					update();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
		}
		
	}
	
	class discardCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleCreateView(false);
		}
	}
	
	class dayToggleBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleAgendaView(false);
			toggleDayView(true);
		}
	}
	
	class agendaToggleBtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleDayView(false);
			toggleAgendaView(true);
		}
		
	}
	
	class calendarViewCBListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(topPanel.viewType.getSelectedItem().equals("Week"))
			{
				weekPanel.setVisible(true);
				dayPanel.setVisible(false);
				changeLabel();
			}
			else
			{
				weekPanel.setVisible(false);
				dayPanel.setVisible(true);
				changeLabel();
			}
			
		}
		
	}
	
	class toggleDoctorListListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleDoctorList(calendarPanel.doctors.isSelected());
			
		}
		
	}
	
	class doctorListWindowListener extends WindowAdapter{
		@Override
		public void windowClosing(WindowEvent arg0) {
			toggleDoctorList(false);
		}
		
	}
	
	private void toggleAgendaView(boolean toggle) {
		dayPanel.scrollAgendaTable.setVisible(toggle);
		dayPanel.scrollAgendaTable.setEnabled(toggle);
		weekPanel.scrollAgendaTable.setVisible(toggle);
		weekPanel.scrollAgendaTable.setEnabled(toggle);
	}
	
	private void toggleDayView(boolean toggle) {
		dayPanel.scrollDayTable.setVisible(toggle);
		dayPanel.scrollDayTable.setEnabled(toggle);
		weekPanel.scrollWeekTable.setVisible(toggle);
		weekPanel.scrollWeekTable.setEnabled(toggle);
	}
	
	protected void toggleCreateView(boolean toggle) {
		toggleDayView(!toggle);
		toggleAgendaView(false);
		topPanel.calendar.setEnabled(!toggle);
		topPanel.agenda.setEnabled(!toggle);
		topPanel.viewType.setEnabled(!toggle);
		topPanel.today.setEnabled(!toggle);
		calendarPanel.create.setEnabled(!toggle);
		createPanel.setVisible(toggle);
		createPanel.setEnabled(toggle);
		clearCreatePanel();
		
		try {
			calendarPanel.doctors.setEnabled(!toggle);
		}catch(Exception e) {}
	}
	
	private void toggleDoctorList(boolean toggle) {
		doctorList.setVisible(toggle);
		calendarPanel.doctors.setSelected(toggle);
	}
	
	private void clearCreatePanel() {
		createPanel.recurring.setSelected(false);
		createPanel.recurrence.setVisible(false);
		createPanel.TOLabelTime.setVisible(true);
		createPanel.setToday();
		createPanel.startTime.setSelectedIndex(0);
		createPanel.endTime.setSelectedIndex(0);
		
		try {
			createPanel.createName.setText("");
			createPanel.doctors.removeAll();
			//TODO: get all doctors and put them into the combobox
		}catch(Exception e) {}
	}
	
	class todayButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GregorianCalendar cal = new GregorianCalendar();
			dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
			monthBound = cal.get(GregorianCalendar.MONTH);
			yearBound = cal.get(GregorianCalendar.YEAR);
			monthToday = monthBound;
			yearToday = yearBound;
			dayToday = dayBound;
			update();
		}
	}
	
	class cancelAllListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//TODO:
			//get the recurring id of the selected slot
			//delete all appointments using that id
			update();
		}
	}
	
	
	// ------------CELL DATA------------//
	
	class CellDataHolder {
		private List<CellData> list;

		CellDataHolder() {
			list = new ArrayList<CellData>();
		}

		public List<CellData> getList() {
			return list;
		}
		
		public int getDayAtCell(int row, int col) throws IllegalArgumentException {
			for (CellData cd : list) {
				if(cd.isAt(row, col))
					return cd.getDay();
			}
			throw new IllegalArgumentException("Invalid coordinates");
		}

	}

	public int getYearBound() {
		return yearBound;
	}

	public void setYearBound(int yearBound) {
		this.yearBound = yearBound;
	}

	public int getMonthBound() {
		return monthBound;
	}

	public void setMonthBound(int monthBound) {
		this.monthBound = monthBound;
	}

	public int getDayBound() {
		return dayBound;
	}

	public void setDayBound(int dayBound) {
		this.dayBound = dayBound;
	}

	public int getYearToday() {
		return yearToday;
	}

	public void setYearToday(int yearToday) {
		this.yearToday = yearToday;
	}

	public int getMonthToday() {
		return monthToday;
	}

	public void setMonthToday(int monthToday) {
		this.monthToday = monthToday;
	}

	public int getDayToday() {
		return dayToday;
	}

	public void setDayToday(int dayToday) {
		this.dayToday = dayToday;
	}
}
