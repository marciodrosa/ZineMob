package com.zine.zinemob.gui;

import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.KeyboardListener;

/**
 * Class used by the GuiScene to control the input. The input are passed for the
 * current focused component of the current window.
 */
class GuiInputController extends Controller implements KeyboardListener {
		
	private Window window;

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
	
}
