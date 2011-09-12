package com.zine.zinemob.gui;

/**
 * A window to be place into a GuiScene.
 */
public class Window extends Container {
	
	private GuiEventsController guiEventsController;
	GuiSceneController guiSceneController;

	/**
	 * Returns the events controller.
	 */
	public GuiEventsController getGuiEventsController() {
		return guiEventsController;
	}

	/**
	 * Sets the events controller.
	 */
	public void setGuiEventsController(GuiEventsController guiEventsController) {
		this.guiEventsController = guiEventsController;
	}

	/**
	 * Overrides to call the onEvent of the GuiEventsController object.
	 */
	public void onGuiEvent(GuiEvent event) {
		if (getGuiEventsController() != null) {
			getGuiEventsController().onEvent(event, guiSceneController);
		}
	}
	
}
