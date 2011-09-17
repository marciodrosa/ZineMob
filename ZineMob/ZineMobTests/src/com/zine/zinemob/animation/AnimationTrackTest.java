package com.zine.zinemob.animation;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class AnimationTrackTest extends TestCase {
	
	private AnimationTrack animationTrack;

	public AnimationTrackTest() {

	}

	public AnimationTrackTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		animationTrack = new AnimationTrack();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new AnimationTrackTest("testGetValueForFrame", new TestMethod()
		{ public void run(TestCase tc) {((AnimationTrackTest)tc).testGetValueForFrame(); } } ));

		testSuite.addTest(new AnimationTrackTest("testGetValueForFrameWhenTheLastValueIsLessThanTheFirst", new TestMethod()
		{ public void run(TestCase tc) {((AnimationTrackTest)tc).testGetValueForFrameWhenTheLastValueIsLessThanTheFirst(); } } ));

		return testSuite;
	}

	private void testGetValueForFrame() {
		
		// given:
		animationTrack.setInitialValue(10);
		animationTrack.setFinalValue(30);
		
		int animationLength = 3;
		
		// when:
		int valueAtFrame0 = animationTrack.getValueForFrame(0, animationLength);
		int valueAtFrame1 = animationTrack.getValueForFrame(1, animationLength);
		int valueAtFrame2 = animationTrack.getValueForFrame(2, animationLength);
		
		// then:
		assertEquals("The value at frame 0 is not the expected.", 10, valueAtFrame0);
		assertEquals("The value at frame 1 is not the expected.", 20, valueAtFrame1);
		assertEquals("The value at frame 2 is not the expected.", 30, valueAtFrame2);
	}

	private void testGetValueForFrameWhenTheLastValueIsLessThanTheFirst() {
		
		// given:
		animationTrack.setInitialValue(30);
		animationTrack.setFinalValue(10);
		
		int animationLength = 3;
		
		// when:
		int valueAtFrame0 = animationTrack.getValueForFrame(0, animationLength);
		int valueAtFrame1 = animationTrack.getValueForFrame(1, animationLength);
		int valueAtFrame2 = animationTrack.getValueForFrame(2, animationLength);
		
		// then:
		assertEquals("The value at frame 0 is not the expected.", 30, valueAtFrame0);
		assertEquals("The value at frame 1 is not the expected.", 20, valueAtFrame1);
		assertEquals("The value at frame 2 is not the expected.", 10, valueAtFrame2);
	}
	
}
