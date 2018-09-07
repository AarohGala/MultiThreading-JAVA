import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//Semaphore are mainly used to limit the number of simultaneous threads that can access a resources, but you can also use them to implement deadlock
//recovery systems since a semaphore with one permit is basically a lock that can unlock from other threads.

//Mutex (or a semaphore initialized to 1; meaning there's only one resource) is basically a mutual exclusion; Only one thread can acquire the resource
//at once, and all other threads trying to acquire the resource are blocked until the thread owning the resource releases.
 
//Semaphore is used to control the number of threads executing. There will be fixed set of resources. The resource count will gets decremented every time
//when a thread owns the same. When the semaphore count reaches 0 then no other threads are allowed to acquire the resource. The threads get blocked till
//other threads owning resource releases.

class connection {

    private static connection instance = new connection();
   
    //limit connections to 10
    //true means whichever thread gets first in the waiting pool (queue) waiting to acquire a resource, 
    //is first to obtain the permit.
       
    private Semaphore sem = new Semaphore(10, true);

    //Singleton Object
    private connection() {
    }

    public static connection getInstance() {
        return instance;
    }

    public void connect() {
        try {

            // get permit decrease the sem value, if 0 wait for release
            sem.acquire();

            System.out.printf("%s:: Current connections (max 10 allowed): %d\n",
                    Thread.currentThread().getName(),
                    sem.availablePermits());

            //do your job
            System.out.printf("%s:: WORKING...\n",
                    Thread.currentThread().getName());
            Thread.sleep(2000);

            System.out.printf("%s:: Connection released. Permits Left = %d\n",
                    Thread.currentThread().getName(),
                    sem.availablePermits());

        } catch (InterruptedException ignored) {
        } finally {
            //release permit, increase the sem value and activate waiting thread
            sem.release();
        }
    }
}

public class semaphores {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i = 0; i < 200; i++) { 
            executor.submit(new Runnable() {
                public void run() {
                    connection.getInstance().connect();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}