package com.sinosarts.zinemobsamples.windows;

import com.sinosarts.zinemob.renderelements.Rectangle;
import com.sinosarts.zinemob.renderelements.ReflectivePanel;
import com.sinosarts.zinemob.renderelements.RenderableTextFont;
import com.sinosarts.zinemob.windows.AnimatedWindow;

public class WindowBaseExample extends AnimatedWindow {

	private static RenderableTextFont font = new RenderableTextFont("/com/sinosarts/zinemobsamples/resources/arial12white.png",
			"/com/sinosarts/zinemobsamples/resources/arial12.dat");
	private static RenderableTextFont focusFont = new RenderableTextFont(font.getFontImage(),
			font.getFontMetrics());

	static {
		focusFont.setFontColor(0xffff0000);
	}

	private ReflectivePanel bg;

	public WindowBaseExample() {
		bg = createBackground();
		setBackground(bg);

		setDefaultFont(font);
		setDefaultFocusFont(focusFont);

		setDefaultActivateAnimation(AnimatedWindow.UNHIDE_FROM_RIGHT);
		setDefaultInactivateAnimation(AnimatedWindow.HIDE_TO_LEFT);

		getDefaultBorder().setBottom(3);
		getDefaultBorder().setTop(3);
		getDefaultBorder().setLeft(10);
		getDefaultBorder().setRight(10);
	}

	private ReflectivePanel createBackground() {
		ReflectivePanel panel = new ReflectivePanel();
		panel.setColor(0xff202030);
		panel.setWireColor(0xffffffff);
		panel.setDrawMode(Rectangle.MODE_FILL_AND_WIRE);
		panel.setRoundedCorner(20);
		panel.setReflectionDistanceFromBorder(3);
		return panel;
	}

	public ReflectivePanel getReflectiveBackground() {
		return bg;
	}

}
