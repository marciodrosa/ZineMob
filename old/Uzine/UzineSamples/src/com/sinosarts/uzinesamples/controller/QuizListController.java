package com.sinosarts.uzinesamples.controller;

import com.sinosarts.uzine.controller.Controller;
import com.sinosarts.uzine.view.Window;
import com.sinosarts.uzinesamples.view.QuizListWindow;
import com.sinosarts.zinemob.windows.events.CommandEvent;

public class QuizListController implements Controller {

	public Window createWindow() {
		return new QuizListWindow();
	}

	public Controller onCommand(CommandEvent event, Window window) {

		switch (event.getCommandId()) {
			case QuizListWindow.FINISH_ID:
				window.close();
				break;
			default:
				return new QuizQuestionController();
		}

		return null;
	}

}
