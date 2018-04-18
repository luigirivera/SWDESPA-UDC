package ultimatedesignchallenge.Doctor;

public class DoctorThread extends Thread {

	private Thread t;
	private DoctorModel dm;

	public DoctorThread(DoctorModel dm) {
		this.dm = dm;
		System.out.println("Creating Thread for " + this.dm);
	}

	public void run() {
		try {

			while (true) {
				System.out.println("Hello Doctor!");
				dm.notifyObservers();
				java.lang.Thread.sleep(3000);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void start() {
		System.out.println("Starting Thread");
		if (t == null) {
			t = new Thread(this/* params */);
			t.start();
		}
	}

}