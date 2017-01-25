package Server;

import java.net.*;

import java.io.*;

public class GoServer extends Thread {


	private GoServerThread clients[] = new GoServerThread[50];
	private int numClients = 0;
	private ServerSocket server;
	private Thread thread;
	private int port;

	public GoServer(int port) {
		try {
			server = new ServerSocket(port);
			System.out.println("Server riding of the waves of port " + port + ". Details: " + server);
			run();
		} catch (IOException ioe) {
			System.out.println("Wash out: server has sunk in port " + port + ": " + ioe.getMessage());
		}

	}

	public void run() {
			try {
				System.out.println("Waiting for a playah ...");
				Socket s = (server.accept());
				System.out.println("Go player!");

				addThread(s);

			} catch (IOException ioe) {
				System.out.println("Server accept error: " + ioe);				
			}
	}

	private int findClient(int ID) {
		for (int i = 0; i < numClients; i++)
			if (clients[i].getID() == ID)
				return i;
		return -1;
	}

	private void addThread(Socket socket) {
		if (numClients < clients.length) {
			System.out.println("Client accepted: " + socket);
			clients[numClients] = new GoServerThread(this, socket);
			try {
				clients[numClients].announceName();
			} catch (IOException e) {
				System.out.println("Invalid name, please enter name.");
			}
			clients[numClients].start();
			numClients++;
		} else
			System.out.println("No overflowing the server: " + clients.length + " clients maximum.");
	}
	
	private void removeServerThread(Socket socket) {
		if (numClients < clients.length) {
			System.out.println("Client accepted: " + socket);
			clients[numClients] = new GoServerThread(this, socket);
			try {
				clients[numClients].announceName();
			} catch (IOException e) {
				System.out.println("Invalid name, please enter name.");
			}
			clients[numClients].start();
			numClients++;
		} else
			System.out.println("No overflowing the server: " + clients.length + " clients maximum.");
	}

	public static void main(String args[]) {
		GoServer server = new GoServer(Integer.parseInt(args[0]));

	}

	public void sendToAllPlayers(String string) {
		// TODO Auto-generated method stub

	}
}