package server;

import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import client.GoClient;
import java.io.*;

public class GoServer extends Thread {

	private int port;
	public Socket socket;
	private ServerSocket serverSocket;
	private Set<ClientHandler> clientHandlers = new HashSet<>();
	private Map<String, Integer> clients = new HashMap<>();
	private PrintWriter output;
	private int maxClients = 50;
	private int clientCheckerDelay = 1;

	public GoServer(int port) {
		System.out.println("Starting server on port " + port);
		try {
			serverSocket = new ServerSocket(port, maxClients);
		} catch (IOException e) {
			System.out.print("Could not listen on port " + port);
		}
		System.out.println("Waiting for clients...");
	}


	@Override
	public void run() {
		listen();
		new Thread(new Timer(this, clientCheckerDelay));
	}

	
	public Map<String, Integer> getClients() {
		return clients;
	}

	public void setClients(Map<String, Integer> clients) {
		this.clients = clients;
	}


	/**
	 * make new thread for each client that connects
	 */
	public void listen() {
		while (true) {
			try {
				ClientHandler newClient = new ClientHandler(serverSocket.accept(), this);
				clientHandlers.add(newClient);
			} catch (IOException e) {
				System.err.println("Cannot accept client.");
			}
		}
	}

	public void removeClient(ClientHandler a) {
		try {
			clientHandlers.remove(a);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	public void sendMessageToAll(String msg) {
		output.write(msg);

	}

	public void clientEntry(String name, int dim) {			
		clients.put(name, dim);
	}

	public void handleWait() {
			
	}

}
