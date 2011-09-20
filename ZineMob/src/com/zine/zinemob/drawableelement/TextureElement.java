package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * An element that draws an image side-by-side until it fill the entire area.
 */
public class TextureElement extends DrawableElement {

	private Image image;
	
	public TextureElement(Image image) {
		this.image = image;
	}
	
	protected void drawElement(Graphics graphics) {
		
		for (int i=0; i<getWidth(); i+=image.getWidth()) {
			for (int j=0; j<getHeight(); j+=image.getHeight()) {
				graphics.drawImage(image, i, j, Graphics.LEFT | Graphics.TOP);
			}
		}
	}
}
