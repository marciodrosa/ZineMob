package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;

/**
 * DrawableElement that just draws a rectangle.
 */
public class RectangleElement extends DrawableElement {
	
	private int color = 0xff000000;
	private boolean fill = true;
	private int roundedCornerH, roundedCornerV;

	protected void drawElement(Graphics graphics) {
		graphics.setColor(this.color);
		if (roundedCornerH == 0 && roundedCornerV == 0) {
			if (fill) {
				graphics.fillRect(0, 0, getWidth(), getHeight());
			} else {
				graphics.drawRect(0, 0, getWidth(), getHeight());
			}
		} else {
			if (fill) {
				graphics.fillRoundRect(0, 0, getWidth(), getHeight(), roundedCornerH, roundedCornerV);
			} else {
				graphics.drawRoundRect(0, 0, getWidth(), getHeight(), roundedCornerH, roundedCornerV);
			}
		}
	}

	/**
	 * Returns the rectangle color (RRGGBB).
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Sets the rectangle color (RRGGBB).
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Return true if the element must fill a rectangle when draw, or false if
	 * only the edges must be drawn. The default value is true.
	 */
	public boolean isFill() {
		return fill;
	}

	/**
	 * Sets true if the element must fill a rectangle when draw, or false if
	 * only the edges must be drawn. The default value is true.
	 */
	public void setFill(boolean fill) {
		this.fill = fill;
	}

	/**
	 * Returns the size of the rounded horizontal corner, in pixels.
	 */
	public int getRoundedCornerH() {
		return roundedCornerH;
	}

	/**
	 * Returns the size of the rounded vertical corner, in pixels.
	 */
	public int getRoundedCornerV() {
		return roundedCornerV;
	}

	/**
	 * Sets the size of the rounded corner, in pixels.
	 */
	public void setRoundedCorner(int roundedCornerH, int roundedCornerV) {
		this.roundedCornerH = roundedCornerH;
		this.roundedCornerV = roundedCornerV;
	}
}
