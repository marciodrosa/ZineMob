package com.zine.zinemob.scene.controller;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class TimeLineControllerTest extends TestCase {
	
	private TimeLineController timeLineController;
	private StringBuffer timeLineEventsCallLog;
	
	public TimeLineControllerTest() {
	}

	public TimeLineControllerTest(String testName, TestMethod method) {
		super(testName, method);
	}

	protected void setUp() throws Exception {
		timeLineController = new TimeLineController();
		timeLineEventsCallLog = new StringBuffer();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new TimeLineControllerTest("testShouldCallTheTimeLineEvents", new TestMethod()
		{ public void run(TestCase tc) {((TimeLineControllerTest)tc).testShouldCallTheTimeLineEvents(); } } ));

		testSuite.addTest(new TimeLineControllerTest("testShouldCallTheEventsAtSameFrameInInsertionOrder", new TestMethod()
		{ public void run(TestCase tc) {((TimeLineControllerTest)tc).testShouldCallTheEventsAtSameFrameInInsertionOrder(); } } ));

		testSuite.addTest(new TimeLineControllerTest("testShouldExtendTimeLineLengthWhenAddEventOutOfRange", new TestMethod()
		{ public void run(TestCase tc) {((TimeLineControllerTest)tc).testShouldExtendTimeLineLengthWhenAddEventOutOfRange(); } } ));
		
		return testSuite;
	}
	
	private void runTimeLine() {
		for (int i=0; i<timeLineController.getLength(); i++) {
			timeLineController.update();
		}
	}

	public void testShouldCallTheTimeLineEvents() {
		// given:
		timeLineController.addTimeLineEvent(new TimeLineEventMock("event one", timeLineEventsCallLog), 10);
		timeLineController.addTimeLineEvent(new TimeLineEventMock("event two", timeLineEventsCallLog), 20);
		
		// when:
		runTimeLine();
		
		// then:
		assertEquals("The events are not call as expected.", "event one called at 10\nevent two called at 20\n", timeLineEventsCallLog.toString());
	}

	public void testShouldCallTheEventsAtSameFrameInInsertionOrder() {
		// given:
		timeLineController.addTimeLineEvent(new TimeLineEventMock("event one", timeLineEventsCallLog), 10);
		timeLineController.addTimeLineEvent(new TimeLineEventMock("event two", timeLineEventsCallLog), 10);
		
		// when:
		runTimeLine();
		
		// then:
		assertEquals("The events are not call as expected.", "event one called at 10\nevent two called at 10\n", timeLineEventsCallLog.toString());
	}

	public void testShouldExtendTimeLineLengthWhenAddEventOutOfRange() {
		// given:
		timeLineController.setLength(10);
		timeLineController.addTimeLineEvent(new TimeLineEventMock("", timeLineEventsCallLog), 5);
		int lengthBeforeExtrapolate = timeLineController.getLength();
		
		// when:
		timeLineController.addTimeLineEvent(new TimeLineEventMock("", timeLineEventsCallLog), 10);
		int lengthAfterExtrapolate = timeLineController.getLength();
		
		// then:
		assertEquals("The length should be the same before add an event out of range.", 10, lengthBeforeExtrapolate);
		assertEquals("The length should be stretched after add an event out of range.", 11, lengthAfterExtrapolate);
	}
	
}
