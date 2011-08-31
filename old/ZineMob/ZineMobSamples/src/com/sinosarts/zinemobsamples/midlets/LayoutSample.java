package com.sinosarts.zinemobsamples.midlets;

import com.sinosarts.zinemob.core.ZineMIDlet;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.renderelements.Rectangle;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;
import javax.microedition.lcdui.Graphics;

public class LayoutSample extends ZineMIDlet {

	public void run() {

		LayoutElement layout = new LayoutElement();

		Rectangle red = new Rectangle();
		red.setColor(0x00ff0000);
		red.setSize(20, 20);

		Rectangle green = new Rectangle();
		green.setColor(0x0000ff00);
		green.setSize(20, 20);

		Rectangle blue = new Rectangle();
		blue.setColor(0x000000ff);
		blue.setSize(20, 20);

		Rectangle white = new Rectangle();
		white.setColor(0x00ffffff);
		white.setSize(20, 20);

		layout.addToLayout(red, new Border(3), 0, false);
		layout.addToLayout(green, new Border(3), LayoutElement.ALIGN_RIGHT | LayoutElement.ALIGN_BOTTOM, true);
		layout.addToLayout(blue, new Border(3), LayoutElement.CENTER_H | LayoutElement.STRETCH_V, true);
		layout.addToLayout(white, new Border(3), LayoutElement.STRETCH_H | LayoutElement.CENTER_V, true);
		layout.setAutoFitToElements(false);
		layout.setLayoutType(LayoutElement.LAYOUT_VERTICAL);
		layout.setShowLayoutLines(true);
		layout.setSize(50, 200);
		layout.refresh();

		Graphics graphics = ZineManager.getInstance().getGraphics();
		layout.draw(graphics);

		ZineManager.getInstance().flushGraphics();

	}

}
