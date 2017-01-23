package goGameGo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO make TGUI for all dims. Coordinate error occurs with double digits.
 */

/**
 * Represents a board in a Go game.
 *
 * @author Martijn Slot
 * @version 1.0
 */
public class Board {

	int dim;
	private Map<Position, Point> points = new HashMap<>();
	private Set<Board> history = new HashSet<>();

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
	 * Returns a deep copy of this board and puts it is a set of board called history.
	 * @return board
	 */
	public Set<Board> writeToHistory() {
		Board copy = new Board(this.dim);
		copy.points = new HashMap<Position, Point>(this.points);
		history.add(copy);
		return history;
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
		points.put(pos, new Point(s));
	}

	/**
	 * Returns all empty positions surrounding argument position.
	 * @param position
	 * @return set
	 */
	public Set<Position> freePositions(Map<Position, Point> nodes) {
		Set<Position> freePositions = new HashSet<>();

		for(Position freepos : nodes.keySet()){
			for (int i = freepos.x - 1; i <= freepos.x + 1; i++) {
				while(isPoint(new Position(i, freepos.y))){
					if (getPoint(new Position(i, freepos.y)).getStone() == Stone.EMPTY) {
						freePositions.add(new Position(i, freepos.y));
					}
				}
			}
			for (int i = freepos.y - 1; i <= freepos.y + 1; i++) {
				while(isPoint(new Position(freepos.x, i))){
					if (getPoint(new Position(freepos.x, i)).getStone() == Stone.EMPTY) {
						freePositions.add(new Position(freepos.x, i));
					}
				}
			}
		}
		return freePositions;
	} 

	/**
	 * checks if position has Liberties
	 * @param position
	 * @return boolean
	 */
	public boolean hasLiberties(Map<Position, Point> nodes) {
		return freePositions(nodes).size() == 0;
	}

	/**
	 * Returns all attacking stone positions surrounding argument position.
	 * @param position
	 * @return set
	 */
	public Map<Position, Point> attackPositions(Position pos) {
		Stone attack = getPoint(pos).getStone().other();
		Map<Position, Point> attackPositions = new HashMap<>();

		for (int i = pos.x - 1; i <= pos.x + 1; i++) {
			if (getPoint(new Position(i, pos.y)).getStone() == attack) {
				attackPositions.put(new Position(i, pos.y), getPoint(pos));
			}
		}
		for (int i = pos.y - 1; i <= pos.y + 1; i++) {
			if (getPoint(new Position(pos.x, i)).getStone() == attack) {
				attackPositions.put(new Position(pos.x, i), getPoint(pos));
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
		return !attackPositions(pos).isEmpty();
	}

	/**
	 * Returns a cluster of defending stone positions in which position pos is situated.
	 * @param position
	 * @return set
	 */
	public Map<Position, Point> defendingCluster(Position pos) {
		Stone defend = getPoint(pos).getStone();

		if (!isEmptyPoint(pos)){
			Map<Position, Point> defendPositions = new HashMap<>();
			defendPositions.put(pos, getPoint(pos));
			int temp = 0;

			while (defendPositions.size() != temp) {
				temp = defendPositions.size();
				for(Position q : defendPositions.keySet()) {
					for (int i = q.x - 1; i <= q.x + 1; i++) {
						Position a = new Position(i, q.y);
						while(isPoint(a)) {
							if (getPoint(a).getStone() == defend) {
								defendPositions.put(a, getPoint(q));
							}
						}
					}
					for (int i = q.y - 1; i <= dim; i++) {
						Position a = new Position(q.x, i);
						while(isPoint(a)) {
							if (getPoint(a).getStone() == defend) {
								defendPositions.put(a, getPoint(q));
							}
						}
					}
				}
			}
			return defendPositions;
		}
		return new HashMap<>();
	}

	/**
	 * checks if position has defending stones
	 * @param position
	 * @return boolean
	 */
	public boolean hasDefenders(Position pos) {
		return defendingCluster(pos).size() >= 1;
	}

	/**
	 * Returns all liberty positions surrounding argument position (even if arguments exists in cluster of samecolor stones).
	 * @param position
	 * @return set
	 */
	public Set<Position> libertyPositions(Position pos) {
		return freePositions(defendingCluster(pos));
	}

	public int numberOfLiberties(Position pos) {
		return libertyPositions(pos).size();
	}

	/**
	 * replace the defending cluster stones (black, white) with EMPTY
	 * 
	 * @param pos
	 */
	public void autoRemove(Position pos) {
		Set<Position> neighbours = new HashSet<>();
		for (int i = pos.x - 1; i <= dim; i++) {
			if (i < 1) i = 1;
			if (i > dim) i = dim;
			neighbours.add(new Position(i, pos.y));
		}		
		for (int i = pos.y - 1; i <= dim; i++) {
			if (i < 1) i = 1;
			if (i > dim) i = dim;
			neighbours.add(new Position(pos.x, i));
		}
		for (Position p : neighbours) {
			if (numberOfLiberties(p) == 0 && !isEmptyPoint(p)) {
				setPoint(p, Stone.EMPTY);
			}
		}
	}

	/**
	 * checks if the placement of a stone is in accordance with the <i>ko-rule</i>
	 * @param pos
	 * @return boolean
	 */
	public boolean inKo(Position pos, Stone s) {


		return false;
	}

	/**
	 * checks if the placement of a stone on pos is legal
	 * stone is placed outside of the dimensions of the board
	 * stone is placed on an occupied spot (black, white)
	 * stone is placed while recreating any previous board position (ko rule)
	 * TODO more conditions for illegal moves???
	 * @param pos, s
	 * @return boolean
	 */
	public boolean isAllowed(Position pos, Stone s) {

		if (!isPoint(pos)) {
			System.out.println("Move not allowed: position does not exist on this playing board.");
			return false;
		}
		if (!isEmptyPoint(pos)) {
			System.out.println("Move not allowed: position occupied.");
			return false;
		}
		if (inKo(pos, s)) {
			System.out.println("Move not allowed: ko rule.");
			return false;
		}

		return true;
	}


	public boolean isWinner(Stone s) {
		return false;
	}


	public String toString() {
		String s = "  ";

		for (int i = 1; i <= dim; i++) {
			s = s + i + " ";
		}
		s = s + "\n";
		for (int i = 1; i <= dim; i++) {
			String row = "" + i;
			for (int j = 1; j <= dim; j++) {
				row = row + " " + getPoint(new Position(i, j)).getStone().toString();
			}
			s = s + row;
			if (i < dim) {
				s = s + "\n";
			}
		}
		return s;
	}



}
