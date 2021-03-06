package ultimatedesignchallenge.view;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import ultimatedesignchallenge.model.Doctor;
import ultimatedesignchallenge.services.DoctorService;

public class DoctorList extends JFrame {
	private static final long serialVersionUID = 1L;
	JScrollPane scrollDoctorList;
	JList<String> doctorList;
	DefaultListModel<String> modelDoctorList;
	
	public DoctorList()
	{
		super("Clinic Doctors");
		
		setSize(300,700);
		setLayout(null);
		instantiate();
		initialize();
		
		setVisible(false);
		setResizable(false);
		
	}
	
	private void instantiate()
	{
		modelDoctorList = new DefaultListModel<String>();
		modelDoctorList.addElement("All");
		
		DoctorService service = new DoctorService();
		List<Doctor> doctors = service.getAll();
		for(Doctor d : doctors) {
			String temp = d.getLastname() + ", " + d.getFirstname();
			modelDoctorList.addElement(temp);
		}
		
		doctorList = new JList<String>(modelDoctorList);
		scrollDoctorList = new JScrollPane(doctorList);
	}
	
	private void initialize()
	{
		add(scrollDoctorList);
		scrollDoctorList.setBounds(0,0,this.getWidth(),this.getHeight());
	}

	public JList<String> getDoctorList() {
		return doctorList;
	}

	public void setDoctorList(JList<String> doctorList) {
		this.doctorList = doctorList;
	}

	public DefaultListModel<String> getModelDoctorList() {
		return modelDoctorList;
	}

	public void setModelDoctorList(DefaultListModel<String> modelDoctorList) {
		this.modelDoctorList = modelDoctorList;
	}
	
	
}
