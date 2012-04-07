package com.zine.zinemobsamples.midlets;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.Color;
import com.zine.zinemob.drawableelement.RectangleElement;
import com.zine.zinemob.scene.Asset;
import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.scene.controller.SceneController;
import com.zine.zinemob.scene.controller.KeyboardListener;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * Shows an white screen with a black dot at the center, that can be moved with the keyboard.
 */
public class BlackDotSample extends ZineMIDlet {

	public void run() {
		new DotScene().run();
	}
}

class DotScene extends Scene {

	public void init() {
		setClearColor(0xffffffff);
		DotAsset dotAsset = new DotAsset();
		dotAsset.attachToScene(this);
	}
}	

class DotAsset extends Asset {
	
	public DotAsset() {
		DotDrawableElement dotDrawableElement = new DotDrawableElement();
		setDrawableElement(dotDrawableElement);
		setController(new DotController(dotDrawableElement));
	}
};

class DotDrawableElement extends RectangleElement {
	
	public DotDrawableElement() {
		setColor(0xff000000);
		setSize(15, 15);
		centerPivot();
	}
}

class DotController extends SceneController implements KeyboardListener {
	
	private DotDrawableElement dotDrawableElement;
	
	public DotController(DotDrawableElement dotDrawableElement) {
		this.dotDrawableElement = dotDrawableElement;
	}

	public void init() {
		int centerX = getScene().getScreenElement().getWidth() / 2;
		int centerY = getScene().getScreenElement().getHeight() / 2;
		dotDrawableElement.setPosition(centerX, centerY);
	}
	
	public void onKeyPressed(int keyCode, int gameAction) {
	}

	public void onKeyRepeated(int keyCode, int gameAction) {
	}

	public void onKeyReleased(int keyCode, int gameAction) {
	}

	public void updateKeyStates(int keyStates) {
		int speed = 3;
		int movementX = 0;
		int movementY = 0;
		if ((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
			movementY += speed;
		}
		if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
			movementY -= speed;
		}
		if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
			movementX += speed;
		}
		if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
			movementX -= speed;
		}
		dotDrawableElement.setPosition(dotDrawableElement.getX() + movementX, dotDrawableElement.getY() + movementY);
	}
	
}