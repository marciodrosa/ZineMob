package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

public class StretchToParentLayout implements Layout {

	public static final int LEFT = 1, TOP = 2, RIGHT = 4, BOTTOM = 8;

	private int constraints = 0;

	public StretchToParentLayout() {
		this(LEFT | TOP | RIGHT | BOTTOM);
	}

	public StretchToParentLayout(int constraints) {
		this.constraints = constraints;
	}

	public void applyFix(DrawableElement drawableElement) {

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

	public void onPositionChanged(DrawableElement drawableElement) {
	}

	public void onSizeChanged(DrawableElement drawableElement) {
	}

	public void onParentChanged(DrawableElement drawableElement) {
		applyFix(drawableElement);
	}

	public void onParentPositionChanged(DrawableElement drawableElement) {
	}

	public void onParentSizeChanged(DrawableElement drawableElement) {
		applyFix(drawableElement);
	}

	public void onChildPositionChanged(DrawableElement drawableElement, DrawableElement child) {
	}

	public void onChildSizeChanged(DrawableElement drawableElement, DrawableElement child) {
	}

	public void onChildAdded(DrawableElement drawableElement, DrawableElement child) {
	}

	public void onChildRemoved(DrawableElement drawableElement, DrawableElement child) {
	}

	public int getConstraints() {
		return constraints;
	}

	public void setConstraints(int constraints) {
		this.constraints = constraints;
	}
}
