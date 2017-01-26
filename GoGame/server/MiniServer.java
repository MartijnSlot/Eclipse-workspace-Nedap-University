package server;

import java.io.*;
import java.net.Socket;

import client.GoClient;
import exceptions.*;

public class MiniServer extends Thread {

	private GoServer server;
	private Socket socket;
	private String clientName;
	private GoClient client;
	private BufferedReader input;
	private BufferedWriter output;
	private int dim;


	public MiniServer(Socket s) {
		super("Miniserver");
		this.socket = s;
	}

	public void run() {
		String inputMessage[] = null;
		String message;
		boolean legalInput = false;
		String chatMessage = null;

		try {
			message = input.readLine();
			System.out.println(inputMessage);
			inputMessage = message.split(" ");
			while(!legalInput){
				if (inputMessage[0] == "GO" && server.checkName(inputMessage[1]) && server.isParsable(inputMessage[2])) {
					clientName = inputMessage[1];
					legalInput = true;
					server.waitForGame();
				} else if (inputMessage[0] == "MOVE" && server.isParsable(inputMessage[1]) && server.isParsable(inputMessage[2])) {
					int col = Integer.parseInt(inputMessage[2]);
					int row = Integer.parseInt(inputMessage[1]);
					if (!server.moveAllowed(col, row)) {
						server.annihilatePlayer();
						output.write("Player + client");
					}
					legalInput = true;
					server.placeMove(col, row);
				} else if (inputMessage[0] == "PASS" && inputMessage.length == 1) {
					legalInput = true;
					server.pass();
				} else if (inputMessage[0] == "TABLEFLIP" && inputMessage.length == 1) {
					legalInput = true;
					server.tableflip();
				} else if (inputMessage[0] == "CHAT") {
					legalInput = true;
					for (int i = 1; i <= inputMessage.length; i++) {
						chatMessage += inputMessage[i];
					}
					System.out.println(clientName + ": " + chatMessage);
				} else {
					server.warning();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
