package com.sinosarts.uzinesamples.view;

import com.sinosarts.uzine.view.Window;
import com.sinosarts.uzine.view.components.Button;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;

public class MenuWindow extends Window {

	public static final int START_QUIZ_ID = 1;
	public static final int ABOUT_UZINE_ID = 2;
	public static final int LOAD_AN_IMAGE_ID = 3;
	public static final int CLICK_ID = 4;

	public MenuWindow() {
		//super("$menuWindow.title", SHOW_TITLE);

		Border textsBorder = new Border(34, 34, 3, 0);

		addTextComponentToLayout(START_QUIZ_ID, "$menuWindow.startQuiz", textsBorder, LayoutElement.ALIGN_LEFT, false);
		addTextComponentToLayout(ABOUT_UZINE_ID, "$menuWindow.aboutUzine", textsBorder, LayoutElement.ALIGN_LEFT, false);
		addTextComponentToLayout(LOAD_AN_IMAGE_ID, "$menuWindow.loadAnImage", textsBorder, LayoutElement.ALIGN_LEFT, false);

		addComponentToLayout(new Button(CLICK_ID, "$menuWindow.click"), new Border(10), LayoutElement.CENTER_H, false);

		//setMaximized();
	}

}
