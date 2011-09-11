package com.zine.zinemob.gui;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.KeyboardListener;

/**
 * The base class of fields and containers.
 */
public abstract class Component implements KeyboardListener {

	public void onKeyPressed(int keyCode, int gameAction) {
		if (getParentComponent() != null) {
			getParentComponent().onKeyPressed(keyCode, gameAction);
		}
	}

	public void onKeyRepeated(int keyCode, int gameAction) {
		if (getParentComponent() != null) {
			getParentComponent().onKeyRepeated(keyCode, gameAction);
		}
	}

	public void onKeyReleased(int keyCode, int gameAction) {
		if (getParentComponent() != null) {
			getParentComponent().onKeyReleased(keyCode, gameAction);
		}
	}

	public void updateKeyStates(int keyStates) {
		if (getParentComponent() != null) {
			getParentComponent().updateKeyStates(keyStates);
		}
	}
	
	/**
	 * Called when some event is propagated by some child.
	 */
	public void onGuiEvent(GuiEvent event) {
		propagateEvent(event);
	}
	
	/**
	 * Propagates the event to the parent component.
	 */
	public void propagateEvent(GuiEvent event) {
		if (getParentComponent() != null) {
			getParentComponent().onGuiEvent(event);
		}
	}
	
	/**
	 * Returns the DrawableElement used to draw this component.
	 */
	public abstract DrawableElement getDrawableElement();
	
	/**
	 * Method called when the component receives or lost focus.
	 * @param focus true if the component received focus, or false if the component
	 * lost focus
	 */
	public abstract void onFocus(boolean focus);
	
	/**
	 * Returns the parent component.
	 */
	public abstract Component getParentComponent();
	
	/**
	 * Returns the current focused component, can be this.
	 */
	public abstract Component getFocusedComponent();
}
