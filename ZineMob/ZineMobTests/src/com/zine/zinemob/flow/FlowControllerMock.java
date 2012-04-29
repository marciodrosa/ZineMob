package com.zine.zinemob.flow;

import com.zine.zinemob.eventmessage.EventMessage;

/**
 * Flow controller that writes the calls into a StringBuffer, that can be asserted
 * after.
 */
public class FlowControllerMock extends FlowController {
	
	private StringBuffer log = new StringBuffer();
	
	// events ids (represented by some random numbers):
	private static final int FIRST_EVENT = 99;
	private static final int SECOND_EVENT = 25;
	private static final int THIRD_EVENT = 11;
	private static final int FOURTH_EVENT = 32;

	public void executeFlowEvent(EventMessage eventMessage) {
		EventMessage nextEventMessage = new EventMessage();
		switch(eventMessage.getId()) {
			case FIRST_EVENT:
				log("first event");
				Flow.getInstance().setNextEvent(SECOND_EVENT);
				break;
			case SECOND_EVENT:
				log("second event");
				nextEventMessage.setId(THIRD_EVENT);
				Flow.getInstance().setNextEvent(nextEventMessage);
				break;
			case THIRD_EVENT:
				log("third event");
				nextEventMessage.setId(FOURTH_EVENT);
				nextEventMessage.set("some object", "The Object!");
				Flow.getInstance().setNextEvent(nextEventMessage);
				break;
			case FOURTH_EVENT:
				log("fourth event - with some object: " + eventMessage.get("some object").toString());
				Flow.getInstance().setNextEvent(Flow.EVENT_FLOW_FINISH);
				break;
		}
	}

	public EventMessage createInitialFlowEvent() {
		EventMessage eventMessage = new EventMessage();
		eventMessage.setId(FIRST_EVENT);
		return eventMessage;
	}
	
	public void init() {
		log("initiated");
	}
	
	public void onFinish() {
		log("finished");
	}
	
	private void log(String string) {
		if (log.length() > 0) {
			log.append('\n');
		}
		log.append(string);
	}
	
	public String getCallLog() {
		return log.toString();
	}
	
	public String getExpectedCallLog() {
		StringBuffer expectedLog = new StringBuffer();
		expectedLog.append("initiated").append('\n');
		expectedLog.append("first event").append('\n');
		expectedLog.append("second event").append('\n');
		expectedLog.append("third event").append('\n');
		expectedLog.append("fourth event - with some object: The Object!").append('\n');
		expectedLog.append("finished");
		return expectedLog.toString();
	}
}
