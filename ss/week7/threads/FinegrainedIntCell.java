package week7.threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FinegrainedIntCell implements IntCell {

	private int value = 0;
	private boolean inbuffer = false;

	private Lock lock;
	private Condition doWrite;
	private Condition doRead;
	
	public FinegrainedIntCell(){
		lock = new ReentrantLock();
		doWrite = lock.newCondition();
		doRead = lock.newCondition();
	}

	public void setValue(int valueArg) {
		lock.lock();
		while(inbuffer){
			try {
				doWrite.await();
			} catch (InterruptedException e){}
		}
		doWrite.signal();
		this.value = valueArg;
		inbuffer = true;
		lock.unlock();
	}

	public int getValue() {
		while(!inbuffer){
			try {
				doRead.await();
			} catch (InterruptedException e){}				
		}
		doRead.signal();
		inbuffer = false;
		notifyAll();
		return value;
	}
}


/**************************************************************************
 * (C) Copyright 1999 by Deitel & Associates, Inc. and Prentice Hall.     *
 * All Rights Reserved.                                                   *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
