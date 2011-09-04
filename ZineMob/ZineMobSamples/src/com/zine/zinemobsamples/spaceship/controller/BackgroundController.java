package com.zine.zinemobsamples.spaceship.controller;

import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.Updateble;
import com.zine.zinemobsamples.spaceship.drawable.BackgroundDrawableElement;

public class BackgroundController extends Controller implements Updateble {

	public BackgroundController() {
		setDrawableElement(new BackgroundDrawableElement());
	}

	public void update() {
	}

}
