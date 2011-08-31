package com.zine.zinemob.scene.controller;

/**
 * Interface para objetos que escutam por eventos de entrada utilizando o teclado
 * do dispositivo.
 */
public interface KeyboardListener {

	/**
	 * M�todo chamado quando uma tecla � pressionada.
	 * @param keyCode o c�digo da tecla pressionada
	 * @param gameAction a a��o vinculada a esta tecla
	 */
	public void onKeyPressed(int keyCode, int gameAction);

	/**
	 * M�todo chamado quando uma tecla � pressionada e assim � mantida. Este m�todo
	 * � chamado de tempos em tempos enquanto a tecla � mantida pressionada, mas
	 * n�o em toda a itera��o do loop. � levado em considera��o um certo tempo de
	 * repeti��o.
	 * @param keyCode o c�digo da tecla mantida pressionada
	 * @param gameAction a a��o vinculada a esta tecla
	 */
	public void onKeyRepeated(int keyCode, int gameAction);

	/**
	 * M�todo chamado quando uma tecla � solta.
	 * @param keyCode o c�digo da tecla solta
	 * @param gameAction a a��o vinculada a esta tecla
	 */
	public void onKeyReleased(int keyCode, int gameAction);

	/**
	 * M�todo chamado para notificar sobre o estado das teclas pressionadas. �
	 * chamado sempre ap�s as notifica��es de eventos (onKeyPressed, onKeyRepeated
	 * ou onKeyReleased).
	 *
	 * O m�todo n�o � chamado quando n�o h� nenhuma tecla pressionada.
	 *
	 * @param keyStates valor onde est�o armazenadas as teclas pressionadas (game
	 * actions). Este valor cont�m o mesmo formato que o retornado pelo m�todo
	 * getKeyStates da classe GameCanvas.
	 */
	public void updateKeyStates(int keyStates);

}
