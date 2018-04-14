package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import designchallenge2.view.CalendarObserver;
import ultimatedesignchallenge.controller.AppointmentController;

//NOTE: Remove comment at refreshTileEvents thanks - Louie
//NOTE: Fix generateWeekAgendaTable thanks - Louie
//TODO: Recurring Events because idk how that works - Louie

public abstract class CalendarFramework extends JFrame implements CalendarObserver {
	private static final long serialVersionUID = 1L;
	/**** Day Components ****/
	protected int yearBound, monthBound, dayBound, yearToday, monthToday;
	protected String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	/**** Swing Components ****/
	protected JLabel monthLabel, titleLabel, dayLabel, createTOLabelTime;
	protected JTextField createName, startDate;
	
	protected JButton btnPrev, btnNext, create, today, save, discard;
	protected JToggleButton calendar, agenda, doctors;
	protected JRadioButton recurringAppRB;
	protected Container pane;
	protected JScrollPane scrollCalendarTable;
	protected JPanel calendarPanel, topPanel, createPanel, dayPanel, weekPanel, loginPanel, doctorListPanel, monthPanel;

	/**** Calendar Table Components ***/
	protected JTable calendarTable;
	protected DefaultTableModel modelCalendarTable;

	/**** Added during the project ****/
	protected int taskCount, dayToday;
	protected JComboBox<LocalTime> startTime, endTime;
	protected JComboBox<String> viewType, doctorsCBList, daysCBList;
	protected CellDataHolder validCells;
	protected final String createPlaceholderName = "Client/Appointment Name";
	protected final String createPlaceholderStartDate = "Date";
	protected JTable dayTable, agendaTable, weekTable, weekAgendaTable, monthTable, monthAgendaTable;
	protected DefaultTableModel modelDayTable, modelAgendaTable, modelWeekTable, modelWeekAgendaTable, modelMonthTable, modelMonthAgendaTable;
	protected JScrollPane scrollDayTable, scrollAgendaTable, scrollWeekTable, scrollWeekAgendaTable, scrollDoctorList, scrollMonthTable, scrollMonthAgendaTable;
	protected JPopupMenu popup;
	protected JFrame doctorListFrame;
	protected JList<String> doctorList;
	protected DefaultListModel<String> modelDoctorList;
	protected JMenuItem cancel, notifyDoctor, notifyClient, cancelAll, update;
	//protected List<CalendarItem> monthItems;
	//protected List<CalendarItem> dayItems;
	protected AppointmentController appointmentController;

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
		
		this.taskCount = 0;
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		
		setLayout(null);
		setSize(950, 700);
		
		
		commonInstantiate(topLabel);
		commonInit();
		generateCalendar(calendarTable, modelCalendarTable);
		generateCalendar(monthTable, modelMonthTable);
		generateDayTable();
		generateWeekTable();
		generateAgendaTable(agendaTable, modelAgendaTable, scrollAgendaTable);
		generateAgendaTable(weekAgendaTable, modelWeekAgendaTable, scrollWeekAgendaTable);
		generateAgendaTable(monthAgendaTable, modelMonthAgendaTable, scrollMonthAgendaTable);
		
