package com.zine.zinemob.drawableelement;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Image;

public class SemiTransparentRectangleTest extends TestCase {
	
	private Image whiteImage;

	public SemiTransparentRectangleTest() {

	}

	public SemiTransparentRectangleTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		whiteImage = Image.createImage(5, 5);
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new SemiTransparentRectangleTest("testDrawShouldPaintOnePixelAtEachTwo", new TestMethod()
		{ public void run(TestCase tc) {((SemiTransparentRectangleTest)tc).testDrawShouldPaintOnePixelAtEachTwo(); } } ));

		testSuite.addTest(new SemiTransparentRectangleTest("testDrawShouldPaintOnePixelAtEachTwoOnAPortraitArea", new TestMethod()
		{ public void run(TestCase tc) {((SemiTransparentRectangleTest)tc).testDrawShouldPaintOnePixelAtEachTwoOnAPortraitArea(); } } ));

		return testSuite;
	}
	
	private void assertFirstRowRemainsWhite() {
		for (int i=0; i<whiteImage.getWidth(); i++) {
			assertPixelRemainsWhite(0, i);
		}
	}
	
	private void assertLastRowRemainsWhite() {
		for (int i=0; i<whiteImage.getWidth(); i++) {
			assertPixelRemainsWhite(whiteImage.getHeight()-1, i);
		}
	}
	
	private void assertFirstColumnRemainsWhite() {
		for (int i=0; i<whiteImage.getHeight(); i++) {
			assertPixelRemainsWhite(i, 0);
		}
	}
	
	private void assertLastColumnRemainsWhite() {
		for (int i=0; i<whiteImage.getWidth(); i++) {
			assertPixelRemainsWhite(i, whiteImage.getWidth()-1);
		}
	}
	
	private int getPixelColor(Image image, int row, int column) {
		int[] rgbData = new int[1];
		image.getRGB(rgbData, 0, 1, column, row, 1, 1);
		return rgbData[0];
	}

	private void assertPixelIsBlue(int row, int column) {
		assertEquals("The pixel at row " + row + " and column " + column + " must be painted with blue.", 0xff0000ff, getPixelColor(whiteImage, row, column));
	}

	private void assertPixelRemainsWhite(int row, int column) {
		assertEquals("The pixel at row " + row + " and column " + column + " must remains white.", 0xffffffff, getPixelColor(whiteImage, row, column));
	}

	public void testDrawShouldPaintOnePixelAtEachTwo() {
		
		// given:
		SemiTransparentRectangle semiTransparentRectangle = new SemiTransparentRectangle();
		semiTransparentRectangle.setColor(new Color(255, 0, 0, 255));
		semiTransparentRectangle.setPosition(1, 1);
		semiTransparentRectangle.setSize(3, 3);
		
		// when:
		semiTransparentRectangle.draw(whiteImage.getGraphics());
		
		// then:
		assertFirstRowRemainsWhite();
		assertLastRowRemainsWhite();
		assertFirstColumnRemainsWhite();
		assertLastColumnRemainsWhite();
		
		assertPixelIsBlue(1, 1);
		assertPixelRemainsWhite(1, 2);
		assertPixelIsBlue(1, 3);
		
		assertPixelRemainsWhite(2, 1);
		assertPixelIsBlue(2, 2);
		assertPixelRemainsWhite(2, 3);
		
		assertPixelIsBlue(3, 1);
		assertPixelRemainsWhite(3, 2);
		assertPixelIsBlue(3, 3);
	}

	public void testDrawShouldPaintOnePixelAtEachTwoOnAPortraitArea() {
		
		// given:
		SemiTransparentRectangle semiTransparentRectangle = new SemiTransparentRectangle();
		semiTransparentRectangle.setColor(new Color(255, 0, 0, 255));
		semiTransparentRectangle.setPosition(1, 1);
		semiTransparentRectangle.setSize(3, 4);
		
		// when:
		semiTransparentRectangle.draw(whiteImage.getGraphics());
		
		// then:
		assertFirstRowRemainsWhite();
		assertFirstColumnRemainsWhite();
		assertLastColumnRemainsWhite();
		
		assertPixelIsBlue(1, 1);
		assertPixelRemainsWhite(1, 2);
		assertPixelIsBlue(1, 3);
		
		assertPixelRemainsWhite(2, 1);
		assertPixelIsBlue(2, 2);
		assertPixelRemainsWhite(2, 3);
		
		assertPixelIsBlue(3, 1);
		assertPixelRemainsWhite(3, 2);
		assertPixelIsBlue(3, 3);
		
		assertPixelRemainsWhite(4, 1);
		assertPixelIsBlue(4, 2);
		assertPixelRemainsWhite(4, 3);
	}
	
}
