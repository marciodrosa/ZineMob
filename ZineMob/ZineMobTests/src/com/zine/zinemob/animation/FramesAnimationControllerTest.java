package com.zine.zinemob.animation;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class FramesAnimationControllerTest extends TestCase {
	
	private StringBuffer framesLog;
	private FramesAnimationControllerMock framesAnimationControllerMock;
	
	/**
	 * Mock that dont animate any DrawableElement. It just writes the frame number
	 * in the framesLog at each frame update and a 'f' when the animation ends.
	 */
	class FramesAnimationControllerMock extends FramesAnimationController {
		
		String tokenToPrintEveryUpdate = "";
		
		public FramesAnimationControllerMock() {
			setDrawableElement(new DrawableElement());
			setFinishWhenDrawableElementParentIsNull(false);
			
			setAnimationListener(new AnimationListener() {
				public void onAnimationFinish() {
					framesLog.append('f');
				}
			});
		}

		public void updateFrame(int frame) {
			framesLog.append(frame);
		}

		public void update() {
			framesLog.append(tokenToPrintEveryUpdate);
			super.update();
		}
	}

	public FramesAnimationControllerTest() {
	}

	public FramesAnimationControllerTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		framesLog = new StringBuffer();
		framesAnimationControllerMock = new FramesAnimationControllerMock();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new FramesAnimationControllerTest("testShouldUpdateFramesUntilItComesToTotalLenght", new TestMethod()
		{ public void run(TestCase tc) {((FramesAnimationControllerTest)tc).testShouldUpdateFramesUntilItComesToTotalLenght(); } } ));

		testSuite.addTest(new FramesAnimationControllerTest("testShouldUpdateFramesManyLoops", new TestMethod()
		{ public void run(TestCase tc) {((FramesAnimationControllerTest)tc).testShouldUpdateFramesManyLoops(); } } ));

		testSuite.addTest(new FramesAnimationControllerTest("testShouldBackToFirstFrameIfConfiguredToRewind", new TestMethod()
		{ public void run(TestCase tc) {((FramesAnimationControllerTest)tc).testShouldBackToFirstFrameIfConfiguredToRewind(); } } ));
		
		testSuite.addTest(new FramesAnimationControllerTest("testShouldUpdateFramesManyLoopsUsingPingPong", new TestMethod()
		{ public void run(TestCase tc) {((FramesAnimationControllerTest)tc).testShouldUpdateFramesManyLoopsUsingPingPong(); } } ));
		
		testSuite.addTest(new FramesAnimationControllerTest("testShouldUpdateFramesWithIntervalOfStepsBetweenFrames", new TestMethod()
		{ public void run(TestCase tc) {((FramesAnimationControllerTest)tc).testShouldUpdateFramesWithIntervalOfStepsBetweenFrames(); } } ));
		
		testSuite.addTest(new FramesAnimationControllerTest("testShouldUpdateFramesManyLoopsWithIntervalOfStepsBetweenLoops", new TestMethod()
		{ public void run(TestCase tc) {((FramesAnimationControllerTest)tc).testShouldUpdateFramesManyLoopsWithIntervalOfStepsBetweenLoops(); } } ));
		
		testSuite.addTest(new FramesAnimationControllerTest("testShouldUpdateFramesForeverWithInfiniteLoop", new TestMethod()
		{ public void run(TestCase tc) {((FramesAnimationControllerTest)tc).testShouldUpdateFramesForeverWithInfiniteLoop(); } } ));

		return testSuite;
	}
	
	public void testShouldUpdateFramesUntilItComesToTotalLenght() {
		
		// given:
		framesAnimationControllerMock.setLength(5);
		
		// when:
		for (int i=0; i<5; i++) {
			framesAnimationControllerMock.update();
		}
		
		// then:
		assertEquals("The frames of the animation is not the expected.", "01234f", framesLog.toString());
	}

	private void testShouldUpdateFramesManyLoops() {
		
		// given:
		framesAnimationControllerMock.setLength(5);
		framesAnimationControllerMock.setLoops(2);
		
		// when:
		for (int i=0; i<15; i++) {
			framesAnimationControllerMock.update();
		}
		
		// then:
		assertEquals("The frames of the animation is not the expected.", "012340123401234f", framesLog.toString());
	}

	private void testShouldBackToFirstFrameIfConfiguredToRewind() {
		
		// given:
		framesAnimationControllerMock.setLength(5);
		framesAnimationControllerMock.setRewind(true);
		
		// when:
		for (int i=0; i<5; i++) {
			framesAnimationControllerMock.update();
		}
		
		// then:
		assertEquals("The frames of the animation is not the expected.", "012340f", framesLog.toString());
	}

	private void testShouldUpdateFramesManyLoopsUsingPingPong() {
		
		// given:
		framesAnimationControllerMock.setLength(3);
		framesAnimationControllerMock.setLoops(5);
		framesAnimationControllerMock.setPingPong(true);
		
		// when:
		for (int i=0; i<13; i++) {
			framesAnimationControllerMock.update();
		}
		
		// then:
		assertEquals("The frames of the animation is not the expected.", "0121012101210f", framesLog.toString());
	}

	private void testShouldUpdateFramesWithIntervalOfStepsBetweenFrames() {
		
		// given:
		framesAnimationControllerMock.tokenToPrintEveryUpdate = "u";
		
		framesAnimationControllerMock.setLength(3);
		framesAnimationControllerMock.setStepsBetweenFrames(2);
		
		// when:
		for (int i=0; i<7; i++) {
			framesAnimationControllerMock.update();
		}
		
		// then:
		assertEquals("The frames of the animation is not the expected.", "u0uuu1uuu2f", framesLog.toString());
	}

	private void testShouldUpdateFramesManyLoopsWithIntervalOfStepsBetweenLoops() {
		
		// given:
		framesAnimationControllerMock.tokenToPrintEveryUpdate = "u";
		
		framesAnimationControllerMock.setLength(3);
		framesAnimationControllerMock.setStepsBetweenLoops(2);
		framesAnimationControllerMock.setLoops(2);
		
		// when:
		for (int i=0; i<13; i++) {
			framesAnimationControllerMock.update();
		}
		
		// then:
		assertEquals("The frames of the animation is not the expected.", "u0u1u2uuu0u1u2uuu0u1u2f", framesLog.toString());
	}

	private void testShouldUpdateFramesForeverWithInfiniteLoop() {
		
		// given:
		framesAnimationControllerMock.setLength(3);
		framesAnimationControllerMock.setLoops(FramesAnimationController.INFINITE_LOOPS);
		
		// when:
		for (int i=0; i<10; i++) {
			framesAnimationControllerMock.update();
		}
		
		// then:
		assertEquals("The frames of the animation is not the expected.", "0120120120", framesLog.toString());
	}
	
}
