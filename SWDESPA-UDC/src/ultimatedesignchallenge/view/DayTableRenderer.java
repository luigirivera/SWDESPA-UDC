package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public 	class DayTableRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 1L;
	
	private int TimeToRowNumber(String eTime) {

        switch (eTime) {

            case "0:30":
                return 1;

            case "1:00":
                return 2;

            case "1:30":
                return 3;

            case "2:00":
                return 4;

            case "2:30":
                return 5;

            case "3:00":
                return 6;

            case "3:30":
                return 7;

            case "4:00":
                return 8;

            case "4:30":
                return 9;

            case "5:00":
                return 10;

            case "5:30":
                return 11;

            case "6:00":
                return 12;

            case "6:30":
                return 13;

            case "7:00":
                return 14;

            case "7:30":
                return 15;

            case "8:00":
                return 16;

            case "8:30":
                return 17;

            case "9:00":
                return 18;

            case "9:30":
                return 19;

            case "10:00":
                return 20;

            case "10:30":
                return 21;

            case "11:00":
                return 22;

            case "11:30":
                return 23;

            case "12:00":
                return 24;

            case "12:30":
                return 25;

            case "13:00":
                return 26;

            case "13:30":
                return 27;

            case "14:00":
                return 28;

            case "14:30":
                return 29;

            case "15:00":
                return 30;

            case "15:30":
                return 31;

            case "16:00":
                return 32;

            case "16:30":
                return 33;

            case "17:00":
                return 34;

            case "17:30":
                return 35;

            case "18:00":
                return 36;

            case "18:30":
                return 37;

            case "19:00":
                return 38;

            case "19:30":
                return 39;

            case "20:00":
                return 40;

            case "20:30":
                return 41;

            case "21:00":
                return 42;

            case "21:30":
                return 43;

            case "22:00":
                return 44;

            case "22:30":
                return 45;

            case "23:00":
                return 46;

            case "23:30":
                return 47;

            //if time is 0:00    
            default:
                return 0;
        }
    }
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused,
			int row, int column) {
		
		super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		
		if(column == 0)
		{
			//TODO:
			setHorizontalAlignment(SwingConstants.LEFT);
			setBackground(Color.WHITE);
		}
		
		if (table.getValueAt(row, column) != null && column == 1)
		{
			String sval = String.valueOf(table.getValueAt(row, column));
			
			String tempTime = sval.substring(sval.indexOf("T")+1, sval.indexOf(" ")+37);
			
			String sTime = sval.substring(sval.indexOf("T") + 1, sval.indexOf(" ")+17);
			String eTime = tempTime.substring(tempTime.indexOf("T")+1, tempTime.length());
			
			String TmonthAndDay = sval.substring(sval.indexOf("-")+1, sval.indexOf("T"));
			String[] monthAndDay = TmonthAndDay.split("-");
			
			String year = sval.substring(sval.indexOf(" ") +1, sval.indexOf("-"));
			String month = monthAndDay[0];
			String day = monthAndDay[1];
			
			//jDoc is first doc
			int jDoc = TimeToRowNumber(sTime);
			
			while(jDoc < TimeToRowNumber(eTime)) {
				String firstTime = String.valueOf(table.getValueAt(row, 0));
				System.out.println(firstTime + " <-- firstTime sTime---> " + sTime);
				System.out.println(firstTime.equals(sTime));
				if(firstTime.equals(sTime)) {
					setBackground(Color.GREEN);
					//setBackground(Color.firstdoctor.getColor());
				}
				jDoc++;
			}
			
		}
		
		
		setBorder(null);
		setForeground(Color.black);
		return this;
	}
}
