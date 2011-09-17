package com.zine.zinemob.animation;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class ResizeAnimationControllerTest extends TestCase {
	
	public ResizeAnimationControllerTest() {
	}

	public ResizeAnimationControllerTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new ResizeAnimationControllerTest("testUpdateShouldResizeTheDrawableElementFromTo", new TestMethod()
		{ public void run(TestCase tc) {((ResizeAnimationControllerTest)tc).testUpdateShouldResizeTheDrawableElementFromTo(); } } ));

		return testSuite;
	}

	private void testUpdateShouldResizeTheDrawableElementFromTo() {
		
		// given:
		DrawableElement drawableElement = new DrawableElement();
		
		ResizeAnimationController resizeAnimationController = new ResizeAnimationController(drawableElement);
		resizeAnimationController.setLength(3);
		resizeAnimationController.setResizeFromTo(10, 100, 30, 300);
		
		// when / then:
		resizeAnimationController.update();
		assertEquals("The width at update 1 is not the expected.", 10, drawableElement.getWidth());
		assertEquals("The height at update 1 is not the expected.", 100, drawableElement.getHeight());
		
		resizeAnimationController.update();
		assertEquals("The width at update 2 is not the expected.", 20, drawableElement.getWidth());
		assertEquals("The height at update 2 is not the expected.", 200, drawableElement.getHeight());
		
		resizeAnimationController.update();
		assertEquals("The width at update 3 is not the expected.", 30, drawableElement.getWidth());
		assertEquals("The height at update 3 is not the expected.", 300, drawableElement.getHeight());
	}
	
}
