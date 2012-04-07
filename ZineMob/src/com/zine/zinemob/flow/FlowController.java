package com.zine.zinemob.flow;

import com.zine.zinemob.eventmessage.EventMessage;

/**
 * This is the object used by the Flow class to execute the flows of the application.
 */
public abstract class FlowController {
	
	/**
	 * Called when the flow starts to running.
	 */
	public void init() {
	}
	
	/**
	 * Called when the flow is finishing.
	 */
	public void onFinish() {
	}
	
	/**
	 * Called by the Flow class when some flow needs to be executed. This method
	 * should return only when the flow is terminated (example: the menu was been showed
	 * and has been closed by the user).
	 * @param eventMessage the event that contains the information of the flow. The
	 * ID of the event and the data of the event can be used to identify and customize
	 * the flow to be executed.
	 */
	public abstract void executeFlowEvent(EventMessage eventMessage);
	
	/**
	 * Creates the initial flow to be passed to the executeFlowEvent method. This is
	 * called by the Flow class after it starts running.
	 */
	public abstract EventMessage createInitialFlowEvent();
	
}
