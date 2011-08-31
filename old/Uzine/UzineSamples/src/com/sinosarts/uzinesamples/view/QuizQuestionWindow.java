package com.sinosarts.uzinesamples.view;

import com.sinosarts.uzine.view.Window;
import com.sinosarts.uzine.view.components.Button;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;

public class QuizQuestionWindow extends Window {

	public static final byte YES_ID = 1;
	public static final byte NO_ID = 2;

	public QuizQuestionWindow() {
//		super("$quizQuestionWindow.title", FLAT_BACKGROUND | SHOW_TITLE);

		addTextComponentToLayout(0, "$quiz.didYouLikeUzine", new Border(20), LayoutElement.CENTER, false);

		addComponentToLayout(new Button(YES_ID, "$answers.yes"), new Border(40, 40, 3, 3), LayoutElement.STRETCH_H, false);
		addComponentToLayout(new Button(NO_ID, "$answers.no"), new Border(40, 40, 3, 3), LayoutElement.STRETCH_H, false);
		
//		refreshAndFit();
//
//		centerToScreen();
//
//		setDefaultActivateAnimation(UNHIDE_FROM_RIGHT);
//		setDefaultCloseAnimation(HIDE_TO_RIGHT);
	}

}
