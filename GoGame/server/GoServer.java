package server;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.ClientHandler.ClientStatus;

import java.io.*;

public class GoServer {

	@SuppressWarnings("unused")
	private int port;
	public Socket socket;
	private ServerSocket serverSocket;
	private Map<Integer, ClientHandler> clientHandlerMap = new HashMap<>();
	private Map<Integer, List<Integer>> pendingClients = new HashMap<>();
	private PrintWriter output;
	private int maxClients = 50;

	public static void main(String args[]) {
		GoServer server = new GoServer(Integer.parseInt(args[0]));
	}
	
	public GoServer(int port) {
		System.out.println("Starting server on port " + port);
		try {
			serverSocket = new ServerSocket(port, maxClients);
		} catch (IOException e) {
			System.out.print("Could not listen on port " + port);
		}
		System.out.println("Waiting for clients...");

		while (true) {
			try {
				ClientHandler newClient = new ClientHandler(serverSocket.accept(), this);

				int clientID = (clientHandlerMap.size() + 1);
				clientHandlerMap.put(clientID, newClient);
				clientEntry(newClient, clientID);
				newClient.start();
			} catch (IOException e) {
				System.err.println("Cannot accept client.");
			}
		}
	}


	public Map<Integer, ClientHandler> getClientHandlerMap() {
		return clientHandlerMap;
	}

	public void removeClient(ClientHandler a) {
		try {
			clientHandlerMap.remove(a);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public void sendMessageToAll(String msg) {
		output.write(msg);
	}

	public void startNewGame(int ID, int ID2, int dim) {
		clientHandlerMap.get(ID).setClientStatus(ClientStatus.INGAME);
		clientHandlerMap.get(ID).setClientStatus(ClientStatus.INGAME);
		new SingleGameServer(clientHandlerMap.get(ID), clientHandlerMap.get(ID), dim);

	}

	public void clientEntry(ClientHandler newClient, int clientID) {			
		List<Integer> clientIDs = new ArrayList<>();
		clientIDs.add(clientID);

		if(newClient.getClientStatus() == ClientStatus.INITIALIZING) {
			if (pendingClients.containsKey(newClient.getDim())) {
				pendingClients.get(newClient.getDim()).addAll(clientIDs);
			} else {
				pendingClients.put(newClient.getDim(), clientIDs);;
			}
			newClient.setClientStatus(ClientStatus.WAITING);
		}

		for (int dim : pendingClients.keySet()) {
			if (pendingClients.get(dim).size() == 2) {
				startNewGame(pendingClients.get(dim).get(0), pendingClients.get(dim).get(1), dim);
			}
		}
	}
}
