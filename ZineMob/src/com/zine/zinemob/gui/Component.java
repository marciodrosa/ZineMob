package com.zine.zinemob.gui;

import com.zine.zinemob.eventmessage.EventMessage;
import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.PointerListener;

/**
 * The base class of fields and containers.
 */
public abstract class Component implements KeyboardListener, PointerListener {
	
	private GuiScene guiScene;
	private boolean focusable = true;

	/**
	 * Returns the GuiScene.
	 */
	public GuiScene getGuiScene() {
		return guiScene;
	}

	/**
	 * Sets the GuiScene. It is automatically called when the component is attached
	 * to a Scene.
	 */
	public void setGuiScene(GuiScene guiScene) {
		this.guiScene = guiScene;
		if (this.guiScene != null) {
			onGuiSceneAttached(guiScene);
		}
	}
	
	/**
	 * Called when a non-null GuiScene is attached to this component. By
	 * default it does nothing.
	 */
	public void onGuiSceneAttached(GuiScene guiScene) {
	}

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

	public void onPointerPressed(int x, int y) {
		if (getParentComponent() != null) {
			getParentComponent().onPointerPressed(x, y);
		}
	}

	public void onPointerReleased(int x, int y) {
		if (getParentComponent() != null) {
			getParentComponent().onPointerReleased(x, y);
		}
	}

	public void updatePointerState(int x, int y) {
		if (getParentComponent() != null) {
			getParentComponent().updatePointerState(x, y);
		}
	}
	
	/**
	 * Called when some event is propagated by some child.
	 */
	public void onGuiEvent(EventMessage event) {
		propagateEvent(event);
	}
	
	/**
	 * Propagates the event to the parent component.
	 */
	public void propagateEvent(EventMessage event) {
		if (getParentComponent() != null) {
			getParentComponent().onGuiEvent(event);
		}
	}
	
	/**
	 * Returns if the component can receive focus.
	 */
	public boolean isFocusable() {
		return focusable;
	}

	/**
	 * Sets if the components can receive focus. If false, the component only can
	 * receive focus programatically. By default, it is true.
	 */
	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
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
	
	/**
	 * Sets the focus to the child of this container.
	 */
	public abstract void setFocusToComponent(Component component);
}
