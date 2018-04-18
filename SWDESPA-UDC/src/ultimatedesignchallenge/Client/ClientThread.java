package ultimatedesignchallenge.Client;

public class ClientThread extends Thread {

	private Thread t;
	private ClientModel cm;

	public ClientThread(ClientModel cm) {
		this.cm = cm;
		System.out.println("Creating Thread for " + this.cm);
	}

	public void run() {
		try {

			while (true) {
				System.out.println("Hello Client!");
				cm.notifyObservers();
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
