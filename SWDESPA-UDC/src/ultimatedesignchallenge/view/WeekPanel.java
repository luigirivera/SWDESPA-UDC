package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ultimatedesignchallenge.view.DayPanel.AgendaTableRenderer;

public class WeekPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JTable weekTable, agendaTable;
	DefaultTableModel modelWeekTable, modelAgendaTable;
	JScrollPane scrollWeekTable, scrollAgendaTable;
	
	
	public WeekPanel()
	{
		super(null);
		setVisible(false);
		instantiate();
		initialize();
		
		generateWeekTable();
		generateAgendaTable();
	}
	
	private void instantiate()
	{
		modelWeekTable = new DefaultTableModel() {
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
		
		weekTable = new JTable(modelWeekTable);
		agendaTable = new JTable(modelAgendaTable);
		scrollWeekTable = new JScrollPane(weekTable);
		scrollAgendaTable = new JScrollPane(agendaTable);
	}
	
	private void initialize()
	{
		add(scrollWeekTable);
		add(scrollAgendaTable);
		
		scrollAgendaTable.setVisible(false);
		scrollWeekTable.setVisible(false);
		
		scrollWeekTable.setBounds(0, 0, 660, 580);
		scrollAgendaTable.setBounds(scrollWeekTable.getBounds());
	}
	
	private void generateWeekTable() {
		modelWeekTable.addColumn("Time");
		for(int i = 0; i< 7; i++)
			modelWeekTable.addColumn("Day " + (i+1));
			
		modelWeekTable.setColumnCount(8);
		modelWeekTable.setRowCount(48);
		
		weekTable.setShowVerticalLines(true);
		weekTable.setGridColor(Color.BLACK);
		weekTable.setRowHeight(75);
		weekTable.getColumnModel().getColumn(0).setCellRenderer(new WeekTableRenderer()); //FOR TIME
		weekTable.getColumnModel().getColumn(0).setPreferredWidth(65);
		for(int i = 1; i < 8; i++)
		{
			weekTable.getColumnModel().getColumn(i).setCellRenderer(new WeekTableRenderer()); //FOR APPOINTMENTS
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
	
	private void generateAgendaTable() {
		modelAgendaTable.addColumn("Time");
		modelAgendaTable.addColumn("Event/Task");
		
		modelAgendaTable.setColumnCount(2);
		
		agendaTable.setRowHeight(50);
		agendaTable.getColumnModel().getColumn(0).setCellRenderer(new AgendaTableRenderer()); //FOR TIME
		agendaTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		//TODO ? AgendaTableCellRenderer itemRender = new AgendaTableCellRenderer();
		//itemRender.setOpaque(false);
		//agendaTable.getColumnModel().getColumn(1).setCellRenderer(itemRender);
		
		agendaTable.getColumnModel().getColumn(1).setCellRenderer(new AgendaTableRenderer()); //FOR APPOINTMENTS
		agendaTable.getColumnModel().getColumn(1).setPreferredWidth(agendaTable.getWidth() - agendaTable.getColumnModel().getColumn(0).getWidth()-95);
		agendaTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		agendaTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		agendaTable.getTableHeader().setReorderingAllowed(false);
		agendaTable.getTableHeader().setResizingAllowed(false);
		//agendaTable.setTableHeader(null);
		
		agendaTable.setOpaque(false);
		scrollAgendaTable.setOpaque(false);
		scrollAgendaTable.getViewport().setOpaque(false);
		scrollAgendaTable.setBorder(BorderFactory.createEmptyBorder());
		agendaTable.setBorder(BorderFactory.createEmptyBorder());
		agendaTable.setShowGrid(false);

		((DefaultTableCellRenderer)agendaTable.getDefaultRenderer(Object.class)).setOpaque(false);
	}
	
	public void refreshWeekTable(int monthToday, int dayToday, int yearToday)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(yearToday, monthToday, dayToday);
		cal.get(Calendar.WEEK_OF_YEAR);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		modelWeekTable.setColumnCount(1);
		modelWeekTable.addColumn(Month.of(cal.get(Calendar.MONTH)+1) + " " + cal.get(Calendar.DATE));

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		modelWeekTable.addColumn(Month.of(cal.get(Calendar.MONTH)+1) + " " + cal.get(Calendar.DATE));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		modelWeekTable.addColumn(Month.of(cal.get(Calendar.MONTH)+1) + " " + cal.get(Calendar.DATE));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		modelWeekTable.addColumn(Month.of(cal.get(Calendar.MONTH)+1) + " " + cal.get(Calendar.DATE));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		modelWeekTable.addColumn(Month.of(cal.get(Calendar.MONTH)+1) + " " + cal.get(Calendar.DATE));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		modelWeekTable.addColumn(Month.of(cal.get(Calendar.MONTH)+1) + " " + cal.get(Calendar.DATE));
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		modelWeekTable.addColumn(Month.of(cal.get(Calendar.MONTH)+1) + " " + cal.get(Calendar.DATE));
		
		for(int i = 1; i < 8; i++)
		{
			weekTable.getColumnModel().getColumn(i).setCellRenderer(new WeekTableRenderer()); //FOR APPOINTMENTS
			weekTable.getColumnModel().getColumn(i).setPreferredWidth(100);
		}
			
	}
	
	class WeekTableRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			
			if(column == 0)
			{
				setHorizontalAlignment(SwingConstants.RIGHT);
			}
			else
			{
				//TODO:
				/* if(this slot is not set by any doctor)
				 * 	setBackground(Color.BLACK);
				 * else if(this slot is unoccupied)
				 * 	setBackground(Color.WHITE);
				 * else
				 * 	setBackground(color of doctor who has an appointment on this slot)
				 * 
				 */
			}
			
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}
	
	class AgendaTableRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
				int row, int column) {
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			
			if(column == 0)
			{
				//TODO:
				setHorizontalAlignment(SwingConstants.RIGHT);
				//setForeground(color of doctor who alloted this slot). mixture if both
			}
			else
			{
				//setForeground(color of doctor who has an appointment on this slot)
			}
			
			
			setBorder(null);
			setForeground(Color.black);
			return this;
		}
	}

	public JTable getWeekTable() {
		return weekTable;
	}

	public void setWeekTable(JTable weekTable) {
		this.weekTable = weekTable;
	}

	public JTable getAgendaTable() {
		return agendaTable;
	}

	public void setAgendaTable(JTable agendaTable) {
		this.agendaTable = agendaTable;
	}

	public DefaultTableModel getModelWeekTable() {
		return modelWeekTable;
	}

	public void setModelWeekTable(DefaultTableModel modelWeekTable) {
		this.modelWeekTable = modelWeekTable;
	}

	public DefaultTableModel getModelAgendaTable() {
		return modelAgendaTable;
	}

	public void setModelAgendaTable(DefaultTableModel modelAgendaTable) {
		this.modelAgendaTable = modelAgendaTable;
	}

	public JScrollPane getScrollWeekTable() {
		return scrollWeekTable;
	}

	public void setScrollWeekTable(JScrollPane scrollWeekTable) {
		this.scrollWeekTable = scrollWeekTable;
	}

	public JScrollPane getScrollAgendaTable() {
		return scrollAgendaTable;
	}

	public void setScrollAgendaTable(JScrollPane scrollAgendaTable) {
		this.scrollAgendaTable = scrollAgendaTable;
	}

}
