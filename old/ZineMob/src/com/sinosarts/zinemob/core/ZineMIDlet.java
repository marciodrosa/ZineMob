package com.sinosarts.zinemob.core;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * Classe MIDlet preparada para execução de um jogo construído utilizando LUMEngine.
 * O método run é chamado em uma thread separada logo no construtor.
 */
public abstract class ZineMIDlet extends MIDlet implements Runnable {

	private boolean mute = false;

	private static ZineMIDlet instance;

	/**
	 * Construtor. Inicializa o display, coloca em modo fullscreen e limpa a tela.
	 * A execução é iniciada através de uma thread.
	 */
	public ZineMIDlet() {
		instance = this;

		ZineManager manager = ZineManager.getInstance();

		// inicia o display:
		Display.getDisplay(this).setCurrent(manager);
		manager.setFullScreenMode(true);
		manager.clearScreen();

		// execução do jogo:
		new Thread(this).start();
	}

	public static ZineMIDlet getMIDlet() {
		return instance;
	}

	/**
	 * Retorna à execução. Se o som foi desligado ao entrar em pausa, o mesmo é
	 * retomado.
	 */
	protected void startApp() throws MIDletStateChangeException {
		SoundManager.getSingleton().setMute(mute);
	}

	/**
	 * Pausa na execução, desligando o som, que é retomado na chamada de startApp.
	 */
	protected void pauseApp() {
		mute = SoundManager.getSingleton().isMute();
		SoundManager.getSingleton().setMute(true);
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
	}

}
