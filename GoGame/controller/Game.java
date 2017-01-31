package controller;

import java.util.HashSet;
import java.util.Set;
import model.Board;
import model.Player;
import model.Position;
import model.Stone;
import viewer.GoGUIIntegrator;

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
	boolean draw;
	public static GoGUIIntegrator gogui;

	public Game(String name1, String name2, int dim) {
		board = new Board(dim);
		players = new Player[numberPlayers];
		players[0] = new Player("name1", Stone.BLACK);
		players[1] = new Player("name1", Stone.WHITE);
		currentPlayer = 0;
		draw = false;
		gogui = new GoGUIIntegrator(false, true, dim);
		gogui.startGUI();
		gogui.setBoardSize(dim);
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
	public void executeTurn(int row, int col) {
		updateTUI();
		players[currentPlayer].makeMove(board, new Position(row, col));
		addToGUI(row, col);
		autoRemove(row, col);
		writeHistory();
		currentPlayer = (currentPlayer + 1) % numberPlayers;
		updateTUI();
	}

	/**
	 * Pass mechanism
	 * As long as there is no winner, a players switches turns with a pass
	 */
	public void passMove() {
		if (!players[(currentPlayer + 1) % numberPlayers].pass) {
			players[currentPlayer].passes();
			currentPlayer = (currentPlayer + 1) % numberPlayers;
		}
		if (players[(currentPlayer + 1) % numberPlayers].pass && players[currentPlayer].pass) {
			determineWinner();
		}

	}

	private void determineWinner() {
		if (board.countScore()[0] > board.countScore()[1]) {
			for (int i = 1; i <= currentPlayer; i++) {
				if (players[i].getStone() == Stone.BLACK) players[i].isWinner();
			}
		}
		if (board.countScore()[1] > board.countScore()[0]) {
			for (int i = 1; i <= currentPlayer; i++) {
				if (players[i].getStone() == Stone.WHITE) players[i].isWinner();
			}
		}
		if (board.countScore()[1] == board.countScore()[0]) {
			draw = true;
		}
	}

	/**
	 * tableflip mechanism
	 * determine winner
	 */
	public void tableflipMove() {
		players[currentPlayer].winner =  false;
		players[(currentPlayer + 1) % numberPlayers].winner = true;
	}

	/**
	 * reset board
	 */
	public void reset() {
		this.board = new Board(board.dim);
	}

	/**
	 * replace the defending cluster stones (black, white) with EMPTY
	 * 
	 * @param pos
	 */
	public void autoRemove(int row, int col) {
		Set<Position> a = new HashSet<>();
		a.add(new Position(col - 1, row));
		a.add(new Position(col + 1, row));
		a.add(new Position(col, row - 1));
		a.add(new Position(col, row + 1));
		for (Position p : a) {
			if (board.isPoint(p) && !board.isEmptyPoint(p) && board.numberOfLiberties(p) == 0) {	
				for (Position q : board.defendingCluster(p)) {
					board.setPoint(q, Stone.EMPTY);
					removeFromGUI(col, row);
				}
			}
		}
		if (!board.isEmptyPoint(new Position(col, row)) && board.numberOfLiberties(new Position(col, row)) == 0) {	
			for (Position r : board.defendingCluster(new Position(col, row))) {
				board.setPoint(r, Stone.EMPTY);
				removeFromGUI(col, row);
			}
		}
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
	public boolean inKo(int row, int  col) {
		boolean inKo = false;
		this.players[currentPlayer].makeMove(this.board, new Position(row, col));
		for(String b : history) {
			if (this.board.toSimpleString().equals(b)) {
				board.removePoint(new Position(row, col));
				inKo = true;
			} 
		}
		board.removePoint(new Position(row, col));
		return inKo;
	}

	public boolean hasWinner() {
		return (players[0].winner | players[1].winner);
	}

	public Player directWinner() {
		players[currentPlayer].winner = true;
		return players[currentPlayer];

	}


	public void addToGUI(int row, int col) {
		boolean white = false;
		if (this.players[currentPlayer].getStone() == Stone.WHITE) {
			white = true ;
		} else {
			white = false;
		}
		gogui.addStone(row - 1, col - 1, white);
	}

	public void removeFromGUI(int row, int col) {
		gogui.removeStone(row - 1, col - 1);
	}

	/**
	 * Update the state of the board to the console!
	 */
	public void updateTUI() {
		System.out.println("\nAwesome-o GO board: \n\n" + board.toString() + "\n");
	}



}
