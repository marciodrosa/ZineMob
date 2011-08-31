package com.zine.zinemob;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * Classe MIDlet que permite acesso � sua inst�ncia atrav�s do m�todo getInstance
 * e que executa a partir da implementa��o do m�todo run.
 */
public abstract class ZineMIDlet extends MIDlet implements Runnable {

	private static ZineMIDlet instance;

	/**
	 * Construtor. A execu��o � iniciada atrav�s de uma thread, que chama pelo
	 * m�todo run.
	 */
	public ZineMIDlet() {
		instance = this;

		// execu��o:
		new Thread(this).start();
	}

	/**
	 * Retorna a inst�ncia do ZineMIDlet iniciada. Pode ser null se a aplica��o
	 * n�o tiver iniciado atrav�s desta MIDlet.
	 * @return a inst�ncia do ZineMIDlet iniciada
	 */
	public static ZineMIDlet getMIDlet() {
		return instance;
	}

	protected void startApp() throws MIDletStateChangeException {
	}

	protected void pauseApp() {
	}

	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
	}

}
