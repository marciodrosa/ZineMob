package com.sinosarts.uzine.view.theme;

import com.sinosarts.uzine.view.components.UZCursor;
import com.sinosarts.zinemob.renderelements.RenderElement;

public abstract class Theme {

	private static Theme currentTheme;

	private ColorPallete colorPallete;
	private LayoutManager layoutManager;
	private UZCursor cursor;
	private FontsManager fontsManager;
	private RenderElement background;

	public synchronized static Theme getCurrentTheme() {
		if (currentTheme == null)
			currentTheme = new DefaultTheme();
		return currentTheme;
	}

	public static void setCurrentTheme(Theme aCurrentTheme) {
		currentTheme = aCurrentTheme;
	}

	public ColorPallete getColorPallete() {
		return colorPallete;
	}

	public void setColorPallete(ColorPallete colorPallete) {
		this.colorPallete = colorPallete;
	}

	public LayoutManager getLayoutManager() {
		return layoutManager;
	}

	public void setLayoutManager(LayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	public UZCursor getCursor() {
		return cursor;
	}

	public void setCursor(UZCursor cursor) {
		this.cursor = cursor;
	}

	public FontsManager getFontsManager() {
		return fontsManager;
	}

	public void setFontsManager(FontsManager fontsManager) {
		this.fontsManager = fontsManager;
	}

	public RenderElement getBackground() {
		return background;
	}

	public void setBackground(RenderElement background) {
		this.background = background;
	}



}
