package com.sinosarts.uzine.view;

import com.sinosarts.uzine.events.InputEvent;
import com.sinosarts.uzine.events.WindowEvent;
import com.sinosarts.uzine.view.components.TextComponent;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.renderelements.Animable;
import com.sinosarts.zinemob.renderelements.AnimationListener;
import com.sinosarts.zinemob.renderelements.RenderElement;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.windows.FooterCommand;
import javax.microedition.lcdui.Canvas;

/**
 * Window é a janela que contém um Container com componentes.
 */
public class Window implements GuiElement {

	private static final byte KEY_LEFT_COMMAND = -6;
	private static final byte KEY_RIGHT_COMMAND = -7;

	WindowsManager windowsManager;
	private WindowRenderElement renderElement;
	private Container container;
	private FooterCommand leftFooterCommand, rightFooterCommand;

	// animação: ---------------------------------------------------------------
	public static final byte ANIMATION_NONE = 0;
	public static final byte ANIMATION_HIDE_TO_LEFT = 1;
	public static final byte ANIMATION_UNHIDE_FROM_LEFT = 2;
	public static final byte ANIMATION_HIDE_TO_RIGHT = 3;
	public static final byte ANIMATION_UNHIDE_FROM_RIGHT = 4;
	public static final byte ANIMATION_UNHIDE = 5;

	private byte evtShowAnimation = ANIMATION_NONE;
	private byte evtFocusReturnedAnimation = ANIMATION_NONE;
	private byte evtLostFocusToAnotherWindowAnimation = ANIMATION_NONE;
	private byte evtClosingAnimaton = ANIMATION_NONE;

	private class WindowAnimationListener implements AnimationListener {

		public void onAnimationEnd(Animable animable) {
		}

		public void onAnimationUpdate(Animable animable) {
			requestRepaint();
		}
		
	}

	/**
	 * Construtor. Inicia a janela com um novo Container.
	 */
	public Window() {
		init();
		setContainer(new Container());
	}

	/**
	 * Construtor.
	 * @param container o Container a ser utilizado na janela.
	 */
	public Window(Container container) {
		init();
		setContainer(container);
	}

	private void init() {
		renderElement = new WindowRenderElement(new WindowAnimationListener());
	}

	public RenderElement getRenderElement() {
		return renderElement;
	}

	/**
	 * Define o Container da janela.
	 * @param container o Container da janela
	 */
	public void setContainer(Container container) {

		if (this.container != null)
			renderElement.removeChild(this.container.getRenderElement());

		this.container = container;

		renderElement.addChild(container.getRenderElement());
	}

	/**
	 * Retorna o Container da janela.
	 * @return o Container da janela
	 */
	public Container getContainer() {
		return container;
	}

	/**
	 * Remove a janela do manager.
	 */
	public void close() {
		if (windowsManager != null)
			windowsManager.removeWindow(this);
	}

	public void requestFocus() {
		if (windowsManager != null)
			windowsManager.requestFocus(container);
	}

	public void requestRepaint() {
		if (windowsManager != null)
			windowsManager.requestRepaint();
	}

	public void onWindowEvent(WindowEvent windowEvent) {
		requestRepaint();
		switch(windowEvent.getEventType()) {
			case WindowEvent.EVT_SHOW:
				onFocus(true);
				break;
			case WindowEvent.EVT_FOCUS_RETURNED:
				onFocus(true);
				break;
			case WindowEvent.EVT_LOST_FOCUS_TO_ANOTHER_WINDOW:
				onFocus(false);
				break;
			case WindowEvent.EVT_CLOSING:
				onFocus(false);
				break;
		}
	}

	public void onInputEvent(InputEvent inputEvent) {
	}

	public void onFocus(boolean focus) {
		container.onFocus(focus);
	}

	public GuiElement getParentGuiElement() {
		return null;
	}

	public int getCursorPositionPointX() {
		return 0;
	}

	public int getCursorPositionPointY() {
		return 0;
	}

	public void keyPressed(int keyCode) {
		requestRepaint();

		int gameKeyCode = ZineManager.getInstance().getGameAction(keyCode);

		if (gameKeyCode == Canvas.FIRE)
			if (windowsManager != null)
				windowsManager.pushInputEvent(new InputEvent(InputEvent.EVT_ACTION));
		else if (keyCode == KEY_LEFT_COMMAND) {
			if (leftFooterCommand != null && windowsManager != null)
				windowsManager.pushCommandEvent(new CommandEvent(leftFooterCommand.getId()));
		}
		else if (keyCode == KEY_RIGHT_COMMAND) {
			if (rightFooterCommand != null && windowsManager != null)
				windowsManager.pushCommandEvent(new CommandEvent(rightFooterCommand.getId()));
		}
	}

