package com.zine.zinemob.scene;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.PointerListener;
import com.zine.zinemob.scene.controller.ScreenListener;
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

	private static SceneModuleCanvas canvas = new SceneModuleCanvas();
	
	private int clearColor = 0;
	private DrawableElement screenElement = new DrawableElement();

	private Vector keyboardListeners = new Vector(); // <KeyboardListener>
	private Vector pointerListeners = new Vector(); // <PointerListener>
	private Vector updatables = new Vector(); // <Updatable>
	private Vector screenListeners = new Vector(); // <ScreenListener>

	private int frameRate = 30;
	private long lastFrameTime = 0;
	private boolean end = false;
	private boolean runningLoop = false;

	private Vector inputEventsQueue = new Vector(); // <KeyboardInputEvent>
	
	private Vector pendingExecutions = new Vector(); // <Runnable>

	private String debugText = null;
	
	/**
	 * Returns the text to be painted in the screen to debug. Can be null.
	 */
	public String getDebugText() {
		return debugText;
	}
	
	/**
	 * Sets a text to be painted in the screen to debug. Can be null.
	 */
	public void setDebugText(String debugText) {
		this.debugText = debugText;
	}
	
	/**
	 * Returns the max FPS setted to the Scene.
	 */
	public int getFrameRate() {
		return frameRate;
	}

	/**
	 * Sets the max FPS. If 0 or less, the frame rate is ignored. By default,
	 * the FPS is 30.
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
	 * Add the controller to the Scene. 
	 * 
	 * If the controller implements Updatable or some input listener, it will
	 * be automatically added to the Scene too.
	 */
	public void addController(Controller controller) {
		
		controller.setScene(this);
		controller.init();

		if (controller instanceof Updateble) {
			updatables.addElement(controller);
		}

		if (controller instanceof KeyboardListener) {
			keyboardListeners.addElement(controller);
		}
		
		if (controller instanceof PointerListener) {
			pointerListeners.addElement(controller);
		}

		if (controller instanceof ScreenListener) {
			screenListeners.addElement(controller);
		}
	}

	/**
	 * Removes the controller from the scene. If the scene is running, the controller
	 * is only removed at the end of the current iteration of the loop.
	 */
	public void removeController(final Controller controller) {
		if (runningLoop) {
			callAfter(new Runnable() {
				public void run() {
					removeControllerNow(controller);
				}
			});
		} else {
			removeControllerNow(controller);
		}
	}
	
	private void removeControllerNow(Controller controller) {
		updatables.removeElement(controller);
		keyboardListeners.removeElement(controller);
		pointerListeners.removeElement(controller);
		screenListeners.removeElement(controller);
		controller.onFinish();
		controller.setScene(null);
	}

	/**
	 * Finishes the execution of the Scene. If the Scene is running, then it finishes
	 * when the current iteration of the main loop comes to end.
	 */
	public void finishExecution() {
		end = true;
	}

	/**
	 * Returns the DrawableElement of the Scene that represents the screen (it is
	 * the root element of the scene and it has the size of the display).
	 */
	public DrawableElement getScreenElement() {
		return screenElement;
	}
	
	/**
	 * Sets the screenElement. By default, the scene automatically creates a
	 * screenElement.
	 */
	public void setScreenElement(DrawableElement screenElement) {
		this.screenElement = screenElement;
	}

	/**
	 * Calls the run method of the Runnable object at the end of the current iteration
	 * of the main loop.
	 */
	public void callAfter(Runnable runnable) {
		pendingExecutions.addElement(runnable);
	}
	
	/**
	 * Method that runs other Scene. When the other Scene execution ends, it continues
	 * this Scene execution.
	 */
	public void runOtherScene(Scene scene) {
		scene.run();
		checkDisplay();
	}
	
	/**
	 * Returns the GameCanvas object used by the Scene.
	 */
	public GameCanvas getGameCanvas() {
		return canvas;
	}
	
	/**
	 * Method called before start the loop. By default, it does nothing.
	 */
	public void init() {
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
		try {
			checkDisplay();

			end = false;

			init();

			runLoop();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * This method is called on run method to own the display to the Scene and
	 * render it. It can be called when another Scene (let's call Scene 2) is called
	 * in the middle of execution of a Scene (let's call Scene 1). When the execution
	 * of Scene 2 comes to end, the Scenes 1 back to execution, but it will only
	 * be visible if checkDisplay is called.
	 * 
	 * Or you can call the runOtherScene method, that does the same thing.
	 */
	public void checkDisplay() {
		canvas.setScene(this);
		ZineMIDlet currentMIDlet = ZineMIDlet.getMIDlet();
		if(currentMIDlet != null) {
			Display display = Display.getDisplay(currentMIDlet);
			if (display.getCurrent() != canvas) {
				Display.getDisplay(currentMIDlet).setCurrent(canvas);
				clearFrame();
				flushScreen();
			}
			screenElement.setSize(canvas.getWidth(), canvas.getHeight());
			callOnScreenUpdated();
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
		runningLoop = true;
		while(!end) {
			updateFrame();
		}
		runningLoop = false;
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
		drawDebugText();
		callPendingExecutions();
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
		updatePointerState();
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
		clearFrame();
		screenElement.draw(canvas.getGraphics());
	}
	
	private void drawDebugText() {
		if (debugText != null) {
			Graphics graphics = canvas.getGraphics();
			graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
			graphics.setFont(null);
			graphics.setColor(0xff00bb00);
			graphics.drawString(debugText, 0, 0, Graphics.LEFT | Graphics.TOP);
		}
	}

	private void clearFrame() {
		Graphics graphics = canvas.getGraphics();
		graphics.translate(-graphics.getTranslateX(), -graphics.getTranslateY());
		graphics.setColor(clearColor);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	private void callPendingExecutions() {
		while (!pendingExecutions.isEmpty()) {
			((Runnable)pendingExecutions.firstElement()).run();
			pendingExecutions.removeElementAt(0);
		}
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

	private synchronized void verifyInputQueueEvents() {
		for (int i=0; i<inputEventsQueue.size(); i++) {
			
			Object inputEventObject = inputEventsQueue.elementAt(i);

			if (inputEventObject instanceof KeyboardInputEvent) {
				
				KeyboardInputEvent keyboardInputEvent = (KeyboardInputEvent) inputEventObject;
				
				int gameAction = canvas.getGameAction(keyboardInputEvent.keyCode);

				for (int j=0; j<keyboardListeners.size(); j++) {
					if (keyboardInputEvent.eventType == KeyboardInputEvent.KEY_PRESSED) {
						((KeyboardListener)keyboardListeners.elementAt(j)).onKeyPressed(keyboardInputEvent.keyCode, gameAction);
					} else if(keyboardInputEvent.eventType == KeyboardInputEvent.KEY_RELEASED) {
						((KeyboardListener)keyboardListeners.elementAt(j)).onKeyReleased(keyboardInputEvent.keyCode, gameAction);
					} else if (keyboardInputEvent.eventType == KeyboardInputEvent.KEY_REPEATED) {
						((KeyboardListener)keyboardListeners.elementAt(j)).onKeyRepeated(keyboardInputEvent.keyCode, gameAction);
					}
				}
			} else if (inputEventObject instanceof PointerInputEvent) {
				
				PointerInputEvent pointerInputEvent = (PointerInputEvent) inputEventObject;
				
				for (int j=0; j<pointerListeners.size(); j++) {
					if (pointerInputEvent.eventType == PointerInputEvent.POINTER_PRESSED) {
						((PointerListener)pointerListeners.elementAt(j)).onPointerPressed(pointerInputEvent.x, pointerInputEvent.y);
					} else if(pointerInputEvent.eventType == PointerInputEvent.POINTER_RELEASED) {
						((PointerListener)pointerListeners.elementAt(j)).onPointerReleased(pointerInputEvent.x, pointerInputEvent.y);
					}
				}
			}
		}
		
		inputEventsQueue = new Vector();
	}

	private void updateKeyStates() {
		int keyStates = canvas.getKeyStates();
		if (keyStates != 0) {
			for(int i=0; i<keyboardListeners.size(); i++) {
				((KeyboardListener)keyboardListeners.elementAt(i)).updateKeyStates(keyStates);
			}
		}
	}
	
	private void updatePointerState() {
		if (canvas.pointerX != -1 && canvas.pointerY != -1) {
			for(int i=0; i<pointerListeners.size(); i++) {
				((PointerListener)pointerListeners.elementAt(i)).updatePointerState(canvas.pointerX, canvas.pointerY);
			}
		}
	}
	
	private void callOnScreenUpdated() {
		callAfter(new Runnable() {
			public void run() {
				for (int i=0; i<screenListeners.size(); i++) {
					((ScreenListener)screenListeners.elementAt(i)).onScreenUpdated(screenElement);
				}
			}
		});
	}
	
	private synchronized void addInputEvent(Object eventObject) {
		inputEventsQueue.addElement(eventObject);
	}

	/**
	 * Estrutura para os eventos de entrada, recebidos pelo SceneModuleCanvas e
	 * armazenados na fila de eventos.
	 */
	static final class KeyboardInputEvent {
		static final byte KEY_RELEASED = 1;
		static final byte KEY_PRESSED = 2;
		static final byte KEY_REPEATED = 3;
		byte eventType;
		int keyCode;
	}
	
	static final class PointerInputEvent {
		static final byte POINTER_PRESSED = 1;
		static final byte POINTER_RELEASED = 2;
		byte eventType;
		int x, y;
	}

	/**
	 * Canvas utilizado pelo SceneModule. Contém o contexto gráfico onde a cena
	 * é desenhada e recebe os eventos de entrada que populam a fila de eventos.
	 */
	static final class SceneModuleCanvas extends GameCanvas {

		private Graphics graphics;
		private int pointerX = -1, pointerY = -1;
		private Scene scene;
		
		SceneModuleCanvas() {
			super(false);
			setFullScreenMode(true);
		}
		
		void setScene(Scene scene) {
			this.scene = scene;
		}

		protected void showNotify() {
			pointerX = -1;
			pointerY = -1;
		}

		protected void hideNotify() {
			pointerX = -1;
			pointerY = -1;
		}

		public Graphics getGraphics() {
			if (graphics == null) {
				graphics = super.getGraphics();
			}
			return graphics;
		}

		protected void sizeChanged(int w, int h) {
			super.sizeChanged(w, h);
			Graphics g = getGraphics();
			g.setClip(-g.getTranslateX(), -g.getTranslateY(), w, h); // to fix the nokia s60 bug
			scene.screenElement.setSize(w, h);
			scene.callOnScreenUpdated();
		}

		protected void keyPressed(int keyCode) {
			KeyboardInputEvent event = new KeyboardInputEvent();
			event.eventType = KeyboardInputEvent.KEY_PRESSED;
			event.keyCode = keyCode;
			scene.addInputEvent(event);
		}

		protected void keyRepeated(int keyCode) {
			KeyboardInputEvent event = new KeyboardInputEvent();
			event.eventType = KeyboardInputEvent.KEY_REPEATED;
			event.keyCode = keyCode;
			scene.addInputEvent(event);
		}

		protected void keyReleased(int keyCode) {
			KeyboardInputEvent event = new KeyboardInputEvent();
			event.eventType = KeyboardInputEvent.KEY_RELEASED;
			event.keyCode = keyCode;
			scene.addInputEvent(event);
		}

		protected void pointerPressed(int x, int y) {
			PointerInputEvent event = new PointerInputEvent();
			event.eventType = PointerInputEvent.POINTER_PRESSED;
			event.x = x;
			event.y = y;
			scene.addInputEvent(event);
			
			pointerX = x;
			pointerY = y;
		}

		protected void pointerDragged(int x, int y) {
			pointerX = x;
			pointerY = y;
		}

		protected void pointerReleased(int x, int y) {
			PointerInputEvent event = new PointerInputEvent();
			event.eventType = PointerInputEvent.POINTER_RELEASED;
			event.x = x;
			event.y = y;
			scene.addInputEvent(event);
			
			pointerX = -1;
			pointerY = -1;
		}
	}
}
