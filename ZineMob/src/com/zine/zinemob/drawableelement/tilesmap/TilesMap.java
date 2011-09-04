package com.zine.zinemob.drawableelement.tilesmap;

import com.zine.zinemob.drawableelement.DrawableElement;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

/**
 * Drawable element that contains a TiledLayer. It also contains a collision system,
 * where each cell can contains 1, 2, 3 or 4 walls (north, south, least and / or weast).
 */
public class TilesMap extends DrawableElement {
	
	private TiledLayer tiledLayer;
	private int[] walls;
	
	public TilesMap(TiledLayer tiledLayer) {
		this.tiledLayer = tiledLayer;
	}
	
	public TilesMap(int columns, int rows, Image image, int tileWidth, int tileHeight) {
		this.tiledLayer = new TiledLayer(columns, rows, image, tileWidth, tileHeight);
	}
	
	public TilesMap(int columns, int rows, TilesSet tilesSet) {
		this.tiledLayer = new TiledLayer(columns, rows, tilesSet.getImage(), tilesSet.getTileWidth(), tilesSet.getTileHeight());
		setTilesSetWalls(tilesSet);
	}

	protected void drawElement(Graphics graphics) {
		getTiledLayer().paint(graphics);
	}
	
	public void setTilesSet(TilesSet tilesSet) {
		getTiledLayer().setStaticTileSet(tilesSet.getImage(), tilesSet.getTileWidth(), tilesSet.getTileHeight());
		setTilesSetWalls(tilesSet);
	}
	
	public int getCellIndexAt(int x, int y, boolean relative) {
		return 0;
	}
	
	public int[] getCellIndexesAtArea(int x, int y, int w, int h, boolean relative) {
		return null;
	}
	
	public int[] getCellIndexesAtRay(int x, int y, int w, int h, boolean relative) {
		return null;
	}
	
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
	
	public boolean isRayCollidedWithWalls(int x1, int y1, int x2, int y2, boolean relative) {
		return false;
	}
	
	public boolean isDrawableElementCollidedWithWalls(DrawableElement drawableElement) {
		if (drawableElement.getParent() == this) {
			return isAreaCollidedWithWalls(drawableElement.getX(), drawableElement.getY(), drawableElement.getWidth(), drawableElement.getHeight(), true);
		} else {
			return isAreaCollidedWithWalls(drawableElement.getGlobalX(), drawableElement.getGlobalY(), drawableElement.getWidth(), drawableElement.getHeight(), false);
		}
	}

	public TiledLayer getTiledLayer() {
		return tiledLayer;
	}

	public void setTiledLayer(TiledLayer tiledLayer) {
		this.tiledLayer = tiledLayer;
	}
	
	public int getWall(int index) {
		return 0;
	}
	
	public void setWall(int index, int wall) {
		
	}
	
	/**
	 * Returns the cell index of the row and column, or -1 if row or column are
	 * invalid.
	 */
	public int getCellIndex(int row, int column) {
		return 0;
	}
	
	/**
	 * Returns the cell row, or -1 if the index is invalid.
	 */
	public int getCellRowByCellIndex(int cellIndex) {
		return 0;
	}
	
	/**
	 * Returns the cell column, or -1 if the index is invalid.
	 */
	public int getCellColumnByCellIndex(int cellIndex) {
		return 0;
	}
	
	private void setTilesSetWalls(TilesSet tilesSet) {
		
	}
	
}
