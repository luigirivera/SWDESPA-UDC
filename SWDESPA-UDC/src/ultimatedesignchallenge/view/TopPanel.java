package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class TopPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	JLabel titleLabel;
	JButton today;
	JToggleButton calendar, agenda;
	JComboBox<String> viewType;
	ButtonGroup buttonGroup;
	
	public TopPanel(String topLabel)
	{
		super(null);
		
		instantiate(topLabel);
		initialize();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	private void instantiate(String topLabel)
	{
		titleLabel = new JLabel(topLabel);
		
		today = new JButton("Today");
		
		calendar = new JToggleButton("Calendar");
		agenda = new JToggleButton("Agenda");
		
		viewType = new JComboBox<String>();
		
		buttonGroup = new ButtonGroup();
	}
	
	private void initialize()
	{
		titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
		
		add(titleLabel);
		add(today);
		add(calendar);
		add(agenda);
		add(viewType);
		
		viewType.addItem("Day");
		viewType.addItem("Week");
		viewType.addItem("Month");
		
		buttonGroup.add(calendar);
		buttonGroup.add(agenda);
		
		titleLabel.setBounds(10, 10, 250, 50);
		today.setBounds(280, 15, 100, 40);
		viewType.setBounds(660, 5, 100, 60);
		calendar.setBounds(785, 15, 70, 40);
		agenda.setBounds(850, 15, 70, 40);
	}

}
