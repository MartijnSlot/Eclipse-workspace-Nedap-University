package goGameGo;

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


	public void makeMove(Board board, Position pos) {
		boolean white = false;
		board.setPoint(pos, this.getStone());
		if (this.getStone() == Stone.WHITE) {
			white = true ;
		} else {
			white = false;
		}
		board.autoRemove(pos);
		GoGo.gogui.addStone(pos.y - 1, pos.x - 1, white);
	}
}


