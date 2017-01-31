package server;

import java.util.Random;
import controller.Game;


public class SingleGameServer {

	private ClientHandler ch1;
	private ClientHandler ch2;
	private Game game;


	public SingleGameServer(ClientHandler a, ClientHandler b, int dim) {
		this.ch1 = a;
		this.ch2 = b;
		
		String playerNames[] = new String[2];
		playerNames[0] = ch1.getClientName();
		playerNames[1] = ch2.getClientName();
		Random rand = new Random();
		int  n = rand.nextInt(1);
		Game game = new Game(playerNames[n], playerNames[1-n], dim);
		while(!game.hasWinner()){
			ch1.handleGame();
		}
	}

	public boolean moveAllowed(int col, int row) {
		if (!game.getBoard().isAllowed(col, row)) {
			System.out.println("\nField " + col + ", " + row + " is no valid position.");
			return false;
		} else if (game.inKo(col, row)) {
			System.out.println("\nField " + col + ", " + row + " is in Ko. \n\nMaar wie is die Ko dan?");
			return false;
		} else {
			return true;
		}
	}
	public void executeTurnMove(int col, int row) {
		game.executeTurn(col, row);

	}
	public void executeTurnPass() {
		game.passMove();

	}

	public void executeTurnTableflip() {
		game.tableflipMove();

	}


	public boolean checkName(String name) {
		if (name.length() > 20 | name.matches(".*\\W+.*")) {
			System.out.println("Illegal input " + name +
					", name requirements: \n- name < 20 characters \n- name may only consist out of digits and letters");
			return false;
		}
		System.out.println("Your name is: " + name);
		return true;
	}

	public boolean checkDim(String input){
		boolean parsable = true;
		try{
			Integer.parseInt(input);
		}catch(NumberFormatException e){
			parsable = false;
		}
		return parsable;
	}

	public void directWinner() {
		game.players[game.currentPlayer].winner = true;
	}
	
}
