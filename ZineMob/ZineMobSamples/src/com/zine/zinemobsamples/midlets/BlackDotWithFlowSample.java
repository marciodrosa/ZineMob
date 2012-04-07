package com.zine.zinemobsamples.midlets;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.Color;
import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.LinearLayoutElement;
import com.zine.zinemob.drawableelement.RectangleElement;
import com.zine.zinemob.drawableelement.layout.StretchToParentLayout;
import com.zine.zinemob.drawableelement.text.ImageTextElement;
import com.zine.zinemob.drawableelement.text.ImageTextElementFont;
import com.zine.zinemob.eventmessage.EventMessage;
import com.zine.zinemob.flow.Flow;
import com.zine.zinemob.flow.FlowController;
import com.zine.zinemob.gui.ActionField;
import com.zine.zinemob.gui.GuiController;
import com.zine.zinemob.gui.GuiScene;
import com.zine.zinemob.gui.Window;
import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.SceneController;
import com.zine.zinemob.scene.controller.Updateble;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * A simple (stupid) game that contains a initial menu, the gameplay screen and
 * an end screen.
 */
public class BlackDotWithFlowSample extends ZineMIDlet {
	
	public static final int FLOW_MAIN_MENU = 1;
	public static final int FLOW_GAME = 2;
	public static final int FLOW_THE_END = 3;
	
	public static final int BUTTON_START = 1;
	public static final int BUTTON_QUIT = 2;
	public static final int BUTTON_BACK_TO_INIT = 3;
	
	private ImageTextElementFont font = new ImageTextElementFont("/com/zine/zinemobsamples/res/courier12.png", "/com/zine/zinemobsamples/res/courier12.dat");

	public void run() {
		Flow.getInstance().startFlow(new MyFlowController());
	}
	
	/**
	 * Controlls the flow of the application. Initiates the main menu, the gameplay
	 * screen and the end screen.
	 */
	class MyFlowController extends FlowController {

		public void executeFlowEvent(EventMessage eventMessage) {
			switch (eventMessage.getId()) {
				case FLOW_MAIN_MENU:
					new MainMenuScene().run();
					break;
				case FLOW_GAME:
					new GameScene().run();
					break;
				case FLOW_THE_END:
					new TheEndScene().run();
					break;
			}
		}

		public EventMessage createInitialFlowEvent() {
			EventMessage event = new EventMessage();
			event.setId(FLOW_MAIN_MENU);
			return event;
		}
		
	}
	
	/**
	 * The scene of the main menu.
	 */
	class MainMenuScene extends GuiScene {
		public MainMenuScene() {
			super(new MainMenuController());
		}
	}
	
	/**
	 * The controller of the scene of the main menu.
	 */
	class MainMenuController extends GuiController {

		public void init() {
			Window window = new Window(true);
			window.addChild(new MyButton("Start", BUTTON_START));
			window.addChild(new MyButton("Quit", BUTTON_QUIT));
			getGuiScene().addWindow(window);
		}
		
		public void onEvent(EventMessage eventMessage) {
			EventMessage flowEvent = new EventMessage();
			switch (eventMessage.getId()) {
				case BUTTON_START:
					flowEvent.setId(FLOW_GAME);
					break;
				case BUTTON_QUIT:
					flowEvent.setId(Flow.EVENT_FLOW_FINISH);
					break;
			}
			Flow.getInstance().setNextEvent(flowEvent);
			getGuiScene().finishExecution();
		}
		
	}
	
	/**
	 * The scene of gameplay screen.
	 */
	class GameScene extends Scene {
		
		public void init() {
			setClearColor(0xffffffff);
			addController(new GameController());
			ImageTextElement label = new ImageTextElement("Move the dot to the left!", font);
			RectangleElement labelBackground = new RectangleElement();
			labelBackground.setColor(new Color(0));
			labelBackground.setSize(0, 20);
			labelBackground.addLayout(new StretchToParentLayout(labelBackground, StretchToParentLayout.LEFT | StretchToParentLayout.RIGHT));
			getScreenElement().addChild(labelBackground);
			getScreenElement().addChild(label);
		}
	}
	
