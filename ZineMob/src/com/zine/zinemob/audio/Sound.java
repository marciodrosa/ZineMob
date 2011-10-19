package com.zine.zinemob.audio;

import java.util.Vector;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

/**
 * A sound object that can be played.
 */
public final class Sound {
	
	private static boolean mute = false;
	private static Vector currentSounds = new Vector();
	private static Sound currentMusic = null;
	
	private boolean isPlaying = false;
	private String resourceName = "";
	private String type = "";
	private Player player;
	private boolean loop;
	
	/**
	 * Loads a midi file from a resource.
	 * @param resourceName the resource name
	 */
	public static Sound loadMidi(String resourceName) {
		Sound sound = new Sound();
		sound.resourceName = resourceName;
		sound.type = "audio/midi";
		sound.load();
		return sound;
	}
	
	/**
	 * Sets all sounds that is playing as mute.
	 * @mute true to set mute, false to return to normal
	 */
	public static void setAllSoundsMute(boolean mute) {
		Sound.mute = mute;
		for (int i=0; i<currentSounds.size(); i++) {
			configPlayerVolume((Sound)currentSounds.elementAt(i));
		}
	}
	
	/**
	 * Return if the sounds were setted to mute.
	 */
	public static boolean isAllSoundsMute() {
		return mute;
	}
	
	/**
	 * Stops all on going sounds.
	 */
	public static void stopAll() {
		while (!currentSounds.isEmpty()) {
			((Sound) currentSounds.firstElement()).stop();
		}
	}
	
	private Sound() {
	}
	
	private void load() {
		if (player == null || player.getState() == Player.CLOSED) {
			try {
				player = Manager.createPlayer(getClass().getResourceAsStream(resourceName), type);
				
				player.addPlayerListener(new PlayerListener() {
					public void playerUpdate(Player player, String event, Object eventData) {
						if (event.equals(PlayerListener.END_OF_MEDIA) && !loop) {
							stop();
						}
					}
				});
				
				player.prefetch();
			} catch (Exception ex) {
				player = null;
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Plays the sound.
	 * @param loop true to loop the sounds, false to play just one time
	 */
	public synchronized void play(boolean loop) {
		if (!isPlaying) {
			
			load();
			
			if (player != null) {
				isPlaying = true;
				currentSounds.addElement(this);
				player.setLoopCount(loop ? -1 : 1);
				this.loop = loop;

				configPlayerVolume(this);

				try {
					player.start();
				} catch (MediaException ex) {
					stop();
					ex.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Plays the sound in the music track. If there is a current music, is will be
	 * stoped.
	 */
	public void playAsMusic(boolean loop) {
		if (currentMusic != null) {
			currentMusic.stop();
		}
		currentMusic = this;
		play(loop);
	}
	
	/**
	 * Stops the execution of the sound.
	 */
	public synchronized void stop() {
		isPlaying = false;
		currentSounds.removeElement(this);
		if (currentMusic == this) {
			currentMusic = null;
		}
		
		if (player != null) {
			try {
				player.stop();
			} catch (MediaException ex) {
				ex.printStackTrace();
			}
			player.close();
			player = null;
		}
	}
	
	private static void configPlayerVolume(Sound sound) {
		VolumeControl control = (VolumeControl)sound.player.getControl("VolumeControl");
		control.setMute(isAllSoundsMute());
	}
	
}
