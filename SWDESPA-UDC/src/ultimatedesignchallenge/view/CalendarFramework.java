package ultimatedesignchallenge.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import ultimatedesignchallenge.controller.AppointmentController;

//NOTE: Remove comment at refreshTileEvents thanks - Louie

public abstract class CalendarFramework extends JFrame implements CalendarObserver {
	private static final long serialVersionUID = 1L;
	/**** Day Components ****/
	protected int yearBound, monthBound, dayBound, yearToday, monthToday, dayToday;
	protected String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };


	/**** Added during the project ****/
	protected CellDataHolder validCells;
	protected JPopupMenu popup;
	protected JMenuItem cancel, notifyDoctor, notifyClient, cancelAll, update;
	//protected List<CalendarItem> monthItems;
	//protected List<CalendarItem> dayItems;
	protected AppointmentController appointmentController;
	
	protected TopPanel topPanel;
	protected CalendarPanel calendarPanel;
	protected CreatePanel createPanel;
	protected DayPanel dayPanel;
	protected WeekPanel weekPanel;
	protected MonthPanel monthPanel;
	protected DoctorList doctorList;

//	Stuff from DC2
//	private CalendarModel model;
//	private CalendarController controller;
//	private ItemGetFlags flags;
	
	public CalendarFramework(String name) {
		super(name);
	}
	
	protected void constructorGen(String topLabel) {
//		this.monthItems = new ArrayList<CalendarItem>();
//		this.dayItems = new ArrayList<CalendarItem>();
//		
//		this.flags = new ItemGetFlags();
//		flags.setAll(true);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		setLayout(null);
		setSize(950, 700);
		
		
		commonInstantiate(topLabel);
		commonInit();
		calendarPanel.generateCalendar(validCells);
		monthPanel.generateCalendar(dayBound, monthBound, yearBound, validCells);
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
		monthPanel = new MonthPanel();
		doctorList = new DoctorList();
		
		popup = new JPopupMenu();
		cancel = new JMenuItem("Cancel");
		cancelAll = new JMenuItem("Cancel All Meetings");
		update = new JMenuItem("Update");
		
		
		validCells = new CellDataHolder();	

		appointmentController = new AppointmentController();
	}
	
	private void commonInit() {
		popup.add(update);
		popup.add(cancel);
		popup.add(cancelAll);

		
		add(calendarPanel);
		add(topPanel);
		add(createPanel);
		add(dayPanel);
		add(weekPanel);
		add(monthPanel);
		
		dayPanel.setVisible(true);
		weekPanel.setVisible(false);
		monthPanel.setVisible(false);
		

		topPanel.setBounds(0,0,this.getWidth(), 70);
		calendarPanel.setBounds(0, 70, 270, 610);
		createPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		dayPanel.setBounds(createPanel.getBounds());
		weekPanel.setBounds(createPanel.getBounds());
		monthPanel.setBounds(createPanel.getBounds());
	}
	
	public void refreshCurrentPage() {
		/*this.refreshCalendar(monthToday, yearToday, modelCalendarTable);
		this.refreshCalendar(monthToday, yearToday, modelMonthTable);
		this.refreshHeader();
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

	public void refreshDay() {
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
	
	public void clearAgenda() {
		for (int row = modelAgendaTable.getRowCount()-1 ; row >= 0 ; row--) {
			modelAgendaTable.removeRow(row);
		}
	}
	
	// ------------TABLE MODELS------------//
	class DayTableCellRenderer extends DefaultTableCellRenderer{
		/*private static final long serialVersionUID = 1L;
		private ItemStringFormatter formatter;
		
		public DayTableCellRenderer() {
			super();
			formatter = new DayHTMLItemStringFormatter();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// TODO Auto-generated method stub
			Component cmp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			TableModel tmodel = table.getModel();
			
			if(tmodel.getValueAt(row, column) instanceof CalendarEvent) {
				cmp.setBackground(Color.decode(CalendarEvent.DEFAULT_COLOR));
			}
			else if(tmodel.getValueAt(row, column) instanceof CalendarTask) {
				cmp.setBackground(Color.decode(CalendarTask.DEFAULT_COLOR));
			}
			else {
				cmp.setBackground(table.getBackground());
			}
			
			return cmp;
		}
		
		@Override
		public void setValue(Object value) {
			super.setValue(value);
			if(value instanceof CalendarItem)
				setText(formatter.format((CalendarItem)value));
		}*/
		
		
	}
	
	class AgendaTableCellRenderer extends DefaultTableCellRenderer{
		/*private static final long serialVersionUID = 1L;
		private ItemStringFormatter formatter;
		
		public AgendaTableCellRenderer() {
			super();
			formatter = new AgendaHTMLItemStringFormatter();
		}
		
		@Override
		public void setValue(Object value) {
			super.setValue(value);
			if(value instanceof CalendarItem)
				setText(formatter.format((CalendarItem)value));
		}*/
		
		
	}
	
	protected void initListeners() {
		calendarPanel.btnNext.addActionListener(new btnNext_Action());
		calendarPanel.btnPrev.addActionListener(new btnPrev_Action());
		calendarPanel.calendarTable.addMouseListener(new calendarTableMouseListener());
		calendarPanel.create.addActionListener(new createbtnListener());
		
		createPanel.discard.addActionListener(new discardCreateBtnListener());
	
		topPanel.calendar.addActionListener(new dayToggleBtnListener());
		topPanel.agenda.addActionListener(new agendaToggleBtnListener());
		topPanel.viewType.addActionListener(new calendarViewCBListener());
		topPanel.today.addActionListener(new todayButtonListener());
	
		dayPanel.dayTable.addMouseListener(new dayTableMouseListener());
//		agendaTable.addMouseListener(new agendaTableMouseListener());
//		weekTable.addMouseListener(new weekTableMouseListener());
//		weekAgendaTable.addMouseListener(new weekAgendaTableMouseListener());
//		monthTable.addMouseListener(new monthTableMouseListener());
//		monthAgendaTable.addMouseListener(new monthAgendaTableMouseListener());
		
		cancel.addActionListener(new deleteItemListener());
		
		try {
			doctorList.addWindowListener(new doctorListWindowListener());
			doctorList.addMouseListener(new doctorListListener());
			calendarPanel.doctors.addActionListener(new toggleDoctorListListener());
		}catch(Exception e) {}
	}
	
	// ------------OVERRIDE METHODS------------//
	
	@Override
	public void update()
	{
		calendarPanel.refreshCalendar(monthToday, yearToday, yearBound, validCells);
		monthPanel.refreshCalendar(monthToday, yearToday, validCells);
		weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);
	}
	
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
			update();
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
			update();
		}
	}
	
	class dayTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = dayPanel.dayTable.getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) /*&& modelDayTable.getValueAt(row, 1) instanceof CalendarItem*/)
			
			//TODO:
			/*if(this is NOT a recurring meeting)
			 *	disable cancelAll
			 *else
			 *	enable cancelAll
			 *
			 */
				popup.show(dayPanel.dayTable, arg0.getX(), arg0.getY());
			
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
	
	class weekTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
