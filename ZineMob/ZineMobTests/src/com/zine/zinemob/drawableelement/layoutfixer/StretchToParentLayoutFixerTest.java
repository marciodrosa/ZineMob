package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class StretchToParentLayoutFixerTest extends TestCase {

	private StretchToParentLayoutFixer stretchToParentLayoutFixer;
	private DrawableElement parent, drawableElement, child;

	public StretchToParentLayoutFixerTest() {

	}

	public StretchToParentLayoutFixerTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		stretchToParentLayoutFixer = new StretchToParentLayoutFixer();

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

		child = new DrawableElement();
		drawableElement.addChild(child);
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new StretchToParentLayoutFixerTest("testApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutFixerTest)tc).testApplyFix(); } } ));

		return testSuite;
	}

	public void testApplyFix() {

		// when:
		fitToChildrenLayoutFixer.applyFix(parent);

		// then:
		assertAfterApplyFix(StretchToParentLayoutFixer.LEFT | StretchToParentLayoutFixer.TOP | StretchToParentLayoutFixer.RIGHT | StretchToParentLayoutFixer.BOTTOM);
	}

	private void assertAfterApplyFix(int constraints) {

		assertEquals("The position X is not the expected.", 76, parent.getX()); // ok
		assertEquals("The position Y is not the expected.", 164, parent.getY()); // verificar
		assertEquals("The width is not the expected.", 1060, parent.getWidth()); // ok
		assertEquals("The height is not the expected.", 2084, parent.getHeight()); // verificar

		assertEquals("The position X of the child 1 is not the expected (must remains the same global position)", 90, drawableElement.getGlobalX());
		assertEquals("The position Y of the child 1 is not the expected (must remains the same global position)", 180, drawableElement.getGlobalY());
		assertEquals("The width of the child 1 must remains the same.", 50, drawableElement.getWidth());
		assertEquals("The height of the child 1 must remains the same.", 50, drawableElement.getHeight());

	}
	
	private void assertApplyFixWasNotCalled() {

		assertEquals("The position X must remains the same.", 100, parent.getX());
		assertEquals("The position Y must remains the same.", 200, parent.getY());
		assertEquals("The width must remains the same.", 300, parent.getWidth());
		assertEquals("The height must remains the same.", 400, parent.getHeight());
	}

}
