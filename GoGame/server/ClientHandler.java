package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import model.Stone;

public class ClientHandler extends Thread {
	private GoServer server;
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private ClientStatus clientStatus;

	public ClientHandler(Socket socket, GoServer server) {
		this.server = server;
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
			switch (clientStatus) {
			case INITIALIZING:
				handleEntry();
				clientStatus = ClientStatus.WAITING;
				output.write(("ClientStatus:" + clientStatus));
			case WAITING:
				handleWaiting();
				clientStatus = ClientStatus.INGAME;
				output.write(("ClientStatus:" + clientStatus));
			case INGAME:
				handleGame();
				clientStatus = ClientStatus.INITIALIZING;
				output.write(("ClientStatus:" + clientStatus));
			}
		} catch (IOException e) { // TODO: (may be) more precise error handling
			e.printStackTrace();
		} 

		try {
			input.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.removeClient(this);


	}

	private void handleEntry() throws IOException {
		boolean inputSyntaxError = false;
		do {
			System.out.println("Please enter {GO yourname boarddim}");
			String message = input.readLine();
			while(input != null){
				String inputMessage[] = message.split(" ");
				if (message.startsWith("GO") && inputMessage.length == 3 && checkName(inputMessage[1]) && isParsable(inputMessage[2])) {
					server.clientEntry(inputMessage[1], Integer.parseInt(inputMessage[2]));
				}
				else {
					output.write("WARNING Must...resist...kicking...you. \n Message " + message + " is invalid input.");
					inputSyntaxError = true;
				}
			} 
		} while(inputSyntaxError);
	}

	public void handleWaiting() throws IOException {
		server.handleWait();
		
//		String message = input.readLine();
//		if (message.startsWith("CHAT")) {
//			server.sendMessage(message);
//		}

	}

	private void handleGame(){
		String inputMessage[] = null;
		String message;
		boolean legalInput = false;

		try {
			message = input.readLine();
			System.out.println(inputMessage);
			inputMessage = message.split(" ");
			while(!legalInput){

				if (inputMessage[0] == "MOVE" && isParsable(inputMessage[1]) && isParsable(inputMessage[2])) {
					int col = Integer.parseInt(inputMessage[2]);
					int row = Integer.parseInt(inputMessage[1]);
					if (!moveAllowed(col, row)) {
						annihilatePlayer();
						output.write("Player + client");
					}
					legalInput = true;
					executeTurnMove(col, row);
				} else if (inputMessage[0] == "PASS" && inputMessage.length == 1) {
					legalInput = true;
					executeTurnPass();
				} else if (inputMessage[0] == "TABLEFLIP" && inputMessage.length == 1) {
					legalInput = true;
					executeTurnTableflip();
				} else if (inputMessage[0] == "CHAT") {
					legalInput = true;
					for (int i = 1; i <= inputMessage.length; i++) {
						chatMessage += inputMessage[i];
					}
					System.out.println(clientName + ": " + chatMessage);
				} else {
					output.write("WARNING Must...resist...kicking...you. Message " + message + " is invalid input.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void warning() {

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

	public boolean isParsable(String input){
		boolean parsable = true;
		try{
			Integer.parseInt(input);
		}catch(NumberFormatException e){
			parsable = false;
		}
		return parsable;
	}

	public enum ClientStatus {

		INITIALIZING, WAITING, INGAME;
	}

	public void nextStatus() {

	}
}