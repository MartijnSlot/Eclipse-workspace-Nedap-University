package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Player;
import model.Position;
import model.Stone;
import viewer.GoGUIIntegrator;

public class LocalGoGo {

	private static Game game;
	public static GoGUIIntegrator gogui;

	//TODO remove dependency from player to GUI and add dependency from game to GUI
	//TODO remove class Point and put it straight to Stone

	public static void main(String[] args) throws IOException {
		initialize();
		while(!game.hasWinner()){
			game.executeTurn(askMove().getY(), askMove().getX());
		}
	}

	public static void initialize() throws IOException {
		boolean legalInput = false;
		int dim = 0;

		while(!legalInput){
			legalInput = true;
			String dimquestion = "Enter integer board dimension dim (board = dim * dim) :";
			System.out.println(dimquestion);
			BufferedReader dimInput = new BufferedReader(new InputStreamReader(System.in));
			String stringDim = dimInput.readLine().replaceAll("\\W+", "");

			try {
				dim = Integer.parseInt(stringDim);
			} catch(NumberFormatException e) {
				legalInput = false;
				System.out.println("Illegal input, please enter a single digit: ");

			}
			System.out.println("The dimensions of the board: " + dim + "x" + dim);
		}

		game = new Game("jan", "piet", dim);
		gogui = new GoGUIIntegrator(false, true, dim);
		gogui.startGUI();
		gogui.setBoardSize(dim);

	}
	
	/**
	 * asks the client for a move, 
	 * ONLY FOR LOCAL PLAY
	 * @return 
	 * @return position
	 * @throws IOException
	 */
	static Position askMove() throws IOException {
		boolean legalchoice = false;
		Position choice = null;

		while (!legalchoice) {
			legalchoice = true;
			String prompt = "Dear " + players[currentPlayer].getName() + " ("
					+ players[currentPlayer].getStone().toString() + ")"
					+ ", please enter positions row, column (row and column integers must exist on the board) " + board.dim
					+ ") ";
			choice = checkPos(prompt);
			legalchoice = true;
		}

		return choice;
	}
	
	

	/**
	 * checks the position given by the client for legality
	 * ONLY FOR LOCAL PLAY
	 * @param prompt
	 * @return Position
	 * @throws IOException
	 */
	public static Position checkPos(String prompt) throws IOException {
		boolean legalpos = false;
		while (!legalpos) {
			System.out.print(prompt);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String inputLine = input.readLine().replaceAll("\\D+", " ").trim();
			String[] coordinates = inputLine.split(" ");
			int[] xy = new int[coordinates.length];
			try {
				for (int i = 0; i < coordinates.length; i++) {
					xy[i] = Integer.parseInt(coordinates[i]);
				} 
			}catch(NumberFormatException e) {
				System.out.println("Empty string input !allowed");
			}
			if (xy.length != 2) {
				System.out.println(
						"\nInput coordina-te-rror! Please put 2 coordinates (row, column) in the input seperated by any number of non-digits.");
			} else if (!board.isAllowed(xy[0], xy[1])) {
				System.out.println("\nField " + xy[0] + ", " + xy[1] + " is no valid position.");
			} else if (inKo(xy[0], xy[1])) {
				System.out.println("\nField " + xy[0] + ", " + xy[1] + " is in Ko. \n\nMaar wie is die Ko dan?");
			} else {
				legalpos = true;
				return new Position(xy[0], xy[1]);
			}
		}
		return null;
	}

}
