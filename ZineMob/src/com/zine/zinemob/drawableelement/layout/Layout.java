package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Handles the position and size of a DrawableElement. Can update the area of the
 * DrawableElement automatically.
 */
public interface Layout {

	/**
	 * Called to apply the changes on the position or size of the DrawableElement.
	 * @param drawableElement the DrawableElement
	 */
	public void applyFix(DrawableElement drawableElement);
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the position of the element was changed.
	 * @param drawableElement the DrawableElement
	 */
	public void onPositionChanged(DrawableElement drawableElement);
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the size of the element was changed.
	 * @param drawableElement the DrawableElement
	 */
	public void onSizeChanged(DrawableElement drawableElement);

	/**
	 * Called when the parent of the DrawableElement was changed.
	 * @param drawableElement the DrawableElement
	 */
	public void onParentChanged(DrawableElement drawableElement);
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the position of the parent of the element was changed.
	 * @param drawableElement the DrawableElement
	 */
	public void onParentPositionChanged(DrawableElement drawableElement);

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the size of the parent of the element was changed.
	 * @param drawableElement the DrawableElement
	 */
	public void onParentSizeChanged(DrawableElement drawableElement);

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the position of one child of the element was changed.
	 * @param drawableElement the DrawableElement
	 * @param child the child
	 */
	public void onChildPositionChanged(DrawableElement drawableElement, DrawableElement child);
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the size of one child of the element was changed.
	 * @param drawableElement the DrawableElement
	 * @param child the child
	 */
	public void onChildSizeChanged(DrawableElement drawableElement, DrawableElement child);

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when a new child is added to DrawableElement.
	 * @param drawableElement the DrawableElement
	 * @param child the child
	 */
	public void onChildAdded(DrawableElement drawableElement, DrawableElement child);

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when some child is removed from the DrawableElement.
	 * @param drawableElement the DrawableElement
	 * @param child the child
	 */
	public void onChildRemoved(DrawableElement drawableElement, DrawableElement child);

}
