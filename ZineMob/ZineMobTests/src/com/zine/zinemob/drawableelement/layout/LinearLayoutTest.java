package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.layout.LinearLayout;
import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class LinearLayoutTest extends TestCase {
	
	private LinearLayout linearLayoutFixer;

	public LinearLayoutTest() {

	}

	public LinearLayoutTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		linearLayoutFixer = new LinearLayout();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldLayoutChildrenVerticallyAndFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldLayoutChildrenVerticallyAndFit(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldLayoutChildrenVerticallyAndDontFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldLayoutChildrenVerticallyAndDontFit(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldLayoutChildrenVerticallyAndStretchSpace", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldLayoutChildrenVerticallyAndStretchSpace(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldLayoutChildrenHorizontallyAndFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldLayoutChildrenHorizontallyAndFit(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldLayoutChildrenHorizontallyAndDontFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldLayoutChildrenHorizontallyAndDontFit(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldLayoutChildrenHorizontallyAndStretchSpace", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldLayoutChildrenHorizontallyAndStretchSpace(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldRespectThePaddingAndMargin", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldRespectThePaddingAndMargin(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldRespectThePaddingAndMarginAndFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldRespectThePaddingAndMarginAndFit(); } } ));

		testSuite.addTest(new LinearLayoutTest("testApplyFixShouldIgnoreChild", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testApplyFixShouldIgnoreChild(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldFitToChildrenHorizontallyExpandingTheActualSize", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldFitToChildrenHorizontallyExpandingTheActualSize(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldFitToChildrenHorizontallyDecreasingTheActualSize", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldFitToChildrenHorizontallyDecreasingTheActualSize(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldFitToChildrenVerticallyExpandingTheActualSize", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldFitToChildrenVerticallyExpandingTheActualSize(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldFitToChildrenVerticallyDecreasingTheActualSize", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldFitToChildrenVerticallyDecreasingTheActualSize(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldFitToChildrenWhenSpaceIsSmallerHorizontally", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldFitToChildrenWhenSpaceIsSmallerHorizontally(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldNotFitToChildrenWhenSpaceIsNotSmallerHorizontally", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldNotFitToChildrenWhenSpaceIsNotSmallerHorizontally(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldFitToChildrenWhenSpaceIsSmallerVertically", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldFitToChildrenWhenSpaceIsSmallerVertically(); } } ));

		testSuite.addTest(new LinearLayoutTest("testShouldNotFitToChildrenWhenSpaceIsNotSmallerVertically", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutTest)tc).testShouldNotFitToChildrenWhenSpaceIsNotSmallerVertically(); } } ));

		return testSuite;
	}
	
	private void assertDrawableElementPositionAndSize(String elementDescription, int expectedX, int expectedY, int expectedWidth, int expectedHeight, DrawableElement drawableElement) {
		
		assertEquals(elementDescription + " dont have the expected position X.", expectedX, drawableElement.getX());
		assertEquals(elementDescription + " dont have the expected position Y.", expectedY, drawableElement.getY());
		assertEquals(elementDescription + " dont have the expected width.", expectedWidth, drawableElement.getWidth());
		assertEquals(elementDescription + " dont have the expected height.", expectedHeight, drawableElement.getHeight());
	}
	
	private DrawableElement createDrawableElementWithSize(int w, int h) {
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.setSize(w, h);
		return drawableElement;
	}
	
	private DrawableElement createDrawableElementWithSizeAndMargin(int w, int h, int marginLeft, int margingTop, int marginRight, int marginBottom) {
		DrawableElement drawableElement = createDrawableElementWithSize(w, h);
		drawableElement.setMargin(marginLeft, margingTop, marginRight, marginBottom);
		return drawableElement;
	}

	public void testApplyFixShouldLayoutChildrenVerticallyAndFit() {
		
		// given:
		DrawableElement drawableElement = new DrawableElement();
		DrawableElement childAtLeft = createDrawableElementWithSize(10, 20);
		DrawableElement childAtRight = createDrawableElementWithSize(30, 40);
		DrawableElement childStretched = createDrawableElementWithSize(50, 60);
		DrawableElement childAtCenter = createDrawableElementWithSize(70, 80);
		DrawableElement bigChild = createDrawableElementWithSize(90, 100);
		
		drawableElement.addChild(childAtLeft);
		drawableElement.addChild(childAtRight);
		drawableElement.addChild(childStretched);
		drawableElement.addChild(childAtCenter);
		drawableElement.addChild(bigChild);
		
		linearLayoutFixer.setLayoutFlags(childAtRight, LinearLayout.ALIGN_RIGHT);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayout.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayout.FIT);
		linearLayoutFixer.setFitToChildren(true);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 90, 300, drawableElement);
		assertDrawableElementPositionAndSize("childAtLeft", 0, 0, 10, 20, childAtLeft);
		assertDrawableElementPositionAndSize("childAtRight", 60, 20, 30, 40, childAtRight);
		assertDrawableElementPositionAndSize("childStretched", 0, 60, 90, 60, childStretched);
		assertDrawableElementPositionAndSize("childAtCenter", 10, 120, 70, 80, childAtCenter);
		assertDrawableElementPositionAndSize("bigChild", 0, 200, 90, 100, bigChild);
	}

	public void testApplyFixShouldLayoutChildrenVerticallyAndDontFit() {
		
		// given:
		DrawableElement drawableElement = createDrawableElementWithSize(200, 500);
		DrawableElement childAtLeft = createDrawableElementWithSize(10, 20);
		DrawableElement childAtRight = createDrawableElementWithSize(30, 40);
		DrawableElement childStretched = createDrawableElementWithSize(50, 60);
		DrawableElement childAtCenter = createDrawableElementWithSize(70, 80);
		DrawableElement bigChild = createDrawableElementWithSize(90, 100);
		
		drawableElement.addChild(childAtLeft);
		drawableElement.addChild(childAtRight);
		drawableElement.addChild(childStretched);
		drawableElement.addChild(childAtCenter);
		drawableElement.addChild(bigChild);
		
		linearLayoutFixer.setLayoutFlags(childAtRight, LinearLayout.ALIGN_RIGHT);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayout.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayout.FIT);
		linearLayoutFixer.setFitToChildren(false);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 200, 500, drawableElement);
		assertDrawableElementPositionAndSize("childAtLeft", 0, 0, 10, 20, childAtLeft);
		assertDrawableElementPositionAndSize("childAtRight", 170, 20, 30, 40, childAtRight);
		assertDrawableElementPositionAndSize("childStretched", 0, 60, 200, 60, childStretched);
		assertDrawableElementPositionAndSize("childAtCenter", 65, 120, 70, 80, childAtCenter);
		assertDrawableElementPositionAndSize("bigChild", 0, 200, 90, 100, bigChild);
	}

	private void testApplyFixShouldLayoutChildrenVerticallyAndStretchSpace() {
		
		// given:
		DrawableElement drawableElement = createDrawableElementWithSize(200, 720);
		DrawableElement childNotStretchable1 = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignRightAndBottom = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignCenterHorizontally = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignCenterVertically = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignCenterHorizontallyAndVertically = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAndStretchVertically = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAndStretchHorizontally = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAndStretchVerticallyAndHorizontally = createDrawableElementWithSize(10, 10);
		DrawableElement childNotStretchable2 = createDrawableElementWithSize(10, 10);
		
		drawableElement.addChild(childNotStretchable1);
		drawableElement.addChild(childStretchSpaceAlignRightAndBottom);
		drawableElement.addChild(childStretchSpaceAlignCenterHorizontally);
		drawableElement.addChild(childStretchSpaceAlignCenterVertically);
		drawableElement.addChild(childStretchSpaceAlignCenterHorizontallyAndVertically);
		drawableElement.addChild(childStretchSpaceAndStretchVertically);
		drawableElement.addChild(childStretchSpaceAndStretchHorizontally);
		drawableElement.addChild(childStretchSpaceAndStretchVerticallyAndHorizontally);
		drawableElement.addChild(childNotStretchable2);
		
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignRightAndBottom, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_RIGHT | LinearLayout.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontally, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_CENTER_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterVertically, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontallyAndVertically, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVertically, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.FIT_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchHorizontally, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.FIT_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVerticallyAndHorizontally, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.FIT);
		linearLayoutFixer.setFitToChildren(false);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 200, 720, drawableElement);
		assertDrawableElementPositionAndSize("childNotStretchable1", 0, 0, 10, 10, childNotStretchable1);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignRightAndBottom", 190, 100, 10, 10, childStretchSpaceAlignRightAndBottom);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignCenterHorizontally", 95, 110, 10, 10, childStretchSpaceAlignCenterHorizontally);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignCenterVertically", 0, 255, 10, 10, childStretchSpaceAlignCenterVertically);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignCenterHorizontallyAndVertically", 95, 355, 10, 10, childStretchSpaceAlignCenterHorizontallyAndVertically);
		assertDrawableElementPositionAndSize("childStretchSpaceAndStretchVertically", 0, 410, 10, 100, childStretchSpaceAndStretchVertically);
		assertDrawableElementPositionAndSize("childStretchSpaceAndStretchHorizontally", 0, 510, 200, 10, childStretchSpaceAndStretchHorizontally);
		assertDrawableElementPositionAndSize("childStretchSpaceAndStretchVerticallyAndHorizontally", 0, 610, 200, 100, childStretchSpaceAndStretchVerticallyAndHorizontally);
		assertDrawableElementPositionAndSize("childNotStretchable2", 0, 710, 10, 10, childNotStretchable2);
	}

	private void testApplyFixShouldLayoutChildrenHorizontallyAndFit() {
		
		// given:
		DrawableElement drawableElement = new DrawableElement();
		DrawableElement childAtTop = createDrawableElementWithSize(10, 20);
		DrawableElement childAtBottom = createDrawableElementWithSize(30, 40);
		DrawableElement childStretched = createDrawableElementWithSize(50, 60);
		DrawableElement childAtCenter = createDrawableElementWithSize(70, 80);
		DrawableElement bigChild = createDrawableElementWithSize(90, 100);
		
		drawableElement.addChild(childAtTop);
		drawableElement.addChild(childAtBottom);
		drawableElement.addChild(childStretched);
		drawableElement.addChild(childAtCenter);
		drawableElement.addChild(bigChild);
		
		linearLayoutFixer.setLayoutType(LinearLayout.LAYOUT_TYPE_HORIZONTAL);
		linearLayoutFixer.setLayoutFlags(childAtTop, LinearLayout.ALIGN_TOP);
		linearLayoutFixer.setLayoutFlags(childAtBottom, LinearLayout.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayout.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayout.FIT_V);
		linearLayoutFixer.setFitToChildren(true);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 250, 100, drawableElement);
		assertDrawableElementPositionAndSize("childAtTop", 0, 0, 10, 20, childAtTop);
		assertDrawableElementPositionAndSize("childAtBottom", 10, 60, 30, 40, childAtBottom);
		assertDrawableElementPositionAndSize("childStretched", 40, 0, 50, 100, childStretched);
		assertDrawableElementPositionAndSize("childAtCenter", 90, 10, 70, 80, childAtCenter);
		assertDrawableElementPositionAndSize("bigChild", 160, 0, 90, 100, bigChild);
	}

	private void testApplyFixShouldLayoutChildrenHorizontallyAndDontFit() {
		
		// given:
		DrawableElement drawableElement = createDrawableElementWithSize(500, 200);
		DrawableElement childAtTop = createDrawableElementWithSize(10, 20);
		DrawableElement childAtBottom = createDrawableElementWithSize(30, 40);
		DrawableElement childStretched = createDrawableElementWithSize(50, 60);
		DrawableElement childAtCenter = createDrawableElementWithSize(70, 80);
		DrawableElement bigChild = createDrawableElementWithSize(90, 100);
		
		drawableElement.addChild(childAtTop);
		drawableElement.addChild(childAtBottom);
		drawableElement.addChild(childStretched);
		drawableElement.addChild(childAtCenter);
		drawableElement.addChild(bigChild);
		
		linearLayoutFixer.setLayoutType(LinearLayout.LAYOUT_TYPE_HORIZONTAL);
		linearLayoutFixer.setLayoutFlags(childAtTop, LinearLayout.ALIGN_TOP);
		linearLayoutFixer.setLayoutFlags(childAtBottom, LinearLayout.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayout.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayout.FIT_V);
		linearLayoutFixer.setFitToChildren(false);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 500, 200, drawableElement);
		assertDrawableElementPositionAndSize("childAtTop", 0, 0, 10, 20, childAtTop);
		assertDrawableElementPositionAndSize("childAtBottom", 10, 160, 30, 40, childAtBottom);
		assertDrawableElementPositionAndSize("childStretched", 40, 0, 50, 200, childStretched);
		assertDrawableElementPositionAndSize("childAtCenter", 90, 60, 70, 80, childAtCenter);
		assertDrawableElementPositionAndSize("bigChild", 160, 0, 90, 100, bigChild);
	}

	private void testApplyFixShouldLayoutChildrenHorizontallyAndStretchSpace() {
		
		// given:
		DrawableElement drawableElement = createDrawableElementWithSize(720, 200);
		DrawableElement childNotStretchable1 = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignRightAndBottom = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignCenterHorizontally = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignCenterVertically = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAlignCenterHorizontallyAndVertically = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAndStretchVertically = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAndStretchHorizontally = createDrawableElementWithSize(10, 10);
		DrawableElement childStretchSpaceAndStretchVerticallyAndHorizontally = createDrawableElementWithSize(10, 10);
		DrawableElement childNotStretchable2 = createDrawableElementWithSize(10, 10);
		
		drawableElement.addChild(childNotStretchable1);
		drawableElement.addChild(childStretchSpaceAlignRightAndBottom);
		drawableElement.addChild(childStretchSpaceAlignCenterHorizontally);
		drawableElement.addChild(childStretchSpaceAlignCenterVertically);
		drawableElement.addChild(childStretchSpaceAlignCenterHorizontallyAndVertically);
		drawableElement.addChild(childStretchSpaceAndStretchVertically);
		drawableElement.addChild(childStretchSpaceAndStretchHorizontally);
		drawableElement.addChild(childStretchSpaceAndStretchVerticallyAndHorizontally);
		drawableElement.addChild(childNotStretchable2);
		
		linearLayoutFixer.setLayoutType(LinearLayout.LAYOUT_TYPE_HORIZONTAL);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignRightAndBottom, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_RIGHT | LinearLayout.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontally, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_CENTER_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterVertically, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontallyAndVertically, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVertically, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.FIT_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchHorizontally, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.FIT_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVerticallyAndHorizontally, LinearLayout.FIT_AVAILABLE_SPACE | LinearLayout.FIT);
		linearLayoutFixer.setFitToChildren(false);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 720, 200, drawableElement);
		assertDrawableElementPositionAndSize("childNotStretchable1", 0, 0, 10, 10, childNotStretchable1);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignRightAndBottom", 100, 190, 10, 10, childStretchSpaceAlignRightAndBottom);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignCenterHorizontally", 155, 0, 10, 10, childStretchSpaceAlignCenterHorizontally);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignCenterVertically", 210, 95, 10, 10, childStretchSpaceAlignCenterVertically);
		assertDrawableElementPositionAndSize("childStretchSpaceAlignCenterHorizontallyAndVertically", 355, 95, 10, 10, childStretchSpaceAlignCenterHorizontallyAndVertically);
		assertDrawableElementPositionAndSize("childStretchSpaceAndStretchVertically", 410, 0, 10, 200, childStretchSpaceAndStretchVertically);
		assertDrawableElementPositionAndSize("childStretchSpaceAndStretchHorizontally", 510, 0, 100, 10, childStretchSpaceAndStretchHorizontally);
		assertDrawableElementPositionAndSize("childStretchSpaceAndStretchVerticallyAndHorizontally", 610, 0, 100, 200, childStretchSpaceAndStretchVerticallyAndHorizontally);
		assertDrawableElementPositionAndSize("childNotStretchable2", 710, 0, 10, 10, childNotStretchable2);
	}

	private void testApplyFixShouldRespectThePaddingAndMargin() {
		
		// given:
		DrawableElement drawableElement = createDrawableElementWithSize(500, 500);
		drawableElement.setPadding(50, 60, 70, 80);
		DrawableElement childAtTopLeft = createDrawableElementWithSizeAndMargin(10, 10, 1, 2, 3, 4);
		DrawableElement childAtBottomRight = createDrawableElementWithSizeAndMargin(10, 10, 5, 6, 7, 8);
		DrawableElement childAtCenter = createDrawableElementWithSizeAndMargin(10, 10, 9, 10, 11, 12);
		DrawableElement childStretched = createDrawableElementWithSizeAndMargin(10, 10, 13, 14, 15, 16);
		
		drawableElement.addChild(childAtTopLeft);
		drawableElement.addChild(childAtBottomRight);
		drawableElement.addChild(childAtCenter);
		drawableElement.addChild(childStretched);
		
		linearLayoutFixer.setLayoutFlags(childAtBottomRight, LinearLayout.ALIGN_BOTTOM | LinearLayout.ALIGN_RIGHT);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayout.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayout.FIT | LinearLayout.FIT_AVAILABLE_SPACE);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 500, 500, drawableElement);
		assertDrawableElementPositionAndSize("childAtTopLeft", 51, 62, 10, 10, childAtTopLeft);
		assertDrawableElementPositionAndSize("childAtBottomRight", 413, 82, 10, 10, childAtBottomRight);
		assertDrawableElementPositionAndSize("childAtCenter", 235, 110, 10, 10, childAtCenter);
		assertDrawableElementPositionAndSize("childStretched", 63, 146, 352, 258, childStretched);
	}

	private void testApplyFixShouldRespectThePaddingAndMarginAndFit() {
		
		// given:
		DrawableElement drawableElement = createDrawableElementWithSize(500, 500);
		drawableElement.setPadding(50, 60, 70, 80);
		DrawableElement childAtTopLeft = createDrawableElementWithSizeAndMargin(10, 10, 1, 2, 3, 4);
		DrawableElement childAtBottomRight = createDrawableElementWithSizeAndMargin(10, 10, 5, 6, 7, 8);
		DrawableElement childAtCenter = createDrawableElementWithSizeAndMargin(10, 10, 9, 10, 11, 12);
		DrawableElement childStretched = createDrawableElementWithSizeAndMargin(10, 10, 13, 14, 15, 16);
		
		drawableElement.addChild(childAtTopLeft);
		drawableElement.addChild(childAtBottomRight);
		drawableElement.addChild(childAtCenter);
		drawableElement.addChild(childStretched);
		
		linearLayoutFixer.setFitToChildren(true);
		linearLayoutFixer.setLayoutFlags(childAtBottomRight, LinearLayout.ALIGN_BOTTOM | LinearLayout.ALIGN_RIGHT);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayout.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayout.FIT | LinearLayout.FIT_AVAILABLE_SPACE);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 158, 252, drawableElement);
		assertDrawableElementPositionAndSize("childAtTopLeft", 51, 62, 10, 10, childAtTopLeft);
		assertDrawableElementPositionAndSize("childAtBottomRight", 71, 82, 10, 10, childAtBottomRight);
		assertDrawableElementPositionAndSize("childAtCenter", 64, 110, 10, 10, childAtCenter);
		assertDrawableElementPositionAndSize("childStretched", 63, 146, 10, 10, childStretched);
	}

	public void testApplyFixShouldIgnoreChild() {
		
		// given:
		DrawableElement drawableElement = new DrawableElement();
		
		DrawableElement child1 = createDrawableElementWithSize(10, 10);
		
		DrawableElement childThatMustBeIgnored = createDrawableElementWithSize(50, 50);
		childThatMustBeIgnored.setPosition(30, 30);
		
		DrawableElement child2 = createDrawableElementWithSize(10, 10);
		
		drawableElement.addChild(child1);
		drawableElement.addChild(childThatMustBeIgnored);
		drawableElement.addChild(child2);
		
		linearLayoutFixer.setFitToChildren(true);
		
		linearLayoutFixer.setLayoutFlags(childThatMustBeIgnored, LinearLayout.IGNORE_LAYOUT);
		
		// when:
		linearLayoutFixer.applyFix(drawableElement);
		
		// then:
		assertDrawableElementPositionAndSize("drawableElement", 0, 0, 10, 20, drawableElement);
		assertDrawableElementPositionAndSize("child1", 0, 0, 10, 10, child1);
		assertDrawableElementPositionAndSize("child2", 0, 10, 10, 10, child2);
		assertDrawableElementPositionAndSize("childThatMustBeIgnored", 30, 30, 50, 50, childThatMustBeIgnored);
	}
	
	private void testShouldFitToChildrenHorizontallyExpandingTheActualSize() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(300, 300);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should be expanded.", 300, drawableElement.getWidth());
		assertEquals("The height should not be expanded.", 200, drawableElement.getHeight());
	}

	private void testShouldFitToChildrenHorizontallyDecreasingTheActualSize() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(100, 100);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should be decreased.", 100, drawableElement.getWidth());
		assertEquals("The height should not be decreased.", 200, drawableElement.getHeight());
	}

	private void testShouldFitToChildrenVerticallyExpandingTheActualSize() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(300, 300);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should not be expanded.", 200, drawableElement.getWidth());
		assertEquals("The height should be expanded.", 300, drawableElement.getHeight());
	}

	private void testShouldFitToChildrenVerticallyDecreasingTheActualSize() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(100, 100);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should not be decreased.", 200, drawableElement.getWidth());
		assertEquals("The height should be decreased.", 100, drawableElement.getHeight());
	}

	private void testShouldFitToChildrenWhenSpaceIsSmallerHorizontally() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(300, 300);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should be expanded.", 300, drawableElement.getWidth());
		assertEquals("The height should not be expanded.", 200, drawableElement.getHeight());
	}

	private void testShouldNotFitToChildrenWhenSpaceIsNotSmallerHorizontally() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(100, 100);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should not be expanded.", 200, drawableElement.getWidth());
		assertEquals("The height should not be expanded.", 200, drawableElement.getHeight());
	}

	private void testShouldFitToChildrenWhenSpaceIsSmallerVertically() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(300, 300);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should not be expanded.", 200, drawableElement.getWidth());
		assertEquals("The height should be expanded.", 300, drawableElement.getHeight());
	}

	private void testShouldNotFitToChildrenWhenSpaceIsNotSmallerVertically() {
		// given:
		linearLayoutFixer.setFitPolicy(LinearLayout.FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.addLayout(linearLayoutFixer);
		drawableElement.setSize(200, 200);
		
		DrawableElement child = new DrawableElement();
		child.setSize(100, 100);
		drawableElement.addChild(child);
		
		// then:
		assertEquals("The width should not be expanded.", 200, drawableElement.getWidth());
		assertEquals("The height should not be expanded.", 200, drawableElement.getHeight());
	}

}
