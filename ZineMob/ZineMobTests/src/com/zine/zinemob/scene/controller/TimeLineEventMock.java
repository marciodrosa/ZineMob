package com.zine.zinemob.scene.controller;

/**
 * TimeLineEvent that writes a log into the StringBuffer when is called. The
 * log is formatted as "(name) called at (frame)(endline)".
 */
public class TimeLineEventMock implements TimeLineEvent {
	
	private final String name;
	private final StringBuffer callLog;
	
	public TimeLineEventMock(String name, StringBuffer callLog) {
		this.name = name;
		this.callLog = callLog;
	}

	public void run(int frame) {
		callLog.append(name).append(" called at ").append(frame).append('\n');
	}
}
