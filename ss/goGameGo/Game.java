package goGameGo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for maintaining a GO game.
 * 
 * @author Martijn Slot
 * @version 1.0
 */
public class Game {

	public int numberPlayers = 2;
	private Board board;
	private Player[] players;
	private int currentPlayer;

	public Game(Player s0, Player s1, int dim) {
		board = new Board(dim);
		players = new Player[numberPlayers];
		players[0] = s0;
		players[1] = s1;
		currentPlayer = 0;
	}
	// -- Queries ----------------------------------------------------

	/**
	 * Returns the board.
	 * 
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the index of the player whose turn it is.
	 * 
	 * @return int
	 */
	public int getCurrent() {
		return currentPlayer;
	}

	public void play() throws IOException {
		updateTUI();
		while (!board.isWinner(Stone.BLACK) || !board.isWinner(Stone.WHITE)) {
			players[currentPlayer].makeMove(board, askMove());
			currentPlayer = (currentPlayer + 1) % numberPlayers;
			updateTUI();
		}
	}

	public void reset() {
		this.board = new Board(board.dim);
	}

	private Position askMove() throws IOException {
		boolean legalchoice = false;
		Position choice = null;

		while (!legalchoice) {
			legalchoice = true;
			String prompt = "Dear " + players[currentPlayer].getName() + " ("
					+ players[currentPlayer].getStone().toString() + ")"
					+ ", please enter positions row, column (row and column integers must exist on the board) " + board.dim
					+ ") ";
			choice = readPos(prompt);
			legalchoice = true;
		}

		return choice;
	}

	private Position readPos(String prompt) throws IOException {
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
						"Input coordina-te-rror! Please put 2 coordinates (x,y) in the input seperated by any number of non-digits.");
			} else if (!board.isAllowed(new Position(xy[0], xy[1]), players[currentPlayer].getStone())) {
				System.out.println("Field " + xy[0] + ", " + xy[1] + " is no valid position.");
			} else {
				legalpos = true;
				return new Position(xy[0], xy[1]);
			}
		}
		return null;
	}

	/**
	 * Update the state of the board to the console!
	 */
	public void updateTUI() {
		System.out.println("\nAwesome-o GO board: \n\n" + board.toString() + "\n");
	}

}
