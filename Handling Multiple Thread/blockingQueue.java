import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class blockingQueue {

	//FIFO Queue which stores request as they come
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
	
	public static void main(String[] args) throws InterruptedException{
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					consumer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
	
	private static void producer() throws InterruptedException
	{
		Random random = new Random();
		while(true) {
			//Add an Object to the queue
			//If the queue is full then it will wait till there is space to add the next Object
			queue.put(random.nextInt(100));
		}
	}
	
	private static void consumer() throws InterruptedException{
		Random random = new Random();
		
		while(true) {
			Thread.sleep(100);
			
			if(random.nextInt(10) == 0) {
				//Remove the first Object from the Queue
				//If there is no Object then it will automatically wait
				Integer value = queue.take();
				System.out.println("Value taken: "+value+" and Queue size is: "+queue.size());
			}
		}
	}
}