package com.zine.zinemob.animation;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Animation that moves the DrawableElement from a place to another.
 */
public class TranslationAnimationController extends FramesAnimationController {
	
	private AnimationTrack xTrack = new AnimationTrack(), yTrack = new AnimationTrack();
	
	public TranslationAnimationController(DrawableElement drawableElement) {
		setDrawableElement(drawableElement);
		int x = drawableElement.getX();
		int y = drawableElement.getY();
		setMoveFromTo(x, y, x, y);
	}
	
	public void setMoveFromTo(int x1, int y1, int x2, int y2) {
		xTrack.setInitialValue(x1);
		xTrack.setFinalValue(x2);
		yTrack.setInitialValue(y1);
		yTrack.setFinalValue(y2);
	}
	
	public void setMoveTo(int x, int y) {
		setMoveFromTo(getDrawableElement().getX(), getDrawableElement().getY(), x, y);
	}
	
	public void setRelativeMovement(int x, int y) {
		setMoveTo(getDrawableElement().getX() + x, getDrawableElement().getY() + y);
	}

	public void updateFrame(int frame) {
		getDrawableElement().setPosition(xTrack.getValueForFrame(frame, getLength()), yTrack.getValueForFrame(frame, getLength()));
	}
}
