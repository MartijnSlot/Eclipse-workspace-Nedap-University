package model;

import java.util.HashSet;
import java.util.Set;

import controller.LocalGoGo;

/**
 * Abstract class for keeping a player in a GO game. 
 * 
 * @author Martijn Slot
 * @version 1.0
 */
public class Player {

	public String name;
	public Stone stone;
	public boolean pass;
	public boolean winner;
	
	public Player(String name, Stone stone) {
		this.name = name;
		this.stone = stone;
		this.pass = false;
		this.winner = false;
	}
	
	// -- QUERIES

	public String getName() {
		return name;
	}

	public Stone getStone() {
		return stone;
	}
	
	// -- METHODS


	public void makeMove(Board board, Position pos) {
		board.setPoint(pos, this.getStone());	
		pass = false;
	}
	
	public void passes() {
		this.pass = true;
	}

	public void guiAutoRemove(Board board, Position pos) {
		Set<Position> a = new HashSet<>();
		a.add(new Position(pos.x - 1, pos.y));
		a.add(new Position(pos.x + 1, pos.y));
		a.add(new Position(pos.x, pos.y - 1));
		a.add(new Position(pos.x, pos.y + 1));
		for (Position p : a) {
			if (board.isPoint(p) && !board.isEmptyPoint(p) && board.numberOfLiberties(p) == 0) {	
				for (Position q : board.defendingCluster(p)) {
					LocalGoGo.gogui.removeStone(q.y - 1, q.x - 1);
				}
			}
		}if (!board.isEmptyPoint(pos) && board.numberOfLiberties(pos) == 0) {	
			for (Position r : board.defendingCluster(pos)) {
				LocalGoGo.gogui.removeStone(r.y - 1, r.x - 1);
			}
		}
	}
	
	public void isWinner() {
		this.winner = true;
	}

	
}


