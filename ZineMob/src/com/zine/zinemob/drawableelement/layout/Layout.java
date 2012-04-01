package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Object that listens for changes on some DrawableElement and apply modifications
 * on it (changing the position and size of the DrawableElement or the children,
 * for example). Uses the method addLayout of the DrawableElement to keep it updated
 * with the layout.
 */
public interface Layout {
	
	/**
	 * Apply the layout on the DrawableElement.
	 */
	public void apply();
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the position of the element was changed.
	 */
	public void onPositionChanged();
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the size of the element was changed.
	 */
	public void onSizeChanged();

	/**
	 * Called when the parent of the DrawableElement was changed.
	 */
	public void onParentChanged();
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the position of the parent of the element was changed.
	 */
	public void onParentPositionChanged();

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the size of the parent of the element was changed.
	 */
	public void onParentSizeChanged();

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the position of one child of the element was changed.
	 * @param child the child
	 */
	public void onChildPositionChanged(DrawableElement child);
	
	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when the size of one child of the element was changed.
	 * @param child the child
	 */
	public void onChildSizeChanged(DrawableElement child);

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when a new child is added to DrawableElement.
	 * @param child the child
	 */
	public void onChildAdded(DrawableElement child);

	/**
	 * Called to apply the changes on the position or size of the DrawableElement
	 * when some child is removed from the DrawableElement.
	 * @param child the child
	 */
	public void onChildRemoved(DrawableElement child);

}
