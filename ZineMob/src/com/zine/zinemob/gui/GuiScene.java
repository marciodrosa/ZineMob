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
public class GuiScene extends Scene {
	
	private Vector windows = new Vector();
	private GuiInputController guiInputController = new GuiInputController();
	
	public GuiScene() {
		addController(guiInputController);
	}
	
	/**
	 * Add the window to the top of windows queue. This window will be the current
	 * window and will receive input events.
	 */
	public void addWindow(final Window window) {
		
		Window currentTopWindow = getTopWindow();
		
		if (currentTopWindow != null) {
			sendCurrentWindowToBackgroundAndShowNewWindow(currentTopWindow, window);
		} else {
			addNewWindowToForeground(window);
		}
	}
	
	/**
	 * Closes the current window (top of windows queue). If there other windows
	 * in the queue, the front window will be the current window.
	 */
	public void closeTopWindow() {
		closeCurrentWindowAndResumeNextWindowOfTheQueue();
	}
	
	/**
	 * Returns the current window (top of windows queue).
	 */
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
				if (currentWindow.mustCloseWhenGoToBackground()) {
					removeWindow(currentWindow);
				}
			}
		});
	}
	
	private void addNewWindowToForeground(Window window) {
		
		getScreenElement().addChild(window.getDrawableElement());
		windows.addElement(window);
		window.setGuiScene(this);
		
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
		Window window = getTopWindow();
		if (window != null) {
			window.onFocus(false);
		}
		guiInputController.setWindow(null);
	}
	
	private void turnOnInputForTopWindow() {
		Window window = getTopWindow();
		if (window != null) {
			window.onFocus(true);
		}
		guiInputController.setWindow(window);
	}
}