//			int row = weekTable.getSelectedRow();
//			int col = weekTable.getSelectedColumn();
//			if(SwingUtilities.isRightMouseButton(arg0) && modelWeekTable.getValueAt(row, 1) instanceof CalendarItem)
//				popup.show(weekTable, arg0.getX(), arg0.getY());
			
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
	
	class weekAgendaTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			/*int row = weekTable.getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) && modelWeekAgendaTable.getValueAt(row, 1) instanceof CalendarItem)
				popup.show(weekAgendaTable, arg0.getX(), arg0.getY()); */
			
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
	
	class monthTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			/*int row = monthTable.getSelectedRow();
			int col = monthTable.getSelectedColumn();
			if(SwingUtilities.isRightMouseButton(arg0) && modelMonthTable.getValueAt(row, 1) instanceof CalendarItem)
				popup.show(monthTable, arg0.getX(), arg0.getY()); */
			
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
	
	class monthAgendaTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			/*int row = weekTable.getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) && modelMonthAgendaTable.getValueAt(row, 1) instanceof CalendarItem)
				popup.show(monthAgendaTable, arg0.getX(), arg0.getY()); */
			
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
	
	
	
	class agendaTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			/*int row = agendaTable.getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) && modelAgendaTable.getValueAt(row, 1) instanceof CalendarItem)
				popup.show(agendaTable, arg0.getX(), arg0.getY()); */
				
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
	
	class calendarTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent evt) {
				int col = calendarPanel.calendarTable.getSelectedColumn();
				int row = calendarPanel.calendarTable.getSelectedRow();
				
				try {
					int day = validCells.getDayAtCell(row, col);
					// TODO: show the day/agenda for that day
					dayToday = day;
					update();
					monthPanel.refreshCalendar(monthToday, yearToday, validCells);
					weekPanel.refreshWeekTable(monthToday, dayToday, yearToday);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
		
	}
	
	class doctorListListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			doctorList.doctorList.getSelectedValuesList();
			
			//filter the tables necessary to the selected
			update();
			
		}
	}
	
	class createbtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleCreateView(true);
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
				monthPanel.setVisible(false);
			}
			else if(topPanel.viewType.getSelectedItem().equals("Month"))
			{
				weekPanel.setVisible(false);
				dayPanel.setVisible(false);
				monthPanel.setVisible(true);
			}
			else
			{
				weekPanel.setVisible(false);
				dayPanel.setVisible(true);
				monthPanel.setVisible(false);
			}
			
		}
		
	}
	
	class toggleDoctorListListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(calendarPanel.doctors.isSelected())
				toggleDoctorList(true);
			else
				toggleDoctorList(false);
			
		}
		
	}
	
	class doctorListWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {}

		@Override
		public void windowClosing(WindowEvent arg0) {
			toggleDoctorList(false);
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {}

		@Override
		public void windowDeiconified(WindowEvent arg0) {}

		@Override
		public void windowIconified(WindowEvent arg0) {}

		@Override
		public void windowOpened(WindowEvent arg0) {}
		
	}
	
	private void toggleAgendaView(boolean toggle) {
		dayPanel.scrollAgendaTable.setVisible(toggle);
		dayPanel.scrollAgendaTable.setEnabled(toggle);
		weekPanel.scrollAgendaTable.setVisible(toggle);
		weekPanel.scrollAgendaTable.setEnabled(toggle);
		monthPanel.scrollAgendaTable.setVisible(toggle);
		monthPanel.scrollAgendaTable.setEnabled(toggle);
	}
	
	private void toggleDayView(boolean toggle) {
		dayPanel.scrollDayTable.setVisible(toggle);
		dayPanel.scrollDayTable.setEnabled(toggle);
		weekPanel.scrollWeekTable.setVisible(toggle);
		weekPanel.scrollWeekTable.setEnabled(toggle);
		monthPanel.scrollMonthTable.setVisible(toggle);
		monthPanel.scrollMonthTable.setEnabled(toggle);
	}
	
	private void toggleCreateView(boolean toggle) {
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
		
		try {
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
	
	class deleteItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*JTable invoker = (JTable)popup.getInvoker();
			CalendarItem item;
			if(invoker.getValueAt(invoker.getSelectedRow(), 1) instanceof CalendarItem) {
				item = (CalendarItem)invoker.getValueAt(invoker.getSelectedRow(), 1);
				controller.deleteItem(item);
				update();
			}*/
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
