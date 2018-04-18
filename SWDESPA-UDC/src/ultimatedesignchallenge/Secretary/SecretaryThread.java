package ultimatedesignchallenge.Secretary;

public class SecretaryThread extends Thread{
	
	private Thread t;
	private SecretaryView sv;
		
		SecretaryThread(SecretaryView sv) {
			this.sv = sv;
			System.out.println("Creating Thread for " + this.sv.getName());
		}
		
		
		public void run()
		{
			try {
				
				while(true)
				{
					System.out.println("Hello Secretary!");
					sv.update();
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
				t = new Thread(this);
						t.start();
			}
		}
}
