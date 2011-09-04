package com.zine.zinemob.scene;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.Updateble;
import java.util.Vector;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * Gerencia o desenho e os eventos de uma cena. Executa em um la�o que desenha
 * e os elementos atualiza o estado em uma frequ�ncia de 30 frames por segundo
 * (valor que pode ser alterado atrav�s do m�todo setFrameRate).
 *
 * Ver o m�todo addSceneController para adicionar elementos que devem ser
 * desenhados, atualizados ou avisados sobre eventos de entrada durante a execu��o
 * da cena.
 *
 * Para a rotina de desenho, o SceneModule renderiza um objeto padr�o que representa
 * a tela (screen) O usu�rio pode adicionar filhos ao elemento para que eles sejam
 * desenhados na cena. Dica: se um objeto SceneController for adicionado � cena
 * (atrav�s do m�todo addSceneController) e o elemento desenh�vel deste controlador
 * n�o tiver pai, ent�o ele � automaticamente adicionado como filho do objeto padr�o.
 */
public class Scene {

	private SceneModuleCanvas canvas = new SceneModuleCanvas();
	private int clearColor = 0;
	private DrawableElement screenElement = new DrawableElement();

	private Vector keyboardListeners = new Vector(); // <KeyboardListener>
	private Vector updatables = new Vector(); // <Updatable>

	private int frameRate = 30;
	private long lastFrameTime = 0;
	private boolean end = false;

	private Vector inputEventsQueue = new Vector(); // <KeyboardInputEvent>

	/**
	 * Adiciona um controlador � cena.
	 *
	 * Se o controlador implementar a interface Updatable, ent�o o seu m�todo
	 * update � chamado a cada itera��o do la�o principal. Todos os objetos
	 * Updatable s�o atualizados antes das rotinas de desenho.
	 *
	 * Se o controlador implementar a interface KeyboardListener, ent�o o objeto
	 * ser� avisado sobre eventos de entrada relacionados ao teclado do dispositivo.
	 * Os avisos destes eventos de entrada podem ocorrer a cada itera��o do loop,
	 * antes da rotina de atualiza��o dos objetos Updatable e antes da rotina de
	 * desenho.
	 *
	 * Se o controlador retornar um DrawableElement em getDrawableElement e este
	 * elemento n�o possuir um pai, ent�o ele � automaticamente definido como filho
	 * do screenElement.
	 *
	 * A ordem em que os controladores tem seus elementos desenhados ou seus m�todos
	 * chamados s�o de acordo com a ordena��o em que os elementos foram adicionados
	 * atrav�s deste m�todo. Ou seja, se o elemento A foi adicionado antes do elemento
	 * B, ent�o a cada itera��o o elemento A ser� desenhado antes do elemento B.
	 * Se forem implementa�oes da interface Updatable, por exemplo, o m�todo
	 * update do elemento A tamb�m ser� chamado antes do m�todo update do elemento
	 * B.
	 *
	 * @param controller o controlador a ser adicionado � cena
	 */
	public void addController(Controller controller) {

		if(controller instanceof Updateble) {
			updatables.addElement(controller);
		}

		if(controller instanceof KeyboardListener) {
			keyboardListeners.addElement(controller);
		}

		DrawableElement drawableElement = controller.getDrawableElement();
		if (drawableElement != null && drawableElement.getParent() == null) {
			screenElement.addChild(drawableElement);
		}
	}

	/**
	 * Remove o elemento da cena, previamente adicionado atrav�s do m�todo
	 * addSceneController. N�o faz nada se o elemento nunca havia sido
	 * adicionado � cena.
	 *
	 * Se o elemento renderiz�vel do controlador (m�todo getDrawableElement) for
	 * filho direto de screenElement (ver m�todo getScreenElement), ent�o
	 * o elemento � removido da camada.
	 * 
	 * @param controller o elemento a ser removido
	 */
	public void removeController(Controller controller) {
		updatables.removeElement(controller);
		keyboardListeners.removeElement(controller);

		DrawableElement drawableElement = controller.getDrawableElement();
		if (drawableElement != null && drawableElement.getParent() == screenElement) {
			screenElement.removeChild(drawableElement);
		}
	}

	/**
	 * Retorna o elemento utilizado como tela da cena.
	 * @return o elemento utilizado como tela da cena
	 */
	public DrawableElement getScreenElement() {
		return screenElement;
	}

	/**
	 * M�todo de inicializa��o do m�dulo. Por padr�o, n�o faz nada e pode ser
	 * livremente reimplementado.
	 */
	public synchronized void init() {
	}

