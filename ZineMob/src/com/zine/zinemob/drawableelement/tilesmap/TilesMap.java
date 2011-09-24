package com.zine.zinemob.drawableelement.tilesmap;

import com.zine.zinemob.drawableelement.DrawableElement;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

/**
 * Drawable element that contains a TiledLayer. It also contains a collision system,
 * where each cell can contains 1, 2, 3 or 4 walls (north, south, east and / or weast).
 */
public class TilesMap extends DrawableElement {
	
	private TiledLayer tiledLayer;
	private int[][] walls;
	private TilesSet tilesSet;
	
	public TilesMap(TiledLayer tiledLayer) {
		this.tiledLayer = tiledLayer;
		walls = new int[tiledLayer.getRows()][tiledLayer.getColumns()];
		setSize(tiledLayer.getWidth(), tiledLayer.getHeight());
	}
	
	public TilesMap(int columns, int rows, Image image, int tileWidth, int tileHeight) {
		this(new TiledLayer(columns, rows, image, tileWidth, tileHeight));
	}
	
	public TilesMap(int columns, int rows, TilesSet tilesSet) {
		this(new TiledLayer(columns, rows, tilesSet.getImage(), tilesSet.getTileWidth(), tilesSet.getTileHeight()));
		this.tilesSet = tilesSet;
		updateTilesSetWalls();
	}

	protected void drawElement(Graphics graphics) {
		getTiledLayer().paint(graphics);
	}
	
	public void setTilesSet(TilesSet tilesSet) {
		this.tilesSet = tilesSet;
		getTiledLayer().setStaticTileSet(tilesSet.getImage(), tilesSet.getTileWidth(), tilesSet.getTileHeight());
		setSize(tiledLayer.getWidth(), tiledLayer.getHeight());
		updateTilesSetWalls();
	}
	
	/**
	 * Returns the cell index at the position x, y, or -1 if the position is outside
	 * the map.
	 */
	public int getCellIndexAtPosition(int x, int y, boolean relative) {
		
		if (!relative) {
			x -= getGlobalX();
			y -= getGlobalY();
		}
		
		if (x < 0 || y < 0 || x > getWidth() || y > getHeight()) {
			return -1;
		} else {
			int column = x / tiledLayer.getCellWidth();
			int row = y / tiledLayer.getCellHeight();
			return getCellIndex(column, row);
		}
	}
	
	/**
	 * Returns the cell indexes ocuped by the area inside the map. If the area is
	 * entirely outside, then it returns an empty array. The indexes are sorted
	 * from left to right, top to bottom.
	 */
	public int[] getCellIndexesAtArea(int x, int y, int w, int h, boolean relative) {
		
		int[] cellIndexes;
		
		int[] columnsAndRowsAtRectangleArea = getColumnsAndRowsAtRectangleArea(x, y, w, h, relative);
		
		if (columnsAndRowsAtRectangleArea.length == 0) {
			
			cellIndexes = new int[0];
			
		} else {
			
			int firstColumn = columnsAndRowsAtRectangleArea[0];
			int firstRow = columnsAndRowsAtRectangleArea[1];
			int lastColumn = columnsAndRowsAtRectangleArea[2];
			int lastRow = columnsAndRowsAtRectangleArea[3];

			int cellsCount = (lastColumn - firstColumn + 1) * (lastRow - firstRow + 1);

			cellIndexes = new int[cellsCount];

			int i = 0;
			for (int j=firstRow; j<=lastRow; j++) {
				for (int k=firstColumn; k<=lastColumn; k++) {
					cellIndexes[i] = getCellIndex(k, j);
					i++;
				}
			}
		}
		
		return cellIndexes;
	}
	
	private int[] getColumnsAndRowsAtRectangleArea(int x, int y, int w, int h, boolean relative) {
		
		if (!relative) {
			x -= getGlobalX();
			y -= getGlobalY();
		}
		if (x >= getWidth() || y >= getHeight()) {
			
			return new int[0];
			
		} else {
			if (x < 0) {
				w += x;
				x = 0;
			}
			if (y < 0) {
				h += y;
				y = 0;
			}
			if ((x + w) >= getWidth()) {
				w = getWidth() - x;
			}
			if ((y + h) >= getHeight()) {
				h = getHeight() - y;
			}

			int firstColumn = x / tiledLayer.getCellWidth();
			int firstRow = y / tiledLayer.getCellHeight();
			int lastColumn = (x + w - 1) / tiledLayer.getCellWidth();
			int lastRow = (y + h - 1) / tiledLayer.getCellHeight();
			
			return new int[] {firstColumn, firstRow, lastColumn, lastRow};
		}
	}
	
