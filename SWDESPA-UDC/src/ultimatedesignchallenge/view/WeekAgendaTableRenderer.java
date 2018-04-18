package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public 	class WeekAgendaTableRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		
		if(column == 0)
		{
			//TODO:
			setHorizontalAlignment(SwingConstants.RIGHT);
			//setForeground(color of doctor who alloted this slot)
		}
		
		else if(column == 1)
		{
			//TODO:
			//setForeground(color of doctor who alloted this slot)
		}
		
		else
		{
			//setForeground(color of doctor who has an appointment on this slot)
		}
		
		
		setBorder(null);
		return this;
	}
}
