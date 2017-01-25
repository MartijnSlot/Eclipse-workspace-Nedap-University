package Model;

import java.util.Objects;

public class Position {

	int x;
	int y;

	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Position)) return false;
		if (o == this) return true;
		return ((Position) o).getX() == this.x && ((Position) o).getY() == this.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

}

