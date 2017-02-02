package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import controller.Game;

public class ServerHandler extends Thread {

	private final GoClient client;
	private BufferedReader inputFromServer;
	private BufferedWriter outputToServer;
	private String clientName;
	private Integer dim;
	private Game game;
	private Socket socket;

	public ServerHandler(GoClient client, Socket socket) {
		this.client = client;
		this.socket = socket;

		try {
			inputFromServer = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
			outputToServer = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
		} catch (IOException ioe) {

		}
	}

	public BufferedReader getInputFromServer() {
		return inputFromServer;
	}

	public BufferedWriter getOutputToServer() {
		return outputToServer;
	}

	public String getClientName() {
		return clientName;
	}


	public Integer getDim() {
		return dim;
	}


	@Override
	public void run() {
		String fromServer = null;

		try {
			while(socket.isConnected()) {
				fromServer = inputFromServer.readLine();
				String serverInputMessage[] = fromServer.split(" ");
				if (fromServer.startsWith("WAITING")) {
					System.out.println(fromServer);
				} else if (fromServer.startsWith("READY")) {
					String color1 = serverInputMessage[1];
					String name1 = null;
					String name2 = null;
					if (color1.equals("black"))	{
						name1 = serverInputMessage[2];
						name2 = clientName;
					} else {
						name1 = clientName;
						name2 = serverInputMessage[2];
					}
					dim = Integer.parseInt(serverInputMessage[3]);
					game = new Game(name1, name2, dim);

				} else if (fromServer.startsWith("VALID")) {
					int col = Integer.parseInt(serverInputMessage[2]);
					int row = Integer.parseInt(serverInputMessage[3]);
					game.executeTurn(row, col);
					game.addToGUI(row, col);
					System.out.println(fromServer);
				} else if (fromServer.startsWith("INVALID") && serverInputMessage.length == 1) {
					System.out.println(fromServer + " kicked from the game due to invalid move");
					try {
						client.shutdown();
					} catch (IOException e) {
						System.out.println("client cannot be shutdowned.");
						e.printStackTrace();
					}
				} else if (fromServer.startsWith("PASSED")) {
					game.passMove();
					System.out.println("Other player " + fromServer);
				} else if (fromServer.startsWith("WARNING")) {
					System.out.println(fromServer + "try something else");
				} else if (fromServer.startsWith("TABLEFLIPPED")) {
					game.tableflipMove();
					System.out.println(fromServer);
				} else if (fromServer.startsWith("CHAT")) {
					System.out.println("Server: " + fromServer);
				} else if (fromServer.startsWith("END")) {
					int scoreBlack = Integer.parseInt(serverInputMessage[1]); //score black
					int scoreWhite = Integer.parseInt(serverInputMessage[2]); //score white
					System.out.println("Score black: " + scoreBlack +  "\nScore white: "  + scoreWhite);
				} else {
					System.out.println("WARNING Must...resist...kicking...you. \n Message " + fromServer + " is invalid input.");
				}
			}
		} catch (IOException e1) {
			System.out.println("No input");
		}
	}



	public synchronized void writeToServer(String message) throws IOException {
		outputToServer.write(message);
		outputToServer.newLine();
		outputToServer.flush();

	}

	public void shutdown() throws IOException {
		outputToServer.close();
		inputFromServer.close();
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

	public void go(String string, String string2, String boardSize) throws IOException {
		clientName = string2;
		dim = Integer.parseInt(boardSize);
		writeToServer(string + " " + string2 + " " + boardSize);
	}

	public void goWithoutName(String string, String boardSize) throws IOException {
		dim = Integer.parseInt(boardSize);
		writeToServer(string + " " + boardSize);
	}

	public void move(String move, String stringX, String stringY) throws IOException {
		int col = Integer.parseInt(stringX);
		int row = Integer.parseInt(stringY);
		if (!moveAllowed(col, row)) {
			System.out.print(move + " " + stringX + " " + stringY + " incorrect, try again");
		}
		writeToServer(move + stringX + stringY + "incorrect, try again");

	}

	public void pass(String pass) throws IOException {
		writeToServer(pass);

	}

	public void tableflip(String tableflip) throws IOException {
		writeToServer(tableflip);
	}

	public void chat(String chat) throws IOException {
		writeToServer(chat);
		System.out.println(clientName + ": " + chat);
	}

}
