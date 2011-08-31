package com.sinosarts.uzine.view.theme;

import com.sinosarts.zinemob.renderelements.RenderableTextFont;

public class DefaultFontsManager implements FontsManager {

	private RenderableTextFont windowDefaultFont, windowDefaultFocusFont,
			flatPanelWindowDefaultFont, flatPanelWindowDefaultFocusFont,
			reflectivePanelWindowDefaultFont, reflectivePanelWindowDefaultFocusFont,
			windowTitleFont, footerCommandFont, selectedFooterCommandFont, menuFont,
			menuFocusFont, textFieldFont, textFieldFocusFont, buttonsFont,
			buttonsFlatPanelFont, buttonsReflectivePanelFont, buttonsAffirmativeFont,
			buttonsNegativeFont;

	public DefaultFontsManager() {
		RenderableTextFont arialFont = new RenderableTextFont("/com/sinosarts/uzine/res/arial12.png",
				"/com/sinosarts/uzine/res/arial12.dat");
		RenderableTextFont arialBoldFont = new RenderableTextFont("/com/sinosarts/uzine/res/arial12bold.png",
				"/com/sinosarts/uzine/res/arial12bold.dat");

		windowDefaultFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		windowDefaultFocusFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		windowDefaultFocusFont.setFontColor(0xff0000ff);
		flatPanelWindowDefaultFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		flatPanelWindowDefaultFocusFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		flatPanelWindowDefaultFocusFont.setFontColor(0xff0000ff);
		reflectivePanelWindowDefaultFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		reflectivePanelWindowDefaultFont.setFontColor(0xffffffff);
		reflectivePanelWindowDefaultFocusFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		reflectivePanelWindowDefaultFocusFont.setFontColor(0xff00ffff);
		windowTitleFont = new RenderableTextFont(arialBoldFont.getFontImage(), arialBoldFont.getFontMetrics());
		windowTitleFont.setFontColor(0xffffffff);
		footerCommandFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		selectedFooterCommandFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		selectedFooterCommandFont.setFontColor(0xffffffff);
		menuFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		menuFont.setFontColor(0xffffffff);
		menuFocusFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		menuFont.setFontColor(0xff00ffff);
		textFieldFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		textFieldFocusFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		buttonsFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		buttonsFlatPanelFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		buttonsReflectivePanelFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		buttonsReflectivePanelFont.setFontColor(0xffffffff);
		buttonsAffirmativeFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		buttonsAffirmativeFont.setFontColor(0xffffffff);
		buttonsNegativeFont = new RenderableTextFont(arialFont.getFontImage(), arialFont.getFontMetrics());
		buttonsNegativeFont.setFontColor(0xffffffff);
	}

	public RenderableTextFont getWindowDefaultFont() {
		return windowDefaultFont;
	}

	public RenderableTextFont getWindowDefaultFocusFont() {
		return windowDefaultFocusFont;
	}

	public RenderableTextFont getFlatPanelWindowDefaultFont() {
		return flatPanelWindowDefaultFont;
	}

	public RenderableTextFont getFlatPanelWindowDefaultFocusFont() {
		return flatPanelWindowDefaultFocusFont;
	}

	public RenderableTextFont getReflectivePanelWindowDefaultFont() {
		return reflectivePanelWindowDefaultFont;
	}

	public RenderableTextFont getReflectivePanelWindowDefaulFocustFont() {
		return reflectivePanelWindowDefaultFocusFont;
	}

	public RenderableTextFont getWindowTitleFont() {
		return windowTitleFont;
	}

	public RenderableTextFont getFooterCommandsFont() {
		return footerCommandFont;
	}

	public RenderableTextFont getSelectedFooterCommandFont() {
		return selectedFooterCommandFont;
	}

	public RenderableTextFont getMenuFont() {
		return menuFont;
	}

	public RenderableTextFont getMenuFocusFont() {
		return menuFocusFont;
	}

	public RenderableTextFont getTextFieldFont() {
		return textFieldFont;
	}

	public RenderableTextFont getTextFieldFocusFont() {
		return textFieldFocusFont;
	}

	public RenderableTextFont getButtonsFont() {
		return buttonsFont;
	}

	public RenderableTextFont getButtonsFlatPanelFont() {
		return buttonsFlatPanelFont;
	}

	public RenderableTextFont getButtonsReflectivePanelFont() {
		return buttonsReflectivePanelFont;
	}

	public RenderableTextFont getButtonsAffirmativeFont() {
		return buttonsAffirmativeFont;
	}

	public RenderableTextFont getButtonsNegativeFont() {
		return buttonsNegativeFont;
	}

}
