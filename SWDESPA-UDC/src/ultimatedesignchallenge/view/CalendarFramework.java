package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
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
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import designchallenge1.CellStringFormatter;
import designchallenge1.EventStringFormatter;
import designchallenge1.HTMLCellMarkerFormatter;
import designchallenge1.HTMLEventMarkerFormatter;
import designchallenge2.item.CalendarEvent;
import designchallenge2.item.CalendarItem;
import designchallenge2.view.CalendarObserver;
import designchallenge2.item.CalendarTask;
import designchallenge2.view.AgendaHTMLItemStringFormatter;
import designchallenge2.view.DayHTMLItemStringFormatter;
import designchallenge2.view.ItemStringFormatter;
import ultimatedesignchallenge.view.CalendarFramework.CellDataHolder;

//NOTE: Remove comment at refreshTileEvents thanks - Louie
//NOTE: Fix generateWeekAgendaTable thanks - Louie
//TODO: Recurring Events because idk how that works - Louie
//TODO: Make login() method for logging in - Louiw

public abstract class CalendarFramework extends JFrame implements CalendarObserver{
	/**** Day Components ****/
	protected int yearBound, monthBound, dayBound, yearToday, monthToday;
	protected String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };

	/**** Swing Components ****/
	protected JLabel monthLabel, titleLabel, dayLabel, createTOLabelTime, login;
	protected JTextField createName, startDate, loginUser;
	
	protected JButton btnPrev, btnNext, create, today, save, discard;
	protected JToggleButton calendar, agenda, doctors;
	protected JRadioButton recurringAppRB;
	protected Container pane;
	protected JScrollPane scrollCalendarTable;
	protected JPanel calendarPanel, topPanel, createPanel, mainCalendarPanel, weekPanel, loginPanel, doctorListPanel;

	/**** Calendar Table Components ***/
	protected JTable calendarTable;
	protected DefaultTableModel modelCalendarTable;

	/**** Added during the project ****/
	protected int taskCount, dayToday;
	
	protected JPasswordField loginPass;
	protected JComboBox<LocalTime> startTime, endTime;
	protected JComboBox<String> viewType, doctorsCBList, recurringCBList;
	protected CellDataHolder validCells;
	protected final String createPlaceholderName = "Client/Appointment Name";
	protected final String createPlaceholderStartDate = "Date";
	protected final String loginPlaceholderUser = "Username";
	protected final String loginPlaceholderPass = "Password";
	protected JTable dayTable, agendaTable, weekTable, weekAgendaTable;
	protected DefaultTableModel modelDayTable, modelAgendaTable, modelWeekTable, modelWeekAgendaTable;
	protected JScrollPane scrollDayTable, scrollAgendaTable, scrollWeekTable, scrollWeekAgendaTable, scrollDoctorList;
	protected JPopupMenu dayMenu;
	protected JFrame loginFrame, doctorListFrame;
	protected JList<String> doctorList;
	protected DefaultListModel<String> modelDoctorList;
	protected JMenuItem delete, notifyDoctor, notifyClient;
	protected List<CalendarItem> monthItems;
	protected List<CalendarItem> dayItems;

