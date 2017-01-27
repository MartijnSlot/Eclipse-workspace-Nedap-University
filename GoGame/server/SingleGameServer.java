package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import client.GoClient;
import controller.Game;
import exceptions.*;
import model.Position;

public class SingleGameServer extends Thread {

	private GoServer server;
	private ServerSocket serverSocket;
	private String clientName;
	private ClientHandler client1;
	private ClientHandler client2;
	private BufferedReader input;
	private BufferedWriter output;
	private Game game;


	public SingleGameServer(ClientHandler a, ClientHandler b) {
		super("SingleGameServer");
		this.client1 = a;
		this.client2 = b;
	}

	public void run() {
		String inputMessage[] = null;
		String message;
		boolean legalInput = false;
		String chatMessage = null;

		
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

	public boolean isParsable(String input){
		boolean parsable = true;
		try{
			Integer.parseInt(input);
		}catch(NumberFormatException e){
			parsable = false;
		}
		return parsable;
	}	
}
