package goGameGo;

public class GoGo {

	public static void main(String[] args) {
		
		Player player1 = new Player("jan", Stone.BLACK);
		Player player2 = new Player("piet", Stone.WHITE);
		Game game = new Game(player1, player2, 9);
		game.play();

	}

}
