package com.sinosarts.uzine.controller;

import com.sinosarts.uzine.view.Window;
import com.sinosarts.zinemob.windows.events.CommandEvent;

public interface Controller {

	public Window createWindow();

	public Controller onCommand(CommandEvent event, Window window);

}
