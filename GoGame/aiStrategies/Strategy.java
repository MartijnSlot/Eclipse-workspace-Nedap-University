package aiStrategies;

import Model.Position;

public abstract class Strategy {
	
	/**
	 * checks if pos is in Atari
	 * @param position
	 * @return boolean
	 */
	public boolean inAtari(Position pos) {
		return board.numberOfLiberties(pos) == 1;
	}
	
	

}
