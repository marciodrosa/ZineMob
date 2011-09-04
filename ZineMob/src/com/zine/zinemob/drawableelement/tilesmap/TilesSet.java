package com.zine.zinemob.drawableelement.tilesmap;

import javax.microedition.lcdui.Image;

/**
 * TilesSet is used on TilesMap class to define the image, size and walls of the
 * tiles.
 */
public class TilesSet {
	
	public static final int WALL_FREE = 0;
	public static final int WALL_NORTH = 1;
	public static final int WALL_SOUTH = 2;
	public static final int WALL_EAST = 4;
	public static final int WALL_WEAST = 8;
	public static final int WALL_ALL = WALL_NORTH | WALL_SOUTH | WALL_EAST | WALL_WEAST;
	
	private Image image;
	private int tileWidth, tileHeight;
	private int[] walls;

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}

	public int[] getWalls() {
		return walls;
	}

	public void setWalls(int[] walls) {
		this.walls = walls;
	}
	
}