	public void keyReleased(int keyCode) {
	}

	public void keyRepeated(int keyCode) {
	}

	/**
	 * Define um comando para o canto inferior esquerdo da tela. Pode ser null.
	 * @param command o comando para o canto inferior esquerdo da tela
	 */
	public void setLeftFooterCommand(FooterCommand command) {
		leftFooterCommand = command;
		requestRepaint();
	}

	/**
	 * Retorna o comando para o canto inferior esquerdo da tela, definido em
	 * setLeftFooterCommand.
	 * @return o comando para o canto inferior esquerdo da tela
	 */
	public FooterCommand getLeftFooterCommand() {
		return leftFooterCommand;
	}

	/**
	 * Define um comando para o canto inferior direito da tela. Pode ser null.
	 * @param command o comando para o canto inferior direito da tela
	 */
	public void setRightFooterCommand(FooterCommand command) {
		rightFooterCommand = command;
		requestRepaint();
	}

	/**
	 * Retorna o comando para o canto inferior direito da tela, definido em
	 * setRightFooterCommand.
	 * @return o comando para o canto inferior direito da tela
	 */
	public FooterCommand getRightFooterCommand() {
		return rightFooterCommand;
	}

	/**
	 * Define uma animação para ser executada quando a janela detecta um evento.
	 * @param animation a animação a ser executada
	 * @param windowEventType o evento que executa a animação (da classe WindowEvent)
	 */
	public void setDefaultAnimationForWindowEvent(byte animation, byte windowEventType) {
		switch(windowEventType) {
			case WindowEvent.EVT_SHOW:
				evtShowAnimation = animation;
				break;
			case WindowEvent.EVT_FOCUS_RETURNED:
				evtFocusReturnedAnimation = animation;
				break;
			case WindowEvent.EVT_LOST_FOCUS_TO_ANOTHER_WINDOW:
				evtLostFocusToAnotherWindowAnimation = animation;
				break;
			case WindowEvent.EVT_CLOSING:
				evtClosingAnimaton = animation;
				break;
		}
	}

	/**
	 * Retorna a animação definida para ser executada quando a janela detecta um
	 * evento.
	 * @param windowEventType o evento que executa a animação (da classe WindowEvent)
	 * @return a animação definida para ser executada quando ocorre o evento
	 */
	public byte getDefaultAnimationForWindowEvent(byte windowEventType) {
		switch(windowEventType) {
			case WindowEvent.EVT_SHOW:
				return evtShowAnimation;
			case WindowEvent.EVT_FOCUS_RETURNED:
				return evtFocusReturnedAnimation;
			case WindowEvent.EVT_LOST_FOCUS_TO_ANOTHER_WINDOW:
				return evtLostFocusToAnotherWindowAnimation;
			case WindowEvent.EVT_CLOSING:
				return evtClosingAnimaton;
		}
		return ANIMATION_NONE;
	}

	/**
	 * Faz o mesmo que getContainer().addContainer.
	 * @param container
	 */
	public void addContainer(Container container) {
		getContainer().addContainer(container);
	}

	/**
	 * Faz o mesmo que getContainer().addContainerToLayout.
	 * @param container
	 * @param border
	 * @param constraints
	 * @param stretchable
	 */
	public void addContainerToLayout(Container container, Border border, int constraints, boolean stretchable) {
		getContainer().addContainerToLayout(container, border, constraints, stretchable);
	}

	/**
	 * Faz o mesmo que getContainer().addComponent.
	 * @param component
	 */
	public void addComponent(Component component) {
		getContainer().addComponent(component);
	}

	/**
	 * Faz o mesmo que getContainer().addComponentToLayout.
	 * @param component
	 * @param border
	 * @param constraints
	 * @param stretchable
	 */
	public void addComponentToLayout(Component component, Border border, int constraints, boolean stretchable) {
		getContainer().addComponentToLayout(component, border, constraints, stretchable);
	}

	/**
	 * Faz o mesmo que getContainer().addTextComponentToLayout.
	 * @param id
	 * @param label
	 * @param border
	 * @param constraints
	 * @param stretchable
	 * @return
	 */
	public TextComponent addTextComponentToLayout(int id, String label, Border border, int constraints, boolean stretchable) {
		return getContainer().addTextComponentToLayout(id, label, border, constraints, stretchable);
	}

	/**
	 * Faz o mesmo que getContainer().removeElement.
	 * @param element
	 */
	public void removeElement(GuiElement element) {
		getContainer().removeElement(element);
	}
}
