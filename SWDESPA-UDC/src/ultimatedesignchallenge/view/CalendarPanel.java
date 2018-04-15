package ultimatedesignchallenge.view;

import java.awt.Font;
import java.awt.Insets;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import ultimatedesignchallenge.view.CalendarFramework.CellDataHolder;

public class CalendarPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December" };
	JButton btnPrev, btnNext, create;
	JTable calendarTable;
	JScrollPane scrollCalendarTable;
	DefaultTableModel modelCalendarTable;
	JLabel monthLabel;
	JToggleButton doctors;
	private CalendarFramework framework;
	
	public CalendarPanel(CalendarFramework framework)
	{
		super(null);
		
		this.framework = framework;
		instantiate();
		initialize();
	}
	
	private void instantiate()
	{
		monthLabel = new JLabel("January");
		
		btnPrev = new JButton("<");
		btnNext = new JButton(">");
		create = new JButton("Set Appointment");
		
		modelCalendarTable = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		doctors = new JToggleButton("Doctors");	
		calendarTable = new JTable(modelCalendarTable);
		scrollCalendarTable = new JScrollPane(calendarTable);
	}
	
	private void initialize()
	{
		add(monthLabel);
		add(btnPrev);
		add(btnNext);
		add(scrollCalendarTable);
		add(create);
		add(doctors);
		
		btnPrev.setMargin(new Insets(0,0,0,0));
		btnNext.setMargin(new Insets(0,0,0,0));
		
		monthLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		
		create.setBounds(10, 10, 250, 40);
		monthLabel.setBounds(10, 50, 200, 50);
		btnPrev.setBounds(180, 60, 40, 30);
		btnNext.setBounds(220, 60, 40, 30);
		scrollCalendarTable.setBounds(10, 100, 250, 390);
		doctors.setBounds(10, 500, 250,50);
	}
	
	void generateCalendar(CellDataHolder validCells) {
		GregorianCalendar cal = new GregorianCalendar();
		framework.setDayBound(cal.get(GregorianCalendar.DAY_OF_MONTH));
		framework.setMonthBound( cal.get(GregorianCalendar.MONTH));
		framework.setYearBound(cal.get(GregorianCalendar.YEAR));
		framework.setMonthToday(framework.getMonthBound());
		framework.setYearToday(framework.getYearBound());
		framework.setDayToday(framework.getDayBound());
		
		String[] headers = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }; // All headers
		calendarTable.setRowHeight(60);
		calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);	
			
		for (int i = 0; i < 7; i++)
			modelCalendarTable.addColumn(headers[i]);

		calendarTable.getParent().setBackground(calendarTable.getBackground()); // Set background

		calendarTable.getTableHeader().setResizingAllowed(false);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		calendarTable.setColumnSelectionAllowed(true);
		calendarTable.setRowSelectionAllowed(true);

		modelCalendarTable.setColumnCount(7);
		modelCalendarTable.setRowCount(6);
		
		refreshCalendar(framework.getMonthBound(), framework.getYearBound(), framework.getYearBound(), validCells); // Refresh calendar
	}
	
	public void refreshCalendar(int month, int year, int yearBound,  CellDataHolder validCells) {
		int nod, som, i, j;

		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= yearBound - 10)
			btnPrev.setEnabled(false);
		if (month == 11 && year >= yearBound + 100)
			btnNext.setEnabled(false);

		monthLabel.setText(months[month] + " " + year);
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
			/*try {
				refreshTileEvents(i, row, column);
			} catch (NullPointerException e) {
				System.out.println("No CalendarModel yet");
			}*/
		}

		calendarTable.setDefaultRenderer(calendarTable.getColumnClass(0), new TableRenderer());
	}

}
