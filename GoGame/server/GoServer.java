package server;

import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.*;

public class GoServer {

	public Socket socket;
	private ServerSocket serverSocket;
	Map<Integer, ClientHandler> clientHandlerMap = new HashMap<>();
	Map<Integer, List<Integer>> pendingClients = new HashMap<>();
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
		int clientID = 0;
		
		while (true) {
			try {
				clientID += 1; 
				ClientHandler newClient = new ClientHandler(serverSocket.accept(), this, clientID);
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
		output.flush();
	}


}
