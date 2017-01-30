package client;

import java.io.*;
import java.net.*;

import controller.Game;
import model.Position;

public class GoClient {


	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private String name;
	private Game game;

	public GoClient(String serverAdress, int serverPort) {
		this.name = name;
		try {
			socket = new Socket(serverAdress, serverPort);
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public static void main(String args[]) {
		GoClient client = null;
		if (args.length != 3)
			System.out.println("Usage: java ChatClient host port name");
		else
			client = new GoClient(args[0], Integer.parseInt(args[1]));
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
}

