package ultimatedesignchallenge.view;

import java.awt.Color;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	JTable dayTable, agendaTable;
	DefaultTableModel modelDayTable, modelAgendaTable;
	JScrollPane scrollDayTable, scrollAgendaTable;
	
	public DayPanel()
	{
		super(null);
		
		instantiate();
		initialize();
		setVisible(true);
		
		generateDayTable();
		generateAgendaTable();
	}
	
	private void instantiate()
	{
		modelDayTable = new DefaultTableModel() {
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
		
		dayTable = new JTable(modelDayTable);
		agendaTable = new JTable(modelAgendaTable);
		
		scrollDayTable = new JScrollPane(dayTable);
		scrollAgendaTable = new JScrollPane(agendaTable);
	}
	
	private void initialize()
	{
		add(scrollDayTable);
		add(scrollAgendaTable);
		
		scrollAgendaTable.setVisible(false);
		
		scrollDayTable.setBounds(0, 0, 660, 580);
		scrollAgendaTable.setBounds(scrollDayTable.getBounds());
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
		//dayTable.getColumnModel().getColumn(1).setCellRenderer(new DayTableCellRenderer());//luis
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
	
	private void generateAgendaTable() {
		DefaultTableCellRenderer rightRender = new DefaultTableCellRenderer();
		rightRender.setHorizontalAlignment(SwingConstants.RIGHT);
		rightRender.setOpaque(false);
		
		modelAgendaTable.setColumnCount(2);
		
		agendaTable.setRowHeight(50);
		agendaTable.getColumnModel().getColumn(0).setCellRenderer(rightRender);
		agendaTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		//TODO ? AgendaTableCellRenderer itemRender = new AgendaTableCellRenderer();
		//itemRender.setOpaque(false);
		//agendaTable.getColumnModel().getColumn(1).setCellRenderer(itemRender);
		
		
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

		((DefaultTableCellRenderer)dayTable.getDefaultRenderer(Object.class)).setOpaque(false);
	}

	public JTable getDayTable() {
		return dayTable;
	}

	public void setDayTable(JTable dayTable) {
		this.dayTable = dayTable;
	}

	public JTable getAgendaTable() {
		return agendaTable;
	}

	public void setAgendaTable(JTable agendaTable) {
		this.agendaTable = agendaTable;
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

	public JScrollPane getScrollDayTable() {
		return scrollDayTable;
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
	
}
