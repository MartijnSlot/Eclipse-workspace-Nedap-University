package client;

import java.io.*;
import java.net.*;

public class GoClient {


	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	private String name;

	public GoClient(String serverAdress, int serverPort, String name) {
		this.name = name;
			try {
				socket = new Socket(serverAdress, serverPort);
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				output = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


	}


	public static void main(String args[]) {
		GoClient client = null;
		if (args.length != 3)
			System.out.println("Usage: java ChatClient host port name");
		else
			client = new GoClient(args[0], Integer.parseInt(args[1]), args[2]);
	}
}

