package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Draws a semi transparent rectangle. The color of the rectangle is intercalated
 * with blank spaces to simulate the transparency.
 */
public class SemiTransparentRectangle extends DrawableElement {
	
	private Color color;
	private Image bufferImage;

	protected void drawElement(Graphics graphics) {
		
		if (bufferImage == null) {
			refreshBufferImage();
		}
		
		graphics.drawImage(bufferImage, 0, 0, Graphics.TOP | Graphics.LEFT);
	}
	
	private void refreshBufferImage() {
		
		int[] rgbaData = new int[getWidth() * getHeight()];
		int colorComponents = color.getComponents();
		
		for (int i=0; i<getWidth(); i++) {
			for (int j=0; j<getHeight(); j++) {
				
				int index = i * getWidth() + j;
				
				if ((i%2==0 && j%2==0) || (i%2==1 && j%2==1)) {
					rgbaData[index] = colorComponents;
				} else {
					rgbaData[index] = 0x00000000;
				}
			}
		}
		
		bufferImage = Image.createRGBImage(rgbaData, getWidth(), getHeight(), true);
	}

	/**
	 * Returns the rectangle color.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the rectangle color.
	 */
	public void setColor(Color color) {
		this.color = color;
		bufferImage = null;
	}

	public void setSize(int w, int h) {
		super.setSize(w, h);
		bufferImage = null;
	}
}
