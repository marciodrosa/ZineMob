package com.sinosarts.zinemob.core;

/**
 * Classe singleton que facilita o gerenciamento de m�sicas no jogo.
 */
public class Jukebox
{
	private static Jukebox singleton = null;
	
	private String currentMusic = new String();
	private MIDIPlayer currentPlayer = null;
	
	private Jukebox()
	{}
	
	public synchronized static Jukebox getSingleton()
	{
		if (singleton==null) singleton = new Jukebox();
		return singleton;
	}
	
	/**
	 * Inicia a m�sica. Se outra estiver tocando no momento a mesma ser�
	 * encerrada. Se a m�sica indicada j� estiver tocando, o m�todo n�o far�
	 * nada. Por padr�o, a m�sica tocar� em loop eterno.
	 * @param musicName o nome do resource que cont�m a m�sica no formato MIDI.
	 */
	public void playMusic (String musicName) {
		playMusic (musicName, true);
	}
	
	/**
	 * Inicia a m�sica. Se outra estiver tocando no momento a mesma ser�
	 * encerrada. Se a m�sica indicada j� estiver tocando, o m�todo n�o far�
	 * nada.
	 * @param musicName o nome do resource que cont�m a m�sica no formato MIDI.
	 * @param loop flag que indica se a m�sica deve tocar em loop.
	 */
	public void playMusic (String musicName, boolean loop)
	{
		if (currentMusic!=musicName)
		{
			if (currentPlayer!=null) currentPlayer.close();
			currentMusic = musicName;
			
			currentPlayer = new MIDIPlayer (musicName);
			if (loop) currentPlayer.setLoopCount(-1);
			currentPlayer.start();
		}
	}
	
	/**
	 * P�ra qualquer m�sica que esteja tocando no Jukebox.
	 */
	public void stop()
	{
		if (currentPlayer!=null)
			currentPlayer.close();
		currentMusic = new String();
	}
}
