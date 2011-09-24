package com.zine.zinemob.scene.controller;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Listener to screen events.
 */
public interface ScreenListener {
	
	/**
	 * Called when the screen size changes.
	 * @param screenElement the Drawable element that represents the screen
	 */
	public void onScreenSizeChanged(DrawableElement screenElement);
	
	/**
	 * Called when the screen is initiated on display.
	 * @param screenElement the Drawable element that represents the screen
	 */
	public void onScreenInitiated(DrawableElement screenElement);
}
