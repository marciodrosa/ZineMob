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
	private int[] walls;
	private TilesSet tilesSet;
	
	public TilesMap(TiledLayer tiledLayer) {
		this.tiledLayer = tiledLayer;
		walls = new int[tiledLayer.getRows() * tiledLayer.getColumns()];
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
		
		if (!relative) {
			x -= getGlobalX();
			y -= getGlobalY();
		}
		if (x >= getWidth() || y >= getHeight()) {
			
			cellIndexes = new int[0];
			
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
				w = getWidth() - x - 1;
			}
			if ((y + h) >= getHeight()) {
				h = getHeight() - y - 1;
			}

			int firstColumn = x / tiledLayer.getCellWidth();
			int firstRow = y / tiledLayer.getCellHeight();
			int lastColumn = (x + w) / tiledLayer.getCellWidth();
			int lastRow = (y + h) / tiledLayer.getCellHeight();

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
	
	/**
	 * Returns the cell indexes intercepted by the line segment. If the line segment is
	 * entirely outside, then it returns an empty array. The indexes are sorted
	 * from (x1, y1) to (x2, y2). This method uses the Bresenham algorithm.
	 */
	public int[] getCellIndexesAtLineSegment(int x1, int y1, int x2, int y2, boolean relative) {
		
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
		
		int[] areaIndexes = getCellIndexesAtArea(areaX, areaY, areaWidth, areaHeight, true);
		
		Vector intersectedIndexes = new Vector();
		
		for (int i=0; i<areaIndexes.length; i++) {
			
			int currentAreaCellIndex = areaIndexes[i];
			
			int cellX1 = getCellColumnByCellIndex(currentAreaCellIndex) * tiledLayer.getCellWidth();
			int cellY1 = getCellRowByCellIndex(currentAreaCellIndex) * tiledLayer.getCellHeight();
			int cellX2 = cellX1 + tiledLayer.getCellWidth();
			int cellY2 = cellY1 + tiledLayer.getCellHeight();
			
			int cellIntersection1 = lineFunction(x1, y1, x2, y2, cellX1, cellY1);
			int cellIntersection2 = lineFunction(x1, y1, x2, y2, cellX2, cellY1);
			int cellIntersection3 = lineFunction(x1, y1, x2, y2, cellX1, cellY2);
			int cellIntersection4 = lineFunction(x1, y1, x2, y2, cellX2, cellY2);
			
			if ((cellIntersection1 < 0 && cellIntersection2 < 0 && cellIntersection3 < 0 && cellIntersection4 < 0)
					|| (cellIntersection1 > 0 && cellIntersection2 > 0 && cellIntersection3 > 0 && cellIntersection4 > 0)) {
				
				
				
			} else {
				intersectedIndexes.addElement(new Integer(currentAreaCellIndex));
			}
		}
		
		int[] intersectedIndexesArray = new int[intersectedIndexes.size()];
		for (int i = 0; i < intersectedIndexes.size(); i++) {
			intersectedIndexesArray[i] = ((Integer)intersectedIndexes.elementAt(i)).intValue();
		}
		
		return intersectedIndexesArray;
	}
	
	private int lineFunction(int x1, int y1, int x2, int y2, int dotX, int dotY) {
		return ((y2-y1)*dotX) + ((x1-x2)*dotY) + (x2*y1-x1*y2);
		//(y2-y1)x + (x1-x2)y + (x2*y1-x1*y2)
	}
	
	/**
	 * Returns the cell indexes ocuped by the drawableElement area inside the map. If the area is
	 * entirely outside, then it returns an empty array. The indexes are sorted
	 * from left to right, top to bottom.
	 */
	public int[] getCellIndexesAtDrawableElementArea(DrawableElement drawableElement) {
		if (drawableElement.getParent() == this) {
			return getCellIndexesAtArea(drawableElement.getX(), drawableElement.getY(), drawableElement.getWidth(), drawableElement.getHeight(), true);
		} else {
			return getCellIndexesAtArea(drawableElement.getGlobalX(), drawableElement.getGlobalY(), drawableElement.getWidth(), drawableElement.getHeight(), false);
		}
	}
	
	public boolean isAreaCollidedWithWalls(int x, int y, int w, int h, boolean relative) {
		return false;
	}
	
	public boolean isLineSegmentCollidedWithWalls(int x1, int y1, int x2, int y2, boolean relative) {
		return false;
	}
	
	public boolean isDrawableElementCollidedWithWalls(DrawableElement drawableElement) {
		if (drawableElement.getParent() == this) {
			return isAreaCollidedWithWalls(drawableElement.getX(), drawableElement.getY(), drawableElement.getWidth(), drawableElement.getHeight(), true);
		} else {
			return isAreaCollidedWithWalls(drawableElement.getGlobalX(), drawableElement.getGlobalY(), drawableElement.getWidth(), drawableElement.getHeight(), false);
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
		return walls[index];
	}
	
	/**
	 * Sets the wall property. See the TilesSet class.
	 */
	public void setWall(int index, int wall) {
		this.walls[index] = wall;
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
	
	private void updateTilesSetWalls() {
		if (tilesSet != null) {
			for (int row=0; row<tiledLayer.getRows(); row++) {
				for (int column=0; column<tiledLayer.getColumns(); column++) {
					int tileSetIndex = tiledLayer.getCell(column, row) - 1;
					if (tileSetIndex >= 0) {
						setWall(getCellIndex(column, row), tilesSet.getWalls()[tileSetIndex]);
					}
				}
			}
		}
	}
	
}
