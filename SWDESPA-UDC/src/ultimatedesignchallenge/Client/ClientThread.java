package ultimatedesignchallenge.Client;

public class ClientThread extends Thread{
	
private Thread t;
private ClientView cv;
	
	ClientThread(ClientView cv) {
		this.cv = cv;
		System.out.println("Creating Thread for " + this.cv.getName());
	}
	
	
	public void run()
	{
		try {
			
			while(true)
			{
				System.out.println("Hello Client!");
				cv.update();
				java.lang.Thread.sleep(3000);
			}
		
		} 
		catch (InterruptedException e) 
				{
			// TODO Auto-generated catch block
			e.printStackTrace();
					}
		
			}
	
	public void start ()
	{
		System.out.println("Starting Thread");
		if(t==null)
		{
			t = new Thread(this/*params*/);
					t.start();
		}
	}

}
