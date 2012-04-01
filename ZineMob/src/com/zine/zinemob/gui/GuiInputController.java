package com.zine.zinemob.gui;

import com.zine.zinemob.scene.controller.SceneController;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.PointerListener;

/**
 * Class used by the GuiScene to control the input. The input are passed for the
 * current focused component of the current window.
 */
class GuiInputController extends SceneController implements KeyboardListener, PointerListener {
		
	private Window window;
	private Component pressedComponent = null;

	public void onKeyPressed(int keyCode, int gameAction) {
		Component currentComponent = getCurrentComponent();
		if (currentComponent != null) {
			currentComponent.onKeyPressed(keyCode, gameAction);
		}
	}

	public void onKeyRepeated(int keyCode, int gameAction) {
		Component currentComponent = getCurrentComponent();
		if (currentComponent != null) {
			currentComponent.onKeyRepeated(keyCode, gameAction);
		}
	}

	public void onKeyReleased(int keyCode, int gameAction) {
		Component currentComponent = getCurrentComponent();
		if (currentComponent != null) {
			currentComponent.onKeyReleased(keyCode, gameAction);
		}
	}

	public void updateKeyStates(int keyStates) {
		Component currentComponent = getCurrentComponent();
		if (currentComponent != null) {
			currentComponent.updateKeyStates(keyStates);
		}
	}

	private Component getCurrentComponent() {
		if (window != null) {
			return window.getFocusedComponent();
		} else {
			return null;
		}
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window currentWindow) {
		this.window = currentWindow;
	}

	public void onPointerPressed(int x, int y) {
		if (window != null) {
			pressedComponent = window.getComponentAt(x, y);
			if (pressedComponent != null) {
				Component parentComponent = pressedComponent.getParentComponent();
				if (parentComponent != null) {
					parentComponent.setFocusToComponent(pressedComponent);
				}
				pressedComponent.onPointerPressed(x, y);
			}
		}
	}

	public void onPointerReleased(int x, int y) {
		if (pressedComponent != null) {
			pressedComponent.onPointerReleased(x, y);
			pressedComponent = null;
		}
	}
	
	public void updatePointerState(int x, int y) {
		if (pressedComponent != null) {
			pressedComponent.updatePointerState(x, y);
		}
	}
	
}
