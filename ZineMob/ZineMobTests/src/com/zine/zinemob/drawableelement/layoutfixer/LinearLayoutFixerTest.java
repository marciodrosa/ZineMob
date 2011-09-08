package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class LinearLayoutFixerTest extends TestCase {
	
	private LinearLayoutFixer linearLayoutFixer;

	public LinearLayoutFixerTest() {

	}

	public LinearLayoutFixerTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		linearLayoutFixer = new LinearLayoutFixer();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenVerticallyAndFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenVerticallyAndFit(); } } ));

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenVerticallyAndDontFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenVerticallyAndDontFit(); } } ));

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenVerticallyAndStretchSpace", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenVerticallyAndStretchSpace(); } } ));

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenHorizontallyAndFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenHorizontallyAndFit(); } } ));

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenHorizontallyAndDontFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenHorizontallyAndDontFit(); } } ));

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenHorizontallyAndStretchSpace", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenHorizontallyAndStretchSpace(); } } ));

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
		
		linearLayoutFixer.setLayoutFlags(childAtRight, LinearLayoutFixer.ALIGN_RIGHT);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayoutFixer.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayoutFixer.STRETCH);
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
		
		linearLayoutFixer.setLayoutFlags(childAtRight, LinearLayoutFixer.ALIGN_RIGHT);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayoutFixer.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayoutFixer.STRETCH);
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
		
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignRightAndBottom, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_RIGHT | LinearLayoutFixer.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontally, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_CENTER_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterVertically, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontallyAndVertically, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVertically, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.STRETCH_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchHorizontally, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.STRETCH_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVerticallyAndHorizontally, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.STRETCH);
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
		
		linearLayoutFixer.setLayoutType(LinearLayoutFixer.LAYOUT_TYPE_HORIZONTAL);
		linearLayoutFixer.setLayoutFlags(childAtTop, LinearLayoutFixer.ALIGN_TOP);
		linearLayoutFixer.setLayoutFlags(childAtBottom, LinearLayoutFixer.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayoutFixer.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayoutFixer.STRETCH_V);
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
		
		linearLayoutFixer.setLayoutType(LinearLayoutFixer.LAYOUT_TYPE_HORIZONTAL);
		linearLayoutFixer.setLayoutFlags(childAtTop, LinearLayoutFixer.ALIGN_TOP);
		linearLayoutFixer.setLayoutFlags(childAtBottom, LinearLayoutFixer.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childAtCenter, LinearLayoutFixer.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretched, LinearLayoutFixer.STRETCH_V);
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
		
		linearLayoutFixer.setLayoutType(LinearLayoutFixer.LAYOUT_TYPE_HORIZONTAL);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignRightAndBottom, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_RIGHT | LinearLayoutFixer.ALIGN_BOTTOM);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontally, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_CENTER_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterVertically, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_CENTER_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAlignCenterHorizontallyAndVertically, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.ALIGN_CENTER);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVertically, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.STRETCH_V);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchHorizontally, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.STRETCH_H);
		linearLayoutFixer.setLayoutFlags(childStretchSpaceAndStretchVerticallyAndHorizontally, LinearLayoutFixer.STRETCH_AVAILABLE_SPACE | LinearLayoutFixer.STRETCH);
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
	
}
