package com.zine.zinemob.gui;

/**
 * Interface to receive gui events.
 */
public interface GuiEventsController {
	
	/**
	 * Callend when some event is propagated.
	 * @param event the event
	 * @param guiSceneController the current controller of the GuiScene
	 */
	void onEvent(GuiEvent event, GuiSceneController guiSceneController);
}
