package com.zine.zinemob.drawableelement.tilesmap;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

public class TilesMapTest extends TestCase {
	
	private TilesMap tilesMap;
	
	public TilesMapTest() {

	}

	public TilesMapTest(String testName, TestMethod method) {
		super(testName, method);
	}
	
	public void setUp() throws Exception {
		tilesMap = new TilesMap(5, 10, Image.createImage("/com/zine/zinemob/res/tilesset.png"), 50, 50);
		tilesMap.setPosition(10, 20);
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new TilesMapTest("testGetCellIndex", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndex(); } } ));

		testSuite.addTest(new TilesMapTest("testGetColumnByCellIndex", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetColumnByCellIndex(); } } ));

		testSuite.addTest(new TilesMapTest("testGetRowByCellIndex", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetRowByCellIndex(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexAtPosition", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexAtPosition(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtAreaWithSomeOutsideAreaAtTopLeftAndRelative", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtAreaWithSomeOutsideAreaAtTopLeftAndRelative(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtAreaWithSomeOutsideAreaAtBottomRightAndRelative", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtAreaWithSomeOutsideAreaAtBottomRightAndRelative(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtAreaWithAllAreaInsideAndRelative", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtAreaWithAllAreaInsideAndRelative(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtAreaWithAllAreaOutsideAndRelative", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtAreaWithAllAreaOutsideAndRelative(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtAreaNoRelative", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtAreaNoRelative(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtLineSegmentShouldReturnAllInterceptedCells", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtLineSegmentShouldReturnAllInterceptedCells(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtLineSegmentPartiallyOutsideShouldIgnoreTheCellsOutside", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtLineSegmentPartiallyOutsideShouldIgnoreTheCellsOutside(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellIndexesAtLineSegmentTotallyOutsideShouldReturnAnEmptyArray", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellIndexesAtLineSegmentTotallyOutsideShouldReturnAnEmptyArray(); } } ));

		return testSuite;
	}
	
	private void assertIndexesArraysAreEqual(int[] expectedArray, int[] actualArray) {
		
		assertEquals("The indexes count is not the expected.", expectedArray.length, actualArray.length);
		
		for (int i=0; i<expectedArray.length; i++) {
			assertEquals("The index " + i + " is not the expected.", expectedArray[i], actualArray[i]);
		}
	}
	
	public void testGetCellIndex() {
		assertEquals("The cell index is not the expected for the column and row (0, 0).", 0, tilesMap.getCellIndex(0, 0));
		assertEquals("The cell index is not the expected for the column and row (4, 0).", 4, tilesMap.getCellIndex(4, 0));
		assertEquals("The cell index is not the expected for the column and row (0, 1).", 5, tilesMap.getCellIndex(0, 1));
		assertEquals("The cell index is not the expected for the column and row (4, 9).", 49, tilesMap.getCellIndex(4, 9));
		assertEquals("The cell index is not the expected for the column and row (5, 0).", -1, tilesMap.getCellIndex(5, 0));
		assertEquals("The cell index is not the expected for the column and row (0, -1).", -1, tilesMap.getCellIndex(0, -1));
	}
	
	public void testGetColumnByCellIndex() {
		assertEquals("The cell column is not the expected for the index 0.", 0, tilesMap.getCellColumnByCellIndex(0));
		assertEquals("The cell column is not the expected for the index 4.", 4, tilesMap.getCellColumnByCellIndex(4));
		assertEquals("The cell column is not the expected for the index 5.", 0, tilesMap.getCellColumnByCellIndex(5));
		assertEquals("The cell column is not the expected for the index 49.", 4, tilesMap.getCellColumnByCellIndex(49));
		assertEquals("The cell column is not the expected for the index 50.", -1, tilesMap.getCellColumnByCellIndex(50));
		assertEquals("The cell column is not the expected for the index -1.", -1, tilesMap.getCellColumnByCellIndex(-1));
	}
	
	public void testGetRowByCellIndex() {
		assertEquals("The cell row is not the expected for the index 0.", 0, tilesMap.getCellRowByCellIndex(0));
		assertEquals("The cell row is not the expected for the index 4.", 0, tilesMap.getCellRowByCellIndex(4));
		assertEquals("The cell row is not the expected for the index 5.", 1, tilesMap.getCellRowByCellIndex(5));
		assertEquals("The cell row is not the expected for the index 49.", 9, tilesMap.getCellRowByCellIndex(49));
		assertEquals("The cell row is not the expected for the index 50.", -1, tilesMap.getCellRowByCellIndex(50));
		assertEquals("The cell row is not the expected for the index -1.", -1, tilesMap.getCellRowByCellIndex(-1));
	}
	
	public void testGetCellIndexAtPosition() {
		assertEquals("The cell index is not expected at the position (0,0).", 0, tilesMap.getCellIndexAtPosition(0, 0, true));
		assertEquals("The cell index is not expected at the position (50,25).", 1, tilesMap.getCellIndexAtPosition(50, 25, true));
		assertEquals("The cell index is not expected at the position (249,25).", 4, tilesMap.getCellIndexAtPosition(249, 25, true));
		assertEquals("The cell index is not expected at the position (51,51).", 6, tilesMap.getCellIndexAtPosition(51, 51, true));
		assertEquals("The cell index is not expected at the position (250,25).", -1, tilesMap.getCellIndexAtPosition(250, 25, true));
		assertEquals("The cell index is not expected at the position (-1,25).", -1, tilesMap.getCellIndexAtPosition(-1, 25, true));
		assertEquals("The cell index is not expected at the position (25,-1).", -1, tilesMap.getCellIndexAtPosition(25, -1, true));
		assertEquals("The cell index is not expected at the position (500,25).", -1, tilesMap.getCellIndexAtPosition(500, 25, true));
		assertEquals("The cell index is not expected at the absolute position (0,0).", -1, tilesMap.getCellIndexAtPosition(0, 0, false));
		assertEquals("The cell index is not expected at the absolute position (10,20).", 0, tilesMap.getCellIndexAtPosition(10, 20, false));
		assertEquals("The cell index is not expected at the absolute position (60,45).", 1, tilesMap.getCellIndexAtPosition(60, 45, false));
	}
	
	public void testGetCellIndexesAtAreaWithSomeOutsideAreaAtTopLeftAndRelative() {
		
// 0  1  2  3  4   (0-49)
// 5  6  7  8  9   (50-99)
// 10 11 12 13 14  (100-149)
// 15 16 17 18 19  (150-199)
// 20 21 22 23 24  (200-249)
// 25 26 27 28 29  (250-299)
// 30 31 32 33 34  (300-349)
// 35 36 37 38 39  (350-399)
// 40 41 42 43 44  (400-449)
// 45 46 47 48 49  (450-499)
//
// (0-49) (50-99) (100-149) (150-199) (200-249)

		// given:
		int x = -500;
		int y = -600;
		int width = 600;
		int height = 799;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtArea(x, y, width, height, true);
		
		// then:
		int[] expectedIndexes = new int[] {0, 1, 2, 5, 6, 7, 10, 11, 12, 15, 16, 17};
		
		assertIndexesArraysAreEqual(expectedIndexes, indexes);
	}
	
	public void testGetCellIndexesAtAreaWithSomeOutsideAreaAtBottomRightAndRelative() {
		
// 0  1  2  3  4   (0-49)
// 5  6  7  8  9   (50-99)
// 10 11 12 13 14  (100-149)
// 15 16 17 18 19  (150-199)
// 20 21 22 23 24  (200-249)
// 25 26 27 28 29  (250-299)
// 30 31 32 33 34  (300-349)
// 35 36 37 38 39  (350-399)
// 40 41 42 43 44  (400-449)
// 45 46 47 48 49  (450-499)
//
// (0-49) (50-99) (100-149) (150-199) (200-249)
		
		// given:
		int x = 100;
		int y = 349;
		int width = 1000;
		int height = 2000;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtArea(x, y, width, height, true);
		
		// then:
		int[] expectedIndexes = new int[] {32, 33, 34, 37, 38, 39, 42, 43, 44, 47, 48, 49};
		
		assertIndexesArraysAreEqual(expectedIndexes, indexes);
	}
	
	public void testGetCellIndexesAtAreaWithAllAreaInsideAndRelative() {
		
// 0  1  2  3  4   (0-49)
// 5  6  7  8  9   (50-99)
// 10 11 12 13 14  (100-149)
// 15 16 17 18 19  (150-199)
// 20 21 22 23 24  (200-249)
// 25 26 27 28 29  (250-299)
// 30 31 32 33 34  (300-349)
// 35 36 37 38 39  (350-399)
// 40 41 42 43 44  (400-449)
// 45 46 47 48 49  (450-499)
//
// (0-49) (50-99) (100-149) (150-199) (200-249)
		
		// given:
		int x = 49;
		int y = 150;
		int width = 100;
		int height = 249;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtArea(x, y, width, height, true);
		
		// then:
		int[] expectedIndexes = new int[] {15, 16, 17, 20, 21, 22, 25, 26, 27, 30, 31, 32, 35, 36, 37};
		
		assertIndexesArraysAreEqual(expectedIndexes, indexes);
	}
	
	public void testGetCellIndexesAtAreaWithAllAreaOutsideAndRelative() {
				
// 0  1  2  3  4   (0-49)
// 5  6  7  8  9   (50-99)
// 10 11 12 13 14  (100-149)
// 15 16 17 18 19  (150-199)
// 20 21 22 23 24  (200-249)
// 25 26 27 28 29  (250-299)
// 30 31 32 33 34  (300-349)
// 35 36 37 38 39  (350-399)
// 40 41 42 43 44  (400-449)
// 45 46 47 48 49  (450-499)
//
// (0-49) (50-99) (100-149) (150-199) (200-249)
		
		// given:
		int x = 300;
		int y = 150;
		int width = 100;
		int height = 200;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtArea(x, y, width, height, true);
		
		assertEquals("The indexes count is not the expected.", 0, indexes.length);
	}
	
	public void testGetCellIndexesAtAreaNoRelative() {
				
// 0  1  2  3  4   (0-49)
// 5  6  7  8  9   (50-99)
// 10 11 12 13 14  (100-149)
// 15 16 17 18 19  (150-199)
// 20 21 22 23 24  (200-249)
// 25 26 27 28 29  (250-299)
// 30 31 32 33 34  (300-349)
// 35 36 37 38 39  (350-399)
// 40 41 42 43 44  (400-449)
// 45 46 47 48 49  (450-499)
//
// (0-49) (50-99) (100-149) (150-199) (200-249)
		
		// given:
		int x = 59;
		int y = 170;
		int width = 100;
		int height = 249;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtArea(x, y, width, height, false);
		
		// then:
		int[] expectedIndexes = new int[] {15, 16, 17, 20, 21, 22, 25, 26, 27, 30, 31, 32, 35, 36, 37};
		
		assertIndexesArraysAreEqual(expectedIndexes, indexes);
	}
	
	public void testGetCellIndexesAtLineSegmentShouldReturnAllInterceptedCells() {
		
		// given:
		int x1 = 25;
		int y1 = 25;
		int x2 = 225;
		int y2 = 75;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtLineSegment(x1, y1, x2, y2, true);
		
		// then:
		int[] expectedIndexes = new int[] {0, 1, 2, 7, 8, 9};
		
		assertIndexesArraysAreEqual(expectedIndexes, indexes);
	}
	
	public void testGetCellIndexesAtLineSegmentPartiallyOutsideShouldIgnoreTheCellsOutside() {
		
		// given:
		int x1 = -50;
		int y1 = 0;
		int x2 = 225;
		int y2 = 75;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtLineSegment(x1, y1, x2, y2, true);
		
		// then:
		int[] expectedIndexes = new int[] {0, 1, 2, 7, 8, 9};
		
		assertIndexesArraysAreEqual(expectedIndexes, indexes);
	}
	
	public void testGetCellIndexesAtLineSegmentTotallyOutsideShouldReturnAnEmptyArray() {
		
		// given:
		int x1 = 300;
		int y1 = 0;
		int x2 = 400;
		int y2 = 75;
		
		// when:
		int[] indexes = tilesMap.getCellIndexesAtLineSegment(x1, y1, x2, y2, true);
		
		// then:
		assertIndexesArraysAreEqual(new int[0], indexes);
	}
}
