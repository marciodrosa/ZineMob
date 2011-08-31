package com.sinosarts.uzinesamples.controller;

import com.sinosarts.uzine.controller.Controller;
import com.sinosarts.uzine.view.Window;
import com.sinosarts.uzinesamples.view.MenuWindow;
import com.sinosarts.zinemob.windows.events.CommandEvent;

public class MenuController implements Controller {

	public Window createWindow() {
		return new MenuWindow();
	}

	public Controller onCommand(CommandEvent event, Window window) {
		switch (event.getCommandId()) {
			case MenuWindow.START_QUIZ_ID:
				return new QuizListController();
		}

		return null;
	}

}
