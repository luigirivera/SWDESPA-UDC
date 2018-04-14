package ultimatedesignchallenge.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import designchallenge1.CellStringFormatter;
import designchallenge1.EventStringFormatter;
import designchallenge1.HTMLCellMarkerFormatter;
import designchallenge1.HTMLEventMarkerFormatter;
import designchallenge2.item.CalendarEvent;
import designchallenge2.item.CalendarItem;
import designchallenge2.item.CalendarTask;
import designchallenge2.item.LegacyEventConverter;
import designchallenge2.view.AgendaHTMLItemStringFormatter;
import designchallenge2.view.CalendarObserver;
import designchallenge2.view.DayHTMLItemStringFormatter;
import designchallenge2.view.ItemStringFormatter;
import ultimatedesignchallenge.model.User;

public class SecretaryView extends CalendarFramework{
	private User model;
	
	public SecretaryView(User model) {
		super("Central Calendar Census - " + model.getFirstname());
		
//		this.model = model;
//		this.controller = controller;
		this.model = model;
		
		instantiate();
		constructorGen("Clinic Secretary");
		doctorListInst();
		init();
		initListeners();
	}
	
	private void instantiate() {
		createName = new JTextField();
		
		notifyDoctor = new JMenuItem("Notify Doctor");
		notifyClient = new JMenuItem("Notify Client");
	}
	
	private void init() {
		popup.add(notifyDoctor);
		popup.add(notifyClient);
		
		createName.setText(createPlaceholderName);
		createName.setForeground(Color.GRAY);
		
		
		createPanel.add(createName);		
		createName.setBounds(10, 30, 400, 40);
	}
}