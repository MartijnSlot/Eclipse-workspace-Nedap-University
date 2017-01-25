package Server;

import java.io.*;
import java.net.Socket;

import Client.GoClient;

public class MiniServer extends Thread {

	private GoServer server;
	private Socket socket;
	private Thread thread;
	private String clientName;
	private GoClient client;
	private BufferedReader input;
	private BufferedWriter output;
	private int ID;
	private int dim;


	public MiniServer(Socket s) {
		super("Miniserver");
		this.socket = s;
	}

	public void run() {
		String inputMessage[] = null;
		String message;
		try {
			message = input.readLine();
			System.out.println(inputMessage);
			inputMessage = message.split(" ");
			if (inputMessage[0] == "GO") {
				clientName = inputMessage[1];
				dim = Integer.parseInt(inputMessage[2]);
				start();
			} else if (inputMessage[0] == "MOVE") {
				server.checkMove();				
			} else if (inputMessage[0] == "PASS") {
				server.pass();
			} else if (inputMessage[0] == "TABLEFLIP") {
				server.tableflip();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
