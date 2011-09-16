package com.zine.zinemob.animation;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.Controller;
import com.zine.zinemob.scene.controller.Updateble;

/**
 * Base class for animations.
 */
public abstract class AnimationController extends Controller implements Updateble {
	
	private DrawableElement drawableElement;
	private boolean finishWhenDrawableElementParentIsNull = false;
	private AnimationListener animationListener;

	public void update() {
		if (mustFinishWhenDrawableElementParentIsNull() && getDrawableElement() != null && getDrawableElement().getParent() == null) {
			finish();
		}
	}
	
	public void finish() {
		if (getAnimationListener() != null) {
			getAnimationListener().onAnimationFinish();
		}
		super.finish();
	}

	/**
	 * Returns the DrawableElement of the animation.
	 */
	public DrawableElement getDrawableElement() {
		return drawableElement;
	}

	/**
	 * Sets the DrawableElement of the animation.
	 */
	public void setDrawableElement(DrawableElement drawableElement) {
		this.drawableElement = drawableElement;
	}

	/**
	 * Returns if the animation must be finished when the parent of the DrawableElement
	 * is null.
	 */
	public boolean mustFinishWhenDrawableElementParentIsNull() {
		return finishWhenDrawableElementParentIsNull;
	}

	/**
	 * Sets if the animation must be finished when the parent of the DrawableElement
	 * is null.
	 */
	public void setFinishWhenDrawableElementParentIsNull(boolean finishWhenDrawableElementParentIsNull) {
		this.finishWhenDrawableElementParentIsNull = finishWhenDrawableElementParentIsNull;
	}

	/**
	 * Returns the AnimationListener.
	 */
	public AnimationListener getAnimationListener() {
		return animationListener;
	}

	/**
	 * Sets the AnimationListener.
	 */
	public void setAnimationListener(AnimationListener animationListener) {
		this.animationListener = animationListener;
	}
}
