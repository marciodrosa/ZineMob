package com.zine.zinemob.scene.controller;

/**
 * Interface para objetos que escutam por eventos de entrada utilizando o teclado
 * do dispositivo.
 */
public interface KeyboardListener {

	/**
	 * Método chamado quando uma tecla é pressionada.
	 * @param keyCode o código da tecla pressionada
	 * @param gameAction a ação vinculada a esta tecla
	 */
	public void onKeyPressed(int keyCode, int gameAction);

	/**
	 * Método chamado quando uma tecla é pressionada e assim é mantida. Este método
	 * é chamado de tempos em tempos enquanto a tecla é mantida pressionada, mas
	 * não em toda a iteração do loop. É levado em consideração um certo tempo de
	 * repetição.
	 * @param keyCode o código da tecla mantida pressionada
	 * @param gameAction a ação vinculada a esta tecla
	 */
	public void onKeyRepeated(int keyCode, int gameAction);

	/**
	 * Método chamado quando uma tecla é solta.
	 * @param keyCode o código da tecla solta
	 * @param gameAction a ação vinculada a esta tecla
	 */
	public void onKeyReleased(int keyCode, int gameAction);

	/**
	 * Método chamado para notificar sobre o estado das teclas pressionadas. É
	 * chamado sempre após as notificações de eventos (onKeyPressed, onKeyRepeated
	 * ou onKeyReleased).
	 *
	 * O método não é chamado quando não há nenhuma tecla pressionada.
	 *
	 * @param keyStates valor onde estão armazenadas as teclas pressionadas (game
	 * actions). Este valor contém o mesmo formato que o retornado pelo método
	 * getKeyStates da classe GameCanvas.
	 */
	public void updateKeyStates(int keyStates);

}
