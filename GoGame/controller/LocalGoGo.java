package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.Player;
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
			game.executeTurn(game.askMove());
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

		game = new Game(new Player("jan", Stone.BLACK), new Player("piet", Stone.WHITE), dim);
		gogui = new GoGUIIntegrator(false, true, dim);
		gogui.startGUI();
		gogui.setBoardSize(dim);

	}

}
