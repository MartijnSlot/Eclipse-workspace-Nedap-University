package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import model.Board;
import model.Player;
import model.Position;
import model.Stone;

/**
 * Class for maintaining a GO game.
 * 
 * @author Martijn Slot
 * @version 1.0
 */
public class Game {

	public int numberPlayers = 2;
	private Board board;
	public Player[] players;
	public int currentPlayer;
	public Set<String> history = new HashSet<>();

	public Game(Player s0, Player s1, int dim) {
		board = new Board(dim);
		players = new Player[numberPlayers];
		players[0] = s0;
		players[1] = s1;
		currentPlayer = 0;
	}
	
	// -- QUERIES

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

	
	// -- METHODS
	
	/**
	 * As long as there is no winner, players keep playing turns
	 * @param a
	 */
	public void play(Position a) {
		updateTUI();
		while (!hasWinner()) {
			players[currentPlayer].makeMove(board, a);
			writeHistory();
			currentPlayer = (currentPlayer + 1) % numberPlayers;
			updateTUI();
		}
	}

	/**
	 * As long as there is no winner, a players switches turns with a pass
	 */
	public void passMove() {
		while (!hasWinner()) {
			if (players[(currentPlayer + 1) % numberPlayers)].pass) //TODO
			players[currentPlayer].passes();
			currentPlayer = (currentPlayer + 1) % numberPlayers;
		}

	}

	/**
	 * reset board
	 */
	public void reset() {
		this.board = new Board(board.dim);
	}

	/**
	 * asks the client for a move, 
	 * ONLY FOR LOCAL PLAY
	 * @return position
	 * @throws IOException
	 */
	Position askMove() throws IOException {
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
	public Position checkPos(String prompt) throws IOException {
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
			} else if (!board.isAllowed(new Position(xy[0], xy[1]))) {
				System.out.println("\nField " + xy[0] + ", " + xy[1] + " is no valid position.");
			} else if (inKo(new Position(xy[0], xy[1]))) {
				System.out.println("\nField " + xy[0] + ", " + xy[1] + " is in Ko. \n\nMaar wie is die Ko dan?");
			} else {
				legalpos = true;
				return new Position(xy[0], xy[1]);
			}
		}
		return null;
	}

	/**
	 * Deep copy of this board + write to history.
	 * @return board
	 */
	public void writeHistory() {
		history.add(this.board.toSimpleString());
	}

	/**
	 * checks if the placement of a stone is in accordance with the <i>ko-rule</i>
	 * @param pos
	 * @return boolean
	 */
	public boolean inKo(Position pos) {
		this.players[0].makeMove(this.board, pos);
		for(String b : history) {
			if (this.board.toSimpleString().equals(b)) {
				board.removePoint(pos);
				return true;
			}
		}
		board.removePoint(pos);
		this.players[1].makeMove(this.board, pos);
		for(String b : history) {
			if (this.board.toSimpleString().equals(b)) {
				board.removePoint(pos);
				return true;
			}
		}
		board.removePoint(pos);
		return false;

	}
	
	public boolean hasWinner() {
		return (players[0].isWinner() | players[1].isWinner());
	}

	/**
	 * Update the state of the board to the console!
	 */
	public void updateTUI() {
		System.out.println("\nAwesome-o GO board: \n\n" + board.toString() + "\n");
	}

}
