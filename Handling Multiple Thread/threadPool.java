import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class process extends Thread{
	
	private int id;
	
	public process(int id) {
		this.id = id;	
	}
	
	public void run() {
		System.out.println("Starting Process: "+id);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Completed Process: "+id);
	}
}

public class threadPool {
	
	public static void main(String[] args) {
		
		//Creating N number of workers which will be assigned tasks.
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i = 0; i < 5; i++) {
			//Assign task to executor
			executor.submit(new process(i));
		}
		
		//Close executor after all the task
		executor.shutdown();
		
		System.out.println("All task submitted.");
		try {
			//Add a timeout to wait till a time
			//If the execution is not over till the timeout then it will start executing next statement
			executor.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All task completed.");
	}
}
