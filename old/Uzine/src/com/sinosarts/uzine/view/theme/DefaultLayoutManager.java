package com.sinosarts.uzine.view.theme;

import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.renderelements.layout.Border;

public class DefaultLayoutManager implements LayoutManager {

	public int getWindowsDefaultWidth() {
		return ZineManager.getInstance().getWidth();
	}

	public int getWindowsDefaultHeight() {
		return ZineManager.getInstance().getHeight() - getTitleBarHeight() - getFooterHeight();
	}

	public int getFlatWindowsDefaultWidth() {
		return 160;
	}

	public int getFlatWindowsDefaultHeight() {
		return 160;
	}

	public int getReflectivePanelWindowsDefaultWidth() {
		return 160;
	}

	public int getReflectivePanelWindowsDefaultHeight() {
		return 160;
	}

	public int getFlatWindowsRoundedCorners() {
		return 7;
	}

	public int getReflectiveWindowsRoundedCorners() {
		return 7;
	}

	public int getMenusRoundedCorners() {
		return 7;
	}

	public int getButtonsRoundedCorners() {
		return 7;
	}

	public int getReflectionIndex() {
		return 40;
	}

	public int getFocusLuminosityIndex() {
		return 20;
	}

	public int getFooterHeight() {
		return 18;
	}

	public int getTitleBarHeight() {
		return 32;
	}

	public int getWindowsTranslationsAnimationSpeed() {
		return 6;
	}

	public int getWindowsMaximizeAnimationSpeed() {
		return 6;
	}

	public Border createDefaultContainerElementsBorder() {
		return new Border(8);
	}

	public Border createFooterCommandsBorder() {
		return new Border(6);
	}

	public Border createMenuBorder() {
		return new Border(8, 8, 6, 6);
	}

	public Border createButtonsInternalContentBorder() {
		return new Border(27, 27, 6, 6);
	}

	public Border createTextFieldInternalContentBorder() {
		return new Border(8);
	}

	public Border createTitleBarInternalContentBorder() {
		return new Border(9);
	}

}
