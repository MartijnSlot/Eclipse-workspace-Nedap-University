package ss.week1.nimGame;

public class NimGame {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NimPlayer player1 = new NimPlayer("Willem");
		NimPile pile1 = new NimPile(5);
		
		NimPile pile2 = new NimPile(10);
		
		player1.takeTurn(pile1);
		player1.takeTurn(pile2);
		
		System.out.println("Aantal stokjes van pile1 = " + pile1.sticks());
		System.out.println("Aantal stokjes van pile2 = " + pile2.sticks());
	}

}
