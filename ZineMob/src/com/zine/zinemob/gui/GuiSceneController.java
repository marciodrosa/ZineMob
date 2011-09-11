package com.zine.zinemob.gui;

import com.zine.zinemob.scene.controller.Controller;

public interface GuiSceneController extends Controller.SceneController {
	
	void addWindow(Window window);
	
	void closeTopWindow();
	
	Window getTopWindow();
}
