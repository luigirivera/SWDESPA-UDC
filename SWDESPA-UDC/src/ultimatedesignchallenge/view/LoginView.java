package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPasswordField loginPass;
	private CalendarFramework frame;
	private JTextField loginUser;
	private JLabel login;
	private final String loginPlaceholderUser = "Username";
	private final String loginPlaceholderPass = "Password";
	
	public LoginView()
	{
		super("Clinic Login");
		setSize(520, 225);
		
		instantiate();
		initialize();
		addListeners();
		
		setLayout(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void instantiate()
	{
		panel = new JPanel();
		loginUser = new JTextField();
		loginPass = new JPasswordField();
		login = new JLabel("Clinic Login");
	}
	
	private void initialize()
	{
		panel.setLayout(null);
		
		login.setFont(new Font("Arial", Font.BOLD, 25));
		
		loginUser.setText(loginPlaceholderUser);
		loginPass.setText(loginPlaceholderPass);
		
		loginUser.setForeground(Color.GRAY);
		loginPass.setForeground(Color.GRAY);
		
		add(panel);
		panel.add(login);
		panel.add(loginUser);
		panel.add(loginPass);
		
		panel.setBounds(0, 0, this.getWidth(), this.getHeight());
		login.setBounds(190, 20, 250, 50);
		loginUser.setBounds(140, login.getY()+60, 250, 30);
		loginPass.setBounds(140, loginUser.getY()+30, 250, 30);
		
	}
	
	private void addListeners()
	{
		loginUser.addKeyListener(new loginKeyListener());
		loginPass.addKeyListener(new loginKeyListener());
		loginUser.addFocusListener(new loginUserFocusListener());
		loginPass.addFocusListener(new loginPassFocusListener());
	}
	
	class loginKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				new ClientView();
				new DoctorView();
				new SecretaryView();
			}
				
			
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
	
	class loginUserFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(loginUser.getText().equals(loginPlaceholderUser))
			{
				loginUser.setText("");
				loginUser.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(loginUser.getText().equals(""))
			{
				loginUser.setText(loginPlaceholderUser);
				loginUser.setForeground(Color.GRAY);
			}
			
		}
		
	}
	
	class loginPassFocusListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent arg0) {
			if(String.valueOf(loginPass.getPassword()).equals(loginPlaceholderPass))
			{
				loginPass.setText("");
				loginPass.setForeground(Color.BLACK);
			}
			
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			if(String.valueOf(loginPass.getPassword()).equals(""))
			{
				loginPass.setText(loginPlaceholderPass);
				loginPass.setForeground(Color.GRAY);
			}
			
		}
	}
}
