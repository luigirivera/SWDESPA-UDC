package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;
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
		modelAgendaTable.addColumn("Start");
		modelAgendaTable.addColumn("End");
		modelAgendaTable.addColumn("Details");
		
		modelAgendaTable.setColumnCount(3);
		
		agendaTable.setRowHeight(50);
		agendaTable.getColumnModel().getColumn(0).setCellRenderer(new WeekAgendaTableRenderer()); //FOR TIME
		agendaTable.getColumnModel().getColumn(0).setPreferredWidth(220);
		
		//TODO ? AgendaTableCellRenderer itemRender = new AgendaTableCellRenderer();
		//itemRender.setOpaque(false);
		//agendaTable.getColumnModel().getColumn(1).setCellRenderer(itemRender);
		
		agendaTable.getColumnModel().getColumn(1).setCellRenderer(new WeekAgendaTableRenderer()); //FOR APPOINTMENTS
		agendaTable.getColumnModel().getColumn(1).setPreferredWidth(220);
		//agendaTable.getColumnModel().getColumn(1).setPreferredWidth(agendaTable.getWidth() - agendaTable.getColumnModel().getColumn(0).getWidth()-95);

		agendaTable.getColumnModel().getColumn(2).setCellRenderer(new WeekAgendaTableRenderer()); //FOR APPOINTMENTS
		agendaTable.getColumnModel().getColumn(2).setPreferredWidth(220);		
		
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
		modelWeekTable.setColumnCount(1);
		modelWeekTable.addColumn("Sunday");
		modelWeekTable.addColumn("Monday");
		modelWeekTable.addColumn("Tuesday");
		modelWeekTable.addColumn("Wednesday");
		modelWeekTable.addColumn("Thursday");
		modelWeekTable.addColumn("Friday");
		modelWeekTable.addColumn("Saturday");
		
		for(int i = 1; i < 8; i++)
		{
			weekTable.getColumnModel().getColumn(i).setCellRenderer(new WeekTableRenderer()); //FOR APPOINTMENTS
			weekTable.getColumnModel().getColumn(i).setPreferredWidth(100);
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
