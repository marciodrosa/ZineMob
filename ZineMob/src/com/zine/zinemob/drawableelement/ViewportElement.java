package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;

/**
 * Elemento em que todos os filhos s�o desenhados dentro da sua �rea (definida
 * pela sua posi��o e tamanho). Os filhos n�o s�o desenhados fora desta �rea.
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
