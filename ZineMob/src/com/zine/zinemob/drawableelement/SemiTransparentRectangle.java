package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Draws a semi transparent rectangle. The color of the rectangle is intercalated
 * with blank spaces to simulate the transparency. By default is black.
 */
public class SemiTransparentRectangle extends DrawableElement {
	
	private Color color = new Color(0xff000000);
	private Image bufferImage;

	protected void drawElement(Graphics graphics) {
		
		if (bufferImage == null) {
			refreshBufferImage();
		}
		
		if (bufferImage != null) {
			graphics.drawImage(bufferImage, 0, 0, Graphics.TOP | Graphics.LEFT);
		}
	}
	
	private void refreshBufferImage() {
		
		int width = getWidth();
		int height = getHeight();
		
		int[] rgbaData = new int[width * height];
		int colorComponents = color.getComponents();
		
		for (int i=0; i<height; i++) {
			for (int j=0; j<width; j++) {
				
				int index = (i * width) + j;
				
				if ((i%2==0 && j%2==0) || (i%2==1 && j%2==1)) {
					rgbaData[index] = colorComponents;
				} else {
					rgbaData[index] = 0x00000000;
				}
			}
		}
		
		if (width > 0 && height > 0) {
			bufferImage = Image.createRGBImage(rgbaData, width, height, true);
		}
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
