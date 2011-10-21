package com.zine.zinemob.gui;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.PointerListener;

/**
 * The base class of fields and containers.
 */
public abstract class Component implements KeyboardListener, PointerListener {
	
	private GuiSceneController guiSceneController;
	private int layout;

	/**
	 * Returns the GuiSceneController.
	 */
	public GuiSceneController getGuiSceneController() {
		return guiSceneController;
	}

	/**
	 * Sets the GuiSceneController.
	 */
	public void setGuiSceneController(GuiSceneController guiSceneController) {
		this.guiSceneController = guiSceneController;
		if (this.guiSceneController != null) {
			onGuiSceneControllerAttached(guiSceneController);
		}
	}
	
	/**
	 * Called when a non-null GuiSceneController is attached to this component. By
	 * default it does nothin.
	 */
	public void onGuiSceneControllerAttached(GuiSceneController guiSceneController) {
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

	public void onPointerDragged(int x, int y) {
		if (getParentComponent() != null) {
			getParentComponent().onPointerDragged(x, y);
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
	 * Returns the layout to be used for this component.
	 */
	public int getLayout() {
		return layout;
	}

	/**
	 * Sets the layout to be used for this component.
	 */
	public void setLayout(int layout) {
		this.layout = layout;
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
