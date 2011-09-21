package com.zine.zinemob.gui;

import javax.microedition.lcdui.Canvas;

/**
 * Field that throw an event when it is selected.
 */
public abstract class ActionField extends Field {
	
	private int actionEventId;

	public int getActionEventId() {
		return actionEventId;
	}

	public void setActionEventId(int actionEventId) {
		this.actionEventId = actionEventId;
	}

	public void onKeyPressed(int keyCode, int gameAction) {
		if (gameAction == Canvas.FIRE) {
			doAction();
		} else {
			super.onKeyPressed(keyCode, gameAction);
		}
	}
	
	/**
	 * Called when the action is activated. By default, creates and propagates a
	 * GuiEvent.
	 */
	public void doAction() {
		GuiEvent event = new GuiEvent();
		event.setId(getActionEventId());
		propagateEvent(event);
	}
	
}
