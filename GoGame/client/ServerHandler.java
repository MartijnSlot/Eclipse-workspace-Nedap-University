package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import controller.Game;

/**
 * Class for creating a Server.
 * handles all communication between client and ClientHandler
 * 
 * @author Martijn Slot
 * @version 1.0
 */
public class ServerHandler extends Thread {

	private final GoClient client;
	private BufferedReader inputFromServer;
	private BufferedWriter outputToServer;
	private String clientName;
	private Integer dim;
	private Game game;
	private Socket socket;

	/**
	 * threaded clienthandler constructor
	 * @param socket
	 * @param server
	 */
	public ServerHandler(GoClient client, Socket socket) {
		this.client = client;
		this.socket = socket;

		try {
			inputFromServer = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
			outputToServer = new BufferedWriter(new OutputStreamWriter(client.getSocket().getOutputStream()));
		} catch (IOException ioe) {

		}
	}

	@Override
	public void run() {
		try {
			while(socket.isConnected()) {
				String fromServer = inputFromServer.readLine();
				handleGame(fromServer);
			}
		} catch (IOException e1) {
			System.out.println("No input");
		}
	}
	
	/**
	 * getter for the bufferedReader input from the server
	 * @return BufferedReader
	 */
	public BufferedReader getInputFromServer() {
		return inputFromServer;
	}

	/**
	 * getter for the bufferedWriter output to the server
	 * @return BufferedWriter
	 */
	public BufferedWriter getOutputToServer() {
		return outputToServer;
	}

	/**
	 * getter for the clientName
	 * @return String
	 */
	public String getClientName() {
		return clientName;
	}


	/**
	 * getter for the dimension
	 * @return int
	 */
	public int getDim() {
		return dim;
	}

	/**
	 * Handles all incoming messages from the clientHandler
	 * @param fromServer
	 * @throws IOException
	 */
	public synchronized void handleGame(String fromServer) throws IOException {
		
		while(fromServer != null) {
			String serverInputMessage[] = fromServer.split(" ");
			if (fromServer.startsWith("WAITING")) {
				System.out.println(fromServer);
				break;
			} else if (fromServer.startsWith("READY")) {
				String color1 = serverInputMessage[1];
//				String name1 = null; //Input necessary for the new game, see below
//				String name2 = null;
				if (color1.equals("black"))	{
//					name1 = serverInputMessage[2];
//					name2 = clientName;
				} else {
//					name1 = clientName;
//					name2 = serverInputMessage[2];
				}
				dim = Integer.parseInt(serverInputMessage[3]);
//				game = new Game(name1, name2, dim); //ONLY 1 GUI can be started at the same time. FIX.
				break;

			} else if (fromServer.startsWith("VALID")) {
				int col = Integer.parseInt(serverInputMessage[2]);
				int row = Integer.parseInt(serverInputMessage[3]);
				game.executeTurn(row, col);
				game.addToGUI(row, col);
				System.out.println(fromServer);
				break;
			} else if (fromServer.startsWith("INVALID") && serverInputMessage.length == 1) {
				System.out.println(fromServer + " kicked from the game due to invalid move");
				try {
					client.shutdown();
				} catch (IOException e) {
					System.out.println("client cannot be shutdowned.");
					e.printStackTrace();
				}
				break;
			} else if (fromServer.startsWith("PASSED")) {
				game.passMove();
				System.out.println("Other player " + fromServer);
				break;
			} else if (fromServer.startsWith("WARNING")) {
				System.out.println(fromServer + "try something else");
				break;
			} else if (fromServer.startsWith("TABLEFLIPPED")) {
				System.out.println(fromServer);
				game.tableflipMove();
				break;
			} else if (fromServer.startsWith("CHAT")) {
				System.out.println(fromServer);
				break;
			} else if (fromServer.startsWith("END")) {
				int scoreBlack = Integer.parseInt(serverInputMessage[1]);
				int scoreWhite = Integer.parseInt(serverInputMessage[2]);
				System.out.println("Score black: " + scoreBlack +  "\nScore white: "  + scoreWhite);
			} else {
				System.out.println("WARNING Must...resist...kicking...you. \n Message " + fromServer + " is invalid input.");
			}
		}
	}

	/**
	 * general message writer from client to server
	 * @param message
	 * @throws IOException
	 */
	public synchronized void writeToServer(String message) throws IOException {
		outputToServer.write(message);
		outputToServer.newLine();
		outputToServer.flush();

	}

	/**
	 * Shuts down the serverHandler
	 * @throws IOException
	 */
	public void shutdown() throws IOException {
		outputToServer.close();
		inputFromServer.close();
	}

	/** 
	 * checks whether a move is allowed and not in KO
	 * @param col
	 * @param row
	 * @return boolean
	 */
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

	/**
	 * checks what to do when the initial command is given by the GoClient
	 * @param string
	 * @param string2
	 * @param boardSize
	 * @throws IOException
	 */
	public void go(String string, String string2, String boardSize) throws IOException {
		clientName = string2;
		dim = Integer.parseInt(boardSize);
		writeToServer(string + " " + string2 + " " + boardSize);
	}

	/**
	 * checks what to do when the initial command is given by the GoClient
	 * @param string
	 * @param boardSize
	 * @throws IOException
	 */
	public void goWithoutName(String string, String boardSize) throws IOException {
		dim = Integer.parseInt(boardSize);
		writeToServer(string + " " + boardSize);
	}

	/**
	 * checks the input of 'MOVE' given by the GoClient and passes it on to the server
	 * @param string
	 * @param boardSize
	 * @throws IOException
	 */
	public void move(String move, String stringX, String stringY) throws IOException {
		int col = Integer.parseInt(stringX);
		int row = Integer.parseInt(stringY);
		if (!moveAllowed(col, row)) {
			System.out.print(move + " " + stringX + " " + stringY + " incorrect, try again");
		}
		writeToServer(move + stringX + stringY + "incorrect, try again");

	}

	/**
	 * passes 'PASS' to the server, given by the client
	 * @param pass
	 * @throws IOException
	 */
	public void pass(String pass) throws IOException {
		writeToServer(pass);

	}
	
	/**
	 * passes 'TABLEFLIP' to the server, given by the client
	 * @param pass
	 * @throws IOException
	 */
	public void tableflip(String tableflip) throws IOException {
		writeToServer(tableflip);
	}

	/**
	 * passes on the 'CHAT' given by the client to the server
	 * @param pass
	 * @throws IOException
	 */
	public void chat(String chat) throws IOException {
		writeToServer(chat);
		System.out.println(clientName + ": " + chat);
	}

}