	/**
	 * The controller of the gameplay.
	 */
	class GameController extends SceneController implements KeyboardListener, Updateble {

		private RectangleElement dotDrawableElement;

		public GameController() {
			dotDrawableElement = new RectangleElement();
			dotDrawableElement.setColor(new Color(0));
			dotDrawableElement.setSize(15, 15);
			dotDrawableElement.centerPivot();
		}

		public void init() {
			int centerX = getScene().getScreenElement().getWidth() / 2;
			int centerY = getScene().getScreenElement().getHeight() / 2;
			dotDrawableElement.setPosition(centerX, centerY);
			getScene().getScreenElement().addChild(dotDrawableElement);
		}

		public void onKeyPressed(int keyCode, int gameAction) {
		}

		public void onKeyRepeated(int keyCode, int gameAction) {
		}

		public void onKeyReleased(int keyCode, int gameAction) {
		}

		public void updateKeyStates(int keyStates) {
			int speed = 3;
			int movementX = 0;
			int movementY = 0;
			if ((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
				movementY += speed;
			}
			if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
				movementY -= speed;
			}
			if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
				movementX += speed;
			}
			if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
				movementX -= speed;
			}
			dotDrawableElement.setPosition(dotDrawableElement.getX() + movementX, dotDrawableElement.getY() + movementY);
		}

		public void update() {
			if (dotDrawableElement.getX() > getScene().getScreenElement().getWidth()) {
				EventMessage flowEvent = new EventMessage();
				flowEvent.setId(FLOW_THE_END);
				Flow.getInstance().setNextEvent(flowEvent);
				getScene().finishExecution();
			}
		}
		
	}
	
	/**
	 * The scene of end screen.
	 */
	class TheEndScene extends GuiScene {
		
		public TheEndScene() {
			super(new TheEndController());
		}
	}
	
	/**
	 * The controller of the scene of end screen.
	 */
	class TheEndController extends GuiController {

		public void init() {
			Window window = new Window(true);
			window.addChild(new ImageTextElement("The end.", font));
			window.addChild(new MyButton("Back to initial menu", BUTTON_BACK_TO_INIT));
			getGuiScene().addWindow(window);
		}
		
		public void onEvent(EventMessage eventMessage) {
			EventMessage flowEvent = new EventMessage();
			flowEvent.setId(FLOW_MAIN_MENU);
			Flow.getInstance().setNextEvent(flowEvent);
			getGuiScene().finishExecution();
		}
		
	}

	/**
	 * A button widget used in the screens.
	 */
	class MyButton extends ActionField {
		
		private LinearLayoutElement linearLayoutElement = new LinearLayoutElement();
		private RectangleElement rectangleElement = new RectangleElement();
		
		public MyButton(String label, int id) {
			setActionEventId(id);
			rectangleElement.setFill(false);
			rectangleElement.setColor(new Color(0xffaaaaaa));
			rectangleElement.addLayout(new StretchToParentLayout(rectangleElement));
			ImageTextElement imageTextElement = new ImageTextElement(label, font);
			linearLayoutElement.setPadding(10);
			linearLayoutElement.setFitPolicy(LinearLayoutElement.FIT_POLICY_ALWAYS_FIT_TO_CHILDREN);
			linearLayoutElement.addChildAndLayout(rectangleElement, LinearLayoutElement.IGNORE_LAYOUT);
			linearLayoutElement.addChild(imageTextElement);
		}

		public DrawableElement getDrawableElement() {
			return linearLayoutElement;
		}

		public void onFocus(boolean focus) {
			if (focus) {
				rectangleElement.setColor(new Color(0xffffffff));
			} else {
				rectangleElement.setColor(new Color(0xffaaaaaa));
			}
		}
	}
}
