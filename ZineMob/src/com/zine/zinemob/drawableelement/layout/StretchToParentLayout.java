package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * A Layout that stretches the DrawableElement to fit the parent. Do nothing if
 * the DrawableElement doesn't have parent. First, create the StretchToParentLayout using
 * the constructor StretchToParentLayout(myDrawableElement) and then call
 * myDrawableElement.addLayout(myStretchToParentLayout) (or just call myStretchToParentLayout.apply()
 * to apply the layout once).
 */
public class StretchToParentLayout implements Layout {

	public static final int LEFT = 1, TOP = 2, RIGHT = 4, BOTTOM = 8;

	private DrawableElement drawableElement;
	private int constraints = 0;

	public StretchToParentLayout(DrawableElement drawableElement) {
		this(drawableElement, LEFT | TOP | RIGHT | BOTTOM);
	}

	/**
	 * Constructor with constraints.
	 * @param drawableElement the DrawableElement
	 * @param constraints combination of the constants LEFT, TOP, RIGHT and BOTTOM
	 * to define the directions to stretch the DrawableElement.
	 */
	public StretchToParentLayout(DrawableElement drawableElement, int constraints) {
		this.constraints = constraints;
		this.drawableElement = drawableElement;
	}

	public void apply() {

		DrawableElement parent = drawableElement.getParent();

		if (parent != null) {

			int x = drawableElement.getX();
			int y = drawableElement.getY();
			int w = drawableElement.getWidth();
			int h = drawableElement.getHeight();

			if ((constraints & LEFT) != 0) {
				w += x;
				x = 0;
			}

			if ((constraints & TOP) != 0) {
				h += y;
				y = 0;
			}

			if ((constraints & RIGHT) != 0) {
				w = parent.getWidth() - x;
			}

			if ((constraints & BOTTOM) != 0) {
				h = parent.getHeight() - y;
			}

			drawableElement.setPosition(x, y);
			drawableElement.setSize(w, h);
		}
	}

	public void onPositionChanged() {
	}

	public void onSizeChanged() {
	}

	public void onParentChanged() {
		apply();
	}

	public void onParentPositionChanged() {
	}

	public void onParentSizeChanged() {
		apply();
	}

	public void onChildPositionChanged(DrawableElement child) {
	}

	public void onChildSizeChanged(DrawableElement child) {
	}

	public void onChildAdded(DrawableElement child) {
	}

	public void onChildRemoved(DrawableElement child) {
	}

	/**
	 * Returns a combination of the constants LEFT, TOP, RIGHT and BOTTOM, that
	 * defines the directions to stretch the DrawableElement.
	 */
	public int getConstraints() {
		return constraints;
	}

	/**
	 * Sets a combination of the constants LEFT, TOP, RIGHT and BOTTOM to define
	 * the directions to stretch the DrawableElement.
	 */
	public void setConstraints(int constraints) {
		this.constraints = constraints;
	}
}
