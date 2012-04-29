package com.zine.zinemob.util;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.audio.Sound;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

/**
 * Class that mutes the sound when the application goes to background. This class
 * is automatically started by the ZineMIDlet class.
 */
public class SoundMuter {
	
	private boolean wasInBackground = false;
	private boolean wasSoundMute;
	private boolean started = false;
	private static SoundMuter instance;
	
	/**
	 * Returns the singleton instance.
	 */
	public static SoundMuter getInstance() {
		if (instance == null) {
			instance = new SoundMuter();
		}
		return instance;
	}
	
	private SoundMuter() {
	}
	
	/**
	 * Starts the thread that verifies if the application is in background of foreground,
	 * to set the sound mute or not. It runs assyncronously. It does nothing if
	 * the thread is already started.
	 */
	public void start() {
		if (!started) {
			started = true;
			Timer timer = new Timer();
			TimerTask timerTask = new TimerTask() {
				public void run() {
					Display display = Display.getDisplay(ZineMIDlet.getMIDlet());
					Displayable currentDisplayable = display.getCurrent();
					if (currentDisplayable != null) {
						if (currentDisplayable.isShown()) {
							if (wasInBackground) {
								if (!wasSoundMute) {
									Sound.setAllSoundsMute(false);
								}
							}
							wasInBackground = false;
						} else {
							if (!wasInBackground) {
								wasSoundMute = Sound.isAllSoundsMute();
								Sound.setAllSoundsMute(true);
							}
							wasInBackground = true;
						}
					}
				}
			};
			timer.schedule(timerTask, 1000, 1000);
		}
	}
}
