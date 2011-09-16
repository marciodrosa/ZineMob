package com.zine.zinemob.gui;

import com.zine.zinemob.scene.Scene;
import java.util.Vector;

/**
 * A scene used to show windows.
 */
public class GuiScene extends Scene implements GuiSceneController {
	
	private Vector windows = new Vector();
	private GuiInputController guiInputController = new GuiInputController();
	
	public GuiScene() {
		addController(guiInputController);
	}
	
	public void addWindow(Window window) {
		getScreenElement().addChild(window.getDrawableElement());
		windows.addElement(window);
		window.setGuiSceneController(this);
		onTopWindowChanged();
	}
	
	public void closeTopWindow() {
		if (windows.size() > 0) {
			getScreenElement().removeChild(getTopWindow().getDrawableElement());
			windows.removeElementAt(windows.size() - 1);
		}
		if (windows.size() > 0) {
			onTopWindowChanged();
		}
	}
	
	public Window getTopWindow() {
		if (windows.size() > 0) {
			return (Window) windows.elementAt(windows.size() - 1);
		} else {
			return null;
		}
	}
	
	private void onTopWindowChanged() {
		guiInputController.setCurrentWindow(getTopWindow());
	}
}
