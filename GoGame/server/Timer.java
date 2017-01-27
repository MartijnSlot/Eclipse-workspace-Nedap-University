package server;

import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

	private int delay;

	public Timer(int delay) {
		this.delay = delay;
	}

	@Override
	public void run() {
		while(true){
			try {
				TimeUnit.SECONDS.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
