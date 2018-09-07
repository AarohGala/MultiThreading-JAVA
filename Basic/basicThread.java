//Thread are small programs which can be executed concurrently within a single program.
//Two ways to create a Thread

//Method 1: Extend to Thread class 
class runnerThread extends Thread{

	@Override
	public void run() {
		
		for(int i = 0; i < 10; i++){
			System.out.println("Hello 2 :"+i);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

//Method 2: Implement Runnable class
class runnerRunnable implements Runnable{

	@Override
	public void run() {
		
		for(int i = 0; i < 10; i++){
			System.out.println("Hello 3 :"+i);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

public class basicThread {
	public static void main(String[] args) {
		
		//This is similar to creating Class which implements Runnable
		//In this instead of creating a class we are creating a new Runnable object within Thread
		Thread runner1 = new Thread(new Runnable() {

			@Override
			public void run() {
				
				for(int i = 0; i < 10; i++){
					System.out.println("Hello 1 :"+i);
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		runner1.start();
		
		//Never do object.run()
		//It will run in the main program and not as a thread
		runnerThread runner2 = new runnerThread();
		runner2.start();
		
		Thread runner3 = new Thread(new runnerRunnable());
		runner3.start();
	}
}
