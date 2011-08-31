package com.zine.zinemobsamples.spaceship.controller;

import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.Updatable;
import com.zine.zinemobsamples.spaceship.drawable.ShipDrawableElement;
import javax.microedition.lcdui.game.GameCanvas;

public class ShipController extends Controller implements Updatable, KeyboardListener {
	
	private ShipDrawableElement shipDrawableElement;
	private Scene scene;

	private static final int SPEED = 4;
	private static final int CANNON_POSITION_X = 22;
	private static final int CANNON_POSITION_Y = 18;

	private static final int VIEWPOINT_DISTANCE = 60;

	public ShipController(Scene scene) {
		shipDrawableElement = new ShipDrawableElement();
		setDrawableElement(shipDrawableElement);

		this.scene = scene;
	}

	public void update() {

		scene.getScreenElement().setChildrenViewPosition(
				shipDrawableElement.getX() + VIEWPOINT_DISTANCE - (scene.getScreenElement().getWidth()/2),
				shipDrawableElement.getY() - (scene.getScreenElement().getHeight()/2));
		
	}

	public void onKeyPressed(int keyCode, int gameAction) {
		if(gameAction == GameCanvas.FIRE) {
			shot();
		}
	}

	public void onKeyRepeated(int keyCode, int gameAction) {
	}

	public void onKeyReleased(int keyCode, int gameAction) {
	}

	public void updateKeyStates(int keyStates) {

		int x = shipDrawableElement.getX();
		int y = shipDrawableElement.getY();

		if((keyStates & GameCanvas.UP_PRESSED) != 0) {
			y -= SPEED;
		}
		if((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
			y += SPEED;
		}
		if((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
			x += SPEED;
		}
		if((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
			x -= SPEED;
		}

		shipDrawableElement.setPosition(x, y);
	}

	private void shot() {
		LaserController laserController = new LaserController(scene,
				shipDrawableElement.getX() + CANNON_POSITION_X,
				shipDrawableElement.getY() + CANNON_POSITION_Y);
		scene.addController(laserController);
	}

}
