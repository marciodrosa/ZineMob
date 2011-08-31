package com.sinosarts.zinemob.module;

import javax.microedition.lcdui.Graphics;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.core.SynchronizedFrame;

/**
 * Gerencia o fluxo da aplica��o / jogo. O objetivo desta classe � separar o
 * ciclo de vida da aplica��o em m�dulos (classe Module). Assim, enquanto executa
 * em um loop infinito (que � interrompido atrav�s da chamada de finish), o GameFlow
 * cria m�dulos atrav�s de chamadas de createModule, que deve ser implementado
 * para retornar uma inst�ncia de um objeto Module.
 *
 * Para saber que objeto Module deve ser criado pelo m�todo createModule, um identificador
 * � passado por par�metro. Este identificador � acessado atrav�s da classe FlowManager.
 * Depende da aplica��o definir no seu FlowManager o identificador de fluxo atual.
 *
 * Exemplos de m�dulos de um jogo:
 * <BR/> Tela inicial
 * <BR/> Menu principal
 * <BR/> Jogo
 * <BR/> Tela de pontua��es
 */
public abstract class GameFlow implements Runnable {

	private FlowManager flowManager;
	private boolean end = false;

	/**
	 * Construtor.
	 * @param flowManager a inst�ncia do FlowManager utilizado pela aplica��o,
	 * onde � definido o fluxo a ser seguido. Recomenda-se que seja implementado
	 * um objeto singleton.
	 */
	public GameFlow(FlowManager flowManager) {
		this.flowManager = flowManager;
	}

	/**
	 * Executa o fluxo. Os seguintes passos s�o executados enquanto o m�todo finish
	 * n�o for chamado:
	 * <BR/> - um m�dulo � criado atrav�s do m�todo createModule, utilizando o fluxo
	 * atual definido no objeto FlowManager;
	 * <BR/> - se o m�dulo possuir uma tela LoadingScreen, a mesma � desenhada
	 * continuamente, em uma Thread separada de baixa prioridade, em velocidade
	 * m�xima de 10 frames por segundo;
	 * <BR/> - o m�todo init do m�dulo � chamado;
	 * <BR/> - ao final do m�todo init, a execu��o da tela LoadingScreen � encerrada;
	 * <BR/> - o m�dulo � executado atrav�s do m�todo run.
	 */
	public void run() {

		end = false;
		
		while (!end) {
			System.gc();

			Module module = createModule(flowManager.getFlow());

			if (module == null)
				continue;

			LoadingScreen loadingScreen = module.createLoadingScreen();

			LoadingScreenThread loadingScreenThread = null;
			if (loadingScreen != null) {
				loadingScreenThread = new LoadingScreenThread(loadingScreen);
				loadingScreenThread.setPriority(Thread.MIN_PRIORITY);
				loadingScreenThread.start();
			}

			module.init();
			System.gc();

			if (loadingScreenThread != null) {
				loadingScreenThread.terminate();
				try {
					loadingScreenThread.join();
				} catch (InterruptedException ex) {
				}
			}

			module.run();
		}
	}

	/**
	 * Finaliza a execu��o do fluxo, fazendo com que o m�todo run finalize assim
	 * que o m�dulo atual chegar ao fim da sua execu��o. Caso o m�todo run n�o
	 * esteja sendo executado, esta chamada n�o tem efeito.
	 *
	 * � poss�vel voltar a executar o fluxo chamando novamente o m�todo run.
	 */
	public void finish() {
		end = true;
	}

	/**
	 * Cria um m�dulo de acordo com o identificador.
	 * @param flowId o identificador do m�dulo que deve ser instanciado
	 * @return o m�dulo criado, pode ser null
	 */
	protected abstract Module createModule(byte flowId);

	private static class LoadingScreenThread extends Thread {

		private SynchronizedFrame frame = new SynchronizedFrame(10);
		private Graphics graphics = ZineManager.getInstance().getGraphics();
		private LoadingScreen screen;
		private boolean end = false;

		public LoadingScreenThread(LoadingScreen screen) {
			this.screen = screen;
			frame.clearFrameAfterFlush(true);
		}

		public void run() {
			while (!end) {
				screen.draw(graphics);
				frame.update();
			}
		}

		public void terminate() {
			end = true;
		}

	}
}
