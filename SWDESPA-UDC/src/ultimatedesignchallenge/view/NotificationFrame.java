package ultimatedesignchallenge.view;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class NotificationFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JTextPane textPane;
	private JScrollPane scrollTextPane;
	
	public NotificationFrame()
	{
		super("Notifications");
		setSize(300,700);
		setResizable(false);
		instantiate();
		initialize();
	}
	
	private void instantiate()
	{
		textPane = new JTextPane();
		scrollTextPane = new JScrollPane(textPane);
	}
	
	private void initialize()
	{
		add(scrollTextPane);
		
		scrollTextPane.setBounds(0, 0, this.getWidth(), this.getHeight());
		
	}

}
