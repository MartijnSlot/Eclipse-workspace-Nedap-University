package goGameGo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a board in a Go game.
 *
 * @author Martijn Slot
 * @version 1.0
 */
public class Board {

	int dim;
	private Map<Position, Point> points;

	/**
	 * constructor of board of size dim * dim, containing only EMPTY fields.
	 * @param dim
	 */

	public Board(int dim) {
		this.dim = dim;
		for (int x = 1; x <= dim; x++) {
			for (int y = 1; y <= dim; y++) {
				this.points.put(new Position(x,y), new Point(Stone.EMPTY));
			}
		}
	}

	/**
	 * Returns a deep copy of this board.
	 * @return board
	 */
	public Board deepCopy() {
		Board copy = new Board(this.dim);
		copy.points = new HashMap<Position, Point>(this.points);
		return copy;
	}

	/**
	 * Returns true when the point at position exists.
	 * @param position
	 * @return boolean
	 */
	public boolean isPoint(Position pos) {
		return (points.containsKey(pos));
	}

	/**
	 * Returns the point at position, asserting this point exists.
	 * @param position
	 * @return point
	 */
	public Point getPoint(Position pos) {
		assert(this.isPoint(pos));
		return points.get(pos);
	}

	/**
	 * Returns true when the point at position is empty.
	 * @param position
	 * @return boolean
	 */
	public boolean isEmptyPoint(Position pos) {
		assert(this.isPoint(pos));
		return (this.getPoint(pos).getStone() == Stone.EMPTY);
	}

	/**
	 * Sets a point at position, asserting this point exists.
	 * @param position, s
	 */
	public void setPoint(Position pos, Stone s) {
		assert(this.isPoint(pos));
		points.put(pos, new Point(s));
	}

	/**
	 * Returns all empty positions surrounding argument position.
	 * @param position
	 * @return set
	 */
	public Set<Position> freePositions(Position pos) {
		Set<Position> freePositions = new HashSet<Position>();
		
		assert(this.isPoint(pos));
		
		for (int i = pos.x - 1; i <= pos.x + 1; pos.x++) {
			if (getPoint(new Position(i, pos.y)).getStone() == Stone.EMPTY) {
				freePositions.add(new Position(i,pos.y));
			}
		}
		for (int i = pos.y - 1; i <= pos.y + 1; pos.y++) {
			if (getPoint(new Position(pos.x, i)).getStone() == Stone.EMPTY) {
				freePositions.add(new Position(pos.x, i));
			}
		}
		return freePositions;
	} 

	/**
	 * checks if position has Liberties
	 * @param position
	 * @return boolean
	 */
	public boolean hasLiberties(Position pos) {
		assert(this.isPoint(pos));
		return freePositions(pos) != null;
	}

	/**
	 * Returns all attacking stone positions surrounding argument position.
	 * @param position
	 * @return set
	 */
	public Set<Position> attackPositions(Position pos) {
		Stone attack = getPoint(pos).getStone().other();
		Set<Position> attackPositions = new HashSet<Position>();

		assert(this.isPoint(pos));
		
		for (int i = pos.x - 1; i <= pos.x + 1; pos.x++) {
			if (getPoint(new Position(i, pos.y)).getStone() == attack) {
				attackPositions.add(new Position(i, pos.y));
			}
		}
		for (int i = pos.y - 1; i <= pos.y + 1; pos.y++) {
			if (getPoint(new Position(pos.x, i)).getStone() == attack) {
				attackPositions.add(new Position(pos.x, i));
			}
		}
		return attackPositions;
	}

	/**
	 * checks if position has attacking stones
	 * @param position
	 * @return boolean
	 */
	public boolean hasAttackers(Position pos) {
		return attackPositions(pos) != null;
	}

	/**
	 * Returns a cluster of defending stone positions in which position pos is situated.
	 * @param position
	 * @return set
	 */
	public Set<Position> defendPositions(Position pos) {
		Stone defend = getPoint(pos).getStone().other();
		Set<Position> defendPositions = new HashSet<Position>();
		defendPositions.add(pos);
		int temp = 0;
		
		while (defendPositions.size() != temp) {
			temp = defendPositions.size();
			for(Position p : defendPositions) {
				for (int i = p.x - 1; i <= p.x + 1; p.x++) {
					if (getPoint(new Position(i, p.y)).getStone() == defend) {
						defendPositions.add(new Position(i, p.y));
					}
				}
				for (int i = pos.y - 1; i <= pos.y + 1; pos.y++) {
					if (getPoint(new Position(pos.x, i)).getStone() == defend) {
						defendPositions.add(new Position(pos.x, i));
					}

				}
			}
		}
		return defendPositions;
	}

	/**
	 * checks if position has defending stones
	 * @param position
	 * @return boolean
	 */
	public boolean hasDefenders(Position pos) {
		return defendPositions(pos) != null;
	}

	/**
	 * Returns all liberty positions surrounding argument position (even if arguments exists in cluster of samecolor stones).
	 * @param position
	 * @return set
	 */
	public Set<Position> checkLibertyPositions(Position pos) {
		
		Set<Position> defendPositions = new HashSet<Position>();
		Set<Position> libertyPositions = new HashSet<Position>();

		for (Position p : defendPositions(pos)) defendPositions.add(p);


		for (Position p : defendPositions) {
			for (Position libertyPos : freePositions(p)) libertyPositions.add(libertyPos);
		}

		return libertyPositions;
	}

	public int numberOfLiberties(Position pos) {
		return checkLibertyPositions(pos).size();
	}

	/**
	 * checks if 
	 * @param position
	 * @return boolean
	 */
	public boolean inAtari(Position pos) {
		return numberOfLiberties(pos) == 1;
	}


}
