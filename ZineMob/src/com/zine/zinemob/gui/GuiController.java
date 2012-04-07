package com.zine.zinemob.gui;

import com.zine.zinemob.eventmessage.EventMessage;

/**
 * Interface to control the logic of GUI scenes and windows. Implement the onEvent
 * method to handle the actions propageted by components and input.
 */
public abstract class GuiController {
	
	private GuiScene guiScene;
	
	/**
	 * Returns the current attached GuiScene.
	 */
	public GuiScene getGuiScene() {
		return guiScene;
	}
	
	/**
	 * Sets the current attached GuiScene. This method is called automatically when
	 * the controller is added to a GuiScene.
	 */
	public void setGuiScene(GuiScene guiScene) {
		this.guiScene = guiScene;
	}
	
	/**
	 * Called when the GuiScene is initiated. This method is not called if the GuiController
	 * is not associated with a GuiScene.
	 */
	public void init() {
	}
	
	/**
	 * Called when some event is propagated. ActionFields and derived classes propagete
	 * EventMessages with the ID of the field, so you can implement this method
	 * to catch those events.
	 * @param eventMessage the EventMessage
	 */
	public abstract void onEvent(EventMessage eventMessage);
}
