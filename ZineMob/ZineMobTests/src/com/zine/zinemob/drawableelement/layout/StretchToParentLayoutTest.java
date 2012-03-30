package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.layout.StretchToParentLayout;
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
		stretchToParentLayoutFixer = new StretchToParentLayout();

		parent = new DrawableElement();
		parent.setPosition(100, 200);
		parent.setSize(300, 400);
		parent.setPadding(1, 2, 3, 4);
		parent.setMargin(5, 6, 7, 8); // not used

		drawableElement = new DrawableElement();
		drawableElement.setPosition(10, 20);
		drawableElement.setSize(50, 50);
		drawableElement.setPadding(9, 10, 11, 12); // not used
		drawableElement.setMargin(13, 14, 15, 16);
		parent.addChild(drawableElement);
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new StretchToParentLayoutTest("testApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testApplyFix(); } } ));

		return testSuite;
	}

	public void testApplyFix() {

		// when:
		stretchToParentLayoutFixer.applyFix(drawableElement);

		// then:
		assertPositionAndSizeOfTheChild(14, 16, 268, 364);
	}

	private void assertPositionAndSizeOfTheChild(int x, int y, int w, int h) {
		
		assertEquals("The position X is not the expected.", x, drawableElement.getX());
		assertEquals("The position Y is not the expected.", y, drawableElement.getY());
		assertEquals("The width is not the expected.", w, drawableElement.getWidth());
		assertEquals("The height is not the expected.", h, drawableElement.getHeight());

	}
}
