package week6;

import java.util.Scanner;

public class Words {

	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		String input;

		while(in.hasNext()){
			input = getInput(in);
			if ("end".equals(input)) break;
			else {
				printWords(input);
			}
		}
	}

	public static String getInput(Scanner in){
		String next = in.nextLine();
		System.out.printf("Line (or \"end\"): %s\n", next);
		return next;
	}

	public static void printWords(String input){
		String[] words = input.split(" ");

		for(int i = 1; i <= words.length; i++){
			System.out.printf("Word %d: %s\n", i, words[i-1]);
		}
	}
}
