package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public 	class DayTableRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		
		if(column == 0)
		{
			//TODO:
			setHorizontalAlignment(SwingConstants.RIGHT);
			//setBackground(color of doctor who alloted this slot). mixture if both
		}
		else
		{
			/*if(there is an appointment)
			 * setBackground(color of doctor who has an appointment on this slot)
			 *else
			 *	setBackground(Color.WHITE)
			 */
		}
		
		
		setBorder(null);
		setForeground(Color.black);
		return this;
	}
}
