package DikkeChatBox;

import java.net.*;
import java.io.*;

public class SimpleChatClient {

	private Socket socket = null;
	private BufferedReader input = null;
	private DataOutputStream output = null;

	public SimpleChatClient(String serverName, int serverPort) {
		System.out.println("Establishing connection. Please wait ...");
		try {
			socket = new Socket(serverName, serverPort);
			System.out.println("Connected: " + socket);
			start();
		} catch (UnknownHostException uhe) {
			System.out.println("Host unknown: " + uhe.getMessage());
		} catch (IOException ioe) {
			System.out.println("Unexpected exception: " + ioe.getMessage());
		}
		String line = "";
		while (!line.equals(".bye")) {
			try {
				line = input.readLine();
				output.writeUTF(line);
				output.flush();
			} catch (IOException ioe) {
				System.out.println("Sending error: " + ioe.getMessage());
			}
		}
	}

	public void start() throws IOException {
		input = new BufferedReader(new InputStreamReader(System.in));
		output = new DataOutputStream(socket.getOutputStream());
	}

	public void stop() {
		try {
			if (input != null)
				input.close();
			if (output != null)
				output.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe) {
			System.out.println("Error closing ...");
		}
	}

	public static void main(String args[]) {
		SimpleChatClient client = null;
		if (args.length != 2)
			System.out.println("Usage: java ChatClient host port");
		else
			client = new SimpleChatClient(args[0], Integer.parseInt(args[1]));
	}
}