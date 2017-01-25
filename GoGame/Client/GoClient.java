package Client;

import java.io.*;
import java.net.*;

public class GoClient implements Runnable {


	private Socket socket = null;
	private Thread thread = null;
	private DataInputStream console = null;
	private DataOutputStream streamOut = null;
	private String name;

	public GoClient(String serverAdress, int serverPort, String name) {
		System.out.println("Establishing connection. Please wait ...");
		this.name = name;
		try {
			socket = new Socket(serverAdress, serverPort);
			System.out.println("Connected: " + socket);
			start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
	}

	public void run() {
		while (thread != null) {
			try {
				streamOut.writeUTF(name + ": " + console.readLine());
				streamOut.flush();
			} catch (IOException ioe) {
				System.out.println("Sending error: " + ioe.getMessage());
				stop();
			}
		}
	}

	public void handle(String msg) {
		if (msg.equals(".bye")) {
			System.out.println("Good bye. Press RETURN to exit ...");
			stop();
		} else
			System.out.println(msg);
	}

	public void start() {
	}

	public void stop() {
	}

	public static void main(String args[]) {
		GoClient client = null;
		if (args.length != 3)
			System.out.println("Usage: java ChatClient host port name");
		else
			client = new GoClient(args[0], Integer.parseInt(args[1]), args[2]);
	}
}

