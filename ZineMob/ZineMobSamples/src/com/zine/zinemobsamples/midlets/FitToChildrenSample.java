package com.zine.zinemobsamples.midlets;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.layoutfixer.FitToChildrenLayoutFixer;
import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.KeyboardListener;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class FitToChildrenSample extends ZineMIDlet {

	private static class ParentDrawableElement extends DrawableElement {

		protected void drawElement(Graphics graphics) {
			graphics.setColor(255, 0, 0);
			graphics.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	private static class ChildDrawableElement extends DrawableElement {

		protected void drawElement(Graphics graphics) {
			graphics.setColor(60, 60, 255);
			graphics.fillRect(0, 0, getWidth(), getHeight());
		}
	}

	private static class FitToChildrenSampleController extends Controller implements KeyboardListener {

		private DrawableElement parent;
		private DrawableElement controlledChild;

		public FitToChildrenSampleController(DrawableElement parent, DrawableElement controlledChild) {
			this.parent = parent;
			this.controlledChild = controlledChild;

			this.parent.addLayoutFixer(new FitToChildrenLayoutFixer());
		}

		public void onKeyPressed(int keyCode, int gameAction) {
//			if (gameAction == GameCanvas.FIRE) {
//				new FitToChildrenAreaFixer().applyFix(parent);
//			}
		}

		public void onKeyRepeated(int keyCode, int gameAction) {
		}

		public void onKeyReleased(int keyCode, int gameAction) {
		}

		public void updateKeyStates(int keyStates) {
			int speed = 2;
			if ((keyStates & GameCanvas.DOWN_PRESSED) != 0) {
				controlledChild.setPosition(controlledChild.getX(), controlledChild.getY() + speed);
			}
			if ((keyStates & GameCanvas.UP_PRESSED) != 0) {
				controlledChild.setPosition(controlledChild.getX(), controlledChild.getY() - speed);
			}
			if ((keyStates & GameCanvas.LEFT_PRESSED) != 0) {
				controlledChild.setPosition(controlledChild.getX() - speed, controlledChild.getY());
			}
			if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0) {
				controlledChild.setPosition(controlledChild.getX() + speed, controlledChild.getY());
			}
		}
		
	}

	private static class FitToChildrenSampleScene extends Scene {

		public FitToChildrenSampleScene() {

			ParentDrawableElement parent = new ParentDrawableElement();
			parent.setSize(50, 50);
			parent.setPadding(4);
			getScreenElement().addChild(parent);

			ChildDrawableElement controlledChild = new ChildDrawableElement();
			controlledChild.setSize(35, 35);
			controlledChild.setMargin(5);
			parent.addChild(controlledChild);

			ChildDrawableElement otherChild = new ChildDrawableElement();
			otherChild.setPosition(100, 200);
			otherChild.setSize(35, 35);
			parent.addChild(otherChild);

			addController(new FitToChildrenSampleController(parent, controlledChild));
		}
	}

	public void run() {

		new FitToChildrenSampleScene().run();
	}

}
