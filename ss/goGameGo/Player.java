package goGameGo;

import java.util.Scanner;

/**
 * Abstract class for keeping a player in a GO game. 
 * 
 * @author Martijn Slot
 * @version 1.0
 */
public class Player {

	public String name;
	public Stone stone;

	public Player(String name, Stone stone) {
		this.name = name;
		this.stone = stone;
	}

	public String getName() {
		return name;
	}

	public Stone getStone() {
		return stone;
	}

	private Position enterMove(Board board) {
		boolean legalChoice = false;
		Position choice = null;

		while(!legalChoice) {
			legalChoice = true;
			String prompt = "Dear " + getName() + " (" + getStone().toString() + ")"
					+ ", please enter positions x,y (x and y must be on the board and can't be larger than " + board.dim + ") ";
			choice = readPos(prompt);
			if (!board.isAllowed(choice, stone)) {
				System.out.println("Field " + choice.getX() + ", " + choice.getY() + " is no valid position.");
				legalChoice = false;
			}
		}

		return choice;
	}


	private Position readPos(String prompt) {


		Scanner input = new Scanner(System.in);
		System.out.print(prompt);

		input.useDelimiter("\n");
		
		while (input.hasNext()){
			String inputLine = input.nextLine().replaceAll("\\D+", " ").trim();
			String[] coordinates = inputLine.split(" ");
			int[] xy = new int[coordinates.length];
			
			for (int i = 0; i < coordinates.length; i++) {
				xy[i] = Integer.parseInt(coordinates[i]);
			}
			if (xy.length == 2) {
				input.close();
				return new Position(xy[1], xy[0]);

			} else {
				input.close();
				System.out.println("Input coordina-te-rror! Please put 2 coordinates (x,y) in the input seperated by any number of non-digits.");
				return null;
			}
		}
		input.close();
		return null;
	}


	public void makeMove(Board board){
		Position move = enterMove(board);
		board.setPoint(move, this.getStone());
	}
}


