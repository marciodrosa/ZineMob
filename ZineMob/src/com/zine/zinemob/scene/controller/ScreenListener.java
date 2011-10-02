package com.zine.zinemob.scene.controller;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Listener to screen events.
 */
public interface ScreenListener {
	
	/**
	 * Called when the screen size changes or the screen is initiated or the screen
	 * comes visible on the display.
	 * @param screenElement the Drawable element that represents the screen
	 */
	public void onScreenUpdated(DrawableElement screenElement);
}