	private int[] getColumnsAndRowsAtRectangleAreaFromTo(int x1, int y1, int x2, int y2, boolean relative) {
		
		if (!relative) {
			x1 -= getGlobalX();
			y1 -= getGlobalY();
			x2 -= getGlobalX();
			y2 -= getGlobalY();
		}
		
		int areaX, areaY, areaWidth, areaHeight;
		
		if (x1 <= x2) {
			areaX = x1;
			areaWidth = x2 - x1;
		} else {
			areaX = x2;
			areaWidth = x1 - x2;
		}
		
		if (y1 <= y2) {
			areaY = y1;
			areaHeight = y2 - y1;
		} else {
			areaY = y2;
			areaHeight = y1 - y2;
		}
		
		return getColumnsAndRowsAtRectangleArea(areaX, areaY, areaWidth, areaHeight, true);
	}
	
	/**
	 * Returns the cell indexes intercepted by the line segment. If the line segment is
	 * entirely outside, then it returns an empty array. The indexes are sorted
	 * from left to right, top to bottom.
	 */
	public int[] getCellIndexesAtLineSegment(int x1, int y1, int x2, int y2, boolean relative) {
		
		int[] columnsAndRowsAtRectangleArea = getColumnsAndRowsAtRectangleAreaFromTo(x1, y1, x2, y2, relative);
		
		Vector intersectedIndexes = new Vector();
		
		if (columnsAndRowsAtRectangleArea.length == 4) {
		
			int firstColumn = columnsAndRowsAtRectangleArea[0];
			int firstRow = columnsAndRowsAtRectangleArea[1];
			int lastColumn = columnsAndRowsAtRectangleArea[2];
			int lastRow = columnsAndRowsAtRectangleArea[3];

			int deltaX = x1-x2;
			int deltaY = y2-y1;
			int delta = x2*y1-x1*y2;

			for (int column=firstColumn; column<=lastColumn; column++) {

				for (int row=firstRow; row<=lastRow; row++) {

					int cellX1 = column * tiledLayer.getCellWidth();
					int cellY1 = row * tiledLayer.getCellHeight();
					int cellX2 = cellX1 + tiledLayer.getCellWidth();
					int cellY2 = cellY1 + tiledLayer.getCellHeight();

					int cellIntersection1 = (deltaY*cellX1) + (deltaX*cellY1) + delta;

					if (cellIntersection1 == 0) {
						intersectedIndexes.addElement(new Integer(getCellIndex(column, row)));
						continue;
					}

					int cellIntersection2 = (deltaY*cellX2) + (deltaX*cellY1) + delta;

					if (cellIntersection2 == 0 || (cellIntersection1 > 0 && cellIntersection2 < 0) || (cellIntersection1 < 0 && cellIntersection2 > 0)) {
						intersectedIndexes.addElement(new Integer(getCellIndex(column, row)));
						continue;
					}

					int cellIntersection3 = (deltaY*cellX2) + (deltaX*cellY2) + delta;

					if (cellIntersection3 == 0 || (cellIntersection2 > 0 && cellIntersection3 < 0) || (cellIntersection2 < 0 && cellIntersection3 > 0)) {
						intersectedIndexes.addElement(new Integer(getCellIndex(column, row)));
						continue;
					}

					int cellIntersection4 = (deltaY*cellX1) + (deltaX*cellY2) + delta;

					if (cellIntersection4 == 0 || (cellIntersection3 > 0 && cellIntersection4 < 0) || (cellIntersection3 < 0 && cellIntersection4 > 0)) {
						intersectedIndexes.addElement(new Integer(getCellIndex(column, row)));
						continue;
					}

					if ((cellIntersection4 > 0 && cellIntersection1 < 0) || (cellIntersection4 < 0 && cellIntersection1 > 0)) {
						intersectedIndexes.addElement(new Integer(getCellIndex(column, row)));
					}
				}
			}
		}
		
		int[] intersectedIndexesArray = new int[intersectedIndexes.size()];
		for (int i = 0; i < intersectedIndexes.size(); i++) {
			intersectedIndexesArray[i] = ((Integer)intersectedIndexes.elementAt(i)).intValue();
		}
		
		return intersectedIndexesArray;
	}
	
	/**
	 * Returns the cell indexes ocuped by the drawableElement area inside the map. If the area is
	 * entirely outside, then it returns an empty array. The indexes are sorted
	 * from left to right, top to bottom.
	 */
	public int[] getCellIndexesAtDrawableElementArea(DrawableElement drawableElement) {
		if (drawableElement.getParent() == this) {
			return getCellIndexesAtArea(drawableElement.getLeftTopX(), drawableElement.getLeftTopY(), drawableElement.getWidth(), drawableElement.getHeight(), true);
		} else {
			return getCellIndexesAtArea(drawableElement.getGlobalLeftTopX(), drawableElement.getGlobalLeftTopY(), drawableElement.getWidth(), drawableElement.getHeight(), false);
		}
	}
	
