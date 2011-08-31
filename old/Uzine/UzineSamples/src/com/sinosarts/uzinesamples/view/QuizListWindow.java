package com.sinosarts.uzinesamples.view;

import com.sinosarts.uzine.view.Window;
import com.sinosarts.uzine.view.components.Button;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;

public class QuizListWindow extends Window {

	public static final int DID_YOU_LIKE_UZINE_QUESTION_ID = 1;
	public static final int WHAT_ABOUT_THIS_QUIZ_QUESTION_ID = 2;
	public static final int READY_TO_START_TO_USE_UZINE_QUESTION_ID = 3;
	public static final int ONE_MORE_QUESTION_ID = 4;
	public static final int FINISH_ID = 5;

	public QuizListWindow() {
		//super(REFLECTIVE_PANEL_BACKGROUND);

		Border textsBorder = new Border(34, 34, 3, 0);

		addTextComponentToLayout(DID_YOU_LIKE_UZINE_QUESTION_ID, "$quiz.didYouLikeUzine", textsBorder, LayoutElement.ALIGN_LEFT, false);
		addTextComponentToLayout(DID_YOU_LIKE_UZINE_QUESTION_ID, "$quiz.whatAboutThisQuiz", textsBorder, LayoutElement.ALIGN_LEFT, false);
		addTextComponentToLayout(DID_YOU_LIKE_UZINE_QUESTION_ID, "$quiz.readyToStart", textsBorder, LayoutElement.ALIGN_LEFT, false);
		addTextComponentToLayout(DID_YOU_LIKE_UZINE_QUESTION_ID, "$quiz.oneMoreQuestion", textsBorder, LayoutElement.ALIGN_LEFT, false);

		addComponentToLayout(new Button(FINISH_ID, "$quiz.finish"), new Border(10), LayoutElement.ALIGN_RIGHT, false);

//		refreshAndFit();

//		centerToScreen();

//		setDefaultActivateAnimation(UNHIDE_FROM_RIGHT);
//		setDefaultInactivateAnimation(HIDE_TO_LEFT);
//		setDefaultCloseAnimation(HIDE_TO_RIGHT);
	}

}
