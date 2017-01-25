package server;

import java.net.*;

import controller.Game;
import model.Player;
import model.Position;
import model.Stone;

import java.io.*;

public class GoServer {

	private MiniServer clients[] = new MiniServer[100];
	private int numClients = 0;
	private ServerSocket serverSocket;
	private Game game;


	public GoServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server riding the waves of port " + port + ". Details: " + serverSocket);
		} catch (IOException ioe) {
			System.out.println("Wash out: server has sunk in port " + port + ": " + ioe.getMessage());
		}

	}

	private void go() throws IOException {
		boolean listeningSocket = true;
		while (listeningSocket) {
			System.out.println("Waiting for a player.");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Go player!");
			MiniServer mini = new MiniServer(clientSocket);
			mini.start();
		}
		serverSocket.close();
	}

	private int findClient(int ID) {
		return ID;
	}

	public void sendToAllPlayers(String string) {
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
	public void placeMove(int col, int row) {
		game.play(new Position(col, row));

	}
	public void pass() {
		game.passMove();

	}

	public void tableflip() {
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

	public static void main(String args[]) {
		GoServer server = new GoServer(Integer.parseInt(args[0]));
		try {
			server.go();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}






}