//Returning data from within the Thread using Callable and Future

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

public class callableFuture {

	public static void main(String[] args) {
		 ExecutorService executor = Executors.newCachedThreadPool();
		 
		 //Future captures all the results from Callable function
		 Future<Integer> future = executor.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				Random random = new Random();
				int duration = random.nextInt(4000);
				
				if(duration > 2000){
					throw new IOException("Sleep too long...");
				}
				
				System.out.println("Starting ... ");
				
				Thread.sleep(duration);
				
				System.out.println("Stopping ... ");
				
				return duration;
			}
			 
		 });
		 
		 executor.shutdown();
		 
		 //Future.get will wait till the thread is done
		 try {
			System.out.println("Result is: "+future.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			TimeoutException ex = (TimeoutException) e.getCause();
            System.out.println(ex.getMessage());
		}
	}
}
