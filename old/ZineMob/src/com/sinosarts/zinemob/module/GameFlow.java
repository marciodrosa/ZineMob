package com.sinosarts.zinemob.module;

import javax.microedition.lcdui.Graphics;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.core.SynchronizedFrame;

/**
 * Gerencia o fluxo da aplicação / jogo. O objetivo desta classe é separar o
 * ciclo de vida da aplicação em módulos (classe Module). Assim, enquanto executa
 * em um loop infinito (que é interrompido através da chamada de finish), o GameFlow
 * cria módulos através de chamadas de createModule, que deve ser implementado
 * para retornar uma instância de um objeto Module.
 *
 * Para saber que objeto Module deve ser criado pelo método createModule, um identificador
 * é passado por parâmetro. Este identificador é acessado através da classe FlowManager.
 * Depende da aplicação definir no seu FlowManager o identificador de fluxo atual.
 *
 * Exemplos de módulos de um jogo:
 * <BR/> Tela inicial
 * <BR/> Menu principal
 * <BR/> Jogo
 * <BR/> Tela de pontuações
 */
public abstract class GameFlow implements Runnable {

	private FlowManager flowManager;
	private boolean end = false;

	/**
	 * Construtor.
	 * @param flowManager a instãncia do FlowManager utilizado pela aplicação,
	 * onde é definido o fluxo a ser seguido. Recomenda-se que seja implementado
	 * um objeto singleton.
	 */
	public GameFlow(FlowManager flowManager) {
		this.flowManager = flowManager;
	}

	/**
	 * Executa o fluxo. Os seguintes passos são executados enquanto o método finish
	 * não for chamado:
	 * <BR/> - um módulo é criado através do método createModule, utilizando o fluxo
	 * atual definido no objeto FlowManager;
	 * <BR/> - se o módulo possuir uma tela LoadingScreen, a mesma é desenhada
	 * continuamente, em uma Thread separada de baixa prioridade, em velocidade
	 * máxima de 10 frames por segundo;
	 * <BR/> - o método init do módulo é chamado;
	 * <BR/> - ao final do método init, a execução da tela LoadingScreen é encerrada;
	 * <BR/> - o módulo é executado através do método run.
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
	 * Finaliza a execução do fluxo, fazendo com que o método run finalize assim
	 * que o módulo atual chegar ao fim da sua execução. Caso o método run não
	 * esteja sendo executado, esta chamada não tem efeito.
	 *
	 * É possível voltar a executar o fluxo chamando novamente o método run.
	 */
	public void finish() {
		end = true;
	}

	/**
	 * Cria um módulo de acordo com o identificador.
	 * @param flowId o identificador do módulo que deve ser instanciado
	 * @return o módulo criado, pode ser null
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
