package com.sinosarts.uzine.view.theme;

import com.sinosarts.zinemob.renderelements.RenderableTextFont;

public interface FontsManager {

	public RenderableTextFont getWindowDefaultFont();
	public RenderableTextFont getWindowDefaultFocusFont();
	public RenderableTextFont getFlatPanelWindowDefaultFont();
	public RenderableTextFont getFlatPanelWindowDefaultFocusFont();
	public RenderableTextFont getReflectivePanelWindowDefaultFont();
	public RenderableTextFont getReflectivePanelWindowDefaulFocustFont();
	public RenderableTextFont getWindowTitleFont();
	public RenderableTextFont getFooterCommandsFont();
	public RenderableTextFont getSelectedFooterCommandFont();
	public RenderableTextFont getMenuFont();
	public RenderableTextFont getMenuFocusFont();
	public RenderableTextFont getTextFieldFont();
	public RenderableTextFont getTextFieldFocusFont();
	public RenderableTextFont getButtonsFont();
	public RenderableTextFont getButtonsFlatPanelFont();
	public RenderableTextFont getButtonsReflectivePanelFont();
	public RenderableTextFont getButtonsAffirmativeFont();
	public RenderableTextFont getButtonsNegativeFont();

}
