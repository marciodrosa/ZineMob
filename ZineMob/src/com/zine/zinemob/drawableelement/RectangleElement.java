package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;

/**
 * DrawableElement that just draws a rectangle.
 */
public class RectangleElement extends DrawableElement {
	
	private Color color;

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	protected void drawElement(Graphics graphics) {
		graphics.setColor(this.color.getComponents());
		graphics.fillRect(0, 0, getWidth(), getHeight());
	}
}
