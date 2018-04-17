package ultimatedesignchallenge.view;

import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ultimatedesignchallenge.view.CalendarFramework.AgendaTableCellRenderer;
import ultimatedesignchallenge.view.CalendarFramework.CellDataHolder;

public class MonthPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JTable monthTable, agendaTable;
	DefaultTableModel modelMonthTable, modelAgendaTable;
	JScrollPane scrollMonthTable, scrollAgendaTable;
	
	public MonthPanel()
	{
		super(null);
		setVisible(false);
		instantiate();
		initialize();
		
		generateAgendaTable();
	}
	
	private void instantiate()
	{
		modelMonthTable = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		modelAgendaTable = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		
		monthTable = new JTable(modelMonthTable);
		agendaTable = new JTable(modelAgendaTable);
		
		scrollMonthTable = new JScrollPane(monthTable);
		scrollAgendaTable = new JScrollPane(agendaTable);
	}
	
	private void initialize()
	{
		add(scrollMonthTable);
		add(scrollAgendaTable);
		
		scrollAgendaTable.setVisible(false);
		scrollMonthTable.setVisible(false);
		
		scrollMonthTable.setBounds(0, 0, 660, 580);
		scrollAgendaTable.setBounds(scrollMonthTable.getBounds());
	}
	
	private void generateAgendaTable() {
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		rightRender.setOpaque(false);
		
		modelAgendaTable.setColumnCount(2);
		
		agendaTable.setRowHeight(50);
		agendaTable.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		agendaTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		//TODO ? AgendaTableCellRenderer itemRender = new AgendaTableCellRenderer();
		// itemRender.setOpaque(false);
		// table.getColumnModel().getColumn(1).setCellRenderer(itemRender);
		
		
		agendaTable.getColumnModel().getColumn(1).setPreferredWidth(agendaTable.getWidth() - agendaTable.getColumnModel().getColumn(0).getWidth()-95);
		agendaTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
	
	void generateCalendar(int dayBound, int monthBound, int yearBound, CellDataHolder validCells) {
		
		String[] headers = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" }; // All headers
		monthTable.setRowHeight(90);
			
		for (int i = 0; i < 7; i++)
			modelMonthTable.addColumn(headers[i]);
		
		monthTable.getParent().setBackground(monthTable.getBackground()); // Set background

		monthTable.getTableHeader().setResizingAllowed(false);
		monthTable.getTableHeader().setReorderingAllowed(false);

		monthTable.setColumnSelectionAllowed(true);
		monthTable.setRowSelectionAllowed(true);

		modelMonthTable.setColumnCount(7);
		modelMonthTable.setRowCount(6);
		
		refreshCalendar(monthBound, yearBound, validCells); // Refresh calendar
	}
	
	public void refreshCalendar(int month, int year, CellDataHolder validCells) {
		int nod, som, i, j;

		for (i = 0; i < 6; i++)
			for (j = 0; j < 7; j++)
				modelMonthTable.setValueAt(null, i, j);

		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		// Added this
		validCells.getList().clear();

		for (i = 1; i <= nod; i++) {
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			modelMonthTable.setValueAt(i, row, column);
			// Added lines below
			validCells.getList().add(new CellData(i, row, column));
			/*try {
				refreshTileEvents(i, row, column);
			} catch (NullPointerException e) {
				System.out.println("No CalendarModel yet");
			}*/
		}

		
		monthTable.setDefaultRenderer(monthTable.getColumnClass(0), new TableRenderer());
	}

}
