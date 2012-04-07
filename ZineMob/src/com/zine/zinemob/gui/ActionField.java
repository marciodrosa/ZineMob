package com.zine.zinemob.gui;

import com.zine.zinemob.eventmessage.EventMessage;
import com.zine.zinemob.drawableelement.DrawableElement;
import javax.microedition.lcdui.Canvas;

/**
 * Field that throw an event whit its ID when it is selected (fire pressed or clicked).
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

	public void onPointerPressed(int x, int y) {
		onPressed(true);
	}

	public void onPointerReleased(int x, int y) {
		onPressed(false);
		if (getDrawableElement().collidesWith(x, y, false)) {
			doAction();
		}
	}
	
	/**
	 * Called when the action is activated. By default, creates and propagates a
	 * GuiEvent.
	 */
	public void doAction() {
		EventMessage event = new EventMessage();
		event.setId(getActionEventId());
		propagateEvent(event);
	}

	/**
	 * Called when the field is pressed with the pointer. By default it does nothing.
	 */
	protected void onPressed(boolean pressed) {
	}
	
	public abstract DrawableElement getDrawableElement();
	
}
