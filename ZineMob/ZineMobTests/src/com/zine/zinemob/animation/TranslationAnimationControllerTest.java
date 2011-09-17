package com.zine.zinemob.animation;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class TranslationAnimationControllerTest extends TestCase {
	
	public TranslationAnimationControllerTest() {
	}

	public TranslationAnimationControllerTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new TranslationAnimationControllerTest("testUpdateShouldMoveTheDrawableElementFromTo", new TestMethod()
		{ public void run(TestCase tc) {((TranslationAnimationControllerTest)tc).testUpdateShouldMoveTheDrawableElementFromTo(); } } ));

		return testSuite;
	}

	private void testUpdateShouldMoveTheDrawableElementFromTo() {
		
		// given:
		DrawableElement drawableElement = new DrawableElement();
		
		TranslationAnimationController translationAnimationController = new TranslationAnimationController(drawableElement);
		translationAnimationController.setLength(3);
		translationAnimationController.setMoveFromTo(10, 100, 30, 300);
		
		// when / then:
		translationAnimationController.update();
		assertEquals("The position X at update 1 is not the expected.", 10, drawableElement.getX());
		assertEquals("The position Y at update 1 is not the expected.", 100, drawableElement.getY());
		
		translationAnimationController.update();
		assertEquals("The position X at update 2 is not the expected.", 20, drawableElement.getX());
		assertEquals("The position Y at update 2 is not the expected.", 200, drawableElement.getY());
		
		translationAnimationController.update();
		assertEquals("The position X at update 3 is not the expected.", 30, drawableElement.getX());
		assertEquals("The position Y at update 3 is not the expected.", 300, drawableElement.getY());
	}
	
}
