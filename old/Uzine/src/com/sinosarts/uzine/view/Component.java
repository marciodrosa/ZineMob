package com.sinosarts.uzine.view;

import com.sinosarts.uzine.events.InputEvent;
import com.sinosarts.zinemob.renderelements.RenderElement;

public abstract class Component implements GuiElement {

	WindowsManager windowsManager;
	GuiElement parentElement;

	private int id;

	/**
	 * Construtor. Inicializa o componente com Id = 0.
	 */
	public Component() {
	}

	/**
	 * Construtor.
	 * @param id o Id do componente
	 */
	public Component(int id) {
		setId(id);
	}

	public void requestFocus() {
		if (windowsManager != null)
			windowsManager.requestFocus(this);
	}

	public void requestRepaint() {
		if (windowsManager != null)
			windowsManager.requestRepaint();
	}

	/**
	 * Faz o tratamento para os eventos genéricos de entrada para o componente.
	 * Por padrão, trata apenas o evento EVT_ACTION, chamando pelo método doAction.
	 * Os outros tipos de eventos são passados imediatamente para o elemento pai
	 * deste, através da chamada do mesmo método onInputEvent.
	 * @param inputEvent o evento de entrada a ser processado
	 */
	public void onInputEvent(InputEvent inputEvent) {
		switch(inputEvent.getEventType()) {
			case InputEvent.EVT_ACTION:
				doAction();
				break;
			default:
				if (parentElement != null)
					parentElement.onInputEvent(inputEvent);
				break;
		}
	}

	public void onFocus(boolean focus) {
	}

	public GuiElement getParentGuiElement() {
		return parentElement;
	}

	public int getCursorPositionPointX() {
		return 0;
	}

	public int getCursorPositionPointY() {
		RenderElement re = getRenderElement();
		if (re != null)
			return re.getHeight() / 2;
		else
			return 0;
	}

	public void keyPressed(int keyCode) {
		if (parentElement != null)
			parentElement.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		if (parentElement != null)
			parentElement.keyReleased(keyCode);
	}

	public void keyRepeated(int keyCode) {
		if (parentElement != null)
			parentElement.keyRepeated(keyCode);
	}

	/**
	 * Retorna o ID associado a este componente.
	 * @return o ID associado a este componente
	 */
	public int getId() {
		return id;
	}

	/**
	 * Define o ID associado a este componente.
	 * @param id o ID associado a este componente
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Executa a ação deste componente. Por padrão, apenas gera um CommandEvent
	 * com o Id do componente.
	 */
	public void doAction() {
		if (windowsManager != null)
			windowsManager.pushCommandEvent(new CommandEvent(getId()));
	}

}
