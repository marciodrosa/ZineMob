package com.zine.zinemob.gui;

import com.zine.zinemob.animation.AnimationController;
import com.zine.zinemob.animation.AnimationListener;
import com.zine.zinemob.scene.Scene;
import java.util.Vector;

/**
 * A scene used to show windows.
 * 
 * When the last window is closed, then the scene is finished.
 */
public class GuiScene extends Scene implements GuiSceneController {
	
	private Vector windows = new Vector();
	private GuiInputController guiInputController = new GuiInputController();
	
	public GuiScene() {
		addController(guiInputController);
	}
	
	public void addWindow(final Window window) {
		
		Window currentTopWindow = getTopWindow();
		
		if (currentTopWindow != null) {
			sendCurrentWindowToBackgroundAndShowNewWindow(currentTopWindow, window);
		} else {
			addNewWindowToForeground(window);
		}
	}
	
	public void closeTopWindow() {
		closeCurrentWindowAndResumeNextWindowOfTheQueue();
	}
	
	public Window getTopWindow() {
		if (windows.size() > 0) {
			return (Window) windows.elementAt(windows.size() - 1);
		} else {
			return null;
		}
	}
	
	private void sendCurrentWindowToBackgroundAndShowNewWindow(final Window currentWindow, final Window newWindow) {
		
		turnOffInputForTopWindow();

		executeWindowAnimation(currentWindow.getGoToBackgroundAnimation(), new AnimationListener() {
			public void onAnimationFinish() {
				if (currentWindow.mustHideWhenGoToBackground()) {
					currentWindow.getDrawableElement().setVisible(false);
				}
				addNewWindowToForeground(newWindow);
			}
		});
	}
	
	private void addNewWindowToForeground(Window window) {
		
		getScreenElement().addChild(window.getDrawableElement());
		windows.addElement(window);
		window.setGuiSceneController(this);
		
		executeWindowAnimation(window.getShowAnimation(), new AnimationListener() {
			public void onAnimationFinish() {
				turnOnInputForTopWindow();
			}
		});
	}
	
	private void closeCurrentWindowAndResumeNextWindowOfTheQueue() {
		
		turnOffInputForTopWindow();
		
		final Window currentWindow = getTopWindow();
		
		if (currentWindow != null) {
			
			executeWindowAnimation(currentWindow.getCloseAnimation(), new AnimationListener() {
				public void onAnimationFinish() {
					removeWindow(currentWindow);
					resumeNextWindowOfTheQueue();
				}
			});
		}
	}
	
	private void removeWindow(Window window) {
		getScreenElement().removeChild(window.getDrawableElement());
		windows.removeElement(window);
		
		if (windows.isEmpty()) {
			finishExecution();
		}
	}
	
	private void resumeNextWindowOfTheQueue() {
		
		Window topWindow = getTopWindow();
		
		if (topWindow != null) {
		
			if (topWindow.mustHideWhenGoToBackground()) {
				topWindow.getDrawableElement().setVisible(true);
			}
			
			executeWindowAnimation(topWindow.getResumeAnimation(), new AnimationListener() {
				public void onAnimationFinish() {
					turnOnInputForTopWindow();
				}
			});
		}
	}
	
	/**
	 * Executes the animation of the Window and, after this, the listener object.
	 * @param animation the animation, or null if the Window has no animation
	 * @param listener the listener to be called after
	 */
	private void executeWindowAnimation(AnimationController animation, AnimationListener listener) {
		
		if (animation != null) {
			
			animation.resetAnimation();
			
			animation.setAnimationListener(listener);
			
			addController(animation);
			
		} else {
			listener.onAnimationFinish();
		}
	}
	
	private void turnOffInputForTopWindow() {
		guiInputController.setWindow(null);
	}
	
	private void turnOnInputForTopWindow() {
		guiInputController.setWindow(getTopWindow());
	}
}
