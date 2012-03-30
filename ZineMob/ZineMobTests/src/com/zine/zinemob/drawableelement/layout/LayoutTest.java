package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class LayoutTest extends TestCase {

	public LayoutTest() {

	}

	public LayoutTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new LayoutTest("testMethodCalls", new TestMethod()
		{ public void run(TestCase tc) {((LayoutTest)tc).testMethodCalls(); } } ));

		return testSuite;
	}

	public void testMethodCalls() {
		
		// given:
		LayoutMock layoutFixerMock = new LayoutMock();
		
		DrawableElement parent = new DrawableElement();
		parent.setName("parent");

		DrawableElement drawableElement = new DrawableElement();
		drawableElement.setName("drawableElement");
		
		DrawableElement child1 = new DrawableElement();
		child1.setName("child1");
		DrawableElement child2 = new DrawableElement();
		child2.setName("child2");
		
		// when:
		drawableElement.addLayout(layoutFixerMock);
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
				+ "onParentChanged(drawableElement);onChildPositionChanged(drawableElement,child1);onChildSizeChanged(drawableElement,child1);"
				+ "onParentPositionChanged(drawableElement);onParentSizeChanged(drawableElement);onPositionChanged(drawableElement);"
				+ "onSizeChanged(drawableElement);onChildRemoved(drawableElement,child1);onChildRemoved(drawableElement,child2);";

		assertEquals("The methods calls of the AreaFixer are not the expected.", expectedCalls, layoutFixerMock.getCallLog());
	}

}
