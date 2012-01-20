package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;

public interface LinearLayoutHandler {
	
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
	 * to can distribute all the children.
	 */
	public static final byte FIT_POLICY_ALWAYS_FIT_TO_CHILDREN = 1 | 2;
	
	/**
	 * Defines the fit policy to fit the available space to the minimum space
	 * to can distribute all the children when the current space is smaller than
	 * the minimum space.
	 */
	public static final byte FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER = 4 | 8;
	
	/**
	 * Defines the fit policy to never fit the available space to the children.
	 */
	public static final byte FIT_POLICY_DONT_FIT_TO_CHILDREN = 16 | 32;
	
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
	 * Stretches the element horizontally inside the available space.
	 */
	public static final int STRETCH_H = 16;

	/**
	 * Stretches the element vertically inside the available space.
	 */
	public static final int STRETCH_V = 32;

	/**
	 * Stretches the element horizontally and vertically inside the available space.
	 */
	public static final int STRETCH = STRETCH_H | STRETCH_V;
	
	/**
	 * Stretches the element space into the layout.
	 */
	public static final int STRETCH_AVAILABLE_SPACE = 64;
	
	/**
	 * Don't layout the element.
	 */
	public static final int IGNORE_LAYOUT = 128;

	/**
	 * Returns the layout type (LAYOUT_TYPE_HORIZONTAL or LAYOUT_TYPE_VERTICAL).
	 */
	public byte getLayoutType();

	/**
	 * Sets the layout type (LAYOUT_TYPE_HORIZONTAL or LAYOUT_TYPE_VERTICAL).
	 */
	public void setLayoutType(byte layoutType);
	
	/**
	 * Defines the layout flags of some of the children of the DrawableElement.
	 */
	public void setLayoutFlags(DrawableElement child, int flags);
	
	/**
	 * Returns the layout flags of some of the children of the DrawableElement.
	 */
	public int getLayoutFlags(DrawableElement child);
	
	/**
	 * Returns if the layout flags of some of the children of the DrawableElement
	 * has the parameter flags.
	 */
	public boolean hasLayoutFlags(DrawableElement child, int flags);
	
	/**
	 * Sets the policy to be used to fit the available space to children.
	 */
	public void setFitPolicy(byte fitPolicy);
	
	/**
	 * Returns the policy to use to fit the available space to children.
	 */
	public byte getFitPolicy();
}