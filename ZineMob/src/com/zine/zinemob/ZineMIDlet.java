package com.zine.zinemob;

import com.zine.zinemob.audio.Sound;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * MIDlet class of ZineMob applications. You should use this class instead the
 * default MIDlet class. Override the ZineMIDlet and implements the run() method
 * to create and run your Scenes. When the run() method finishes, the application
 * finishes. If you need to override the startApp, pauseApp and destroyApp methods,
 * remember to call the method of the super class too, or the behavior of the ZineMIDlet
 * can be not the expected.
 * 
 * After the creation of the ZineMIDlet, this instance can be accessed by the getMIDlet
 * method.
 */
public abstract class ZineMIDlet extends MIDlet implements Runnable {

	private static ZineMIDlet instance;
	
	private boolean wasMuteBeforePause = false;
	private boolean isPaused = false;

	public ZineMIDlet() {
		instance = this;
	}

	/**
	 * Returns the created ZineMIDlet. It is the last instantiated ZineMIDlet object.
	 */
	public static ZineMIDlet getMIDlet() {
		return instance;
	}

	protected void startApp() throws MIDletStateChangeException {
		if (isPaused) {
			if (!wasMuteBeforePause) {
				Sound.setAllSoundsMute(false);
			}
		} else {
			runThread();
		}
		isPaused = false;
	}

	protected void pauseApp() {
		isPaused = true;
		wasMuteBeforePause = Sound.isAllSoundsMute();
		Sound.setAllSoundsMute(true);
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		isPaused = false;
	}
	
	private void runThread() {
		Thread thread = new Thread() {
			public void run() {
				ZineMIDlet.this.run();
				notifyDestroyed();
			}
		};
		thread.setPriority(Thread.MAX_PRIORITY);
		thread.start();
	}

}
