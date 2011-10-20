package com.zine.zinemob.scene.controller;

/**
 * Listener to pointer events.
 */
public interface PointerListener {
	
	/**
	 * Called when the pointer is pressed.
	 */
	public void onPointerPressed(int x, int y);
	
	/**
	 * Called when the pointer is released.
	 */
	public void onPointerReleased(int x, int y);
}
