package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import client.GoClient;

public class ClientHandler extends Thread {
	private GoServer server;
	private SingleGameServer sgs;
	private GoClient client;
	private Socket socket;
	private BufferedWriter outputToClient;
	private BufferedReader inputFromClient;
	private ClientStatus clientStatus;
	private int clientID;
	private int dim;
	private String clientName;

	public ClientHandler(Socket socket, GoServer server, int clientID) {
		this.server = server;
		this.socket = socket;
		this.clientID = clientID;
		this.clientStatus = ClientStatus.INITIALIZING;
	}

	@Override
	public void run() {
		try {
			inputFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outputToClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			while(socket.isConnected()){
				switch (clientStatus) {
				case INITIALIZING:
					assessInput(inputFromClient);
					outputToClient.write("CHAT ClientStatus:" + clientStatus);
					outputToClient.newLine();
					outputToClient.flush();
				case WAITING:
					outputToClient.write("CHAT ClientStatus:" + clientStatus);
					outputToClient.newLine();
					outputToClient.flush();
				case INGAME:
					handleGame();
					outputToClient.write("CHAT ClientStatus:" + clientStatus);
					outputToClient.newLine();
					outputToClient.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				inputFromClient.close();
				outputToClient.close();
				server.removeClient(this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public void setClientStatus(ClientStatus cs) {
		this.clientStatus = cs;

	}

	public ClientStatus getClientStatus() {
		return clientStatus;
	}

	public String getClientName() {
		return clientName;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int id) {
		clientID = id;
	}

	public int getDim() {
		return dim;
	}

	public void setDim(int d) {
		dim = d;
	}

	public void assessInput(BufferedReader input) throws IOException {
		String message = input.readLine();
		String inputMessage[] = message.split(" ");

		if (message.startsWith("GO") && inputMessage.length == 3 && checkName(inputMessage[1]) && checkDim(inputMessage[2])) {
			dim = Integer.parseInt(inputMessage[2]);
			clientName = inputMessage[1];
			clientEntry(this, clientID);
			outputToClient.write("Input correct:" + message);
			outputToClient.flush();
		} else if (message.startsWith("GO") && inputMessage.length == 2 && checkDim(inputMessage[1])) {
			dim = Integer.parseInt(inputMessage[1]);
			clientEntry(this, clientID);
			outputToClient.write("Input correct:" + message);
			outputToClient.flush();
		}
		else {
			outputToClient.write("WARNING Must...resist...kicking...you. \n Message " + message + " is invalid input. \n"
					+ "Please enter *GO name dim* or *GO dim*: \n" + clientStatus);
			outputToClient.newLine();
			outputToClient.flush();
		}
	}

	public void clientEntry(ClientHandler newClient, int clientID) throws IOException {			
		ArrayList<Integer> clientIDs = new ArrayList<>();
		clientIDs.add(clientID);
		server.pendingClients.put(5, clientIDs);
		server.clientHandlerMap.put(clientID, newClient);	

		if(newClient.getClientStatus() == ClientStatus.INITIALIZING) {
			if (server.pendingClients.containsKey(newClient.getDim())) {
				server.pendingClients.get(newClient.getDim()).addAll(clientIDs);
			} else {
				server.pendingClients.put(newClient.getDim(), clientIDs);;
			}
			newClient.setClientStatus(ClientStatus.WAITING);
			outputToClient.write("WAITING");
			outputToClient.newLine();
			outputToClient.flush();
		}

		for (int dim : server.pendingClients.keySet()) {
			if (server.pendingClients.get(dim).size() == 2) {
				startNewGame(server.pendingClients.get(dim).get(0), server.pendingClients.get(dim).get(1), dim);
				server.pendingClients.get(dim).clear();
			}
		}
	}

	public void startNewGame(int ID, int ID2, int dim) {
		server.clientHandlerMap.get(ID).setClientStatus(ClientStatus.INGAME);
		server.clientHandlerMap.get(ID2).setClientStatus(ClientStatus.INGAME);
		new SingleGameServer(server.clientHandlerMap.get(ID), server.clientHandlerMap.get(ID2), dim);

	}

	public void annihilatePlayer(GoClient client, int clientID) throws IOException {
		sgs.directWinner();
		outputToClient.write("You've been caught cheating, therefore you shall be annihilated!");
		server.pendingClients.get(this.getDim()).remove(clientID);
		outputToClient.close();
		inputFromClient.close();
		server.socket.close();
	}

	void handleGame(){
		String inputMessage[] = null;
		String message;
		boolean inputError = false;

		try {
			outputToClient.write("CHAT State your action (MOVE int int, PASS, TABLEFLIP, CHAT, EXIT)");
			outputToClient.newLine();
			outputToClient.flush();
			message = inputFromClient.readLine();
			System.out.println(inputMessage);
			inputMessage = message.split(" ");
			do {

				if (inputMessage[0] == "MOVE" && isParsable(inputMessage[1]) && isParsable(inputMessage[2]) && inputMessage.length == 3) {
					int col = Integer.parseInt(inputMessage[2]);
					int row = Integer.parseInt(inputMessage[1]);
					if (!sgs.moveAllowed(col, row)) {
						annihilatePlayer(client, clientID);
					}
					sgs.executeTurnMove(col, row);
				} else if (message.startsWith("PASS") && inputMessage.length == 1) {
					sgs.executeTurnPass();
				} else if (message.startsWith("TABLEFLIP") && inputMessage.length == 1) {
					sgs.executeTurnTableflip();
					outputToClient.write("TABLEFLIPPED" + message);
					outputToClient.newLine();
					outputToClient.flush();
					playAgain();
				} else if (message.startsWith("CHAT")) {
					outputToClient.write(client.getClientName() + ": " + message);
					outputToClient.newLine();
					outputToClient.flush();
				} else {
					outputToClient.write("WARNING Must...resist...kicking...you. Message " + message + " is invalid input.");
					outputToClient.flush();
					inputError = false;
				}
			} while(inputError);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
			System.out.println("CHAT Server: Illegal input " + name +
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
			if (parsedInput < 5 || parsedInput > 131 && parsedInput % 2 == 0) {
				dimIsOk = false;
			}
		}catch(NumberFormatException e){
			dimIsOk = false;
		}
		return dimIsOk;
	}

	private boolean playAgain() throws IOException {
		boolean inputError = false;
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

		do {
			outputToClient.write("CHAT Play again? (Y/N)");
			outputToClient.newLine();
			outputToClient.flush();
			try {
				String playAgain = input.readLine();
				if (playAgain.equals("Y") | playAgain.equals("y") | playAgain.equals("yes")) {
					clientStatus = ClientStatus.INITIALIZING;
					return true;
				}
				else if (playAgain.equals("N") | playAgain.equals("n") | playAgain.equals("no")) {
					socket.close();
					inputFromClient.close();
					outputToClient.close();
					return false;
				}
				else {
					outputToClient.write("CHAT Wrond input (Y/N)");
					outputToClient.newLine();
					outputToClient.flush();
					inputError = true;
				}
			} catch (IOException e) {
				outputToClient.write("CHAT Wrond input (Y/N)");
				outputToClient.newLine();
				outputToClient.flush();
				inputError = true;
			}
		} while(inputError);

		return false;
	}

	public enum ClientStatus {

		INITIALIZING, WAITING, INGAME;
	}

}