package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

public class StretchToParentLayout implements Layout {

	public static final int LEFT = 1, TOP = 2, RIGHT = 4, BOTTOM = 8;

	private DrawableElement drawableElement;
	private int constraints = 0;

	public StretchToParentLayout(DrawableElement drawableElement) {
		this(drawableElement, LEFT | TOP | RIGHT | BOTTOM);
	}

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
				x = parent.getPaddingLeft() + drawableElement.getMarginLeft();
			}

			if ((constraints & TOP) != 0) {
				y = parent.getPaddingTop() + drawableElement.getMarginTop();
			}

			if ((constraints & RIGHT) != 0) {
				w = parent.getWidth() - x - parent.getPaddingRight() - drawableElement.getMarginRight();
			}

			if ((constraints & BOTTOM) != 0) {
				h = parent.getHeight() - y - parent.getPaddingBottom() - drawableElement.getMarginBottom();
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

	public int getConstraints() {
		return constraints;
	}

	public void setConstraints(int constraints) {
		this.constraints = constraints;
	}
}
