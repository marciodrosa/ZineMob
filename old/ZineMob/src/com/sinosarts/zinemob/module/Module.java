package com.sinosarts.zinemob.module;

/**
 * Module � um objeto que � iniciado e executado pelo objeto GameFlow. O GameFlow
 * tamb�m pode exibir uma tela enquanto o Module � inicializado.
 */
public abstract class Module implements Runnable {

	/**
	 * Cria um objeto LoadingScreen para ser desenhado na tela enquanto o Module
	 * � inicializado (ou seja, enquanto o m�todo init estiver sendo executado).
	 * Por padr�o, retorna null, o que significa que nenhuma tela ser� utilizada.
	 * Deve ser reimplementado para retornar uma nova inst�ncia de uma LoadingScreen.
	 * @return a tela a ser desenhada pelo GameFlow enquanto o m�todo init executa
	 */
	public LoadingScreen createLoadingScreen() {
		return null;
	}

	/**
	 * M�todo de inicializa��o do objeto. Preferencialmente, todas as inicializa��es
	 * devem ser escritas neste m�todo ao inv�s de no construtor ou no m�todo run.
	 * Se uma tela for criada atrav�s da reimplementa��o do m�todo createLoadingScreen,
	 * esta tela ser� exibida enquanto este m�todo � executado.
	 */
	public abstract void init();

}
