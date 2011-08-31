package com.zine.zinemobsamples.spaceship.drawable;

import com.zine.zinemob.drawableelement.DrawableElement;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class BackgroundDrawableElement extends DrawableElement {

	private Image backgroundImage;

	public BackgroundDrawableElement() {

		try {
			backgroundImage = Image.createImage("/com/zine/zinemobsamples/res/stars.png");
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void drawElement(Graphics graphics) {

		int cellX = graphics.getClipX() / backgroundImage.getWidth();
		int cellY = graphics.getClipY() / backgroundImage.getHeight();

		int x1 = cellX * backgroundImage.getWidth();
		int x2 = x1 + graphics.getClipWidth();
		int y1 = cellY * backgroundImage.getHeight();
		int y2 = y1 + graphics.getClipHeight();

		for (int i=x1; i<=x2; i+=backgroundImage.getWidth()) {
			for(int j=y1; j<=y2; j+=backgroundImage.getHeight()) {
				graphics.drawImage(backgroundImage, i, j, Graphics.LEFT | Graphics.TOP);
			}
		}
	}

}
