package server;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import server.ClientHandler.ClientStatus;

import java.io.*;

public class GoServer extends Thread {

	public Socket socket;
	private ServerSocket serverSocket;
	Map<ClientHandler, Integer> clientHandlerMap = new HashMap<>();
	Map<Integer, List<ClientHandler>> pendingClients = new HashMap<>();
	private int maxClients = 50;

	public GoServer(int port) {
		System.out.println("Starting server on port " + port);
		try {
			serverSocket = new ServerSocket(port, maxClients);
		} catch (IOException e) {
			System.out.print("Could not listen on port " + port);
		}
	}

	@Override
	public void run() {		
		System.out.println("Waiting for clients...");
		int clientCounter = 0;

		while (true) {
			try {
				clientCounter += 1; 
				if (clientCounter >= 500) {
					System.out.println("Too many Clients!, restart server!");
					socket.close();
					break;
				}
				ClientHandler newClient = new ClientHandler(serverSocket.accept(), this);
				System.out.println("Client Accepted! Client count: " + clientCounter);
				newClient.start();			
			} catch (IOException e) {
				System.out.println("Cannot accept client.");
			}
		}
	}
	
	public synchronized void chatToAllPlayers(String message) throws IOException {
		for (ClientHandler a : clientHandlerMap.keySet()) {
			a.writeToClient(message);
		}
	}
	
	public void clientEntry(ClientHandler client, int dim) throws IOException {			
		ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
		clientHandlers.add(client);
		clientHandlerMap.put(client, dim);	

		if(client.getClientStatus() == ClientStatus.PREGAME) {
			if (pendingClients.containsKey(dim)) {
				pendingClients.get(dim).addAll(clientHandlers);
			} else {
				pendingClients.put(dim, clientHandlers);
			}

			client.setClientStatus(ClientStatus.WAITING);
		}

		for (int dimBoard : pendingClients.keySet()) {
			if (pendingClients.get(dimBoard).size() == 2) {
				Random r = new Random();
				int  n = r.nextInt(1);
				ClientHandler ch1 = pendingClients.get(dimBoard).get(n);
				ClientHandler ch2 = pendingClients.get(dimBoard).get(1-n);
				pendingClients.remove(dimBoard);
				new SingleGameServer(ch1, ch2, dim);
			}
		}
	}

	public Map<ClientHandler, Integer> getClientHandlerMap() {
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

}
