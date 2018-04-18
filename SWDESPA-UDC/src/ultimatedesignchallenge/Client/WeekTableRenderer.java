package ultimatedesignchallenge.Client;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class WeekTableRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		
		if(column == 0)
		{
			setHorizontalAlignment(SwingConstants.LEFT);
		}
		else
		{
//			if(table.getValueAt(row, column) == null)
//				setBackground(Color.BLACK);
//			else
//				setBackground(Color.GREEN);
			
			/*	if(table.getValueAt(row, column) == null)
			 * 		setBackground(Color.BLACK);
			 * 	else if(slot is client's)
			 * 		setBackground(color of the doctor)
			 * 	else if(slot is not theirs)
			 * 		setBackground(Color.GRAY)
			 * 	else
			 * 		setBackground(Color.WHITE)
			 */
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
