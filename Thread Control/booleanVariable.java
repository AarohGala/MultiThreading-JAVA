//How to Interrupt a Thread

import java.util.*;

class processor extends Thread{

	//Thread normally doesn't expect another thread to change it's variable or manipulate anything. So in some system Thread cache the variable it is working/using.
	//Any update from other thread will not let the thread know change in variable in some system. Making the variable volatile will resolve this issue.
	//Making it volatile means the value of this variable will never be cached thread-locally: all reads and writes will go straight to main memory
	private volatile boolean running = true; 
	
	@Override
	public void run() {
		while(running)
		{
			System.out.println("Hello");
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		running = false;
	}
	
}

public class threadInterruption1 {
	
	public static void main(String[] args){
	
		processor process1 = new processor();
		process1.start();
		
		System.out.println("Press Return Key to stop the Thread ...");
		Scanner s = new Scanner(System.in);
		s.nextLine();
		
		process1.shutdown();
	}
}
