package week7.threads;

public class SynchronizedIntCell implements IntCell {

	private int value = 0;
	private boolean inbuffer = false;
	
	public synchronized void setValue(int valueArg) {
		while(inbuffer){
			try {
				wait();
			} catch (InterruptedException e){}
		}
		this.value = valueArg;
		notifyAll();
		inbuffer = true;
	}

	public synchronized int getValue() {
		while(!inbuffer){
			try {
				wait();
			} catch (InterruptedException e){}				
		}
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