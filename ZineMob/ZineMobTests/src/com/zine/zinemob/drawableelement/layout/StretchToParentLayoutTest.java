package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class StretchToParentLayoutTest extends TestCase {

	private StretchToParentLayout stretchToParentLayoutFixer;
	private DrawableElement parent, drawableElement;

	public StretchToParentLayoutTest() {

	}

	public StretchToParentLayoutTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {

		parent = new DrawableElement();
		parent.setPosition(100, 200);
		parent.setSize(300, 400);

		drawableElement = new DrawableElement();
		drawableElement.setPosition(10, 20);
		drawableElement.setSize(50, 50);
		parent.addChild(drawableElement);
		
		stretchToParentLayoutFixer = new StretchToParentLayout(drawableElement);
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new StretchToParentLayoutTest("testApply", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testApply(); } } ));

		return testSuite;
	}

	public void testApply() {

		// when:
		stretchToParentLayoutFixer.apply();

		// then:
		assertPositionAndSizeOfTheChild(0, 0, 300, 400);
	}

	private void assertPositionAndSizeOfTheChild(int x, int y, int w, int h) {
		
		assertEquals("The position X is not the expected.", x, drawableElement.getX());
		assertEquals("The position Y is not the expected.", y, drawableElement.getY());
		assertEquals("The width is not the expected.", w, drawableElement.getWidth());
		assertEquals("The height is not the expected.", h, drawableElement.getHeight());

	}
}
