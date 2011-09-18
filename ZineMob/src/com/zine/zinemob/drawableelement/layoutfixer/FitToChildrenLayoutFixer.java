package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * AreaFixer that fits the area of the DrawableElement to take exactly the same
 * space of the children. Updates the position and size of the DrawableElement.
 */
public class FitToChildrenLayoutFixer implements LayoutFixer {

	public void applyFix(DrawableElement drawableElement) {

		int childrenCount = drawableElement.getChildrenCount();

		if (childrenCount > 0) {

			DrawableElement child = drawableElement.getChild(0);

			int x = child.getX() - child.getMarginLeft() - drawableElement.getPaddingLeft();
			int y = child.getY() - child.getMarginTop() - drawableElement.getPaddingTop();
			int w = child.getX() + child.getWidth() + child.getMarginRight() + drawableElement.getPaddingRight();
			int h = child.getY() + child.getHeight() + child.getMarginBottom() + drawableElement.getPaddingBottom();

			int minX = x;
			int minY = y;
			int maxWidth = w;
			int maxHeight = h;

			for (int i=1; i<drawableElement.getChildrenCount(); i++) {

				child = drawableElement.getChild(i);

				x = child.getX() - child.getMarginLeft() - drawableElement.getPaddingLeft();
				y = child.getY() - child.getMarginTop() - drawableElement.getPaddingTop();
				w = child.getX() + child.getWidth() + child.getMarginRight() + drawableElement.getPaddingRight();
				h = child.getY() + child.getHeight() + child.getMarginBottom() + drawableElement.getPaddingBottom();

				minX = (x < minX) ? x : minX;
				minY = (y < minY) ? y : minY;
				maxWidth = (w > maxWidth) ? w : maxWidth;
				maxHeight = (h > maxHeight) ? h : maxHeight;
			}

			if (minX != 0 || minY != 0) {
				moveDrawableElementWithoutMoveChildren(drawableElement, minX, minY);
			}

			drawableElement.setSize(maxWidth - minX, maxHeight - minY);

		} else {
			drawableElement.setSize(0, 0);
		}
	}

	private void moveDrawableElementWithoutMoveChildren(DrawableElement drawableElement, int movementX, int movementY) {

		drawableElement.setPosition(drawableElement.getX() + movementX, drawableElement.getY() + movementY);

		for (int i=0; i<drawableElement.getChildrenCount(); i++) {

			DrawableElement child = drawableElement.getChild(i);
			child.setPosition(child.getX() - movementX, child.getY() - movementY);
		}
	}

	public void onPositionChanged(DrawableElement drawableElement) {
	}

	public void onSizeChanged(DrawableElement drawableElement) {
		applyFix(drawableElement);
	}

	public void onParentChanged(DrawableElement drawableElement) {
	}

	public void onParentPositionChanged(DrawableElement drawableElement) {
	}

	public void onParentSizeChanged(DrawableElement drawableElement) {
	}

	public void onChildPositionChanged(DrawableElement drawableElement, DrawableElement child) {
		applyFix(drawableElement);
	}

	public void onChildSizeChanged(DrawableElement drawableElement, DrawableElement child) {
		applyFix(drawableElement);
	}

	public void onChildAdded(DrawableElement drawableElement, DrawableElement child) {
		applyFix(drawableElement);
	}

	public void onChildRemoved(DrawableElement drawableElement, DrawableElement child) {
		applyFix(drawableElement);
	}
}
