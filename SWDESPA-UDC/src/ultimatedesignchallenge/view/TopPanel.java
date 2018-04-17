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
	
	JLabel titleLabel, viewLabel;
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
		viewLabel = new JLabel("");
		
		today = new JButton("Today");
		
		calendar = new JToggleButton("Calendar");
		agenda = new JToggleButton("Agenda");
		
		viewType = new JComboBox<String>();
		
		buttonGroup = new ButtonGroup();
	}
	
	private void initialize()
	{
		titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
		viewLabel.setFont(new Font("Arial", Font.BOLD, 25));
		
		add(titleLabel);
		add(today);
		add(viewLabel);
		add(calendar);
		add(agenda);
		add(viewType);
		
		viewType.addItem("Day");
		viewType.addItem("Week");
		
		buttonGroup.add(calendar);
		buttonGroup.add(agenda);
		
		titleLabel.setBounds(10, 10, 250, 50);
		today.setBounds(210, 15, 100, 40);
		viewLabel.setBounds(320, 10, 350, 50);
		viewType.setBounds(660, 5, 100, 60);
		calendar.setBounds(780, 15, 80, 40);
		agenda.setBounds(850, 15, 70, 40);
	}

	public JLabel getViewLabel() {
		return viewLabel;
	}

	public void setViewLabel(JLabel viewLabel) {
		this.viewLabel = viewLabel;
	}

}
