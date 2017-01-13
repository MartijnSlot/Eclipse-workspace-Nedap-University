package week4;

import java.util.Scanner;

public class importExample {
	
	private Scanner sc;

	public importExample(){
	}
	
	public void getPow(){
	    sc = new Scanner(System.in);
	    System.out.println("Enter first integer: ");    // 3
	    int first = sc.nextInt();
	    System.out.println("Enter second integer: ");    // 2
	    int second = sc.nextInt();
	    System.out.println(first + " to the power of " + second + " is " + 
	        Math.pow((double)first, (double)second));    // outputs 9
	}
	
	public static void main(String[] args){
		importExample snikkel = new importExample();
		snikkel.getPow();
	}
}