package ss.week1.nimGame;

public class NimPile {
	
	private int sticksLeft; //sticks left in the pile
	
	//constructor
	public NimPile(int sticks){
		this.sticksLeft = sticks;		
	}
	
	//query
	public int sticks(){
		return sticksLeft;
	}
	
	//commands
	public void remove(int number) {
		sticksLeft = sticksLeft - number;
		
	}

}
