package com.zine.zinemobsamples.spaceship.drawable;

import com.zine.zinemob.drawableelement.SpriteElement;
import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class ShipDrawableElement extends SpriteElement {

	public ShipDrawableElement() {
		super((Sprite)null);
		try {
			Sprite sprite = new Sprite(Image.createImage("/com/zine/zinemobsamples/res/ship.png"));
			setSprite(sprite);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		centerPivot();
	}

}
