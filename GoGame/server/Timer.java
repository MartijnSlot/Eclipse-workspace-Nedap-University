package server;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

	private int delay;
	private GoServer server;
	public Timer(GoServer goServer, int delay) {
		this.delay = delay;
		this.server = goServer;
	}

	/**
	 * timer. Uses multimap to select double values from map.
	 */

	@Override
	public void run() {
		while(true){
			try {
				TimeUnit.SECONDS.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} if (server.getWaitingClients().) {
			
		}
	}


}
