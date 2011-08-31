package com.zine.zinemobsamples.spaceship.controller;

import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.Updatable;
import com.zine.zinemobsamples.spaceship.drawable.LaserDrawableElement;

public class LaserController extends Controller implements Updatable {

	private LaserDrawableElement laserDrawableElement;
	private Scene sceneModule;
	private int lifeCount = 60;

	private static final int SPEED = 15;

	public LaserController(Scene sceneModule, int shotPositionX, int shotPositionY) {
		laserDrawableElement = new LaserDrawableElement();
		laserDrawableElement.setPosition(shotPositionX, shotPositionY);
		setDrawableElement(laserDrawableElement);

		this.sceneModule = sceneModule;
	}

	public void update() {
		laserDrawableElement.setPosition(laserDrawableElement.getX() + SPEED, laserDrawableElement.getY());
		lifeCount--;
		if(lifeCount == 0) {
			sceneModule.removeController(this);
		}
	}

}
