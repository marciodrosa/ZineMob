package com.zine.zinemob.audio;

import java.util.Vector;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

/**
 * Class to play and control sounds and music.
 */
public class AudioManager {
	
	private static AudioManager instance;
	private boolean mute;
	private String currentMidiMusicResourceName;
	private Player currentMidiMusicPlayer;
	private AudioPlayerListener audioPlayerListener = new AudioPlayerListener();
	private Vector currentPlayers = new Vector();
	
	public static AudioManager getInstance() {
		if (instance == null) {
			instance = new AudioManager();
		}
		return instance;
	}
	
	private AudioManager() {
	}
	
	/**
	 * Loads and starts the music.
	 * @param resourceName the resource name to load the music
	 * @param loop true to play in loop
	 * @param restart true to restart the music if the music is already playing now
	 */
	public void playMidiMusic(String resourceName, boolean loop, boolean restart) {
		
		if (currentMidiMusicResourceName != null && currentMidiMusicResourceName.equals(resourceName)) {
			if (restart) {
				currentMidiMusicResourceName = null;
				playMidiMusic(resourceName, loop, restart);
			}
		} else {
			
			if (currentMidiMusicPlayer != null) {
				try {
					currentMidiMusicPlayer.stop();
					currentMidiMusicPlayer.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			
			try {
				currentMidiMusicResourceName = resourceName;
				currentMidiMusicPlayer = playMidi(resourceName, loop);
			} catch (Exception ex) {
				currentMidiMusicResourceName = null;
				currentMidiMusicPlayer = null;
				ex.printStackTrace();
			}
		}
	}
	
	private Player playMidi(String resourceName, boolean loop) throws Exception {
		Player player = Manager.createPlayer(getClass().getResourceAsStream(resourceName), "audio/midi");
		player.addPlayerListener(audioPlayerListener);
		player.prefetch();
		if (loop) {
			player.setLoopCount(-1);
		}
		player.start();
		configPlayerVolume(player);
		return player;
	}
	
	private void configPlayerVolume(Player player) {
		VolumeControl control = (VolumeControl)player.getControl("VolumeControl");
		control.setMute(isMute());
	}
	
	private void configCurrentPlayersVolume() {
		for (int i=0; i<currentPlayers.size(); i++) {
			configPlayerVolume((Player)currentPlayers.elementAt(i));
		}
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
		configCurrentPlayersVolume();
	}
	
	public void stopAll() {
		while (!currentPlayers.isEmpty()) {
			try {
				Player player = (Player)currentPlayers.elementAt(0);
				player.stop();
				player.close();
			} catch (Exception ex) {
			}
		}
	}
	
	private class AudioPlayerListener implements PlayerListener {

		public void playerUpdate(Player player, String event, Object eventData) {
			if (event.equals(STARTED)) {
				currentPlayers.addElement(player);
			} else if (event.equals(STOPPED) || event.equals(END_OF_MEDIA) || event.equals(CLOSED)) {
				currentPlayers.removeElement(player);
				if (player == currentMidiMusicPlayer) {
					currentMidiMusicPlayer = null;
					currentMidiMusicResourceName = null;
				}
			}
		}
		
	}
	
}
