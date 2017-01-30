package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import client.GoClient;
import controller.Game;
import exceptions.*;
import model.Player;
import model.Position;
import model.Stone;

public class SingleGameServer {

	private GoServer server;
	private ServerSocket serverSocket;
	private String clientName;
	private ClientHandler client1;
	private ClientHandler client2;
	private BufferedReader input;
	private BufferedWriter output;
	private Game game;


	public SingleGameServer(ClientHandler a, ClientHandler b, int dim) {
		this.client1 = a;
		this.client2 = b;
		
		String playerNames[] = new String[2];
		playerNames[0] = client1.getClientName();
		playerNames[1] = client2.getClientName();
		Random rand = new Random();
		int  n = rand.nextInt(1);
		Game game = new Game(new Player(playerNames[n], Stone.BLACK), new Player(playerNames[1-n], Stone.WHITE), dim);
		while(!game.hasWinner()){
			client1.handleGame();
		}
	}

	public boolean moveAllowed(int col, int row) {
		if (!game.getBoard().isAllowed(new Position(col, row))) {
			System.out.println("\nField " + col + ", " + row + " is no valid position.");
			return false;
		} else if (game.inKo(new Position(col, row))) {
			System.out.println("\nField " + col + ", " + row + " is in Ko. \n\nMaar wie is die Ko dan?");
			return false;
		} else {
			return true;
		}
	}
	public void executeTurnMove(int col, int row) {
		game.executeTurn(new Position(col, row));

	}
	public void executeTurnPass() {
		game.passMove();

	}

	public void executeTurnTableflip() {
		// TODO Auto-generated method stub

	}

	public void waitForGame() {
		// TODO Auto-generated method stub

	}

	public void annihilatePlayer() {
		// TODO Auto-generated method stub

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

	public void warning() {
		//TODO sent warning to client
		System.out.println("Must... resist... kicking...you. Invalid input.");

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

	public void handleWait() {
		// TODO Auto-generated method stub
		
	}	
}