		setResizable(false);
		setVisible(true);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//		this.update();	
		this.toggleAgendaView(false);
		this.toggleDayView(true);
	}
	
	private void commonInstantiate(String topLabel) {		
		loginPanel = new JPanel();
		calendarPanel = new JPanel();
		topPanel = new JPanel();
		createPanel = new JPanel();
		dayPanel = new JPanel();
		weekPanel = new JPanel();
		monthPanel = new JPanel();
		
		monthLabel = new JLabel("January");
		dayLabel = new JLabel("");
		createTOLabelTime = new JLabel("to");
		titleLabel = new JLabel(topLabel);
		
		btnPrev = new JButton("<");
		btnNext = new JButton(">");
		create = new JButton("Set Appointment");
		today = new JButton("Today");
		save = new JButton("Save");
		discard = new JButton("Discard");
		
		calendar = new JToggleButton("Calendar");
		agenda = new JToggleButton("Agenda");
		
		recurringAppRB = new JRadioButton("Recurring");
		
		startTime = new JComboBox<LocalTime>();
		endTime = new JComboBox<LocalTime>();
		viewType = new JComboBox<String>();
		daysCBList = new JComboBox<String>();
		startDate = new JTextField();
		
		popup = new JPopupMenu();
		cancel = new JMenuItem("Cancel");
		cancelAll = new JMenuItem("Cancel All Meetings");
		update = new JMenuItem("Update");
		
		modelCalendarTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		modelDayTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		modelAgendaTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		modelWeekTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		modelWeekAgendaTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		modelMonthTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		modelMonthAgendaTable = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		validCells = new CellDataHolder();
		
		calendarTable = new JTable(modelCalendarTable);
		dayTable = new JTable(modelDayTable);
		agendaTable = new JTable(modelAgendaTable);
		weekTable = new JTable(modelWeekTable);
		weekAgendaTable = new JTable(modelWeekAgendaTable);
		monthTable = new JTable(modelMonthTable);
		monthAgendaTable = new JTable(modelMonthAgendaTable);
		
		scrollCalendarTable = new JScrollPane(calendarTable);
		scrollDayTable = new JScrollPane(dayTable);
		scrollAgendaTable = new JScrollPane(agendaTable);
		scrollWeekTable = new JScrollPane(weekTable);
		scrollWeekAgendaTable = new JScrollPane(weekAgendaTable);
		scrollMonthTable = new JScrollPane(monthTable);
		scrollMonthAgendaTable = new JScrollPane(monthAgendaTable);
		
		appointmentController = new AppointmentController();
	}
	
	private void commonInit() {
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		

		
		loginPanel.setLayout(null);
		calendarPanel.setLayout(null);
		topPanel.setLayout(null);
		createPanel.setLayout(null);
		dayPanel.setLayout(null);
		weekPanel.setLayout(null);
		monthPanel.setLayout(null);
		
		popup.add(update);
		popup.add(cancel);
		popup.add(cancelAll);
		
		titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
		dayLabel.setFont(new Font("Arial", Font.BOLD, 25));
		monthLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		createTOLabelTime.setFont(new Font("Arial", Font.BOLD, 15));
		
		
		
		LocalTime tmpTime = LocalTime.of(0, 0);
		for(int i=0 ; i<48 ; i++) {
			startTime.addItem(tmpTime);
			endTime.addItem(tmpTime);
			tmpTime = tmpTime.plusMinutes(30);
		}
		
		viewType.addItem("Day");
		viewType.addItem("Week");
		viewType.addItem("Month");
		
		btnPrev.setMargin(new Insets(0,0,0,0));
		btnNext.setMargin(new Insets(0,0,0,0));
		
		startDate.setHorizontalAlignment(JTextField.CENTER);
		startDate.setText(createPlaceholderStartDate);
		

		
		startDate.setForeground(Color.GRAY);

		
		add(calendarPanel);
		calendarPanel.add(monthLabel);
		calendarPanel.add(btnPrev);
		calendarPanel.add(btnNext);
		calendarPanel.add(scrollCalendarTable);
		calendarPanel.add(create);
		calendarPanel.add(recurringAppRB);
		
		add(topPanel);
		topPanel.add(titleLabel);
		topPanel.add(today);
		topPanel.add(calendar);
		topPanel.add(agenda);
		topPanel.add(dayLabel);
		topPanel.add(viewType);
		
		add(createPanel);
		createPanel.add(startDate);
		createPanel.add(startTime);
		createPanel.add(endTime);
		createPanel.add(recurringAppRB);
		createPanel.add(save);
		createPanel.add(discard);
		createPanel.add(createTOLabelTime);
		createPanel.add(daysCBList);
		createPanel.setVisible(false);
		
		add(dayPanel);
		dayPanel.add(scrollDayTable);
		dayPanel.add(scrollAgendaTable);
		
		add(weekPanel);
		weekPanel.add(scrollWeekTable);
		weekPanel.add(scrollWeekAgendaTable);
		weekPanel.setVisible(false);
		
		add(monthPanel);
		monthPanel.add(scrollMonthTable);
		monthPanel.add(scrollMonthAgendaTable);
		monthPanel.setVisible(false);
		
		scrollAgendaTable.setVisible(false);
		scrollWeekAgendaTable.setVisible(false);
		scrollMonthAgendaTable.setVisible(false);
		scrollWeekTable.setVisible(false);
		scrollMonthTable.setVisible(false);
		
		
		topPanel.setBounds(0,0,this.getWidth(), 70);
		titleLabel.setBounds(10, 10, 250, 50);
		today.setBounds(280, 15, 100, 40);
		dayLabel.setBounds(400, 10, 250, 50);
		viewType.setBounds(660, 5, 100, 60);
		calendar.setBounds(785, 15, 70, 40);
		agenda.setBounds(850, 15, 70, 40);
		
		calendarPanel.setBounds(0, 70, 270, 610);
		create.setBounds(10, 10, 250, 40);
		monthLabel.setBounds(10, 50, 200, 50);
		btnPrev.setBounds(180, 60, 40, 30);
		btnNext.setBounds(220, 60, 40, 30);
		scrollCalendarTable.setBounds(10, 100, 250, 390);
		
		createPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		recurringAppRB.setBounds(10, 70, 150, 50);
		startDate.setBounds(10, 120, 120, 40);
		startTime.setBounds(10, 160, 120, 40);
		daysCBList.setBounds(160, 70, 120, 40);
		createTOLabelTime.setBounds(140, 160, 20, 40);
		endTime.setBounds(160, 160, 120, 40);
		save.setBounds(300, 120, 90, 40);
		discard.setBounds(300, 160, 90, 40);
		
		String[] headers = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
		
		for(String h : headers)
			daysCBList.addItem(h);

		
		dayPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		scrollDayTable.setBounds(20, 20, dayPanel.getWidth()-50, dayPanel.getHeight()-50);
		scrollAgendaTable.setBounds(20, 20, dayPanel.getWidth()-50, dayPanel.getHeight()-50);
		
		weekPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		scrollWeekTable.setBounds(20, 20, weekPanel.getWidth()-30, weekPanel.getHeight()-50);
		scrollWeekAgendaTable.setBounds(20, 20, weekPanel.getWidth()-50, weekPanel.getHeight()-50);
		
		monthPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		scrollMonthTable.setBounds(20, 20, monthPanel.getWidth(), monthPanel.getHeight());
		scrollMonthAgendaTable.setBounds(20, 20, weekPanel.getWidth()-50, weekPanel.getHeight()-50);
		
		toggleRecurringCBList(false);
	}
	
	private void generateWeekTable() {
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		modelWeekTable.addColumn("Time");
		for(int i = 0; i< 7; i++)
			modelWeekTable.addColumn("Day " + (i+1));
			
		modelWeekTable.setColumnCount(8);
		modelWeekTable.setRowCount(48);
		
		weekTable.setShowVerticalLines(true);
		weekTable.setGridColor(Color.BLACK);
		weekTable.setRowHeight(75);
		weekTable.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		weekTable.getColumnModel().getColumn(0).setPreferredWidth(65);
		for(int i = 1; i < 8; i++)
		{
			weekTable.getColumnModel().getColumn(i).setCellRenderer(new DayTableCellRenderer());//luis
			weekTable.getColumnModel().getColumn(i).setPreferredWidth(80);
		}
		
		weekTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		weekTable.getTableHeader().setReorderingAllowed(false);
		weekTable.getTableHeader().setResizingAllowed(false);
		
		/*
		for(int i = 0; i < 48; i++)
			if(i%2==0)
				dayTable.setValueAt(i/2 + ":00", i, 0);*/
		
		LocalTime tmpTime = LocalTime.of(0, 0);
		for(int i=0 ; i<48 ; i++) {
			weekTable.setValueAt(tmpTime, i, 0);
			tmpTime = tmpTime.plusMinutes(30);
		}
				
	}
	
	private void generateDayTable() {
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		
		modelDayTable.addColumn("Time");
		modelDayTable.addColumn("Event/Task");
			
		modelDayTable.setColumnCount(2);
		modelDayTable.setRowCount(48);
		
		dayTable.setShowVerticalLines(true);
		dayTable.setGridColor(Color.BLACK);
		dayTable.setRowHeight(75);
		dayTable.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		dayTable.getColumnModel().getColumn(0).setPreferredWidth(100);
		dayTable.getColumnModel().getColumn(1).setCellRenderer(new DayTableCellRenderer());//luis
		dayTable.getColumnModel().getColumn(1).setPreferredWidth(scrollDayTable.getWidth() - dayTable.getColumnModel().getColumn(0).getWidth() - 45);
		dayTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		dayTable.getTableHeader().setReorderingAllowed(false);
		dayTable.getTableHeader().setResizingAllowed(false);
		
		/*
		for(int i = 0; i < 48; i++)
			if(i%2==0)
				dayTable.setValueAt(i/2 + ":00", i, 0);*/
		
		LocalTime tmpTime = LocalTime.of(0, 0);
		for(int i=0 ; i<48 ; i++) {
			dayTable.setValueAt(tmpTime, i, 0);
			tmpTime = tmpTime.plusMinutes(30);
		}
				
	}
	
	private void generateAgendaTable(JTable table, DefaultTableModel tableModel, JScrollPane scrollPane) {
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		rightRender.setOpaque(false);
		
		tableModel.setColumnCount(2);
		
		table.setRowHeight(50);
		table.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		table.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		AgendaTableCellRenderer itemRender = new AgendaTableCellRenderer();
		itemRender.setOpaque(false);
		table.getColumnModel().getColumn(1).setCellRenderer(itemRender);
		
		
		table.getColumnModel().getColumn(1).setPreferredWidth(table.getWidth() - table.getColumnModel().getColumn(0).getWidth()-95);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setTableHeader(null);
		
		table.setOpaque(false);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		table.setBorder(BorderFactory.createEmptyBorder());
		table.setShowGrid(false);

		((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
	}
	
	private void generateCalendar(JTable table, DefaultTableModel tableModel) {
		GregorianCalendar cal = new GregorianCalendar();
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound;
		yearToday = yearBound;
		dayToday = dayBound; //added in dc2
		
		if(table == calendarTable)
		{
			String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }; // All headers
			table.setRowHeight(60);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
			
			for (int i = 0; i < 7; i++) {
				tableModel.addColumn(headers[i]);
			}
		}
		else
		{
			String[] headers = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }; // All headers
			table.setRowHeight(90);
			
			for (int i = 0; i < 7; i++) {
				tableModel.addColumn(headers[i]);
			}
		}
		table.getParent().setBackground(table.getBackground()); // Set background

		table.getTableHeader().setResizingAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);

		table.setColumnSelectionAllowed(true);
		table.setRowSelectionAllowed(true);

		tableModel.setColumnCount(7);
		tableModel.setRowCount(6);
		
		refreshCalendar(monthBound, yearBound, tableModel); // Refresh calendar
	}
	
	public void refreshCalendar(int month, int year, DefaultTableModel tableModel) {
		int nod, som, i, j;

		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= yearBound - 10)
			btnPrev.setEnabled(false);
		if (month == 11 && year >= yearBound + 100)
			btnNext.setEnabled(false);

		monthLabel.setText(months[month] + " " + yearToday);
		monthLabel.setBounds(10, 50, 360, 50);

		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				tableModel.setValueAt(null, i, j);

		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Added this
		validCells.getList().clear();

		for (i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			tableModel.setValueAt(i, row, column);
			// Added lines below
			validCells.getList().add(new CellData(i, row, column));
			/*try {
				refreshTileEvents(i, row, column);
			} catch (NullPointerException e) {
				System.out.println("No CalendarModel yet");
			}*/
		}

		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
		monthTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
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
	
	public void refreshHeader() {
		dayLabel.setText(months[monthToday] + " " + dayToday + ", " + yearToday);
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
		btnNext.addActionListener(new btnNext_Action());
		btnPrev.addActionListener(new btnPrev_Action());
		calendarTable.addMouseListener(new calendarTableMouseListener());
		
		create.addActionListener(new createbtnListener());
		discard.addActionListener(new discardCreateBtnListener());
		recurringAppRB.addActionListener(new recurringRBListener());
		startDate.addFocusListener(new createStartDateFocusListener());
		
		calendar.addActionListener(new dayToggleBtnListener());
		agenda.addActionListener(new agendaToggleBtnListener());
		viewType.addActionListener(new calendarViewCBListener());
		today.addActionListener(new todayButtonListener());
		
		dayTable.addMouseListener(new dayTableMouseListener());
		agendaTable.addMouseListener(new agendaTableMouseListener());
		weekTable.addMouseListener(new weekTableMouseListener());
		weekAgendaTable.addMouseListener(new weekAgendaTableMouseListener());
		monthTable.addMouseListener(new monthTableMouseListener());
		monthAgendaTable.addMouseListener(new monthAgendaTableMouseListener());
		
		cancel.addActionListener(new deleteItemListener());
		
		try {
			doctorListFrame.addWindowListener(new doctorListWindowListener());
			doctors.addActionListener(new toggleDoctorListListener());
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
			refreshCalendar(monthToday, yearToday, modelCalendarTable);
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
			refreshCalendar(monthToday, yearToday, modelCalendarTable);
			update();
		}
	}
	
	class dayTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			/*int row = dayTable.getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) && modelDayTable.getValueAt(row, 1) instanceof CalendarItem)
				popup.show(dayTable, arg0.getX(), arg0.getY()); */
			
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
			/*int row = weekTable.getSelectedRow();
			int col = weekTable.getSelectedColumn();
			if(SwingUtilities.isRightMouseButton(arg0) && modelWeekTable.getValueAt(row, 1) instanceof CalendarItem)
				popup.show(weekTable, arg0.getX(), arg0.getY()); */
			
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
				/*int col = calendarTable.getSelectedColumn();
				int row = calendarTable.getSelectedRow();
				
				try {
					int day = getValidCells().getDayAtCell(row, col);
					// TODO: show the day/agenda for that day
					dayToday = day;
					update();
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}*/
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
			if(viewType.getSelectedItem().equals("Week"))
			{
				weekPanel.setVisible(true);
				dayPanel.setVisible(false);
				monthPanel.setVisible(false);
			}
			else if(viewType.getSelectedItem().equals("Month"))
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
	
	class createStartDateFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(startDate.getText().equals(createPlaceholderStartDate))
			{
				startDate.setText("");
				startDate.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(startDate.getText().equals(""))
			{
				startDate.setText(createPlaceholderStartDate);
				startDate.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class toggleDoctorListListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(doctors.isSelected())
				toggleDoctorList(true);
			else
				toggleDoctorList(false);
			
		}
		
	}
	
	class doctorListWindowListener implements WindowListener{

		@Override
		public void windowActivated(WindowEvent arg0) {}

		@Override
		public void windowClosed(WindowEvent arg0) {
			
			
		}

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
	
	class recurringRBListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleRecurringCBList(recurringAppRB.isSelected());
			
		}
		
	}
	
	private void toggleRecurringCBList(boolean toggle)
	{
		daysCBList.setVisible(toggle);
		daysCBList.setEnabled(toggle);
		
		if(daysCBList.getItemCount()>0)
			daysCBList.setSelectedIndex(0);
	}
	
	private void toggleAgendaView(boolean toggle) {
		agenda.setSelected(toggle);
		scrollAgendaTable.setVisible(toggle);
		scrollAgendaTable.setEnabled(toggle);
		scrollWeekAgendaTable.setVisible(toggle);
		scrollWeekAgendaTable.setEnabled(toggle);
		scrollMonthAgendaTable.setVisible(toggle);
		scrollMonthAgendaTable.setEnabled(toggle);
	}
	
	private void toggleDayView(boolean toggle) {
		calendar.setSelected(toggle);
		scrollDayTable.setVisible(toggle);
		scrollDayTable.setEnabled(toggle);
		scrollWeekTable.setVisible(toggle);
		scrollWeekTable.setEnabled(toggle);
		scrollMonthTable.setVisible(toggle);
		scrollMonthTable.setEnabled(toggle);
	}
	
	private void toggleCreateView(boolean toggle) {
		toggleDayView(!toggle);
		toggleAgendaView(false);
		calendar.setEnabled(!toggle);
		agenda.setEnabled(!toggle);
		viewType.setEnabled(!toggle);
		today.setEnabled(!toggle);
		create.setEnabled(!toggle);
		createPanel.setVisible(toggle);
		createPanel.setEnabled(toggle);
		clearCreatePanel();
		
		try {
			doctors.setEnabled(!toggle);
		}catch(Exception e) {}
	}
	
	private void toggleDoctorList(boolean toggle) {
		doctorListFrame.setVisible(toggle);
		doctors.setSelected(toggle);
	}
	
	private void clearCreatePanel() {
		startDate.setText(createPlaceholderStartDate);
		startDate.setForeground(Color.GRAY);
		recurringAppRB.setSelected(false);
		createTOLabelTime.setVisible(true);
		endTime.setVisible(true);
		endTime.setEnabled(true);

		if(doctorsCBList.getItemCount()>0)
			doctorsCBList.setSelectedIndex(0);
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
	
	class markTaskListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/*JTable invoker = (JTable)popup.getInvoker();
			CalendarTask task;
			if(invoker.getValueAt(invoker.getSelectedRow(), 1) instanceof CalendarTask) {
				task = (CalendarTask)invoker.getValueAt(invoker.getSelectedRow(), 1);
				controller.markTask(task, !task.isDone());
				update();
				System.out.println("Gets here");
			}*/
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

	class CellData {
		private int day;
		private int row;
		private int col;

		CellData(int day, int row, int col) {
			this.day = day;
			this.row = row;
			this.col = col;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}
		
		public boolean isAt(int row, int col) {
			return this.row==row && this.col==col;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof CellData)
				return this.day == ((CellData) o).getDay() && this.row == ((CellData) o).getRow()
						&& this.col == ((CellData) o).getCol();
			else
				return false;
		}

	}
	
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
}
