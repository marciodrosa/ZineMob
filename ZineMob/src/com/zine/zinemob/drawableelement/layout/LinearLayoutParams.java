package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Contains the parameters to configure the layout of some DrawableElement.
 */
public class LinearLayoutParams {
	
	DrawableElement drawableElement;
	private int marginLeft, marginTop, marginRight, marginBottom;
	private int layoutFlags;

	/**
	 * Sets the margin of the element. It is used by the LayoutFixers to define
	 * the space between the element and the parent.
	 * @param marginLeft the space, in pixels, of the left side of the element
	 * @param marginTop the space, in pixels, of the top of the element
	 * @param marginRight the space, in pixels, of the right side of the element
	 * @param marginBottom the space, in pixels, of the bottom of the element
	 */
	public void setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
		this.marginLeft = marginLeft;
		this.marginTop = marginTop;
		this.marginRight = marginRight;
		this.marginBottom = marginBottom;
	}

	/**
	 * Sets the margin of the element. It is used by the LayoutFixers to define
	 * the space between the element and the children.
	 * @param margin the space, in pixels, of all sides of the element
	 */
	public void setMargin(int margin) {
		this.marginLeft = margin;
		this.marginTop = margin;
		this.marginRight = margin;
		this.marginBottom = margin;
	}

	/**
	 * Returns the margin, in pixels, of the left side of the element.
	 * @return the margin, in pixels, of the left side of the element
	 */
	public int getMarginLeft() {
		return marginLeft;
	}

	/**
	 * Returns the margin, in pixels, of the top of the element.
	 * @return the margin, in pixels, of the top of the element
	 */
	public int getMarginTop() {
		return marginTop;
	}

	/**
	 * Returns the margin, in pixels, of the right side of the element.
	 * @return the margin, in pixels, of the right side of the element
	 */
	public int getMarginRight() {
		return marginRight;
	}

	/**
	 * Returns the margin, in pixels, of the bottom of the element.
	 * @return the margin, in pixels, of the bottom of the element
	 */
	public int getMarginBottom() {
		return marginBottom;
	}

	/**
	 * Returns the flags to configure the layout of the DrawableElement.
	 */
	public int getLayoutFlags() {
		return layoutFlags;
	}

	/**
	 * Sets the flags to configure the layout of the DrawableElement.
	 */
	public void setLayoutFlags(int layoutFlags) {
		this.layoutFlags = layoutFlags;
	}
}
