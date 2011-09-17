package com.zine.zinemob.animation;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Animation that resizes the DrawableElement from a size to another.
 */
public class ResizeAnimationController extends FramesAnimationController {
	
	private AnimationTrack wTrack = new AnimationTrack(), hTrack = new AnimationTrack();
	
	public ResizeAnimationController(DrawableElement drawableElement) {
		setDrawableElement(drawableElement);
		int w = drawableElement.getWidth();
		int h = drawableElement.getHeight();
		setResizeFromTo(w, h, w, h);
	}
	
	public void setResizeFromTo(int w1, int h1, int w2, int h2) {
		wTrack.setInitialValue(w1);
		wTrack.setFinalValue(w2);
		hTrack.setInitialValue(h1);
		hTrack.setFinalValue(h2);
	}
	
	public void setResizeTo(int w, int h) {
		setResizeFromTo(getDrawableElement().getWidth(), getDrawableElement().getHeight(), w, h);
	}
	
	public void setRelativeMovement(int w, int h) {
		setResizeTo(getDrawableElement().getWidth() + w, getDrawableElement().getHeight() + h);
	}

	public void updateFrame(int frame) {
		getDrawableElement().setSize(wTrack.getValueForFrame(frame, getLength()), hTrack.getValueForFrame(frame, getLength()));
	}
}
