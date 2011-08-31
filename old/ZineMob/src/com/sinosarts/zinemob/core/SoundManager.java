package com.sinosarts.zinemob.core;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

public class SoundManager implements PlayerListener
{
	private boolean mute = false;
	private Hashtable currentSounds = new Hashtable();
	private static SoundManager singleton;
	
	public static SoundManager getSingleton() {
		if (singleton==null) singleton = new SoundManager();
		return singleton;
	}
	
	private SoundManager()
	{}

	public void playerUpdate (Player player, String event, Object eventData)
	{
		if (event.equals(STARTED))
			currentSounds.put (player, player);
			
		else if (event.equals(STOPPED) || event.equals(END_OF_MEDIA)
				|| event.equals(CLOSED))
			currentSounds.remove (player);
	}
	
	public void setMute (boolean m)
	{
		mute = m;
		Player player;
		Enumeration sounds=currentSounds.elements();
		while (sounds.hasMoreElements()) {
			player = (Player)sounds.nextElement();
			VolumeControl control = (VolumeControl)player.getControl("VolumeControl");
			control.setMute(m);
		}
	}
	
	public boolean isMute() {
		return mute;
	}

}
