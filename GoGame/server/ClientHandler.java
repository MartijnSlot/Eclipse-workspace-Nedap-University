package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import client.GoClient;
import model.Stone;
import server.ClientHandler.ClientStatus;

public class ClientHandler extends Thread {
	private GoServer server;
	private SingleGameServer sgs;
	private GoClient client;
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private ClientStatus clientStatus;
	private String clientName;
	private int dim;

	public ClientHandler(Socket socket, GoServer server) {
		this.client = client;
		this.server = server;
		this.socket = socket;
		this.clientName = null;
		this.dim = 0;
		this.clientStatus = ClientStatus.INITIALIZING;
	}

	public String getClientName() {
		return clientName;
	}

	@Override
	public void run() {
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
			while(true){
				switch (clientStatus) {
				case INITIALIZING:
					handleEntry();
					output.write(("ClientStatus:" + clientStatus));
				case WAITING:
//					handleWaiting();
					output.write(("ClientStatus:" + clientStatus));
				case INGAME:
					handleGame();
					clientStatus = ClientStatus.INITIALIZING;
					output.write(("ClientStatus:" + clientStatus));
				}
			}
		} catch (IOException e) {
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

	public Integer getDim() {
		return dim;
	}
	
	public void setClientStatus(ClientStatus cs) {
		this.clientStatus = cs;
		
	}

	public ClientStatus getClientStatus() {
		return clientStatus;
	}

	private void handleEntry() throws IOException {
		boolean inputSyntaxError = false;
		do {
			System.out.println("Please enter {GO yourname boarddim}");
			String message = input.readLine();
			while(input != null){
				String inputMessage[] = message.split(" ");
				if (message.startsWith("GO") && inputMessage.length == 3 && checkName(inputMessage[1]) && checkDim(inputMessage[2])) {
					clientName = inputMessage[1];
					dim = Integer.parseInt(inputMessage[2]);
				}
				if (message.startsWith("GO") && inputMessage.length == 2 && checkDim(inputMessage[1])) {
					dim = Integer.parseInt(inputMessage[2]);
				}
				else {
					output.write("WARNING Must...resist...kicking...you. \n Message " + message + " is invalid input.");
					inputSyntaxError = true;
				}
			} 
		} while(inputSyntaxError);
	}

	public void handleWaiting() throws IOException {
//		server.handleWait();

		//		String message = input.readLine();
		//		if (message.startsWith("CHAT")) {
		//			server.sendMessage(message);
		//		}

	}

	void handleGame(){
		String inputMessage[] = null;
		String message;
		boolean legalInput = false;

		try {
			System.out.println("State your action (MOVE int int\n, PASS, TABLEFLIP, CHAT)");
			message = input.readLine();
			System.out.println(inputMessage);
			inputMessage = message.split(" ");
			while(!legalInput){

				if (inputMessage[0] == "MOVE" && isParsable(inputMessage[1]) && isParsable(inputMessage[2])) {
					int col = Integer.parseInt(inputMessage[2]);
					int row = Integer.parseInt(inputMessage[1]);
					if (!sgs.moveAllowed(col, row)) {
						sgs.annihilatePlayer();
						output.write("Player + client destroyed");
					}
					legalInput = true;
					sgs.executeTurnMove(col, row);
				} else if (inputMessage[0] == "PASS" && inputMessage.length == 1) {
					legalInput = true;
					sgs.executeTurnPass();
				} else if (inputMessage[0] == "TABLEFLIP" && inputMessage.length == 1) {
					legalInput = true;
					sgs.executeTurnTableflip();
				} else if (inputMessage[0] == "CHAT") {
					legalInput = true;
					output.print(clientName + ": " + message);
					System.out.println(clientName + ": " + message);
				} else {
					output.write("WARNING Must...resist...kicking...you. Message " + message + " is invalid input.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			if (parsedInput < 5 | parsedInput > 131 && parsedInput % 2 == 0) {
				dimIsOk = false;
			}
		}catch(NumberFormatException e){
			dimIsOk = false;
		}
		return dimIsOk;
	}

	public boolean isParsable(String input) {
		try{
			Integer.parseInt(input);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}

	public enum ClientStatus {

		INITIALIZING, WAITING, INGAME;
	}

}