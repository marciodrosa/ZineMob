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

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenAndFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenAndFit(); } } ));

		testSuite.addTest(new LinearLayoutFixerTest("testApplyFixShouldLayoutChildrenAndDontFit", new TestMethod()
		{ public void run(TestCase tc) {((LinearLayoutFixerTest)tc).testApplyFixShouldLayoutChildrenAndDontFit(); } } ));

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

	public void testApplyFixShouldLayoutChildrenAndFit() {
		
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

	public void testApplyFixShouldLayoutChildrenAndDontFit() {
		
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
	
}
