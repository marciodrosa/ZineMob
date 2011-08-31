package com.sinosarts.zinemob.module;

import javax.microedition.lcdui.Graphics;

public class DefaultLoadingScreen extends LoadingScreen {

	private static final int WIDTH = 22;
	private static final int HEIGHT = 10;
	private int frame = 0;

	public DefaultLoadingScreen() {
		centerToScreen();
	}

	public void drawNode(Graphics g) {
		g.setColor(0x00ffffff);

		g.drawRect(0, 0, 22, 10);

		int pos = 3;
		for (int i=0; i<frame; i++) {
			g.fillRect(pos, 3, 4, 4);
			pos += 6;
		}

		if (frame == 3)
			frame = 0;
		else
			frame++;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

}
