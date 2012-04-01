package com.zine.zinemobsamples.midlets;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.SpriteElement;
import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.scene.controller.KeyboardListener;
import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.Updateble;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class SpriteSample extends ZineMIDlet {

	public void run() {
		SampleScene sampleScene = new SampleScene();
		sampleScene.run();
	}
	
	private class SampleScene extends Scene {
		public void init() {
			setClearColor(0xffffffff);
			addController(new SampleSpriteElementController());
		}
	}

	private class SampleSpriteDrawableElement extends SpriteElement {

		public SampleSpriteDrawableElement() {
			super((Sprite)null);
			try {
				Image spriteImage = Image.createImage("/com/zine/zinemobsamples/res/sprite.png");
				Sprite sprite = new Sprite(spriteImage, 40, 40);
				setSprite(sprite);
				setStopped();
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch(Exception ex) {
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
		}

		public void setWalking() {
			getSprite().setFrameSequence(new int[]{0,0,1,1,2,2,3,3,4,4,5,5,4,4,3,3,2,2,1,1});
		}

		public void setStopped() {
			getSprite().setFrameSequence(new int[]{3});
		}

		public void updateAnimation() {
			getSprite().nextFrame();
		}

	}

	private class SampleSpriteElementController extends Controller implements Updateble, KeyboardListener {

		private SampleSpriteDrawableElement drawableElement = new SampleSpriteDrawableElement();

		public SampleSpriteElementController() {
		}

		public void update() {
			if (drawableElement.getParent() == null) {
				getScene().getScreenElement().addChild(drawableElement);
			}
			drawableElement.updateAnimation();
		}

		public void onKeyPressed(int keyCode, int gameAction) {
			if(gameAction == GameCanvas.RIGHT || gameAction == GameCanvas.LEFT) {
				drawableElement.setWalking();
			}
		}

		public void onKeyRepeated(int keyCode, int gameAction) {
		}

		public void onKeyReleased(int keyCode, int gameAction) {
			if(gameAction == GameCanvas.RIGHT || gameAction == GameCanvas.LEFT) {
				drawableElement.setStopped();
			}
		}

		public void updateKeyStates(int keyStates) {
			if((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
				drawableElement.setPosition(drawableElement.getX() + 4, drawableElement.getY());
			}
			if((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
				drawableElement.setPosition(drawableElement.getX() - 4, drawableElement.getY());
			}
		}

	}

}


