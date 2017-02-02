package server;

import java.io.IOException;

import controller.Game;
import server.ClientHandler.ClientStatus;


public class SingleGameServer {

	private ClientHandler[] chs;
	private Game game;
	private int currentClient;

	public SingleGameServer(ClientHandler a, ClientHandler b, int dim) throws IOException {
		this.chs[0] = a;
		this.chs[1] = b;

		String playerNames[] = new String[2];
		playerNames[0] = a.getClientName();
		playerNames[1] = b.getClientName();
		game = new Game(playerNames[0], playerNames[1], dim);

		chs[0].setClientStatus(ClientStatus.INGAME);
		chs[1].setClientStatus(ClientStatus.WAITING);

		String opponent1 = chs[1].getName();
		String color1 = "black";
		chs[0].sendReady(color1, opponent1, dim);

		String opponent2 = chs[0].getName();
		String color2 = "black";
		chs[1].sendReady(color2, opponent2, dim);

	}

	public int getCurrentClient() {
		return currentClient;
	}

	public void setCurrentClient(int a) {
		this.currentClient = a;
	}

	public void executeTurnMove(int col, int row) throws IOException {
		if (game.getBoard().isAllowed(row, col)) {
			game.executeTurn(col, row);
			setCurrentClient(game.currentPlayer);
			chs[(currentClient + 1) % 2].writeToClient("MOVE: ");
			chs[currentClient].setClientStatus(ClientStatus.WAITING);
			chs[(currentClient + 1) % 2].setClientStatus(ClientStatus.INGAME);
		}
	}

	public void executeTurnPass() throws IOException {
		game.passMove();
		setCurrentClient(game.currentPlayer);
		chs[(currentClient + 1) % 2].writeToClient("PASSED");
		chs[currentClient].setClientStatus(ClientStatus.WAITING);
		chs[(currentClient + 1) % 2].setClientStatus(ClientStatus.INGAME);
	}

	public void executeTurnTableflip() throws IOException {
		game.tableflipMove();
		chs[(currentClient + 1) % 2].writeToClient("TABLEFLIPPED");
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

	public void otherPlayerWins() throws IOException {
		chs[(currentClient + 1) % 2].writeToClient("END");
	}

	public synchronized void chatToOtherPlayer(String message) throws IOException {
		chs[(currentClient + 1) % 2].writeToClient(message);
	}

}
