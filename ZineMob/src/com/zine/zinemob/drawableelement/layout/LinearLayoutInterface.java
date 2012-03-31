package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

public interface LinearLayoutInterface {
	
	/**
	 * Defines a horizontal layout (elements side-by-side).
	 */
	public static final byte LAYOUT_TYPE_HORIZONTAL = 1;
	
	/**
	 * Defines a vertical layout (each element below the other).
	 */
	public static final byte LAYOUT_TYPE_VERTICAL = 2;
	
	/**
	 * Defines the fit policy to always fit the available space to the minimum space
	 * to can distribute all the children (horizontal).
	 */
	public static final int FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H = 1;
	
	/**
	 * Defines the fit policy to always fit the available space to the minimum space
	 * to can distribute all the children (vertical).
	 */
	public static final int FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V = 2;
	
	/**
	 * Defines the fit policy to always fit the available space to the minimum space
	 * to can distribute all the children.
	 */
	public static final int FIT_POLICY_ALWAYS_FIT_TO_CHILDREN = FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H | FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V;
	
	/**
	 * Defines the fit policy to fit the available space to the minimum space
	 * to can distribute all the children when the current space is smaller than
	 * the minimum space (horizontal).
	 */
	public static final int FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H = 4;
	
	/**
	 * Defines the fit policy to fit the available space to the minimum space
	 * to can distribute all the children when the current space is smaller than
	 * the minimum space (vertical).
	 */
	public static final int FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V = 8;
	
	/**
	 * Defines the fit policy to fit the available space to the minimum space
	 * to can distribute all the children when the current space is smaller than
	 * the minimum space.
	 */
	public static final int FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER = FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H | FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V;
	
	/**
	 * Defines the fit policy to never fit the available space to the children.
	 */
	public static final int FIT_POLICY_DONT_FIT_TO_CHILDREN = 0;
	
	/**
	 * Aligns the element at left.
	 */
	public static final int ALIGN_LEFT = 0;

	/**
	 * Aligns the element at top.
	 */
	public static final int ALIGN_TOP = 0;

	/**
	 * Aligns the element at right.
	 */
	public static final int ALIGN_RIGHT = 1;

	/**
	 * Aligns the element at bottom.
	 */
	public static final int ALIGN_BOTTOM = 2;

	/**
	 * Centralizes the element vertically.
	 */
	public static final int ALIGN_CENTER_V = 4;

	/**
	 * Centralizes the element horizontally.
	 */
	public static final int ALIGN_CENTER_H = 8;

	/**
	 * Centralizes the element vertically and horizontally.
	 */
	public static final int ALIGN_CENTER = ALIGN_CENTER_V | ALIGN_CENTER_H;
	
	/**
	 * Fits the element horizontally inside the available space.
	 */
	public static final int FIT_H = 16;

	/**
	 * Fits the element vertically inside the available space.
	 */
	public static final int FIT_V = 32;

	/**
	 * Fits the element horizontally and vertically inside the available space.
	 */
	public static final int FIT = FIT_H | FIT_V;
	
	/**
	 * Fits the element space into the layout. This flag fits only the
	 * available space; to fit the element size, use FIT.
	 */
	public static final int FIT_AVAILABLE_SPACE = 64;
	
	/**
	 * Stretches the element horizontally inside the available space, if the size
	 * is smaller than the available space.
	 */
	public static final int STRETCH_H = 128;

	/**
	 * Stretches the element vertically inside the available space, if the size
	 * is smaller than the available space.
	 */
	public static final int STRETCH_V = 256;

	/**
	 * Stretches the element horizontally and vertically inside the available space, if the size
	 * is smaller than the available space.
	 */
	public static final int STRETCH = STRETCH_H | STRETCH_V;
	
	/**
	 * Don't layout the element.
	 */
	public static final int IGNORE_LAYOUT = 512;
	
	/**
	 * Sets the padding of the element. It is used by the LayoutFixers to define
	 * the space between the element and the children.
	 * @param paddingLeft the space, in pixels, of the left side of the element
	 * @param paddingTop the space, in pixels, of the top of the element
	 * @param paddingRight the space, in pixels, of the right side of the element
	 * @param paddingBottom the space, in pixels, of the bottom of the element
	 */
	public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom);

	/**
	 * Sets the padding of the element. It is used by the LayoutFixers to define
	 * the space between the element and the children.
	 */
	public void setPadding(int padding);

	/**
	 * Returns the padding, in pixels, of the left side of the element.
	 */
	public int getPaddingLeft();

	/**
	 * Returns the padding, in pixels, of the top of the element.
	 */
	public int getPaddingTop();

	/**
	 * Returns the padding, in pixels, of the right side of the element.
	 */
	public int getPaddingRight();

	/**
	 * Returns the padding, in pixels, of the bottom of the element.
	 */
	public int getPaddingBottom();

	/**
	 * Returns the layout type (LAYOUT_TYPE_HORIZONTAL or LAYOUT_TYPE_VERTICAL).
	 */
	public byte getLayoutType();

	/**
	 * Sets the layout type (LAYOUT_TYPE_HORIZONTAL or LAYOUT_TYPE_VERTICAL).
	 */
	public void setLayoutType(byte layoutType);
	
	/**
	 * Sets the policy to be used to fit the available space to children.
	 */
	public void setFitPolicy(int fitPolicy);
	
	/**
	 * Returns the policy to use to fit the available space to children.
	 */
	public int getFitPolicy();
	
	/**
	 * Sets the layout parameters of some of the children of the DrawableElement.
	 * @param child the child
	 * @param params the LinearLayoutParams
	 */
	public void setParams(DrawableElement child, LinearLayoutParams params);
	
	/**
	 * Returns the layout parameters of some of the children of the DrawableElement.
	 * @param child the child
	 * @returns the LinearLayoutParams object associated with the DrawableElement, or
	 * null it it does'nt have one
	 */
	public LinearLayoutParams getParams(DrawableElement child);
	
	/**
	 * Defines the layout flags of some of the children of the DrawableElement.
	 */
	public void setLayoutFlags(DrawableElement child, int flags);
	
	/**
	 * Returns the layout flags of some of the children of the DrawableElement.
	 */
	public int getLayoutFlags(DrawableElement child);
	
	/**
	 * Sets the margin of some of the children of the DrawableElement. The margin
	 * is the left, top, right and bottom space, in pixels, between the element
	 * and the neighborhoods.
	 * @param child the child
	 * @param margin the space to be setted to the left, top, right and bottom margin
	 */
	public void setMargin(DrawableElement child, int margin);
	
	/**
	 * Sets the margin of some of the children of the DrawableElement. The margin
	 * is the left, top, right and bottom space, in pixels, between the element
	 * and the neighborhoods.
	 * @param child the child
	 * @param marginLeft the left margin
	 * @param marginTop the top margin
	 * @param marginRight the right margin
	 * @param marginBottom the bottom margin
	 */
	public void setMargin(DrawableElement child, int marginLeft, int marginTop, int marginRight, int marginBottom);
	
	/**
	 * Returns the left margin of the child of the DrawableElement.
	 */
	public int getMarginLeft(DrawableElement child);
	
	/**
	 * Returns the top margin of the child of the DrawableElement.
	 */
	public int getMarginTop(DrawableElement child);
	
	/**
	 * Returns the right margin of the child of the DrawableElement.
	 */
	public int getMarginRight(DrawableElement child);
	
	/**
	 * Returns the bottom margin of the child of the DrawableElement.
	 */
	public int getMarginBottom(DrawableElement child);
}
