package ultimatedesignchallenge.Secretary;

public class SecretaryThread extends Thread {

	private Thread t;
	private SecretaryModel sm;

	public SecretaryThread(SecretaryModel sm) {
		this.sm = sm;
		System.out.println("Creating Thread for " + this.sm);
	}

	public void run() {
		try {

			while (true) {
				System.out.println("Hello Secretary!");
				sm.notifyObservers();
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
			t = new Thread(this);
			t.start();
		}
	}
}
