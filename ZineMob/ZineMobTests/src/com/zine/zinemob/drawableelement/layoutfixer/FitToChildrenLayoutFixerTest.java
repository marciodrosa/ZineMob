package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class FitToChildrenLayoutFixerTest extends TestCase {

	private FitToChildrenLayoutFixer fitToChildrenLayoutFixer;
	private DrawableElement drawableElement, child1, child2;

	public FitToChildrenLayoutFixerTest() {

	}

	public FitToChildrenLayoutFixerTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		fitToChildrenLayoutFixer = new FitToChildrenLayoutFixer();

		drawableElement = new DrawableElement();
		drawableElement.setPosition(100, 200);
		drawableElement.setSize(300, 400);
		drawableElement.setPadding(1, 2, 3, 4);
		drawableElement.setMargin(5, 6, 7, 8); // not used

		child1 = new DrawableElement();
		child1.setPosition(-10, -20);
		child1.setSize(50, 50);
		child1.setPadding(9, 10, 11, 12); // not used
		child1.setMargin(13, 14, 15, 16);
		drawableElement.addChild(child1);

		child2 = new DrawableElement();
		child2.setPosition(10, 20);
		child2.setSize(1000, 2000);
		child2.setPadding(17, 18, 19, 20); // not used
		child2.setMargin(21, 22, 23, 24);
		drawableElement.addChild(child2);
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testApplyFix(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnPositionChangedMustDoNothing", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnPositionChangedMustDoNothing(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnSizeChangedMustApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnSizeChangedMustApplyFix(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnParentPositionChangedMustDoNothing", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnParentPositionChangedMustDoNothing(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnParentSizeChangedChangedMustDoNothing", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnParentSizeChangedChangedMustDoNothing(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnChildPositionChangedMustApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnChildPositionChangedMustApplyFix(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnChildSizeChangedMustApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnChildSizeChangedMustApplyFix(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnChildAddedMustApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnChildAddedMustApplyFix(); } } ));

		testSuite.addTest(new FitToChildrenLayoutFixerTest("testOnChildRemovedMustApplyFix", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testOnChildRemovedMustApplyFix(); } } ));
		
		testSuite.addTest(new FitToChildrenLayoutFixerTest("testApplyFixWithoutChildren", new TestMethod()
		{ public void run(TestCase tc) {((FitToChildrenLayoutFixerTest)tc).testApplyFixWithoutChildren(); } } ));

		return testSuite;
	}

	public void testApplyFix() {
		
		// when:
		fitToChildrenLayoutFixer.applyFix(drawableElement);

		// then:
		assertAfterApplyFix();
	}

	public void testOnPositionChangedMustDoNothing() {

		// when:
		fitToChildrenLayoutFixer.onPositionChanged(drawableElement);

		// then:
		assertApplyFixWasNotCalled();
	}

	public void testOnSizeChangedMustApplyFix() {

		// when:
		fitToChildrenLayoutFixer.onSizeChanged(drawableElement);

		// then:
		assertAfterApplyFix();
	}

	public void testOnParentPositionChangedMustDoNothing() {

		// when:
		fitToChildrenLayoutFixer.onParentPositionChanged(drawableElement);

		// then:
		assertApplyFixWasNotCalled();
	}

	public void testOnParentSizeChangedChangedMustDoNothing() {

		// when:
		fitToChildrenLayoutFixer.onParentSizeChanged(drawableElement);

		// then:
		assertApplyFixWasNotCalled();
	}

	public void testOnChildPositionChangedMustApplyFix() {

		// when:
		fitToChildrenLayoutFixer.onChildPositionChanged(drawableElement, child1);

		// then:
		assertAfterApplyFix();
	}

	public void testOnChildSizeChangedMustApplyFix() {

		// when:
		fitToChildrenLayoutFixer.onChildSizeChanged(drawableElement, child1);

		// then:
		assertAfterApplyFix();
	}

	public void testOnChildAddedMustApplyFix() {

		// when:
		fitToChildrenLayoutFixer.onChildAdded(drawableElement, child1);

		// then:
		assertAfterApplyFix();
	}

	public void testOnChildRemovedMustApplyFix() {

		// when:
		fitToChildrenLayoutFixer.onChildRemoved(drawableElement, child1);

		// then:
		assertAfterApplyFix();
	}

	public void testApplyFixWithoutChildren() {

		// given:
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.setPosition(10, 20);
		drawableElement.setSize(30, 40);

		// when:
		fitToChildrenLayoutFixer.applyFix(drawableElement);

		// then:
		assertEquals("The position X must remain the same.", 10, drawableElement.getX());
		assertEquals("The position Y must remain the same.", 20, drawableElement.getY());
		assertEquals("The width must be 0 because there is no children.", 0, drawableElement.getWidth());
		assertEquals("The height must be 0 because there is no children.", 0, drawableElement.getHeight());
	}

	private void assertAfterApplyFix() {

		assertEquals("The position X is not the expected.", 76, drawableElement.getX()); // ok
		assertEquals("The position Y is not the expected.", 164, drawableElement.getY()); // verificar
		assertEquals("The width is not the expected.", 1060, drawableElement.getWidth()); // ok
		assertEquals("The height is not the expected.", 2084, drawableElement.getHeight()); // verificar

		assertEquals("The position X of the child 1 is not the expected (must remains the same global position)", 90, child1.getGlobalX());
		assertEquals("The position Y of the child 1 is not the expected (must remains the same global position)", 180, child1.getGlobalY());
		assertEquals("The width of the child 1 must remains the same.", 50, child1.getWidth());
		assertEquals("The height of the child 1 must remains the same.", 50, child1.getHeight());

		assertEquals("The position X of the child 2 is not the expected (must remains the same global position)", 110, child2.getGlobalX());
		assertEquals("The position Y of the child 2 is not the expected (must remains the same global position)", 220, child2.getGlobalY());
		assertEquals("The width of the child 2 must remains the same.", 1000, child2.getWidth());
		assertEquals("The height of the child 2 must remains the same.", 2000, child2.getHeight());
	}
	
	private void assertApplyFixWasNotCalled() {

		assertEquals("The position X must remains the same.", 100, drawableElement.getX());
		assertEquals("The position Y must remains the same.", 200, drawableElement.getY());
		assertEquals("The width must remains the same.", 300, drawableElement.getWidth());
		assertEquals("The height must remains the same.", 400, drawableElement.getHeight());
	}

}
