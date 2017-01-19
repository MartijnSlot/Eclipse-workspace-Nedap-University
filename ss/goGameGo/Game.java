package goGameGo;

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
	 * @return board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the index of the player whose turn it is.
	 * @return int
	 */
	public int getCurrent() {
		return currentPlayer;
	}
	
	public void reset() {
		this.board = new Board(board.dim);
	}

	public void play() {
		updateTUI();
		while(true) {//!board.isWinner(Stone.BLACK) || !board.isWinner(Stone.WHITE)) {
			players[currentPlayer].makeMove(board);
			currentPlayer = (currentPlayer + 1) % numberPlayers;
			updateTUI();
		}
	}


	/**
	 * Update the state of the board to the console!
	 */
	public void updateTUI() {
		System.out.println("\nAwesome-o GO board: \n" + board.toString()
		+ "\n");
	}

}