	/**
	 * Returns if the area is collided with some wall.
	 */
	public boolean isAreaCollidedWithWalls(int x, int y, int w, int h, boolean relative) {
		
		boolean isCollided = false;
		
		int[] columnsAndRowsAtRectangleArea = getColumnsAndRowsAtRectangleArea(x, y, w, h, relative);
		
		if (columnsAndRowsAtRectangleArea.length == 4) {
			
			int firstColumn = columnsAndRowsAtRectangleArea[0];
			int firstRow = columnsAndRowsAtRectangleArea[1];
			int lastColumn = columnsAndRowsAtRectangleArea[2];
			int lastRow = columnsAndRowsAtRectangleArea[3];

			for (int i=firstRow; i<=lastRow; i++) {
				
				for (int j=firstColumn; j<=lastColumn; j++) {
					
					int wallsToValidate = TilesSet.WALL_NORTH | TilesSet.WALL_SOUTH | TilesSet.WALL_WEAST | TilesSet.WALL_EAST;
					
					if (i == firstRow) {
						wallsToValidate ^= TilesSet.WALL_NORTH;
					}
					if (i == lastRow) {
						wallsToValidate ^= TilesSet.WALL_SOUTH;
					}
					if (j == firstColumn) {
						wallsToValidate ^= TilesSet.WALL_WEAST;
					}
					if (j == lastColumn) {
						wallsToValidate ^= TilesSet.WALL_EAST;
					}
					
					isCollided = (walls[i][j] & wallsToValidate) != 0;
					
					if (isCollided) {
						break;
					}
				}
				
				if (isCollided) {
					break;
				}
			}
		}
		
		return isCollided;
	}
	
