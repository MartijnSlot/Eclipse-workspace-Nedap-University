package week3;

import java.io.PrintStream;

public class Format {
	
	public static String printLine(String text, double amount, PrintStream out){
		String output = String.format("%-20s%.2f\n\t", text, amount, out);
		System.out.println(output);
		return output;
	}
	
	public static void main(String[] args){
		printLine("text1", 1.00, System.out);
		printLine("other text", -12.12, System.out);
		printLine("something", 9.20, System.out);
	}

}
