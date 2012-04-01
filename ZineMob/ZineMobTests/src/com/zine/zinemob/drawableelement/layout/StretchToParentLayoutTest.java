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

		testSuite.addTest(new StretchToParentLayoutTest("testOnParentChangeShouldApplyLayout", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testOnParentChangeShouldApplyLayout(); } } ));

		testSuite.addTest(new StretchToParentLayoutTest("testOnParentSizeChangeShouldApplyLayout", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testOnParentSizeChangeShouldApplyLayout(); } } ));

		testSuite.addTest(new StretchToParentLayoutTest("testShouldStretchToBottomAndRightOnly", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testShouldStretchToBottomAndRightOnly(); } } ));

		testSuite.addTest(new StretchToParentLayoutTest("testShouldStretchToTopAndLeftOnly", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testShouldStretchToTopAndLeftOnly(); } } ));

		testSuite.addTest(new StretchToParentLayoutTest("testShouldDoNothingWhenParentIsNull", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testShouldDoNothingWhenParentIsNull(); } } ));

		testSuite.addTest(new StretchToParentLayoutTest("testConstructorShouldSetConstraints", new TestMethod()
		{ public void run(TestCase tc) {((StretchToParentLayoutTest)tc).testConstructorShouldSetConstraints(); } } ));

		return testSuite;
	}

	private void assertPositionAndSizeOfTheChild(int x, int y, int w, int h) {
		assertEquals("The position X is not the expected.", x, drawableElement.getX());
		assertEquals("The position Y is not the expected.", y, drawableElement.getY());
		assertEquals("The width is not the expected.", w, drawableElement.getWidth());
		assertEquals("The height is not the expected.", h, drawableElement.getHeight());
	}

	public void testApply() {
		// when:
		stretchToParentLayoutFixer.apply();

		// then:
		assertPositionAndSizeOfTheChild(0, 0, 300, 400);
	}

	private void testOnParentChangeShouldApplyLayout() {
		// when:
		stretchToParentLayoutFixer.onParentChanged();

		// then:
		assertPositionAndSizeOfTheChild(0, 0, 300, 400);
	}

	private void testOnParentSizeChangeShouldApplyLayout() {
		// when:
		stretchToParentLayoutFixer.onParentSizeChanged();

		// then:
		assertPositionAndSizeOfTheChild(0, 0, 300, 400);
	}

	private void testShouldStretchToBottomAndRightOnly() {
		// given:
		stretchToParentLayoutFixer.setConstraints(StretchToParentLayout.BOTTOM | StretchToParentLayout.RIGHT);
		
		// when:
		stretchToParentLayoutFixer.onParentSizeChanged();

		// then:
		assertPositionAndSizeOfTheChild(10, 20, 290, 380);
	}

	private void testShouldStretchToTopAndLeftOnly() {
		// given:
		stretchToParentLayoutFixer.setConstraints(StretchToParentLayout.TOP | StretchToParentLayout.LEFT);
		
		// when:
		stretchToParentLayoutFixer.onParentSizeChanged();

		// then:
		assertPositionAndSizeOfTheChild(0, 0, 60, 70);
	}

	private void testShouldDoNothingWhenParentIsNull() {
		// given:
		parent.removeChild(drawableElement);
		
		// when:
		stretchToParentLayoutFixer.onParentSizeChanged();

		// then:
		assertPositionAndSizeOfTheChild(10, 20, 50, 50);
	}

	private void testConstructorShouldSetConstraints() {
		// given:
		int constraints = StretchToParentLayout.BOTTOM | StretchToParentLayout.RIGHT;
		
		// when:
		stretchToParentLayoutFixer = new StretchToParentLayout(drawableElement, constraints);
		
		// then:
		assertEquals("The constraints are not the expected.", constraints, stretchToParentLayoutFixer.getConstraints());
	}
}