	/**
	 * Finaliza a execu��o ao final do loop, se o m�dulo estiver executando atrav�s
	 * do m�todo run. Se run n�o estiver executando mas for executado AP�S a
	 * chamada de finishExecution, ent�o a execu��o iniciar� normalmente.
	 */
	public void finishExecution() {
		end = true;
	}

	/**
	 * Inicia a execu��o do m�dulo. Executa em um la�o que dura no m�nimo o tempo
	 * definido pela quantidade de frames por segundo (padr�o 30 frames, ver m�todo
	 * setFrameRate), desenhando os elementos. O la�o termina quando finishExecution
	 * � chamado.
	 *
	 * Assim que � iniciada a execu��o, este m�dulo ir� tomar para si a tela do
	 * dispositivo. � importante que a aplica��o esteja executando atrav�s de uma
	 * ZineMIDlet, caso contr�rio a cena n�o poder� aparecer na tela correspondente
	 * � MIDlet.
	 *
	 */
	public void run() {

		initDisplay();

		end = false;

		runLoop();
	}

	private void initDisplay() {
		ZineMIDlet currentMIDlet = ZineMIDlet.getMIDlet();
		if(currentMIDlet != null) {
			Display.getDisplay(currentMIDlet).setCurrent(canvas);
		}
	}

	/**
	 * Executa o loop de atualiza��o e desenho da cena. Este m�todo � chamado pelo
	 * m�todo run ap�s este realizar as inicializa��es necess�rias, sendo respons�vel
	 * por atualizar o frame indefinidamente at� que finishExecution seja chamado.
	 *
	 * A cada itera��o do loop o m�todo updateFrame � chamado, sendo o respons�vel
	 * pelas as atualiza��es e desenhos de cada itera��o.
	 */
	protected void runLoop() {
		while(!end) {
			updateFrame();
		}
	}

	/**
	 * M�todo que � chamado consecutivamente durante a execu��o do loop de atualiza��o
	 * e desenho da cena. � o m�todo respons�vel por fazer esta atualiza��o para
	 * cada itera��o do loop.
	 *
	 * Este m�todo executa os seguintes passos principais, em ordem:
	 *
	 * - verifica os eventos de entrada, chamando pelo m�todo verifyInputEvents;
	 *
	 * - atualiza a l�gica da cena, chamando pelo m�todo updateScene;
	 *
	 * - desenha a cena, chamando pelo m�todo drawScene.
	 *
	 * Ap�s estes tr�s passos principais, a execu��o e sincronizada de acordo com
	 * a taxa m�xima de frames por segundo configurada e, em seguida, a tela �
	 * atualizada, exibindo a cena desenhada.
	 */
	protected void updateFrame() {
		verifyInputEvents();
		updateScene();
		drawScene();
		verifyFrameRate();
		flushScreen();
	}

	/**
	 * M�todo chamado a cada itera��o do loop para verificar e manipular os eventos
	 * de entrada. Os controladores da cena (adicionados atrav�s do m�todo
	 * addSceneController) que implementarem alguma interface que escuta
	 * por eventos de entrada s�o avisados sobre os eventos.
	 */
	protected void verifyInputEvents() {
		verifyInputQueueEvents();
		updateKeyStates();
	}

	/**
	 * M�todo chamado a cada itera��o do loop para que os controladores da cena
	 * (adicionados atrav�s do m�todo addSceneController) que implementarem
	 * a interface Updatable atualizem a sua l�gica atrav�s dos seus m�todos update.
	 */
	protected void updateScene() {
		for(int i=0; i<updatables.size(); i++) {
			Updateble updatable = (Updateble)updatables.elementAt(i);
			updatable.update();
		}
	}

	/**
	 * M�todo chamado a cada itera��o do loop para desenhar a cena. � desenhado
	 * na tela as camadas da cena e todos os seus filhos.
	 */
	protected void drawScene() {
		prepareGraphics();
		clearFrame();
		screenElement.draw(canvas.getGraphics());
	}

