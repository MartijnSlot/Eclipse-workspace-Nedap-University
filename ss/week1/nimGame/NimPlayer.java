package ss.week1.nimGame;

public class NimPlayer {
	
	private String name;
	private int numberTaken;
	
	//constructor
	public NimPlayer(String name) {
		this.name = name;
		this.numberTaken = 0;
	}
	
	//queries
	public String name(){
		return name;
	}
	public int numberTaken(){
		return numberTaken;
	}
	
	//commands: remove 1,2 or three sticks from the specified pile. Pile must not be empty
	public void takeTurn (NimPile pile){
		pile.remove(2);
		numberTaken = 2;
	}

}
