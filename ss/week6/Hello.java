package week6;

import java.util.Scanner;

public class Hello {

	public static void main(String[] args){

		String input = null;
		
		while (true){
			Scanner in = new Scanner(System.in); //Creates a new scanner
			System.out.println("What? Is? Your? Name?"); //Asks question
			input = in.nextLine(); //Waits for input
			if ("".equals(input)) break;			
			System.out.println("Hello " + input);//Displays the shit
		}
	}
}
