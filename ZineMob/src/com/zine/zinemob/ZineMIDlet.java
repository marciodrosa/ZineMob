package com.zine.zinemob;

import com.zine.zinemob.audio.AudioManager;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * Classe MIDlet que permite acesso à sua instância através do método getInstance
 * e que executa a partir da implementação do método run.
 */
public abstract class ZineMIDlet extends MIDlet implements Runnable {

	private static ZineMIDlet instance;
	
	private boolean wasMuteBeforePause = false;

	/**
	 * Construtor. A execução é iniciada através de uma thread, que chama pelo
	 * método run.
	 */
	public ZineMIDlet() {
		instance = this;

		// execução:
		new Thread(this).start();
	}

	/**
	 * Retorna a instância do ZineMIDlet iniciada. Pode ser null se a aplicação
	 * não tiver iniciado através desta MIDlet.
	 * @return a instância do ZineMIDlet iniciada
	 */
	public static ZineMIDlet getMIDlet() {
		return instance;
	}

	protected void startApp() throws MIDletStateChangeException {
		if (!wasMuteBeforePause) {
			AudioManager.getInstance().setMute(false);
		}
	}

	protected void pauseApp() {
		wasMuteBeforePause = AudioManager.getInstance().isMute();
		AudioManager.getInstance().setMute(true);
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
		AudioManager.getInstance().stopAll();
	}

}
