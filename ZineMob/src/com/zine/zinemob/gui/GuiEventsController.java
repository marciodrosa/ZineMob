package com.zine.zinemob.gui;

/**
 * Interface to receive gui events.
 */
public interface GuiEventsController {
	
	/**
	 * Callend when some event is propagated.
	 * @param event the event
	 * @param guiScene the current GuiScene
	 */
	void onEvent(GuiEvent event, GuiScene guiScene);
}
