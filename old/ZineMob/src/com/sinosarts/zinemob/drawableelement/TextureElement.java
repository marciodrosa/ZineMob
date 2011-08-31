package com.sinosarts.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class TextureElement extends DrawableElement
{
	private Image image;
	private int borderSize = 0;
	
	public TextureElement (Image image, int w, int h)
	{
		this.image = image;
		setSize(w,h);
	}

	public TextureElement (Image image, int w, int h, int borderSize)
	{
		this.image = image;
		setSize(w, h);
		this.borderSize = borderSize;
	}

	public void setBorderSize (int borderSize) {
		this.borderSize = borderSize;
	}

	public int getBorderSize() {
		return borderSize;
	}
	
	public void refresh() {
		
	}

	public void drawOnGraphics (Graphics g) {

	}

	private void drawPattern (Graphics g, int imageX, int imageY, int imageW, int imageH, int drawX, int drawY, int drawW, int drawH) {
		int imW, imH;

		for (int i = 0; i < drawW; i += imageW)
		{
			for (int j = 0; j < drawH; j += imageH)
			{
				imW = imageW;
				imH = imageH;

				if (i+imW > drawW)
					imW = drawW-i;
				if (j+imH > drawH)
					imH = drawH-j;

				g.drawRegion (image, imageX, imageY, imW, imH, Sprite.TRANS_NONE, drawX+i, drawY+j, Graphics.LEFT | Graphics.TOP);
			}
		}
	}
	
	public void drawNode (Graphics g)
	{
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int doubleBorderSize = borderSize*2;

		int rightBorderX = getWidth()-borderSize;
		int bottomBorderY = getHeight()-borderSize;
		int centerWidth = getWidth()-(doubleBorderSize);
		int centerHeight = getHeight()-(doubleBorderSize);
		
		int rightImageBorderX = imageWidth-borderSize;
		int bottomImageBorderY = imageHeight-borderSize;
		int topImageBorderX = imageHeight - (doubleBorderSize);
		int centerImageWidth = imageWidth-(doubleBorderSize);

		// centro da imagem:
		drawPattern (g, borderSize, borderSize, centerImageWidth, imageHeight-(doubleBorderSize),
				borderSize, borderSize, centerWidth, centerHeight);

		// bordas
		if (borderSize > 0) {
			// 1. canto superior esquerdo:
			drawPattern (g, 0, 0, borderSize, borderSize,
					0, 0, borderSize, borderSize);
			// 2. canto superior direito:
			drawPattern (g, rightImageBorderX, 0, borderSize, borderSize,
					rightBorderX, 0, borderSize, borderSize);
			// 3. canto inferior esquerdo:
			drawPattern (g, 0, bottomImageBorderY, borderSize, borderSize,
					0, bottomBorderY, borderSize, borderSize);
			// 4. canto inferior direito:
			drawPattern (g, rightImageBorderX, bottomImageBorderY, borderSize, borderSize,
					rightBorderX, bottomBorderY, borderSize, borderSize);

			// 5. borda esquerda:
			drawPattern (g, 0, borderSize, borderSize, topImageBorderX,
					0, borderSize, borderSize, centerHeight);
			// 6. borda direita:
			drawPattern (g, rightImageBorderX, borderSize, borderSize, topImageBorderX,
					rightBorderX, borderSize, borderSize, centerHeight);
			// 7. borda superior:
			drawPattern (g, borderSize, 0, centerImageWidth, borderSize,
					borderSize, 0, centerWidth, borderSize);
			// 8. borda inferior:
			drawPattern (g, borderSize, bottomImageBorderY, centerImageWidth, borderSize,
					borderSize, bottomBorderY, centerWidth, borderSize);
		}

	}
}

/*
 * Versão 01.04: removidos métodos setSize, getWidth e getHeight (post ZM_20100417)
 */
