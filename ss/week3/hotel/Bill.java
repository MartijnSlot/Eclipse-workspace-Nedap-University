package week3.hotel;

import java.io.PrintStream;
import week3.Format;

public class Bill {
	
	private PrintStream out;
	private double sum;

	public Bill(PrintStream out) {
		if (out == null) {
			this.out = null;
		} else {
		this.out = out;
		}
		this.sum = 0;
	}
	
	public void newItem(Bill.Item item){
		Format.printLine(item.toString(), item.getAmount(), out);
		sum += item.getAmount();
	}

	public void close() {
		Format.printLine("Total sum: ", this.getSum(), out);
		
	}
	
	public double getSum() {
		return sum;
	}
	
	public static interface Item {
		double getAmount();
	}

}
