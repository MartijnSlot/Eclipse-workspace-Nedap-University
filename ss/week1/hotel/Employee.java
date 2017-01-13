package ss.week1.hotel;

public class Employee {
	
	private static int hours = 50;
	private static double rate = 15.0;
	private static double pay;
	
	public static void main(String[] args){

		if (hours <= 40)
			pay = hours * rate;
		else
			pay = rate * 40 + rate * 1.5 * (hours - 40);
		System.out.println("Paid: " + pay + " dollar");
	}

}
