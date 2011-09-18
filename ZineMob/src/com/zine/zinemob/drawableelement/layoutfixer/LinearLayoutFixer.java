package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;
import java.util.Hashtable;

/**
 * Layout that organizes the children of the element side-by-side (if it is horizontal)
 * or each child below the other (if it is vertical). The layout is vertical by default.
 */
public class LinearLayoutFixer implements LayoutFixer {
	
	/**
	 * Defines a horizontal layout (elements side-by-side).
	 */
	public static final byte LAYOUT_TYPE_HORIZONTAL = 1;
	
	/**
	 * Defines a vertical layout (each element below the other).
	 */
	public static final byte LAYOUT_TYPE_VERTICAL = 2;
	
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
	
	private byte layoutType = LAYOUT_TYPE_VERTICAL;
	private Hashtable layoutFlags = new Hashtable(); // <DrawableElement, Integer>
	private boolean fitToChildren = false;

	public void applyFix(DrawableElement drawableElement) {
		
		int[] requiredSpaces = getChildrenRequiredSpaces(drawableElement);
		int requiredPeripheralSpace = getChildrenRequiredPeripheralSpace(drawableElement);
		
		int currentLayoutPosition = 0;
		
		if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
			currentLayoutPosition = drawableElement.getPaddingTop();
		} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
			currentLayoutPosition = drawableElement.getPaddingLeft();
		}

		for (int i=0; i<drawableElement.getChildrenCount(); i++) {
			
			DrawableElement child = drawableElement.getChild(i);
			
			if (mustIgnore(child)) {
				continue;
			}

			int x = child.getMarginLeft();
			int y = child.getMarginTop();
			int w = child.getWidth();
			int h = child.getHeight();
			
			// horizontal setup:
			if (hasLayoutFlags(child, ALIGN_RIGHT)) {
				if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
					x = requiredPeripheralSpace - child.getWidth() - child.getMarginRight();
				} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
					x = requiredSpaces[i] - child.getWidth() - child.getMarginRight();
				}
			} else if (hasLayoutFlags(child, ALIGN_CENTER_H)) {
				if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
					x = (requiredPeripheralSpace/2) - (child.getWidth()/2);
				} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
					x = (requiredSpaces[i]/2) - (child.getWidth()/2);
				}
			} else if (hasLayoutFlags(child, STRETCH_H)) {
				if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
					w = requiredPeripheralSpace - child.getMarginLeft() - child.getMarginRight();
				} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
					w = requiredSpaces[i] - child.getMarginLeft() - child.getMarginRight();
				}
			}

			// vertical setup:
			if (hasLayoutFlags(child, ALIGN_BOTTOM)) {
				if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
					y = requiredSpaces[i] - child.getHeight() - child.getMarginBottom();
				} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
					y = requiredPeripheralSpace - child.getHeight() - child.getMarginBottom();
				}
			}
			else if (hasLayoutFlags(child, ALIGN_CENTER_V)) {
				if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
					y = ( (requiredSpaces[i] - child.getMarginTop() - child.getMarginBottom())/2) - (child.getHeight()/2) + child.getMarginTop();
				} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
					y = (requiredPeripheralSpace/2) - (child.getHeight()/2);
				}
			}
			else if (hasLayoutFlags(child, STRETCH_V)) {
				if (getLayoutType() == LAYOUT_TYPE_VERTICAL)
					h = requiredSpaces[i] - child.getMarginTop() - child.getMarginBottom();
				else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL)
					h = requiredPeripheralSpace - child.getMarginTop() - child.getMarginBottom();
			}

			if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
				x += currentLayoutPosition;
				y += drawableElement.getPaddingTop();
			} if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
				x += drawableElement.getPaddingLeft();
				y += currentLayoutPosition;
			}

			currentLayoutPosition += requiredSpaces[i];
			
			child.setPosition(x, y);
			child.setSize(w, h);
		}
		
		if (mustFitToChildren()) {
			if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
				drawableElement.setSize(currentLayoutPosition + drawableElement.getPaddingRight(),
						requiredPeripheralSpace + drawableElement.getPaddingTop() + drawableElement.getPaddingBottom());
			} if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
				drawableElement.setSize(requiredPeripheralSpace + drawableElement.getPaddingLeft() + drawableElement.getPaddingRight(),
						currentLayoutPosition + drawableElement.getPaddingBottom());
			}
		}
	}
	
	private boolean mustIgnore(DrawableElement drawableElement) {
		return hasLayoutFlags(drawableElement, IGNORE_LAYOUT);
	}
	
	/**
	 * Returns the peripheral space required to acomodate the children.
	 */
	private int getChildrenRequiredPeripheralSpace(DrawableElement drawableElement) {
		
		int peripheralSpace = 0;
		
		if (!mustFitToChildren()) {
			if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
				peripheralSpace = drawableElement.getWidth() - drawableElement.getPaddingLeft() - drawableElement.getPaddingRight();
			} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
				peripheralSpace = drawableElement.getHeight() - drawableElement.getPaddingTop() - drawableElement.getPaddingBottom();
			}
		}
		
		if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
			for (int i=0; i<drawableElement.getChildrenCount(); i++) {
				DrawableElement child = drawableElement.getChild(i);
				if (!mustIgnore(child)) {
					int childPeripheralSpace = child.getWidth() + child.getMarginLeft() + child.getMarginRight();
					if (childPeripheralSpace > peripheralSpace) {
						peripheralSpace = childPeripheralSpace;
					}
				}
			}
		} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
			for (int i=0; i<drawableElement.getChildrenCount(); i++) {
				DrawableElement child = drawableElement.getChild(i);
				if (!mustIgnore(child)) {
					int childPeripheralSpace = child.getHeight() + child.getMarginTop() + child.getMarginBottom();
					if (childPeripheralSpace > peripheralSpace) {
						peripheralSpace = childPeripheralSpace;
					}
				}
			}
		}
		
		return peripheralSpace;
	}
	
	/**
	 * Returns an array with the sizes of the spaces for each DrawableElement.
	 * If the layout has an fixed size, then some DrawableElements can be stretcheds
	 * to fit the layout space.
	 * 1) All drawable elements sizes are summed.
	 * 2) If some space N remains:
	 * - then the stretchable elements will be stretched.
	 * - if there is X stretchable elements, each one will be stretched by N / X.
	 * 3) If some space N lacks:
	 * - then the stretchable elements are decreased.
	 * - if there is X stretchable elements, each one will be decreased by N / X.
	 * - the size never is decreased to be less than 0.
	 */
	private int[] getChildrenRequiredSpaces(DrawableElement drawableElement) {
		
		int[] requiredSpaces = new int[drawableElement.getChildrenCount()];
		boolean[] isStretchable = new boolean[drawableElement.getChildrenCount()];

		int totalRequiredSpace = 0;
		int stretchableElementsCount = 0;

		for (int i=0; i<drawableElement.getChildrenCount(); i++) {
			DrawableElement child = drawableElement.getChild(i);
			if (!mustIgnore(child)) {
				if (hasLayoutFlags(child, STRETCH_AVAILABLE_SPACE)) {
					isStretchable[i] = true;
					stretchableElementsCount++;
				}
				int requiredSpace = getItemSpace(child);
				totalRequiredSpace += requiredSpace;
				requiredSpaces[i] = requiredSpace;
			}
		}
		
		int availableSpace = 0;
		if (mustFitToChildren()) {
			availableSpace = totalRequiredSpace;
		} else {
			if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
				availableSpace = drawableElement.getHeight() - drawableElement.getPaddingTop() - drawableElement.getPaddingBottom();
			} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
				availableSpace = drawableElement.getWidth() - drawableElement.getPaddingLeft() - drawableElement.getPaddingRight();
			}
		}

		if (!mustFitToChildren()) {
			if (availableSpace > totalRequiredSpace && stretchableElementsCount > 0) { // some space remains, some drawable elements can be stretched

				int spaceToBeStretchedByElement = (availableSpace-totalRequiredSpace) / stretchableElementsCount;
				
				for (int i=0; i<drawableElement.getChildrenCount(); i++) {

					if (isStretchable[i]) {
						requiredSpaces[i] += spaceToBeStretchedByElement;
					}
				}
				
			} else if (availableSpace < totalRequiredSpace && stretchableElementsCount > 0) { // some space lacks, some drawable elements must be be squached

				int spaceToBeSquachedByElement = (totalRequiredSpace - availableSpace) / stretchableElementsCount;

				for (int i=0; i<drawableElement.getChildrenCount(); i++) {

					if (isStretchable[i]) {
						int newRequiredSpace = requiredSpaces[i] - spaceToBeSquachedByElement;

						if (newRequiredSpace < 0) {
							newRequiredSpace = 0;
						}

						requiredSpaces[i] = newRequiredSpace;
					}
				}
			}
		}
		
		return requiredSpaces;
	}
	
	/**
	 * Returns the minimum space required by the DrawableElement (width if it is a
	 * horizontal layout, height is it is a vertical layout).
	 */
	private int getItemSpace(DrawableElement drawableElement) {
		
		switch(getLayoutType()) {
			case LAYOUT_TYPE_VERTICAL:
				return drawableElement.getHeight() + drawableElement.getMarginTop() + drawableElement.getMarginBottom();
			case LAYOUT_TYPE_HORIZONTAL:
				return drawableElement.getWidth() + drawableElement.getMarginLeft() + drawableElement.getMarginRight();
			default:
				return 0;
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
	}

	public void onChildSizeChanged(DrawableElement drawableElement, DrawableElement child) {
		applyFix(drawableElement);
	}

	public void onChildAdded(DrawableElement drawableElement, DrawableElement child) {
		applyFix(drawableElement);
	}

	public void onChildRemoved(DrawableElement drawableElement, DrawableElement child) {
		layoutFlags.remove(child);
		applyFix(drawableElement);
	}

	/**
	 * Returns the layout type (LAYOUT_TYPE_HORIZONTAL or LAYOUT_TYPE_VERTICAL).
	 */
	public byte getLayoutType() {
		return layoutType;
	}

	/**
	 * Sets the layout type (LAYOUT_TYPE_HORIZONTAL or LAYOUT_TYPE_VERTICAL).
	 */
	public void setLayoutType(byte layoutType) {
		this.layoutType = layoutType;
	}
	
	/**
	 * Defines the layout flags of some of the children of the DrawableElement.
	 */
	public void setLayoutFlags(DrawableElement child, int flags) {
		layoutFlags.put(child, new Integer(flags));
	}
	
	/**
	 * Returns the layout flags of some of the children of the DrawableElement.
	 */
	public int getLayoutFlags(DrawableElement child) {
		Integer flags = (Integer) layoutFlags.get(child);
		if (flags == null) {
			return 0;
		} else {
			return flags.intValue();
		}
	}
	
	/**
	 * Returns if the layout flags of some of the children of the DrawableElement
	 * has the parameter flags.
	 */
	public boolean hasLayoutFlags(DrawableElement child, int flags) {
		int elementFlags = getLayoutFlags(child);
		return ((elementFlags & flags) != 0);
	}

	/**
	 * Returns if the DrawableElement must fits his size to the children space.
	 */
	public boolean mustFitToChildren() {
		return fitToChildren;
	}

	/**
	 * Defines if the DrawableElement must fits his size to the children space.
	 */
	public void setFitToChildren(boolean fitToChildren) {
		this.fitToChildren = fitToChildren;
	}
	
}
