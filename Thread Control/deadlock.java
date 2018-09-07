import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {

    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acc1, Account acc2, int amount) {
        acc1.withdraw(amount);
        acc2.deposit(amount);
    }
}

class AccountTransaction {

    private Account acc1 = new Account();
    private Account acc2 = new Account();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    //don't hold several locks at once, else it will result into deadlock.
    //If you do, always acquire the locks in the same order and try to get the both locks
    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true) {
        	
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;
            try {
                /**
                 * tryLock() which will only acquire a lock if it’s available
                 * and not already acquired by another thread and tryLock(long
                 * time,TimeUnit unit), which will try to acquire a lock and, if
                 * it's unavailable wait for the specified timer to expire
                 * before giving up
                 */
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } 
            finally {
                if (gotFirstLock && gotSecondLock) 
                	return;
                //If only one lock is acquired then release that lock
                else if (gotFirstLock) 
                	firstLock.unlock();
                else if (gotSecondLock) 
                	secondLock.unlock();
            }
            // Locks not acquired
            Thread.sleep(1);
        }
    }

    public void firstThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
        	//deadlock condition
        	//lock1.lock();
        	//lock2.lock();
            acquireLocks(lock1, lock2);
            try {
                Account.transfer(acc1, acc2, random.nextInt(100));
            } 
            finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    //SecondThread runs
    public void secondThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            
        	//deadlock condition
        	//lock2.lock();
        	//lock1.lock();
        	//deadlock happened when firstThread acquired lock1 and secondThread acquired lock2 at the same time
        	//Both are waiting on for other to release the lock
        	acquireLocks(lock2, lock1);
            try {
                Account.transfer(acc2, acc1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    //When both threads finish execution, finished runs
    public void finished() {
        System.out.println("Account 1 balance: " + acc1.getBalance());
        System.out.println("Account 2 balance: " + acc2.getBalance());
        System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));
    }
}

public class deadlock {

	public static void main(String[] args) {
		final AccountTransaction account = new AccountTransaction();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    account.firstThread();
                } catch (InterruptedException ignored) {}
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    account.secondThread();
                } catch (InterruptedException ignored) {}
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
       
        account.finished();
	}
}