//	Stuff from DC2
//	private CalendarModel model;
//	private CalendarController controller;
//	private ItemGetFlags flags;
	
	public CalendarFramework(String name) {
		super(name);
	}
	
	protected void constructorGen(String loginLabel, String topLabel) {
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
		
		
		commonInstantiate(loginLabel, topLabel);
		commonInit();
		generateCalendar();
		generateDayTable();
		generateWeekTable();
		generateAgendaTable();
		generateWeekAgendaTable();
		
		setResizable(false);
		setVisible(true);	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
//		this.update();	
		this.toggleAgendaView(false);
		this.toggleDayView(true);
	}
	
	private void commonInstantiate(String loginLabel, String topLabel) {
		loginFrame = new JFrame("Login");
		
		loginPanel = new JPanel();
		calendarPanel = new JPanel();
		topPanel = new JPanel();
		createPanel = new JPanel();
		mainCalendarPanel = new JPanel();
		weekPanel = new JPanel();
		
		loginUser = new JTextField();
		loginPass = new JPasswordField();
		
		monthLabel = new JLabel("January");
		dayLabel = new JLabel("");
		createTOLabelTime = new JLabel("to");
		titleLabel = new JLabel(topLabel);
		login = new JLabel(loginLabel);
		
		btnPrev = new JButton("<");
		btnNext = new JButton(">");
		create = new JButton("Set Appointment");
		today = new JButton("Today");
		save = new JButton("Save");
		discard = new JButton("Discard");
		
		calendar = new JToggleButton("Calendar");
		agenda = new JToggleButton("Agenda");
		
		recurringAppRB = new JRadioButton("Recurring Appointment");
		
		startTime = new JComboBox<LocalTime>();
		endTime = new JComboBox<LocalTime>();
		viewType = new JComboBox<String>();
		recurringCBList = new JComboBox<String>();
		startDate = new JTextField();
		
		dayMenu = new JPopupMenu();
		delete = new JMenuItem("Delete");
		
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
		
		validCells = new CellDataHolder();
		calendarTable = new JTable(modelCalendarTable);
		dayTable = new JTable(modelDayTable);
		agendaTable = new JTable(modelAgendaTable);
		weekTable = new JTable(modelWeekTable);
		weekAgendaTable = new JTable(modelWeekAgendaTable);
		scrollCalendarTable = new JScrollPane(calendarTable);
		scrollDayTable = new JScrollPane(dayTable);
		scrollAgendaTable = new JScrollPane(agendaTable);
		scrollWeekTable = new JScrollPane(weekTable);
		scrollWeekAgendaTable = new JScrollPane(weekAgendaTable);
		
		
	}
	
	private void commonInit() {
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		loginFrame.setSize(520, 225);
		loginFrame.setLayout(null);
		loginFrame.setResizable(false);
		
		loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		loginPanel.setLayout(null);
		calendarPanel.setLayout(null);
		topPanel.setLayout(null);
		createPanel.setLayout(null);
		mainCalendarPanel.setLayout(null);
		weekPanel.setLayout(null);
		
		dayMenu.add(delete);
		
		titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
		login.setFont(new Font("Arial", Font.BOLD, 25));
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
		
		btnPrev.setMargin(new Insets(0,0,0,0));
		btnNext.setMargin(new Insets(0,0,0,0));
		
		startDate.setHorizontalAlignment(JTextField.CENTER);
		startDate.setText(createPlaceholderStartDate);
		
		loginUser.setText(loginPlaceholderUser);
		loginPass.setText(loginPlaceholderPass);
		
		startDate.setForeground(Color.GRAY);
		loginUser.setForeground(Color.GRAY);
		loginPass.setForeground(Color.GRAY);
		
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
		createPanel.add(recurringCBList);
		createPanel.setVisible(false);
		
		add(mainCalendarPanel);
		mainCalendarPanel.add(scrollDayTable);
		mainCalendarPanel.add(scrollAgendaTable);
		
		add(weekPanel);
		weekPanel.add(scrollWeekTable);
		weekPanel.add(scrollWeekAgendaTable);
		weekPanel.setVisible(false);
		
		loginFrame.add(loginPanel);
		loginPanel.add(login);
		loginPanel.add(loginUser);
		loginPanel.add(loginPass);
		loginFrame.setVisible(true);
		
		scrollAgendaTable.setVisible(false);
		scrollWeekAgendaTable.setVisible(false);
		
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
		recurringAppRB.setBounds(10, 70, 140, 50);
		recurringCBList.setBounds(160, 70, 120, 40);
		startDate.setBounds(10, 120, 120, 40);
		startTime.setBounds(10, 160, 120, 40);
		createTOLabelTime.setBounds(140, 160, 20, 40);
		endTime.setBounds(160, 160, 120, 40);
		save.setBounds(300, 120, 90, 40);
		discard.setBounds(300, 160, 90, 40);
		
		mainCalendarPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		scrollDayTable.setBounds(20, 20, mainCalendarPanel.getWidth()-50, mainCalendarPanel.getHeight()-50);
		scrollAgendaTable.setBounds(20, 20, mainCalendarPanel.getWidth()-50, mainCalendarPanel.getHeight()-50);
		
		weekPanel.setBounds(270, 70, this.getWidth() - 270, 610);
		scrollWeekTable.setBounds(20, 20, weekPanel.getWidth()-30, weekPanel.getHeight()-50);
		scrollWeekAgendaTable.setBounds(20, 20, mainCalendarPanel.getWidth()-50, mainCalendarPanel.getHeight()-50);

		loginPanel.setBounds(0, 0, loginFrame.getWidth(), loginFrame.getHeight());
		login.setBounds(190, 20, 250, 50);
		loginUser.setBounds(140, login.getY()+60, 250, 30);
		loginPass.setBounds(140, loginUser.getY()+30, 250, 30);
		
		toggleRecurringCBList(false);
	}
	
	protected void doctorListInst() {
		doctorListFrame = new JFrame("Clinic Doctors");
		doctorListPanel = new JPanel();
		
		doctorListFrame.setResizable(false);
		
		doctors = new JToggleButton("Doctors");	
		doctorsCBList = new JComboBox<String>();
		scrollDoctorList = new JScrollPane(doctorList);
		
		doctorListFrame.setSize(420, 625);
		doctorListFrame.setLayout(null);
		doctorListPanel.setLayout(null);

		calendarPanel.add(doctors);
		createPanel.add(doctorsCBList);
		doctors.setBounds(10, 500, 250,50);
		doctorsCBList.setBounds(160, 120, 120, 40);	
		doctorListPanel.setBounds(0, 0, doctorListFrame.getWidth(), doctorListFrame.getHeight());
		scrollDoctorList.setBounds(doctorListPanel.getBounds());
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
		weekTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		dayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
	
	private void generateAgendaTable() {
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		rightRender.setOpaque(false);
		
		modelAgendaTable.setColumnCount(2);
		
		agendaTable.setRowHeight(50);
		agendaTable.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		agendaTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		AgendaTableCellRenderer itemRender = new AgendaTableCellRenderer();
		itemRender.setOpaque(false);
		agendaTable.getColumnModel().getColumn(1).setCellRenderer(itemRender);
		
		
		agendaTable.getColumnModel().getColumn(1).setPreferredWidth(scrollDayTable.getWidth() - dayTable.getColumnModel().getColumn(0).getWidth()-95);
		agendaTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		agendaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		agendaTable.getTableHeader().setReorderingAllowed(false);
		agendaTable.getTableHeader().setResizingAllowed(false);
		agendaTable.setTableHeader(null);
		
		agendaTable.setOpaque(false);
		scrollAgendaTable.setOpaque(false);
		scrollAgendaTable.getViewport().setOpaque(false);
		scrollAgendaTable.setBorder(BorderFactory.createEmptyBorder());
		agendaTable.setBorder(BorderFactory.createEmptyBorder());
		agendaTable.setShowGrid(false);

		((DefaultTableCellRenderer)agendaTable.getDefaultRenderer(Object.class)).setOpaque(false);
	}
	
	private void generateWeekAgendaTable() {
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		rightRender.setOpaque(false);
		
		modelWeekAgendaTable.setColumnCount(2);
		weekAgendaTable.setRowHeight(50);
		weekAgendaTable.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		weekAgendaTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		AgendaTableCellRenderer itemRender = new AgendaTableCellRenderer();
		itemRender.setOpaque(false);
		weekAgendaTable.getColumnModel().getColumn(1).setCellRenderer(itemRender);
		
		
		weekAgendaTable.getColumnModel().getColumn(1).setPreferredWidth(scrollWeekTable.getWidth() - weekTable.getColumnModel().getColumn(0).getWidth()-95);
		weekAgendaTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		weekAgendaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		weekAgendaTable.getTableHeader().setReorderingAllowed(false);
		weekAgendaTable.getTableHeader().setResizingAllowed(false);
		weekAgendaTable.setTableHeader(null);
		
		weekAgendaTable.setOpaque(false);
		scrollWeekAgendaTable.setOpaque(false);
		scrollWeekAgendaTable.getViewport().setOpaque(false);
		scrollWeekAgendaTable.setBorder(BorderFactory.createEmptyBorder());
		weekAgendaTable.setBorder(BorderFactory.createEmptyBorder());
		weekAgendaTable.setShowGrid(false);

		((DefaultTableCellRenderer)weekAgendaTable.getDefaultRenderer(Object.class)).setOpaque(false);
	}
	
	private void generateCalendar() {
		GregorianCalendar cal = new GregorianCalendar();
		dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
		monthBound = cal.get(GregorianCalendar.MONTH);
		yearBound = cal.get(GregorianCalendar.YEAR);
		monthToday = monthBound;
		yearToday = yearBound;
		dayToday = dayBound; //added in dc2

		String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }; // All headers
		for (int i = 0; i < 7; i++) {
			modelCalendarTable.addColumn(headers[i]);
		}

		calendarTable.getParent().setBackground(calendarTable.getBackground()); // Set background

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		calendarTable.setRowHeight(60);
		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);
		
		refreshCalendar(monthBound, yearBound); // Refresh calendar
	
	}
	
	public void refreshCalendar(int month, int year) {
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
				modelCalendarTable.setValueAt(null, i, j);

		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Added this
		validCells.getList().clear();

		for (i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			modelCalendarTable.setValueAt(i, row, column);
			// Added lines below
			validCells.getList().add(new CellData(i, row, column));
			try {
				refreshTileEvents(i, row, column);
			} catch (NullPointerException e) {
				System.out.println("No CalendarModel yet");
			}
		}

		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
	}
	
	public void refreshCurrentPage() {
		this.refreshCalendar(monthToday, yearToday);
		this.refreshHeader();
		this.refreshDay();
		this.refreshAgenda();
	}

	/* Added this */
	public void refreshTileEvents(int day, int row, int column) throws NullPointerException {
		EventStringFormatter esformatter = new HTMLEventMarkerFormatter();
		CellStringFormatter csformatter = new HTMLCellMarkerFormatter();

		//modelCalendarTable.setValueAt(csformatter.format(day,
		//		esformatter.formatEvents(LegacyEventConverter.convert(model.getItemsOn(flags, LocalDate.of(yearToday, monthToday+1, day))))), row, column);
	}
	
	public void refreshHeader() {
		getDayLabel().setText(getMonths()[getMonthToday()] + " " + dayToday + ", " + getYearToday());
	}
	
	public void refreshDay() {
		for (int row = 0 ; row < modelDayTable.getRowCount() ; row++) {
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
		}
	}
	
	public void refreshAgenda() {
		clearAgenda();
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
		}
	}
	
	public void clearAgenda() {
		for (int row = modelAgendaTable.getRowCount()-1 ; row >= 0 ; row--) {
			modelAgendaTable.removeRow(row);
		}
	}
	
	// ------------TABLE MODELS------------//
	class DayTableCellRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;
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
		}
		
		
	}
	
	class AgendaTableCellRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;
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
		}
		
		
	}
	
	protected void initListeners() {
		addbtnNextListener(new btnNext_Action());
		addbtnPrevListener(new btnPrev_Action());
		addcalendarListener(new calendarTableMouseListener());
		addCreateButtonListener(new createbtnListener());
		addCreateSaveButtonListener(new saveCreateBtnListener());
		addCreateDiscardButtonListener(new discardCreateBtnListener());
		addCalendarToggleButtonListener(new dayToggleBtnListener());
		addAgendaToggleButtonListener(new agendaToggleBtnListener());
		addCreateStartDateListener(new createStartDateFocusListener(), new createStartDateKeyListener());
		addDayTableListener(new dayTableMouseListener());
		addAgendaTableListener(new agendaTableMouseListener());
		addViewTypeListener(new calendarViewCBListener());
		addTodayButtonListener(new todayButtonListener());
		addDeleteItemListener(new deleteItemListener());
		addLoginListener(new loginKeyListener(), new loginUserFocusListener(), new loginPassFocusListener());
		addRecurringRBListener(new recurringRBListener());

		try {
			addDoctorToggleButtonListener(new toggleDoctorListListener());
			addDoctorListWindowListener(new doctorListWindowListener());
		}catch(Exception e) {}
	}
	
	// ------------OVERRIDE METHODS------------//
	@Override
	public void update() {
		try {
			monthItems = model.getItemsOn(flags, YearMonth.of(yearToday, monthToday+1));
			dayItems = model.getItemsOn(flags, LocalDate.of(yearToday, monthToday+1, dayToday));
			taskCount = model.getTaskCount();
			refreshCurrentPage();
		}catch (DateTimeException e) {}
	}
	
	// ------------LISTENERS------------//
	
	class btnPrev_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (getMonthToday() == 0) {
				setMonthToday(11);
				setYearToday(getYearToday() - 1);
			} else {
				setMonthToday(getMonthToday()-1);
			}
			refreshCalendar(getMonthToday(), getYearToday());
			update();
		}
	}

	class btnNext_Action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (getMonthToday() == 11) {
				setMonthToday(0);
				setYearToday(getYearToday() + 1);
			} else {
				setMonthToday(getMonthToday()+1);
			}
			refreshCalendar(getMonthToday(), getYearToday());
			update();
		}
	}
	
	class dayTableMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			int row = getDayTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) && modelDayTable.getValueAt(row, 1) instanceof CalendarItem)
				getDayMenu().show(getDayTable(), arg0.getX(), arg0.getY()); 
			
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
			int row = getAgendaTable().getSelectedRow();
			if(SwingUtilities.isRightMouseButton(arg0) && modelAgendaTable.getValueAt(row, 1) instanceof CalendarItem)
				getDayMenu().show(getAgendaTable(), arg0.getX(), arg0.getY()); 
				
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
			int col = getCalendarTable().getSelectedColumn();
			int row = getCalendarTable().getSelectedRow();
			
			try {
				int day = getValidCells().getDayAtCell(row, col);
				// TODO: show the day/agenda for that day
				dayToday = day;
				update();
				
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
		public void mouseReleased(MouseEvent arg0) {}
		
	}
	
	class createbtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			toggleCreateView(true);
		}
		
	}
	
	class createNameKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				saveCreation();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
	}
	
	class createStartDateKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				saveCreation();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
	}
	
	class createStartTimeKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				saveCreation();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
	}
	
	class createEndTimeKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				saveCreation();
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
	}
	
	class saveCreateBtnListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			saveCreation();
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
			if(getViewType().getSelectedItem().equals("Week"))
				toggleViewType(true);
			else
				toggleViewType(false);
			
		}
		
	}
	
	class createStartDateFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(getStartDate().getText().equals(createPlaceholderStartDate))
			{
				getStartDate().setText("");
				getStartDate().setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(getStartDate().getText().equals(""))
			{
				getStartDate().setText(createPlaceholderStartDate);
				getStartDate().setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class toggleDoctorListListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(getDoctors().isSelected())
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
	private void toggleViewType(boolean toggle) {
		getWeekPanel().setVisible(toggle);
		getMainCalendarPanel().setVisible(!toggle);
	}
	
	private void toggleAgendaView(boolean toggle) {
		getAgenda().setSelected(toggle);
		getScrollAgendaTable().setVisible(toggle);
		getScrollAgendaTable().setEnabled(toggle);
		getScrollWeekAgendaTable().setVisible(toggle);
		getScrollWeekAgendaTable().setVisible(toggle);
	}
	
	private void toggleDayView(boolean toggle) {
		getCalendar().setSelected(toggle);
		getScrollDayTable().setVisible(toggle);
		getScrollDayTable().setEnabled(toggle);
		getScrollWeekTable().setVisible(toggle);
		getScrollWeekTable().setEnabled(toggle);
	}
	
	private void toggleCreateView(boolean toggle) {
		toggleDayView(!toggle);
		toggleAgendaView(false);
		getCalendar().setEnabled(!toggle);
		getAgenda().setEnabled(!toggle);
		getViewType().setEnabled(!toggle);
		today.setEnabled(!toggle);
		getCreate().setEnabled(!toggle);
		getCreatePanel().setVisible(toggle);
		getCreatePanel().setEnabled(toggle);
		clearCreatePanel();
		
		try {
			getDoctors().setEnabled(!toggle);
		}catch(Exception e) {}
	}
	
	private void toggleDoctorList(boolean toggle) {
		getDoctorListFrame().setVisible(toggle);
		getDoctors().setSelected(toggle);
	}
	
	private void clearCreatePanel() {
		getStartDate().setText(createPlaceholderStartDate);
		getStartDate().setForeground(Color.GRAY);
		getRecurringAppRB().setSelected(false);
		getCreateTOLabelTime().setVisible(true);
		getEndTime().setVisible(true);
		getEndTime().setEnabled(true);

		if(getDoctorsCBList().getItemCount()>0)
			getDoctorsCBList().setSelectedIndex(0);
	}
	
	class recurringRBListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			toggleRecurringCBList(recurringAppRB.isSelected());
			
		}
		
	}
	
	private void toggleRecurringCBList(boolean toggle)
	{
		recurringCBList.setVisible(toggle);
		recurringCBList.setEnabled(toggle);
		
		if(recurringCBList.getItemCount()>0)
			recurringCBList.setSelectedIndex(0);
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
			JTable invoker = (JTable)dayMenu.getInvoker();
			CalendarTask task;
			if(invoker.getValueAt(invoker.getSelectedRow(), 1) instanceof CalendarTask) {
				task = (CalendarTask)invoker.getValueAt(invoker.getSelectedRow(), 1);
				controller.markTask(task, !task.isDone());
				update();
				System.out.println("Gets here");
			}
		}
	}
	
	class deleteItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JTable invoker = (JTable)dayMenu.getInvoker();
			CalendarItem item;
			if(invoker.getValueAt(invoker.getSelectedRow(), 1) instanceof CalendarItem) {
				item = (CalendarItem)invoker.getValueAt(invoker.getSelectedRow(), 1);
				controller.deleteItem(item);
				update();
			}
		}
	}
	
	class loginKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				login();
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	class loginUserFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(getLoginUser().getText().equals(loginPlaceholderUser))
			{
				getLoginUser().setText("");
				getLoginUser().setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(getLoginUser().getText().equals(""))
			{
				getLoginUser().setText(loginPlaceholderUser);
				getLoginUser().setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class loginPassFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(getLoginPass().getPassword()).equals(loginPlaceholderPass))
			{
				getLoginPass().setText("");
				getLoginPass().setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(getLoginPass().getPassword()).equals(""))
			{
				getLoginPass().setText(loginPlaceholderPass);
				getLoginPass().setForeground(Color.GRAY);
			}
			
		}
	}
	
	// ------------LISTENER SETTERS------------//
	public void addbtnNextListener(ActionListener e) {
		btnNext.addActionListener(e);
	}
	
	public void addbtnPrevListener(ActionListener e) {
		btnPrev.addActionListener(e);
	}
	
	public void addLoginListener(KeyListener e, FocusListener focusU, FocusListener focusPass) {
		loginUser.addKeyListener(e);
		loginPass.addKeyListener(e);
		loginUser.addFocusListener(focusU);
		loginPass.addFocusListener(focusPass);
	}
	
	public void addcalendarListener(MouseListener e) {
		calendarTable.addMouseListener(e);
	}
	
	public void addCreateButtonListener(ActionListener e) {
		create.addActionListener(e);
	}
	
	public void addCreateSaveButtonListener(ActionListener e) {
		save.addActionListener(e);
	}
	
	public void addCreateDiscardButtonListener(ActionListener e) {
		discard.addActionListener(e);
	}
	
	public void addCalendarToggleButtonListener(ActionListener e) {
		calendar.addActionListener(e);
	}
	
	public void addAgendaToggleButtonListener(ActionListener e) {
		agenda.addActionListener(e);
	}

	public void addCreateStartDateListener(FocusListener f, KeyListener k) {
		startDate.addFocusListener(f);
		startDate.addKeyListener(k);
	}
	
	public void addRecurringRBListener(ActionListener e) {
		recurringAppRB.addActionListener(e);
	}
	
	public void addDayTableListener(MouseListener e) {
		dayTable.addMouseListener(e);
	}
	
	public void addAgendaTableListener(MouseListener e) {
		agendaTable.addMouseListener(e);
	}
	
	public void addTodayButtonListener(ActionListener e) {
		today.addActionListener(e);
	}
	
	public void addDeleteItemListener(ActionListener e) {
		delete.addActionListener(e);
	}
	
	public void addViewTypeListener(ActionListener e) {
		viewType.addActionListener(e);
	}
	
	public void addDoctorListWindowListener(WindowListener e) {
		doctorListFrame.addWindowListener(e);;
	}
	
	public void addDoctorToggleButtonListener(ActionListener e) {
		doctors.addActionListener(e);
	}
	
	// ------------GETTERS AND SETTERS------------//
	
	public JScrollPane getScrollDayTable() {
		return scrollDayTable;
	}

	public JComboBox<String> getViewType() {
		return viewType;
	}

	public void setViewType(JComboBox<String> viewType) {
		this.viewType = viewType;
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

	public JTable getDayTable() {
		return dayTable;
	}

	public JLabel getCreateTOLabelTime() {
		return createTOLabelTime;
	}

	public void setCreateTOLabelTime(JLabel createTOLabelTime) {
		this.createTOLabelTime = createTOLabelTime;
	}

	public JButton getCreate() {
		return create;
	}

	public void setCreate(JButton create) {
		this.create = create;
	}

	public JLabel getDayLabel() {
		return dayLabel;
	}

	public JPopupMenu getDayMenu() {
		return dayMenu;
	}

	public void setDayMenu(JPopupMenu dayMenu) {
		this.dayMenu = dayMenu;
	}

	public void setDayLabel(JLabel dayLabel) {
		this.dayLabel = dayLabel;
	}

	public JTextField getCreateName() {
		return createName;
	}

	public void setCreateName(JTextField createName) {
		this.createName = createName;
	}

	public JTextField getStartDate() {
		return startDate;
	}

	public void setStartDate(JTextField startDate) {
		this.startDate = startDate;
	}

	public JButton getSave() {
		return save;
	}

	public JPanel getMainCalendarPanel() {
		return mainCalendarPanel;
	}

	public void setMainCalendarPanel(JPanel mainCalendarPanel) {
		this.mainCalendarPanel = mainCalendarPanel;
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

	public JRadioButton getRecurringAppRB() {
		return recurringAppRB;
	}

	public void setRecurringAppRB(JRadioButton recurringAppRB) {
		this.recurringAppRB = recurringAppRB;
	}

	public JPanel getCreatePanel() {
		return createPanel;
	}

	public JTable getCalendarTable() {
		return calendarTable;
	}

	public void setCalendarTable(JTable calendarTable) {
		this.calendarTable = calendarTable;
	}

	public JTable getAgendaTable() {
		return agendaTable;
	}

	public void setAgendaTable(JTable agendaTable) {
		this.agendaTable = agendaTable;
	}

	public void setDayTable(JTable dayTable) {
		this.dayTable = dayTable;
	}

	public void setCreatePanel(JPanel createPanel) {
		this.createPanel = createPanel;
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

	public JToggleButton getCalendar() {
		return calendar;
	}

	public void setCalendar(JToggleButton calendar) {
		this.calendar = calendar;
	}

	public JToggleButton getAgenda() {
		return agenda;
	}

	public void setAgenda(JToggleButton agenda) {
		this.agenda = agenda;
	}

	public JScrollPane getScrollCalendarTable() {
		return scrollCalendarTable;
	}

	public void setScrollCalendarTable(JScrollPane scrollCalendarTable) {
		this.scrollCalendarTable = scrollCalendarTable;
	}

	public JPanel getWeekPanel() {
		return weekPanel;
	}

	public void setWeekPanel(JPanel weekPanel) {
		this.weekPanel = weekPanel;
	}

	public JTable getWeekTable() {
		return weekTable;
	}

	public JScrollPane getScrollWeekAgendaTable() {
		return scrollWeekAgendaTable;
	}

	public void setScrollWeekAgendaTable(JScrollPane scrollWeekAgendaTable) {
		this.scrollWeekAgendaTable = scrollWeekAgendaTable;
	}

	public void setWeekTable(JTable weekTable) {
		this.weekTable = weekTable;
	}

	public void setScrollDayTable(JScrollPane scrollDayTable) {
		this.scrollDayTable = scrollDayTable;
	}

	public JScrollPane getScrollAgendaTable() {
		return scrollAgendaTable;
	}

	public void setScrollAgendaTable(JScrollPane scrollAgendaTable) {
		this.scrollAgendaTable = scrollAgendaTable;
	}

	public JScrollPane getScrollWeekTable() {
		return scrollWeekTable;
	}

	public String[] getMonths() {
		return months;
	}

	public void setMonths(String[] months) {
		this.months = months;
	}

	public void setScrollWeekTable(JScrollPane scrollWeekTable) {
		this.scrollWeekTable = scrollWeekTable;
	}
	
	public int getMonthBound() {
		return monthBound;
	}

	public void setMonthBound(int monthBound) {
		this.monthBound = monthBound;
	}

	public JLabel getLogin() {
		return login;
	}

	public void setLogin(JLabel login) {
		this.login = login;
	}

	public JTextField getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(JTextField loginUser) {
		this.loginUser = loginUser;
	}

	public JButton getBtnPrev() {
		return btnPrev;
	}

	public void setBtnPrev(JButton btnPrev) {
		this.btnPrev = btnPrev;
	}

	public JButton getBtnNext() {
		return btnNext;
	}

	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}

	public JToggleButton getDoctors() {
		return doctors;
	}

	public void setDoctors(JToggleButton doctors) {
		this.doctors = doctors;
	}

	public JPanel getCalendarPanel() {
		return calendarPanel;
	}

	public void setCalendarPanel(JPanel calendarPanel) {
		this.calendarPanel = calendarPanel;
	}

	public JPanel getLoginPanel() {
		return loginPanel;
	}

	public void setLoginPanel(JPanel loginPanel) {
		this.loginPanel = loginPanel;
	}

	public JPanel getDoctorListPanel() {
		return doctorListPanel;
	}

	public void setDoctorListPanel(JPanel doctorListPanel) {
		this.doctorListPanel = doctorListPanel;
	}

	public DefaultTableModel getModelCalendarTable() {
		return modelCalendarTable;
	}

	public void setModelCalendarTable(DefaultTableModel modelCalendarTable) {
		this.modelCalendarTable = modelCalendarTable;
	}

	public JPasswordField getLoginPass() {
		return loginPass;
	}

	public void setLoginPass(JPasswordField loginPass) {
		this.loginPass = loginPass;
	}

	public JComboBox<String> getDoctorsCBList() {
		return doctorsCBList;
	}

	public void setDoctorsCBList(JComboBox<String> doctorsCBList) {
		this.doctorsCBList = doctorsCBList;
	}

	public JTable getWeekAgendaTable() {
		return weekAgendaTable;
	}

	public void setWeekAgendaTable(JTable weekAgendaTable) {
		this.weekAgendaTable = weekAgendaTable;
	}

	public DefaultTableModel getModelDayTable() {
		return modelDayTable;
	}

	public void setModelDayTable(DefaultTableModel modelDayTable) {
		this.modelDayTable = modelDayTable;
	}

	public DefaultTableModel getModelAgendaTable() {
		return modelAgendaTable;
	}

	public void setModelAgendaTable(DefaultTableModel modelAgendaTable) {
		this.modelAgendaTable = modelAgendaTable;
	}

	public DefaultTableModel getModelWeekTable() {
		return modelWeekTable;
	}

	public void setModelWeekTable(DefaultTableModel modelWeekTable) {
		this.modelWeekTable = modelWeekTable;
	}

	public DefaultTableModel getModelWeekAgendaTable() {
		return modelWeekAgendaTable;
	}

	public void setModelWeekAgendaTable(DefaultTableModel modelWeekAgendaTable) {
		this.modelWeekAgendaTable = modelWeekAgendaTable;
	}

	public JScrollPane getScrollDoctorList() {
		return scrollDoctorList;
	}

	public void setScrollDoctorList(JScrollPane scrollDoctorList) {
		this.scrollDoctorList = scrollDoctorList;
	}

	public JFrame getLoginFrame() {
		return loginFrame;
	}

	public void setLoginFrame(JFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	public JFrame getDoctorListFrame() {
		return doctorListFrame;
	}

	public void setDoctorListFrame(JFrame doctorListFrame) {
		this.doctorListFrame = doctorListFrame;
	}

	public JList<String> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(JList<String> doctorList) {
		this.doctorList = doctorList;
	}

	public DefaultListModel<String> getModelDoctorList() {
		return modelDoctorList;
	}

	public void setModelDoctorList(DefaultListModel<String> modelDoctorList) {
		this.modelDoctorList = modelDoctorList;
	}

	public JMenuItem getDelete() {
		return delete;
	}

	public void setDelete(JMenuItem delete) {
		this.delete = delete;
	}

	public JMenuItem getNotifyDoctor() {
		return notifyDoctor;
	}

	public void setNotifyDoctor(JMenuItem notifyDoctor) {
		this.notifyDoctor = notifyDoctor;
	}

	public JMenuItem getNotifyClient() {
		return notifyClient;
	}

	public void setNotifyClient(JMenuItem notifyClient) {
		this.notifyClient = notifyClient;
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
