import java.util.Random;

public class interrupt {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Starting.");

        Thread t1 =  new Thread(new Runnable() {

            public void run() {

            	Random random = new Random();
                for (int i = 0; i < 1E8; i++) {
                	
                	//One needs to check if the thread was interrupted or not.
//                    if (Thread.currentThread().isInterrupted()) {
//                        System.out.printf("Interrupted");
//                        break;
//                    }
                	try {
                		Thread.sleep(1);
                	}
                	catch(Exception e) {
                		System.out.println("Interrupted");
                		break;
                	}
                    Math.sin(random.nextInt());
                }

            }
        });
        
        t1.start();

        Thread.sleep(500);
        t1.interrupt();
        
        t1.join();
        System.out.println("Finished.");
    }

}