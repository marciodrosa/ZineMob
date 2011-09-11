package com.zine.zinemob.gui;

import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.KeyboardListener;

class GuiInputController extends Controller implements KeyboardListener {
		
	private Window currentWindow;

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
		if (currentWindow != null) {
			return currentWindow.getFocusedComponent();
		} else {
			return null;
		}
	}

	public Window getCurrentWindow() {
		return currentWindow;
	}

	public void setCurrentWindow(Window currentWindow) {
		this.currentWindow = currentWindow;
	}
	
}
