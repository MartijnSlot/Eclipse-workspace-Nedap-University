package week7.account;

public class AccountSync {;

public static void main(String[] args) {
	Account account = new Account();
	Thread thread1 = new Thread(new MyThread(500.0, 5, account));
	Thread thread2 = new Thread(new MyThread(-500.0, 5, account));
	thread1.start();
	thread2.start();

	try { 
		thread1.join(); 
		thread2.join();
		Thread.sleep(10);
	} catch (InterruptedException e) {} 

	System.out.println(account.getBalance());
}

}
