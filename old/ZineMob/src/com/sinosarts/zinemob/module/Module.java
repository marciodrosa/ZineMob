package com.sinosarts.zinemob.module;

/**
 * Module é um objeto que é iniciado e executado pelo objeto GameFlow. O GameFlow
 * também pode exibir uma tela enquanto o Module é inicializado.
 */
public abstract class Module implements Runnable {

	/**
	 * Cria um objeto LoadingScreen para ser desenhado na tela enquanto o Module
	 * é inicializado (ou seja, enquanto o método init estiver sendo executado).
	 * Por padrão, retorna null, o que significa que nenhuma tela será utilizada.
	 * Deve ser reimplementado para retornar uma nova instância de uma LoadingScreen.
	 * @return a tela a ser desenhada pelo GameFlow enquanto o método init executa
	 */
	public LoadingScreen createLoadingScreen() {
		return null;
	}

	/**
	 * Método de inicialização do objeto. Preferencialmente, todas as inicializações
	 * devem ser escritas neste método ao invés de no construtor ou no método run.
	 * Se uma tela for criada através da reimplementação do método createLoadingScreen,
	 * esta tela será exibida enquanto este método é executado.
	 */
	public abstract void init();

}
