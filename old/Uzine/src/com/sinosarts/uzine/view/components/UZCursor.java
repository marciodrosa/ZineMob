package com.sinosarts.uzine.view.components;

import com.sinosarts.uzine.view.theme.LayoutManager;
import com.sinosarts.zinemob.windows.Component;
import com.sinosarts.zinemob.windows.Cursor;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class UZCursor extends Cursor {

	private byte animation = 0;
	private static final byte ANIMATION_TURN_TO_LEFT = 1;
	private static final byte ANIMATION_TURN_TO_RIGHT = 2;

	private Sprite cursorSprite;

	public UZCursor() {
		try {
			Image cursorImage = Image.createImage("/com/sinosarts/uzine/res/cursor.png");
			cursorSprite = new Sprite(cursorImage, 16, 19);
			setLayerElement(cursorSprite);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}

		setMovementAnimationSteps(7);
	}

	public void onFocusComponent(Component c) {
		super.onFocusComponent(c);

		// se a posição do cursor no widget fica na metade direita do widget, vira o cursor para a esquerda (animação do sprite):
		if (c.getCursorPositionPointX() > c.getRenderElement().getWidth() / 2) {
			animation = ANIMATION_TURN_TO_LEFT;
		}
		else {
			animation = ANIMATION_TURN_TO_RIGHT;
		}
	}

	public void draw(Graphics g) {

		super.draw(g);

		if (animation == ANIMATION_TURN_TO_LEFT) {
			if (cursorSprite.getFrame() < cursorSprite.getFrameSequenceLength() - 1) {
				cursorSprite.nextFrame();
				requestRepaint();
			}
		}
		else if (animation == ANIMATION_TURN_TO_RIGHT) {
			if (cursorSprite.getFrame() > 0) {
				cursorSprite.prevFrame();
				requestRepaint();
			}
		}
	}

	protected void updatePosition(Component c) {

		if (animation == ANIMATION_TURN_TO_LEFT)
			setGlobalPosition(c.getRenderElement().getGlobalX() + c.getCursorPositionPointX(),
					c.getRenderElement().getGlobalY() + c.getCursorPositionPointY());
		else
			setGlobalPosition(c.getRenderElement().getGlobalX() + c.getCursorPositionPointX() - cursorSprite.getWidth(),
					c.getRenderElement().getGlobalY() + c.getCursorPositionPointY());
	}

}
