package com.zine.zinemob.scene.controller;

/**
 * TimeLineEvents are attached to a TimeLineController.
 */
public interface TimeLineEvent {

	/**
	 * Called by the TimeLineController when the frame of the event is executed.
	 * @param frame the frame of the timeline
	 */
	public void run(int frame);
}
