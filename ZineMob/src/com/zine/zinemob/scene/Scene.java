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
 * Gerencia o desenho e os eventos de uma cena. Executa em um laço que desenha
 * e os elementos atualiza o estado em uma frequência de 30 frames por segundo
 * (valor que pode ser alterado através do método setFrameRate).
 *
 * Ver o método addSceneController para adicionar elementos que devem ser
 * desenhados, atualizados ou avisados sobre eventos de entrada durante a execução
 * da cena.
 *
 * Para a rotina de desenho, o SceneModule renderiza um objeto padrão que representa
 * a tela (screen) O usuário pode adicionar filhos ao elemento para que eles sejam
 * desenhados na cena. Dica: se um objeto SceneController for adicionado à cena
 * (através do método addSceneController) e o elemento desenhável deste controlador
 * não tiver pai, então ele é automaticamente adicionado como filho do objeto padrão.
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
	 * Adiciona um controlador à cena.
	 *
	 * Se o controlador implementar a interface Updatable, então o seu método
	 * update é chamado a cada iteração do laço principal. Todos os objetos
	 * Updatable são atualizados antes das rotinas de desenho.
	 *
	 * Se o controlador implementar a interface KeyboardListener, então o objeto
	 * será avisado sobre eventos de entrada relacionados ao teclado do dispositivo.
	 * Os avisos destes eventos de entrada podem ocorrer a cada iteração do loop,
	 * antes da rotina de atualização dos objetos Updatable e antes da rotina de
	 * desenho.
	 *
	 * Se o controlador retornar um DrawableElement em getDrawableElement e este
	 * elemento não possuir um pai, então ele é automaticamente definido como filho
	 * do screenElement.
	 *
	 * A ordem em que os controladores tem seus elementos desenhados ou seus métodos
	 * chamados são de acordo com a ordenação em que os elementos foram adicionados
	 * através deste método. Ou seja, se o elemento A foi adicionado antes do elemento
	 * B, então a cada iteração o elemento A será desenhado antes do elemento B.
	 * Se forem implementaçoes da interface Updatable, por exemplo, o método
	 * update do elemento A também será chamado antes do método update do elemento
	 * B.
	 *
	 * @param controller o controlador a ser adicionado à cena
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
	 * Remove o elemento da cena, previamente adicionado através do método
	 * addSceneController. Não faz nada se o elemento nunca havia sido
	 * adicionado à cena.
	 *
	 * Se o elemento renderizável do controlador (método getDrawableElement) for
	 * filho direto de screenElement (ver método getScreenElement), então
	 * o elemento é removido da camada.
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
	 * Método de inicialização do módulo. Por padrão, não faz nada e pode ser
	 * livremente reimplementado.
	 */
	public synchronized void init() {
	}

	/**
	 * Finaliza a execução ao final do loop, se o módulo estiver executando através
	 * do método run. Se run não estiver executando mas for executado APÓS a
	 * chamada de finishExecution, então a execução iniciará normalmente.
	 */
	public void finishExecution() {
		end = true;
	}

	/**
	 * Inicia a execução do módulo. Executa em um laço que dura no mínimo o tempo
	 * definido pela quantidade de frames por segundo (padrão 30 frames, ver método
	 * setFrameRate), desenhando os elementos. O laço termina quando finishExecution
	 * é chamado.
	 *
	 * Assim que é iniciada a execução, este módulo irá tomar para si a tela do
	 * dispositivo. É importante que a aplicação esteja executando através de uma
	 * ZineMIDlet, caso contrário a cena não poderá aparecer na tela correspondente
	 * à MIDlet.
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
	 * Executa o loop de atualização e desenho da cena. Este método é chamado pelo
	 * método run após este realizar as inicializações necessárias, sendo responsável
	 * por atualizar o frame indefinidamente até que finishExecution seja chamado.
	 *
	 * A cada iteração do loop o método updateFrame é chamado, sendo o responsável
	 * pelas as atualizações e desenhos de cada iteração.
	 */
	protected void runLoop() {
		while(!end) {
			updateFrame();
		}
	}

	/**
	 * Método que é chamado consecutivamente durante a execução do loop de atualização
	 * e desenho da cena. É o método responsável por fazer esta atualização para
	 * cada iteração do loop.
	 *
	 * Este método executa os seguintes passos principais, em ordem:
	 *
	 * - verifica os eventos de entrada, chamando pelo método verifyInputEvents;
	 *
	 * - atualiza a lógica da cena, chamando pelo método updateScene;
	 *
	 * - desenha a cena, chamando pelo método drawScene.
	 *
	 * Após estes três passos principais, a execução e sincronizada de acordo com
	 * a taxa máxima de frames por segundo configurada e, em seguida, a tela é
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
	 * Método chamado a cada iteração do loop para verificar e manipular os eventos
	 * de entrada. Os controladores da cena (adicionados através do método
	 * addSceneController) que implementarem alguma interface que escuta
	 * por eventos de entrada são avisados sobre os eventos.
	 */
	protected void verifyInputEvents() {
		verifyInputQueueEvents();
		updateKeyStates();
	}

	/**
	 * Método chamado a cada iteração do loop para que os controladores da cena
	 * (adicionados através do método addSceneController) que implementarem
	 * a interface Updatable atualizem a sua lógica através dos seus métodos update.
	 */
	protected void updateScene() {
		for(int i=0; i<updatables.size(); i++) {
			Updateble updatable = (Updateble)updatables.elementAt(i);
			updatable.update();
		}
	}

	/**
	 * Método chamado a cada iteração do loop para desenhar a cena. É desenhado
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
	 * Retorna a quantidade de frames por segundo de atualização da tela.
	 * @return a quantidade de frames por segundo de atualização da tela
	 */
	public int getFrameRate() {
		return frameRate;
	}

	/**
	 * Define a quantidade de frames por segundo de atualização da tela. Se não
	 * for maior que 0, não é utilizada taxa de frames por segundo.
	 * @param frameRate a quantidade de frames por segundo de atualização da tela
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
	 * Canvas utilizado pelo SceneModule. Contém o contexto gráfico onde a cena
	 * é desenhada e recebe os eventos de entrada que populam a fila de eventos.
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
