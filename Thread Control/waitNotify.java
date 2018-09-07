import java.util.Scanner;

class Process{
	public void Producer() throws InterruptedException {
		synchronized(this) {
			System.out.println("Producer Thread started...");
			//Leave the intrinsic lock and wait till notify is called to acquire lock again and execute the rest.
			wait();
			System.out.println("Producer Thread Resumed...");
		}
	}
	
	public void Consumer() throws InterruptedException {
		Scanner s = new Scanner(System.in);
		Thread.sleep(2000);
		
		synchronized(this) {
			System.out.println("Waiting for return key ...");
			s.nextLine();
			System.out.println("Return Key Pressed.");
			//Will notify the wait. It won't release the lock until rest of the code is executed, then it will release the lock.
			notify();
		}
	}
}
public class waitNotify {

	private static Process process = new Process();
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					process.Producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					process.Consumer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
