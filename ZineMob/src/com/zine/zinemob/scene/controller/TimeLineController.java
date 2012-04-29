package com.zine.zinemob.scene.controller;

import com.zine.zinemob.animation.FramesAnimationController;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Animation controller that represents a time line where events are attached to a frame.
 * When this frame is executed, the events are triggered.
 */
public class TimeLineController extends FramesAnimationController {
	
	private Hashtable events = new Hashtable(); //<int, Vector<TimeLineEvent> >
	
	/**
	 * Adds a TimeLineEvent to be executed in a specified frame.
	 * @param timeLineEvent the event
	 * @param frame the frame index. If the frame is out of range of the length
	 * of the animation, then the animation length is stretched.
	 */
	public void addTimeLineEvent(TimeLineEvent timeLineEvent, int frame) {
		Vector eventsAtFrame = (Vector) events.get(new Integer(frame));
		if (eventsAtFrame == null) {
			eventsAtFrame = new Vector();
			events.put(new Integer(frame), eventsAtFrame);
		}
		eventsAtFrame.addElement(timeLineEvent);
		if (frame >= getLength()) {
			setLength(frame + 1);
		}
	}

	public void updateFrame(int frame) {
		Vector eventsAtFrame = (Vector) events.get(new Integer(frame));
		if (eventsAtFrame != null) {
			for (int i=0; i<eventsAtFrame.size(); i++) {
				TimeLineEvent event = (TimeLineEvent) eventsAtFrame.elementAt(i);
				event.run(frame);
			}
		}
	}
	
}