	private void clearFrame() {
		Graphics graphics = canvas.getGraphics();
		graphics.setColor(clearColor);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	private void prepareGraphics() {
		Graphics graphics = canvas.getGraphics();
		graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
	}

	private void verifyFrameRate() {

		if(frameRate > 0) {
			long frameTime = System.currentTimeMillis() - lastFrameTime;
			long minFrameTime = 1000/frameRate;

			if(frameTime < minFrameTime) {
				try {
					Thread.sleep(minFrameTime - frameTime);
				} catch (InterruptedException ex) {
				}
			}
			lastFrameTime = System.currentTimeMillis();
		}

	}

	private void flushScreen() {
		canvas.flushGraphics();
	}

	private void verifyInputQueueEvents() {
		while (!inputEventsQueue.isEmpty()) {

			KeyboardInputEvent event = (KeyboardInputEvent)inputEventsQueue.firstElement();
			int gameAction = canvas.getGameAction(event.keyCode);

			for(int i=0; i<keyboardListeners.size(); i++) {
				if (event.eventType == KeyboardInputEvent.KEY_PRESSED) {
					((KeyboardListener)keyboardListeners.elementAt(i)).onKeyPressed(event.keyCode, gameAction);
				}
				else if(event.eventType == KeyboardInputEvent.KEY_RELEASED) {
					((KeyboardListener)keyboardListeners.elementAt(i)).onKeyReleased(event.keyCode, gameAction);
				}
				else if (event.eventType == KeyboardInputEvent.KEY_REPEATED) {
					((KeyboardListener)keyboardListeners.elementAt(i)).onKeyRepeated(event.keyCode, gameAction);
				}
			}

			inputEventsQueue.removeElementAt(0);
		}
	}

	private void updateKeyStates() {
		int keyStates = canvas.getKeyStates();
		if (keyStates != 0) {
			for(int i=0; i<keyboardListeners.size(); i++) {
				((KeyboardListener)keyboardListeners.elementAt(i)).updateKeyStates(keyStates);
			}
		}
	}

	/**
	 * Retorna a quantidade de frames por segundo de atualiza��o da tela.
	 * @return a quantidade de frames por segundo de atualiza��o da tela
	 */
	public int getFrameRate() {
		return frameRate;
	}

	/**
	 * Define a quantidade de frames por segundo de atualiza��o da tela. Se n�o
	 * for maior que 0, n�o � utilizada taxa de frames por segundo.
	 * @param frameRate a quantidade de frames por segundo de atualiza��o da tela
	 */
	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}

	/**
	 * Retorna a cor utilizada para limpar a tela antes do desenho da cena.
	 * @return a cor para limpar a tela, no formato 0x00RRGGBB
	 */
	public int getClearColor() {
		return clearColor;
	}

	/**
	 * Define a cor utilizada para limpar a tela antes do desenho da cena.
	 * @param clearColor a cor para limpar a tela, no formato 0x00RRGGBB
	 */
	public void setClearColor(int clearColor) {
		this.clearColor = clearColor;
	}

	/**
	 * Estrutura para os eventos de entrada, recebidos pelo SceneModuleCanvas e
	 * armazenados na fila de eventos.
	 */
	class KeyboardInputEvent {
		static final byte KEY_RELEASED = 1;
		static final byte KEY_PRESSED = 2;
		static final byte KEY_REPEATED = 3;
		byte eventType;
		int keyCode;
	}

	/**
	 * Canvas utilizado pelo SceneModule. Cont�m o contexto gr�fico onde a cena
	 * � desenhada e recebe os eventos de entrada que populam a fila de eventos.
	 */
	class SceneModuleCanvas extends GameCanvas {

		private Graphics graphics;

		public SceneModuleCanvas() {
			super(false);
			setFullScreenMode(true);
		}

		public Graphics getGraphics() {
			if (graphics == null) {
				graphics = super.getGraphics();
			}
			return graphics;
		}

		protected void showNotify() {
			screenElement.setSize(getWidth(), getHeight());
		}

		protected void sizeChanged(int w, int h) {
			screenElement.setSize(w, h);
		}

		protected void keyPressed(int keyCode) {
			KeyboardInputEvent event = new KeyboardInputEvent();
			event.eventType = KeyboardInputEvent.KEY_PRESSED;
			event.keyCode = keyCode;
			inputEventsQueue.addElement(event);
		}

		protected void keyRepeated(int keyCode) {
			KeyboardInputEvent event = new KeyboardInputEvent();
			event.eventType = KeyboardInputEvent.KEY_REPEATED;
			event.keyCode = keyCode;
			inputEventsQueue.addElement(event);
		}

		protected void keyReleased(int keyCode) {
			KeyboardInputEvent event = new KeyboardInputEvent();
			event.eventType = KeyboardInputEvent.KEY_RELEASED;
			event.keyCode = keyCode;
			inputEventsQueue.addElement(event);
		}
	}
	
}
