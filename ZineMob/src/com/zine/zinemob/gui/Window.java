package com.zine.zinemob.gui;

public class Window extends Container {
	
	private GuiEventsController guiEventsController;
	GuiSceneController guiSceneController;

	public GuiEventsController getGuiEventsController() {
		return guiEventsController;
	}

	public void setGuiEventsController(GuiEventsController guiEventsController) {
		this.guiEventsController = guiEventsController;
	}

	public void onGuiEvent(GuiEvent event) {
		if (getGuiEventsController() != null) {
			getGuiEventsController().onEvent(event, guiSceneController);
		}
	}
	
}
