package Catan;

public class Main {

	public static void main(String[] args) {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		    	
		    	DatabaseManager.disconnect();
		        
		    }
		});
	}

}