	/**
	 * Returns if the lineSegment is collided with some wall.
	 */
	public boolean isLineSegmentCollidedWithWalls(int x1, int y1, int x2, int y2, boolean relative) {
		
		int[] columnsAndRowsAtRectangleArea = getColumnsAndRowsAtRectangleAreaFromTo(x1, y1, x2, y2, relative);
		
		if (columnsAndRowsAtRectangleArea.length == 4) {
			
			int firstColumn = columnsAndRowsAtRectangleArea[0];
			int firstRow = columnsAndRowsAtRectangleArea[1];
			int lastColumn = columnsAndRowsAtRectangleArea[2];
			int lastRow = columnsAndRowsAtRectangleArea[3];

			int deltaX = x1-x2;
			int deltaY = y2-y1;
			int delta = x2*y1-x1*y2;

			for (int column=firstColumn; column<=lastColumn; column++) {

				for (int row=firstRow; row<=lastRow; row++) {

					int wall = getWall(column, row);

					int cellLeft = column * tiledLayer.getCellWidth() - 1;
					int cellTop = row * tiledLayer.getCellHeight() -1;
					int cellRight = cellLeft + tiledLayer.getCellWidth() + 2;
					int cellBottom = cellTop + tiledLayer.getCellHeight() + 2;

					int cellIntersectionTopLeft = (deltaY*cellLeft) + (deltaX*cellTop) + delta;

					if (cellIntersectionTopLeft == 0 && (wall & (TilesSet.WALL_NORTH|TilesSet.WALL_WEAST) ) != 0) {
						return true;
					}

					int cellIntersectionTopRight = (deltaY*cellRight) + (deltaX*cellTop) + delta;

					if (cellIntersectionTopRight == 0 && (wall & (TilesSet.WALL_NORTH|TilesSet.WALL_EAST) ) != 0) {
						return true;
					}

					if (((cellIntersectionTopLeft > 0 && cellIntersectionTopRight < 0) || (cellIntersectionTopLeft < 0 && cellIntersectionTopRight > 0))
							 && (wall & (TilesSet.WALL_NORTH) ) != 0) {
						return true;
					}

					int cellIntersectionBottomRight = (deltaY*cellRight) + (deltaX*cellBottom) + delta;

					if (cellIntersectionBottomRight == 0 && (wall & (TilesSet.WALL_SOUTH|TilesSet.WALL_EAST) ) != 0) {
						return true;
					}

					if (((cellIntersectionTopRight > 0 && cellIntersectionBottomRight < 0) || (cellIntersectionTopRight < 0 && cellIntersectionBottomRight > 0))
							 && (wall & (TilesSet.WALL_EAST) ) != 0) {
						return true;
					}

					int cellIntersectionBottomLeft = (deltaY*cellLeft) + (deltaX*cellBottom) + delta;

					if (cellIntersectionBottomLeft == 0 && (wall & (TilesSet.WALL_SOUTH|TilesSet.WALL_WEAST) ) != 0) {
						return true;
					}

					if (((cellIntersectionBottomRight > 0 && cellIntersectionBottomLeft < 0) || (cellIntersectionBottomRight < 0 && cellIntersectionBottomLeft > 0))
							&& (wall & (TilesSet.WALL_SOUTH) ) != 0) {
						return true;
					}

					if (((cellIntersectionBottomLeft > 0 && cellIntersectionTopLeft < 0) || (cellIntersectionBottomLeft < 0 && cellIntersectionTopLeft > 0))
							&& (wall & (TilesSet.WALL_WEAST) ) != 0) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Returns if the DrawableElement is collided with some wall.
	 */
	public boolean isDrawableElementCollidedWithWalls(DrawableElement drawableElement) {
		if (drawableElement.getParent() == this) {
			return isAreaCollidedWithWalls(drawableElement.getLeftTopX(), drawableElement.getLeftTopY(), drawableElement.getWidth(), drawableElement.getHeight(), true);
		} else {
			return isAreaCollidedWithWalls(drawableElement.getGlobalLeftTopX(), drawableElement.getGlobalLeftTopY(), drawableElement.getWidth(), drawableElement.getHeight(), false);
		}
	}

	/**
	 * Returns the TiledLayer.
	 */
	public TiledLayer getTiledLayer() {
		return tiledLayer;
	}

	/**
	 * Sets the TiledLayer.
	 */
	public void setTiledLayer(TiledLayer tiledLayer) {
		this.tiledLayer = tiledLayer;
	}
	
	/**
	 * Returns the wall property by cell index. See the TilesSet class.
	 */
	public int getWall(int index) {
		return getWall(getCellRowByCellIndex(index), getCellColumnByCellIndex(index));
	}
	
	public int getWall(int column, int row) {
		return walls[row][column];
	}
	
	/**
	 * Sets the wall property. See the TilesSet class.
	 */
	public void setWall(int index, int wall) {
		setWall(getCellRowByCellIndex(index), getCellColumnByCellIndex(index), wall);
	}
	
	/**
	 * Sets the wall property. See the TilesSet class.
	 */
	public void setWall(int column, int row, int wall) {
		walls[row][column] = wall;
	}
	
	/**
	 * Adds the wall property. See the TilesSet class.
	 */
	public void addWall(int index, int wall) {
		addWall(getCellRowByCellIndex(index), getCellColumnByCellIndex(index), wall);
	}
	
	/**
	 * Adds the wall property. See the TilesSet class.
	 */
	public void addWall(int column, int row, int wall) {
		walls[row][column] |= wall;
	}
	
	/**
	 * Returns the cell index of the row and column, or -1 if row or column are
	 * invalid.
	 */
	public int getCellIndex(int column, int row) {
		if (row < 0 || column < 0 || row >= tiledLayer.getRows() || column >= tiledLayer.getColumns()) {
			return -1;
		} else {
			return (row * tiledLayer.getColumns()) + column;
		}
	}
	
	/**
	 * Returns the cell row, or -1 if the index is invalid.
	 */
	public int getCellRowByCellIndex(int cellIndex) {
		if (cellIndex < 0 || cellIndex >= (tiledLayer.getRows() * tiledLayer.getColumns())) {
			return -1;
		} else {
			return cellIndex / tiledLayer.getColumns();
		}
	}
	
	/**
	 * Returns the cell column, or -1 if the index is invalid.
	 */
	public int getCellColumnByCellIndex(int cellIndex) {
		if (cellIndex < 0 || cellIndex >= (tiledLayer.getRows() * tiledLayer.getColumns())) {
			return -1;
		} else {
			return cellIndex % tiledLayer.getColumns();
		}
	}
	
	/**
	 * Refresh all the walls with the cells of the TiledLayer and the respective tile
	 * of the TilesSet (if there is one).
	 */
	public void updateTilesSetWalls() {
		if (tilesSet != null) {
			for (int row=0; row<tiledLayer.getRows(); row++) {
				for (int column=0; column<tiledLayer.getColumns(); column++) {
					int tileSetIndex = tiledLayer.getCell(column, row) - 1;
					if (tileSetIndex >= 0) {
						int wall;
						try {
							wall = tilesSet.getWalls()[tileSetIndex];
						} catch (ArrayIndexOutOfBoundsException ex) {
							wall = TilesSet.WALL_FREE;
						}
						setWall(column, row, wall);
					}
				}
			}
		}
	}
	
	/**
	 * Returns the centered position X of the cell at the column.
	 */
	public int getCellCenteredPositionX(int column) {
		return (column * tiledLayer.getCellWidth()) + (tiledLayer.getCellWidth() / 2);
	}
	
	/**
	 * Returns the centered position Y of the cell at the column.
	 */
	public int getCellCenteredPositionY(int row) {
		return (row * tiledLayer.getCellHeight()) + (tiledLayer.getCellHeight() / 2);
	}
	
}
