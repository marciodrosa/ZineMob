package com.sinosarts.uzinesamples.controller;

import com.sinosarts.uzine.controller.Controller;
import com.sinosarts.uzine.view.Window;
import com.sinosarts.uzinesamples.view.QuizQuestionWindow;
import com.sinosarts.zinemob.windows.events.CommandEvent;

public class QuizQuestionController implements Controller {

	public Window createWindow() {
		return new QuizQuestionWindow();
	}

	public Controller onCommand(CommandEvent event, Window window) {
		window.close();
		return null;
	}

}
