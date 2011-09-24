package com.zine.zinemob.drawableelement.tilesmap;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Image;

public class TilesMapTest extends TestCase {
	
	private TilesMap tileMap;
	
	public TilesMapTest() {

	}

	public TilesMapTest(String testName, TestMethod method) {
		super(testName, method);
	}
	
	public void setUp() throws Exception {
		tileMap = new TilesMap(5, 10, Image.createImage("/com/zine/zinemob/res/tilesset.png"), 50, 50);
		tileMap.setPosition(10, 20);
	}
	
	/**
	 * Surounds the specified cells with walls. The cells at top will receive north
	 * walls, the cells at bottom will receive south walls, and etc.
	 */
	private void surroundTilesWithWalls(int column1, int row1, int column2, int row2) {
		
		for (int i=column1; i<=column2; i++) {
			tileMap.addWall(i, row1, TilesSet.WALL_NORTH);
			tileMap.addWall(i, row2, TilesSet.WALL_SOUTH);
		}
		for (int i=row1; i<=row2; i++) {
			tileMap.addWall(column1, i, TilesSet.WALL_WEAST);
			tileMap.addWall(column2, i, TilesSet.WALL_EAST);
		}
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

		testSuite.addTest(new TilesMapTest("testIsAreaCollidedWithWallsShouldReturnFalseIfTheAreaIsInsideTheWalls", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testIsAreaCollidedWithWallsShouldReturnFalseIfTheAreaIsInsideTheWalls(); } } ));

		testSuite.addTest(new TilesMapTest("testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromNorthAtTheTop", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromNorthAtTheTop(); } } ));

		testSuite.addTest(new TilesMapTest("testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromSouthAtTheBottom", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromSouthAtTheBottom(); } } ));

		testSuite.addTest(new TilesMapTest("testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromEastAtTheRight", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromEastAtTheRight(); } } ));

		testSuite.addTest(new TilesMapTest("testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromWeastAtTheLeft", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromWeastAtTheLeft(); } } ));

		testSuite.addTest(new TilesMapTest("testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAnyWallInTheMiddleOfTheArea", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAnyWallInTheMiddleOfTheArea(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldCollideWithASouthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldCollideWithASouthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldCollideWithEastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldCollideWithEastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldCollideWithNorthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldCollideWithNorthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldCollideWithWeastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldCollideWithWeastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldCollideWithANorthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldCollideWithANorthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldCollideWithAWeastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldCollideWithAWeastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldCollideWithSouthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldCollideWithSouthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldCollideWithEastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldCollideWithEastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldNotCollideWithASouthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldNotCollideWithASouthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldNotCollideWithEastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldNotCollideWithEastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldNotCollideWithNorthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldNotCollideWithNorthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromLeftTopToRightBottomShouldNotCollideWithWeastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromLeftTopToRightBottomShouldNotCollideWithWeastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldNotCollideWithANorthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldNotCollideWithANorthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldNotCollideWithAWeastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldNotCollideWithAWeastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldNotCollideWithSouthWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldNotCollideWithSouthWall(); } } ));

		testSuite.addTest(new TilesMapTest("testALineFromRightBottomToLeftTopShouldNotCollideWithEastWall", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testALineFromRightBottomToLeftTopShouldNotCollideWithEastWall(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellCenteredPositionX", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellCenteredPositionX(); } } ));

		testSuite.addTest(new TilesMapTest("testGetCellCenteredPositionY", new TestMethod()
		{ public void run(TestCase tc) {((TilesMapTest)tc).testGetCellCenteredPositionY(); } } ));

		return testSuite;
	}
	
	private void assertIndexesArraysAreEqual(int[] expectedArray, int[] actualArray) {
		
		assertEquals("The indexes count is not the expected.", expectedArray.length, actualArray.length);
		
		for (int i=0; i<expectedArray.length; i++) {
			assertEquals("The index " + i + " is not the expected.", expectedArray[i], actualArray[i]);
		}
	}
	
	public void testGetCellIndex() {
		assertEquals("The cell index is not the expected for the column and row (0, 0).", 0, tileMap.getCellIndex(0, 0));
		assertEquals("The cell index is not the expected for the column and row (4, 0).", 4, tileMap.getCellIndex(4, 0));
		assertEquals("The cell index is not the expected for the column and row (0, 1).", 5, tileMap.getCellIndex(0, 1));
		assertEquals("The cell index is not the expected for the column and row (4, 9).", 49, tileMap.getCellIndex(4, 9));
		assertEquals("The cell index is not the expected for the column and row (5, 0).", -1, tileMap.getCellIndex(5, 0));
		assertEquals("The cell index is not the expected for the column and row (0, -1).", -1, tileMap.getCellIndex(0, -1));
	}
	
	public void testGetColumnByCellIndex() {
		assertEquals("The cell column is not the expected for the index 0.", 0, tileMap.getCellColumnByCellIndex(0));
		assertEquals("The cell column is not the expected for the index 4.", 4, tileMap.getCellColumnByCellIndex(4));
		assertEquals("The cell column is not the expected for the index 5.", 0, tileMap.getCellColumnByCellIndex(5));
		assertEquals("The cell column is not the expected for the index 49.", 4, tileMap.getCellColumnByCellIndex(49));
		assertEquals("The cell column is not the expected for the index 50.", -1, tileMap.getCellColumnByCellIndex(50));
		assertEquals("The cell column is not the expected for the index -1.", -1, tileMap.getCellColumnByCellIndex(-1));
	}
	
	public void testGetRowByCellIndex() {
		assertEquals("The cell row is not the expected for the index 0.", 0, tileMap.getCellRowByCellIndex(0));
		assertEquals("The cell row is not the expected for the index 4.", 0, tileMap.getCellRowByCellIndex(4));
		assertEquals("The cell row is not the expected for the index 5.", 1, tileMap.getCellRowByCellIndex(5));
		assertEquals("The cell row is not the expected for the index 49.", 9, tileMap.getCellRowByCellIndex(49));
		assertEquals("The cell row is not the expected for the index 50.", -1, tileMap.getCellRowByCellIndex(50));
		assertEquals("The cell row is not the expected for the index -1.", -1, tileMap.getCellRowByCellIndex(-1));
	}
	
	public void testGetCellIndexAtPosition() {
		assertEquals("The cell index is not expected at the position (0,0).", 0, tileMap.getCellIndexAtPosition(0, 0, true));
		assertEquals("The cell index is not expected at the position (50,25).", 1, tileMap.getCellIndexAtPosition(50, 25, true));
		assertEquals("The cell index is not expected at the position (249,25).", 4, tileMap.getCellIndexAtPosition(249, 25, true));
		assertEquals("The cell index is not expected at the position (51,51).", 6, tileMap.getCellIndexAtPosition(51, 51, true));
		assertEquals("The cell index is not expected at the position (250,25).", -1, tileMap.getCellIndexAtPosition(250, 25, true));
		assertEquals("The cell index is not expected at the position (-1,25).", -1, tileMap.getCellIndexAtPosition(-1, 25, true));
		assertEquals("The cell index is not expected at the position (25,-1).", -1, tileMap.getCellIndexAtPosition(25, -1, true));
		assertEquals("The cell index is not expected at the position (500,25).", -1, tileMap.getCellIndexAtPosition(500, 25, true));
		assertEquals("The cell index is not expected at the absolute position (0,0).", -1, tileMap.getCellIndexAtPosition(0, 0, false));
		assertEquals("The cell index is not expected at the absolute position (10,20).", 0, tileMap.getCellIndexAtPosition(10, 20, false));
		assertEquals("The cell index is not expected at the absolute position (60,45).", 1, tileMap.getCellIndexAtPosition(60, 45, false));
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
		int[] indexes = tileMap.getCellIndexesAtArea(x, y, width, height, true);
		
		// then:
		int[] expectedIndexes = new int[] {0, 1, 5, 6, 10, 11, 15, 16};
		
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
		int[] indexes = tileMap.getCellIndexesAtArea(x, y, width, height, true);
		
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
		int[] indexes = tileMap.getCellIndexesAtArea(x, y, width, height, true);
		
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
		int[] indexes = tileMap.getCellIndexesAtArea(x, y, width, height, true);
		
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
		int[] indexes = tileMap.getCellIndexesAtArea(x, y, width, height, false);
		
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
		int[] indexes = tileMap.getCellIndexesAtLineSegment(x1, y1, x2, y2, true);
		
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
		int[] indexes = tileMap.getCellIndexesAtLineSegment(x1, y1, x2, y2, true);
		
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
		int[] indexes = tileMap.getCellIndexesAtLineSegment(x1, y1, x2, y2, true);
		
		// then:
		assertIndexesArraysAreEqual(new int[0], indexes);
	}

	public void testIsAreaCollidedWithWallsShouldReturnFalseIfTheAreaIsInsideTheWalls() {
		
		// given:
		surroundTilesWithWalls(1, 1, 3, 3);
		
		int areaX = 50;
		int areaY = 50;
		int areaWidth = 150;
		int areaHeight = 150;
		
		// when:
		boolean isareaCollidedWithWalls = tileMap.isAreaCollidedWithWalls(areaX, areaY, areaWidth, areaHeight, true);
		
		// then:
		assertTrue("The area should NOT be collided, because it is exactly in the limit.", !isareaCollidedWithWalls);
	}

	public void testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromNorthAtTheTop() {
		
		// given:
		surroundTilesWithWalls(1, 1, 3, 3);
		
		tileMap.addWall(2, 1, TilesSet.WALL_EAST);
		
		int areaX = 50;
		int areaY = 50;
		int areaWidth = 150;
		int areaHeight = 150;
		
		// when:
		boolean isareaCollidedWithWalls = tileMap.isAreaCollidedWithWalls(areaX, areaY, areaWidth, areaHeight, true);
		
		// then:
		assertTrue("The area should be collided, because there is a wall intersection at north.", isareaCollidedWithWalls);
	}

	public void testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromSouthAtTheBottom() {
		
		// given:
		surroundTilesWithWalls(1, 1, 3, 3);
		
		tileMap.addWall(2, 3, TilesSet.WALL_EAST);
		
		int areaX = 50;
		int areaY = 50;
		int areaWidth = 150;
		int areaHeight = 150;
		
		// when:
		boolean isareaCollidedWithWalls = tileMap.isAreaCollidedWithWalls(areaX, areaY, areaWidth, areaHeight, true);
		
		// then:
		assertTrue("The area should be collided, because there is a wall intersection at south.", isareaCollidedWithWalls);
	}

	public void testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromEastAtTheRight() {
		
		// given:
		surroundTilesWithWalls(1, 1, 3, 3);
		
		tileMap.addWall(3, 2, TilesSet.WALL_NORTH);
		
		int areaX = 50;
		int areaY = 50;
		int areaWidth = 150;
		int areaHeight = 150;
		
		// when:
		boolean isareaCollidedWithWalls = tileMap.isAreaCollidedWithWalls(areaX, areaY, areaWidth, areaHeight, true);
		
		// then:
		assertTrue("The area should be collided, because there is a wall intersection at east.", isareaCollidedWithWalls);
	}

	public void testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAWallDifferentFromWeastAtTheLeft() {
		
		// given:
		surroundTilesWithWalls(1, 1, 3, 3);
		
		tileMap.addWall(1, 2, TilesSet.WALL_NORTH);
		
		int areaX = 50;
		int areaY = 50;
		int areaWidth = 150;
		int areaHeight = 150;
		
		// when:
		boolean isareaCollidedWithWalls = tileMap.isAreaCollidedWithWalls(areaX, areaY, areaWidth, areaHeight, true);
		
		// then:
		assertTrue("The area should be collided, because there is a wall intersection at weast.", isareaCollidedWithWalls);
	}

	public void testIsAreaCollidedWithWallsShouldReturnTrueIfThereIsAnyWallInTheMiddleOfTheArea() {
		
		// given:
		tileMap.addWall(2, 2, TilesSet.WALL_ALL);
		
		int areaX = 50;
		int areaY = 50;
		int areaWidth = 150;
		int areaHeight = 150;
		
		// when:
		boolean isareaCollidedWithWalls = tileMap.isAreaCollidedWithWalls(areaX, areaY, areaWidth, areaHeight, true);
		
		// then:
		assertTrue("The area should be collided, because there is a wall intersection in the middle of the area.", isareaCollidedWithWalls);
	}

	public void testALineFromLeftTopToRightBottomShouldCollideWithASouthWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(2, 1, TilesSet.WALL_SOUTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromLeftTopToRightBottomShouldCollideWithEastWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(1, 1, TilesSet.WALL_EAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromLeftTopToRightBottomShouldCollideWithNorthWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(1, 1, TilesSet.WALL_NORTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromLeftTopToRightBottomShouldCollideWithWeastWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(2, 1, TilesSet.WALL_WEAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldCollideWithANorthWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(1, 1, TilesSet.WALL_NORTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldCollideWithAWeastWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(2, 1, TilesSet.WALL_WEAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldCollideWithSouthWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(2, 1, TilesSet.WALL_SOUTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldCollideWithEastWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(1, 1, TilesSet.WALL_EAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should collide with the wall.", isCollided);
	}

	public void testALineFromLeftTopToRightBottomShouldNotCollideWithASouthWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(1, 1, TilesSet.WALL_SOUTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	public void testALineFromLeftTopToRightBottomShouldNotCollideWithEastWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(2, 1, TilesSet.WALL_EAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	public void testALineFromLeftTopToRightBottomShouldNotCollideWithNorthWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(2, 1, TilesSet.WALL_NORTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	public void testALineFromLeftTopToRightBottomShouldNotCollideWithWeastWall() {
		
		// given:
		int x1 = 0;
		int y1 = 0;
		int x2 = 200;
		int y2 = 150;
		
		tileMap.setWall(1, 1, TilesSet.WALL_WEAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldNotCollideWithANorthWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(2, 1, TilesSet.WALL_NORTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldNotCollideWithAWeastWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(1, 1, TilesSet.WALL_WEAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldNotCollideWithSouthWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(1, 1, TilesSet.WALL_SOUTH);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	public void testALineFromRightBottomToLeftTopShouldNotCollideWithEastWall() {
		
		// given:
		int x1 = 200;
		int y1 = 150;
		int x2 = 0;
		int y2 = 0;
		
		tileMap.setWall(2, 1, TilesSet.WALL_EAST);
		
		// when:
		boolean isCollided = tileMap.isLineSegmentCollidedWithWalls(x1, y1, x2, y2, true);
		
		// then:
		assertTrue("The line segment should NOT collide with the wall.", !isCollided);
	}

	private void testGetCellCenteredPositionX() {
		
		// given:
		try {
			tileMap = new TilesMap(5, 5, Image.createImage("/com/zine/zinemob/res/tilesset.png"), 50, 1);
		} catch (Exception ex) {
			fail(ex.toString());
		}
		
		// when:
		int position = tileMap.getCellCenteredPositionX(3);
		
		// then:
		assertEquals("The cell centered position X of the column 3 is not the expected.", 175, position);
	}

	private void testGetCellCenteredPositionY() {
		
		// given:
		try {
			tileMap = new TilesMap(5, 5, Image.createImage("/com/zine/zinemob/res/tilesset.png"), 1, 50);
		} catch (Exception ex) {
			fail(ex.toString());
		}
		
		// when:
		int position = tileMap.getCellCenteredPositionY(3);
		
		// then:
		assertEquals("The cell centered position Y of the row 3 is not the expected.", 175, position);
	}


}
