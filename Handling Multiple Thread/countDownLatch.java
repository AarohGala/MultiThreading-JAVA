import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processors implements Runnable{
	private CountDownLatch latch;
	
	public Processors(CountDownLatch latch) {
		this.latch = latch;
	}
	
	public void run() {
		System.out.println("Started");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Reduce Latch CountDown
		latch.countDown();
	}
}

public class countDownLatch {

	public static void main(String[] args) {
		
		//CountDownLatch will wait till the latch CountDown becomes 0.
		//In simple words it will wait till CountDownLatch number of threads execution is complete.
		CountDownLatch latch = new CountDownLatch(3);
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		for(int i = 0; i < 3; i++){
			executor.submit(new Processors(latch));
		}
		
//		System.out.println("Started..");
		
		try {
			//Wait till the Latch CountDown becomes zero.
			//In executors we define wait time. If the work is done by then, than it will execute something ahead
			//Else it will wait till that time to timeout, which cannot be accurately predicted.
			latch.await();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Completed...");
		
	}
}
