package com.sinosarts.uzine.view.theme;

import com.sinosarts.uzine.view.components.UZCursor;

public class DefaultTheme extends Theme{

	public DefaultTheme() {
		setCursor(new UZCursor());
		setColorPallete(new DefaultColorPallete());
		setLayoutManager(new DefaultLayoutManager());
		setFontsManager(new DefaultFontsManager());
	}
}
