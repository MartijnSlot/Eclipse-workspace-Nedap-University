package client;

import java.io.*;
import java.net.*;

import controller.Game;

public class GoClient {


	private Socket socket;
	private BufferedReader inputFromServer;
	private BufferedReader inputFromPlayer;
	private BufferedWriter outputToServer;
	private Game game;
	private String clientName;
	private int dim;


	public GoClient(String serverAdress, int serverPort) {
		this.clientName = null;
		this.dim = 0;
		try {
			socket = new Socket(serverAdress, serverPort);
			inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			inputFromPlayer = new BufferedReader(new InputStreamReader(System.in));
			outputToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	public static void main(String args[]) throws IOException {
		if (args.length != 2)
			System.out.println("Usage: java ChatClient host port");
		else {
			GoClient client = new GoClient(args[0], Integer.parseInt(args[1]));
			client.game();
		}
	}

	public String getClientName() {
		return clientName;
	}


	public Integer getDim() {
		return dim;
	}

	public void game() throws IOException {
		System.out.println("Please enter {GO yourname boarddim}");

		do {
			while(inputFromPlayer.readLine() != null){
				playerHandling(inputFromPlayer.readLine());
			}

			while(inputFromServer.readLine() != null) {
				serverHandling(inputFromServer.readLine());
			}

		} while(socket.isConnected());

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
	
	public void playerHandling(String message) throws IOException {
		boolean legalInput = true;
		do {
			message = inputFromPlayer.readLine();
			String inputMessage[] = message.split(" ");
			if (message.startsWith("GO") && inputMessage.length == 3 && checkName(inputMessage[1]) && checkDim(inputMessage[2])) {
				clientName = inputMessage[1];
				dim = Integer.parseInt(inputMessage[2]);
				outputToServer.write("PLAYER " + clientName);
				outputToServer.newLine();
				outputToServer.flush();
			} else if (message.startsWith("GO") && inputMessage.length == 2 && checkDim(inputMessage[1]) && clientName != null) {
				dim = Integer.parseInt(inputMessage[1]);
				outputToServer.write(message);
				outputToServer.newLine();
				outputToServer.flush();
			} else if (message.startsWith("MOVE") && isParsable(inputMessage[1]) && isParsable(inputMessage[2]) && clientName != null) {
				int col = Integer.parseInt(inputMessage[2]);
				int row = Integer.parseInt(inputMessage[1]);
				if (!moveAllowed(col, row)) {
					System.out.print(message + "incorrect, try again");
					legalInput = false;
				}
				outputToServer.write(message);
				outputToServer.newLine();
				outputToServer.flush();
			} else if (message.startsWith("PASS") && inputMessage.length == 1 && clientName != null) {
				outputToServer.write(message);
				outputToServer.newLine();
				outputToServer.flush();
			} else if (message.startsWith("TABLEFLIP") && inputMessage.length == 1 && clientName != null) {
				outputToServer.write(message);
				outputToServer.newLine();
				outputToServer.flush();
			} else if (message.startsWith("CHAT")) {
				outputToServer.write(clientName + ": " + message);
				System.out.println(clientName + ": " + message);
			} else if (message.startsWith("EXIT") && inputMessage.length == 1) {
				outputToServer.write(message);
				outputToServer.newLine();
				outputToServer.flush();
				socket.close();
			} else {
				System.out.println("WARNING Must...resist...kicking...you. \n Message " + message + " is invalid input.");
				legalInput = false;
			}
		} while(legalInput);
	}

	public void serverHandling(String servermessage) throws IOException {
		boolean legalInput = true;
		do {
			servermessage = inputFromServer.readLine();
			String serverInputMessage[] = servermessage.split(" ");
			if (servermessage.startsWith("WAITING")) {
				System.out.println(servermessage);
			} else if (servermessage.startsWith("READY")) {
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
				int boardSize = Integer.parseInt(serverInputMessage[3]);
				game.reset();
				game = new Game(name1, name2, boardSize);
				System.out.println(servermessage);
			} else if (servermessage.startsWith("VALID")) {
				int col = Integer.parseInt(serverInputMessage[2]);
				int row = Integer.parseInt(serverInputMessage[3]);
				game.executeTurn(row, col);
				game.addToGUI(row, col);
				System.out.println(servermessage);
			} else if (servermessage.startsWith("INVALID") && serverInputMessage.length == 1) {
				System.out.println(servermessage + " kicked from the game due to invalid move");
				socket.close();
			} else if (servermessage.startsWith("PASSED")) {
				game.passMove();
				System.out.println("Other player " + servermessage);
			} else if (servermessage.startsWith("TABLEFLIPPED")) {
				game.tableflipMove();
				System.out.println(servermessage);
			} else if (servermessage.startsWith("CHAT")) {
				System.out.println("Server: " + servermessage);
			} else if (servermessage.startsWith("END")) {
				int scoreBlack = Integer.parseInt(serverInputMessage[1]); //score black
				int scoreWhite = Integer.parseInt(serverInputMessage[2]); //score white
				System.out.println("Score black: " + scoreBlack +  "\nScore white: "  + scoreWhite);
			} else {
				System.out.println("WARNING Must...resist...kicking...you. \n Message " + servermessage + " is invalid input.");
				legalInput = false;
			}
		} while(legalInput);
	}

	public boolean isParsable(String input) {
		try{
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
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
		boolean dimIsOk = true;
		try{
			int parsedInput = Integer.parseInt(input);
			if (parsedInput % 2 == 0 || parsedInput < 5 || parsedInput > 131) {
				dimIsOk = false;
			}
		}catch(NumberFormatException e){
			dimIsOk = false;
		}
		return dimIsOk;
	}
}

