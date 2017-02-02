package playthegame;

import java.io.IOException;
import client.GoClient;
import server.GoServer;


/**
 * Main class for playing the game.
 * 
 * @author Martijn Slot
 * @version 1.0
 */
public class PlayTheGame {
	
	public static int serverPort;
	public static String hostName;

	public static void main(String[] args) throws IOException {
		try {
		
		hostName = args[0];
		serverPort = Integer.parseInt(args[1]);
		
		} catch (NumberFormatException e) {
			System.out.println("wrong input. correct input has 2 arguments divided by a space: ipaddress port \n"
					+ "For starting own server, enter '0.0.0.0' as ipadress.");
		}
		
		if (args[0].equals("0.0.0.0")) {
			GoServer server = new GoServer(serverPort);
			server.start();
			GoClient client = new GoClient(hostName, serverPort);
			client.start();
		} else {
			GoClient client = new GoClient(hostName, serverPort);
			client.start();
		}
	}

}
