/*
 * MIDIPlayer.java 1.0
 * M�rcio Daniel da Rosa
 */

package com.sinosarts.zinemob.core;

import java.io.IOException;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

public class MIDIPlayer 
{
	private Player player;
	private SoundManager soundManager = SoundManager.getSingleton();
	
	public MIDIPlayer (String midiFileName)
	{
		try {
			player = Manager.createPlayer (getClass().getResourceAsStream(midiFileName),
				"audio/midi");
			player.addPlayerListener (soundManager);
			player.prefetch();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void close() {
		player.close();
	}

	public int getState() {
		return player.getState();
	}

	public void setLoopCount (int lc) {
		player.setLoopCount(lc);
	}

	public void start()
	{		
		try {
			player.start();
			if (soundManager.isMute()) {
				VolumeControl control = (VolumeControl)player.getControl("VolumeControl");
				control.setMute(true);
			}
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	public void stop()
	{
		try {
			player.stop();
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

}
