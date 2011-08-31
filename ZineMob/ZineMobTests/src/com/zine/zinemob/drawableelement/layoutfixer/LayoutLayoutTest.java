package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class LayoutLayoutTest extends TestCase {

	public LayoutLayoutTest() {

	}

	public LayoutLayoutTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new LayoutLayoutTest("testMethodCalls", new TestMethod()
		{ public void run(TestCase tc) {((LayoutLayoutTest)tc).testMethodCalls(); } } ));

		return testSuite;
	}

	public void testMethodCalls() {
		
		// given:
		LayoutFixerMock layoutFixerMock = new LayoutFixerMock();
		
		DrawableElement parent = new DrawableElement();
		parent.setName("parent");

		DrawableElement drawableElement = new DrawableElement();
		drawableElement.setName("drawableElement");
		
		DrawableElement child1 = new DrawableElement();
		child1.setName("child1");
		DrawableElement child2 = new DrawableElement();
		child2.setName("child2");
		
		// when:
		drawableElement.addAreaFixer(layoutFixerMock);
		drawableElement.addChild(child1);
		drawableElement.addChild(child2, 0);
		parent.addChild(drawableElement);
		child1.setPosition(10, 10);
		child1.setSize(10, 10);
		parent.setPosition(10, 10);
		parent.setSize(10, 10);
		drawableElement.setPosition(10, 10);
		drawableElement.setSize(10, 10);
		drawableElement.removeChild(child1);
		drawableElement.removeChildren();

		// then:
		String expectedCalls = "applyFix(drawableElement);onChildAdded(drawableElement,child1);onChildAdded(drawableElement,child2);"
				+ "onChildPositionChanged(drawableElement,child1);onChildSizeChanged(drawableElement,child1);"
				+ "onParentPositionChanged(drawableElement);onParentSizeChanged(drawableElement);onPositionChanged(drawableElement);"
				+ "onSizeChanged(drawableElement);onChildRemoved(drawableElement,child1);onChildRemoved(drawableElement,child2);";

		assertEquals("The methods calls of the AreaFixer are not the expected.", expectedCalls, layoutFixerMock.getCallLog());
	}

}
