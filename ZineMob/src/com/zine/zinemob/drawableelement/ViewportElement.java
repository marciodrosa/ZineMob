package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;

/**
 * Elemento em que todos os filhos são desenhados dentro da sua área (definida
 * pela sua posição e tamanho). Os filhos não são desenhados fora desta área.
 */
public class ViewportElement extends DrawableElement {
	
	boolean ignoreChildrenOrigin = false;

	public void draw(Graphics graphics) {

		int currentClipX = graphics.getClipX();
		int currentClipY = graphics.getClipY();
		int currentClipW = graphics.getClipWidth();
		int currentClipH = graphics.getClipHeight();

		graphics.setClip(getX(), getY(), getWidth(), getHeight());

		super.draw(graphics);

		graphics.setClip(currentClipX, currentClipY, currentClipW, currentClipH);
	}
	
}
