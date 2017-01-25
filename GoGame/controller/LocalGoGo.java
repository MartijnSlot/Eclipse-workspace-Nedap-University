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

	public static void main(String[] args) throws IOException {
		initialize();
		game.play(game.askMove()); // TODO creates infinite loop
	}

	public static void initialize() throws IOException {
		boolean legalInput = false;
		int dim = 0;
		String name1;
		String name2;
		Player player1 = null;
		Player player2 = null;

		while(!legalInput) {
			legalInput = true;
			String namequestion1 = "Player 1, please enter your name (lowercase letters & length <= 20) :";
			System.out.println(namequestion1);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			name1 = input.readLine().replaceAll("\\W+", "").toLowerCase();
			if (name1.length() > 20) {
				legalInput = false;
				System.out.println("Illegal input, name too long (maximum 20 characters): ");
			}
			player1 = new Player(name1, Stone.BLACK);
			System.out.println("Your name is: " + name1);
		}
		legalInput = false;


		while(!legalInput) {
			legalInput = true;
			String namequestion2 = "Player 2, please enter your name (lowercase letters & length <= 20) :";
			System.out.println(namequestion2);
			BufferedReader input2 = new BufferedReader(new InputStreamReader(System.in));
			name2 = input2.readLine().replaceAll("\\W+", "").toLowerCase();
			if (name2.length() > 20) {
				legalInput = false;
				System.out.println("Illegal input, name too long (maximum 20 characters): ");
			}
			player2 = new Player(name2, Stone.WHITE);
			System.out.println("Your name is: " + name2);
		}
		legalInput = false;


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
		
		game = new Game(player1, player2, dim);
		gogui = new GoGUIIntegrator(false, true, dim);
		gogui.startGUI();
		gogui.setBoardSize(dim);

	}

}
