/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.SwingConstants;

public class TableRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	private CalendarFramework framework;
	
	public TableRenderer(CalendarFramework framework)
	{
		this.framework = framework;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		
		if (column == 0 || column == 6)
			setBackground(new Color(220, 220, 255));
		else
			setBackground(Color.WHITE);
		
		if(table.getValueAt(row, column) == null)
			setBackground(Color.GRAY);
		
		
		else
		{
			Calendar cal = Calendar.getInstance();
			cal.set(framework.yearToday, framework.monthToday, (int)table.getValueAt(row, column));
			
			//TODO:
			//if(this day has no slots allocated OR slots are full)
			//	setBackground(Color.RED);
			//else
			// setBackground(Color.GREEN);
		}
		
		setBorder(null);
		setForeground(Color.black);
		setVerticalAlignment(SwingConstants.TOP); // added
		return this;
	}
}
