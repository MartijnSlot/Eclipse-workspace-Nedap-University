package goGameGo;

/**
 * Class for maintaining a GO game.
 * 
 * @author Martijn Slot
 * @version 1.0
 */
public class Game {
	
	private Board board;
	private Player player1;
	private Player player2;
	private Stone current;
	
	
    // -- Queries ----------------------------------------------------

    /**
     * Returns the board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the mark of the player whose turn it is.
     */
    public Stone getCurrent() {
        return current;
    }

}
