package Server;

import java.net.*;

import java.io.*;

public class GoServer {

	private MiniServer clients[] = new MiniServer[100];
	private int numClients = 0;
	private ServerSocket serverSocket;


	public GoServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server riding the waves of port " + port + ". Details: " + serverSocket);
		} catch (IOException ioe) {
			System.out.println("Wash out: server has sunk in port " + port + ": " + ioe.getMessage());
		}

	}

	private void go() throws IOException {
		boolean listeningSocket = true;
		while (listeningSocket) {
			System.out.println("Waiting for a player.");
			Socket clientSocket = serverSocket.accept();
			System.out.println("Go player!");
			MiniServer mini = new MiniServer(clientSocket);
			mini.start();
		}
		serverSocket.close();
	}

	private int findClient(int ID) {
		return ID;
	}

	public void sendToAllPlayers(String string) {
	}

	public static void main(String args[]) {
		GoServer server = new GoServer(Integer.parseInt(args[0]));
		try {
			server.go();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void checkMove() {
		
		
	}

	public void pass() {
		// TODO Auto-generated method stub
		
	}

	public void tableflip() {
		// TODO Auto-generated method stub
		
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}




}