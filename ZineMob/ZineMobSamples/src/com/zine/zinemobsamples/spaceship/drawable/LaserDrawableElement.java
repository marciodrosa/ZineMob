package com.zine.zinemobsamples.spaceship.drawable;

import com.zine.zinemob.drawableelement.SpriteElement;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class LaserDrawableElement extends SpriteElement {

	public LaserDrawableElement() {
		super((Sprite)null);
		try {
			Sprite sprite = new Sprite(Image.createImage("/com/zine/zinemobsamples/res/laser.png"));
			setSprite(sprite);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		setPivot(0, getHeight()/2);
	}

}
