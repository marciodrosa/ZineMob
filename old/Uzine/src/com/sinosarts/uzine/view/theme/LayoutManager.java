package com.sinosarts.uzine.view.theme;

import com.sinosarts.zinemob.renderelements.layout.Border;

public interface LayoutManager {

	public int getWindowsDefaultWidth();
	public int getWindowsDefaultHeight();
	public int getFlatWindowsDefaultWidth();
	public int getFlatWindowsDefaultHeight();
	public int getReflectivePanelWindowsDefaultWidth();
	public int getReflectivePanelWindowsDefaultHeight();

	public int getFlatWindowsRoundedCorners();
	public int getReflectiveWindowsRoundedCorners();
	public int getMenusRoundedCorners();
	public int getButtonsRoundedCorners();

	public int getReflectionIndex();
	public int getFocusLuminosityIndex();

	public int getFooterHeight();
	public int getTitleBarHeight();

	public int getWindowsTranslationsAnimationSpeed();
	public int getWindowsMaximizeAnimationSpeed();

	public Border createDefaultContainerElementsBorder();
	public Border createFooterCommandsBorder();
	public Border createMenuBorder();
	public Border createButtonsInternalContentBorder();
	public Border createTextFieldInternalContentBorder();
	public Border createTitleBarInternalContentBorder();

}
