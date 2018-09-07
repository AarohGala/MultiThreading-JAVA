
public class threadCompleteOperation {

	public int count = 0;
	
	// Every object in Java has an intrinsic lock with it. The syntax contains an implied lock and unlock for it. 
	//The body of the synchronized block will not be entered until the lock has been acquired.
	public synchronized void increment() {
		count++;
	}
	
	public static void main(String[] args){
		
		threadCompleteOperation app = new threadCompleteOperation();
		app.doWork();
		
	}
	
	public void doWork() {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 0; i < 10000; i++)
					increment();
			}
			
		});
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int i = 0; i < 10000; i++)
					increment();
			}
			
		});
		
		t1.start();
		t2.start();
		
		try {
			
			//Join will make the program wait at this point till the thread is not complete.
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Count is : " + count);
	}
}
