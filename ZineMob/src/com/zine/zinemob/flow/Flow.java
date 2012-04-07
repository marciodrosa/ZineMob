package com.zine.zinemob.flow;

import com.zine.zinemob.eventmessage.EventMessage;

/**
 * Flow is a singleton class that manages the workflow of an application divided
 * into n submodules. Example: imagine an application that contains an initial screen,
 * a gameplay screen and a screen that displays the credits when the game is over.
 * Each one of those parts can be a "module"; each module is executed individually,
 * like different applications, only after the previously was finished.
 * 
 * To do that, create an FlowController instance. This is the object that creates
 * and start the initial screen, the gameplay screen, the credits, etc. Then,
 * call Flow.getInstance().startFlow(myFlowController) (in some cases, it is the only
 * line written in the run() method of the ZineMIDlet). In the example, when the
 * initial screen is being finished, then you should call Flow.getInstance().setNextEvent.
 * The event ID is an number that represents the next flow (in this case, the gameplay
 * screen). After the initial screen is finished, the Flow automatically call the
 * FlowController to start the next flow.
 */
public class Flow {
	
	/**
	 * Special event ID used to finish the flow. The value is -1.
	 */
	public static final int EVENT_FLOW_FINISH = -1;
	
	private static Flow instance;
	private EventMessage event;
	
	private Flow() {
	}
	
	public synchronized static Flow getInstance() {
		if (instance == null) {
			instance = new Flow();
		}
		return instance;
	}
	
	/**
	 * Runs the Flow. This method is a loop. It calls the executeFlowEvent of the FlowController
	 * until the next event uses the ID EVENT_FLOW_FINISH.
	 */
	public void startFlow(FlowController flowController) {
		flowController.init();
		event = flowController.createInitialFlowEvent();
		while (event.getId() != EVENT_FLOW_FINISH) {
			flowController.executeFlowEvent(event);
		}
		flowController.onFinish();
	}
	
	/**
	 * Sets the next event to be processed. It will be passed to the FlowController
	 * after the current flow finish. This method only have effect if the Flow is
	 * running (after call the startFlow method).
	 */
	public void setNextEvent(EventMessage eventMessage) {
		this.event = eventMessage;
	}
	
	/**
	 * Auxiliar method. It only creates an EventMessage object with the ID and calls
	 * the setNextEvent(eventMessage).
	 * @param eventId the event ID
	 */
	public void setNextEvent(int eventId) {
		EventMessage eventMessage = new EventMessage();
		eventMessage.setId(eventId);
		setNextEvent(eventMessage);
	}
}
