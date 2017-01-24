package goGameGo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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
	Set<Board> history = new HashSet<>();

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
			writeHistory();
			currentPlayer = (currentPlayer + 1) % numberPlayers;
			updateTUI();
		}
	}

	/**
	 * reset board
	 */
	public void reset() {
		this.board = new Board(board.dim);
	}

	/**
	 * asks the client for a move
	 * @return position
	 * @throws IOException
	 */
	private Position askMove() throws IOException {
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
						"Input coordina-te-rror! Please put 2 coordinates (row, column) in the input seperated by any number of non-digits.");
			} else if (!board.isAllowed(new Position(xy[0], xy[1]))) {
				System.out.println("Field " + xy[0] + ", " + xy[1] + " is no valid position.");
			} else if(inKo(new Position(xy[0], xy[1]), players[currentPlayer].getStone())) {
				System.out.println("Field " + xy[0] + ", " + xy[1] + " is in Ko. \n\n\nMaar wie is die Ko dan?");
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
		Board copy = new Board(this.board.dim);
		copy.points = new HashMap<Position, Point>(this.board.points);
		history.add(copy);
	}

	/**
	 * checks if the placement of a stone is in accordance with the <i>ko-rule</i>
	 * @param pos
	 * @return boolean
	 */
	public boolean inKo(Position pos, Stone s) {
		this.players[currentPlayer].makeMove(this.board, pos);
		if (history.contains(this))	{
			board.removePoint(pos);
			return true;
		}
		board.removePoint(pos);
		return false;

	}

	/**
	 * Update the state of the board to the console!
	 */
	public void updateTUI() {
		System.out.println("\nAwesome-o GO board: \n\n" + board.toString() + "\n");
	}

}
