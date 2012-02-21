package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Layout that organizes the children of the element side-by-side (if it is horizontal)
 * or each child below the other (if it is vertical). The layout is vertical by default.
 */
public class LinearLayoutFixer implements LayoutFixer, LinearLayoutHandler {
	
	private static class ChildLayoutFlags {
		DrawableElement drawableElement;
		int layoutFlags;
	};
	
	private byte layoutType = LAYOUT_TYPE_VERTICAL;
	private Vector layoutFlags = new Vector(); // <ChildLayoutFlags>
	private int fitPolicy = FIT_POLICY_DONT_FIT_TO_CHILDREN;
	private Hashtable onGoingElementsSet = new Hashtable();
	private Hashtable pendingElementsSet = new Hashtable();
	
	public synchronized void applyFix(DrawableElement drawableElement) {
		if (onGoingElementsSet.containsKey(drawableElement)) {
			pendingElementsSet.put(drawableElement, drawableElement);
		} else {
			onGoingElementsSet.put(drawableElement, drawableElement);
			applyLinearLayout(drawableElement);
			if (pendingElementsSet.containsKey(drawableElement)) {
				applyLinearLayout(drawableElement);
			}
			pendingElementsSet.remove(drawableElement);
			onGoingElementsSet.remove(drawableElement);
		}
	}
	
	private void applyLinearLayout(DrawableElement drawableElement) {
		int childrenRequiredPeripheralSpace = getChildrenRequiredPeripheralSpace(drawableElement);
		int[] requiredSpaces = getChildrenRequiredSpaces(drawableElement);
		
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
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					x = childrenRequiredPeripheralSpace - child.getWidth() - child.getMarginRight();
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					x = requiredSpaces[i] - child.getWidth() - child.getMarginRight();
				}
			} else if (hasLayoutFlags(child, ALIGN_CENTER_H)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					x = (childrenRequiredPeripheralSpace/2) - (child.getWidth()/2);
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					x = (requiredSpaces[i]/2) - (child.getWidth()/2);
				}
			} else if (hasLayoutFlags(child, STRETCH_H)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					w = childrenRequiredPeripheralSpace - child.getMarginLeft() - child.getMarginRight();
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					w = requiredSpaces[i] - child.getMarginLeft() - child.getMarginRight();
				}
			}

			// vertical setup:
			if (hasLayoutFlags(child, ALIGN_BOTTOM)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					y = requiredSpaces[i] - child.getHeight() - child.getMarginBottom();
				} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
					y = childrenRequiredPeripheralSpace - child.getHeight() - child.getMarginBottom();
				}
			}
			else if (hasLayoutFlags(child, ALIGN_CENTER_V)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					y = ( (requiredSpaces[i] - child.getMarginTop() - child.getMarginBottom())/2) - (child.getHeight()/2) + child.getMarginTop();
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					y = (childrenRequiredPeripheralSpace/2) - (child.getHeight()/2);
				}
			}
			else if (hasLayoutFlags(child, STRETCH_V)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL)
					h = requiredSpaces[i] - child.getMarginTop() - child.getMarginBottom();
				else if (layoutType == LAYOUT_TYPE_HORIZONTAL)
					h = childrenRequiredPeripheralSpace - child.getMarginTop() - child.getMarginBottom();
			}

			if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
				x += currentLayoutPosition;
				y += drawableElement.getPaddingTop();
			} if (layoutType == LAYOUT_TYPE_VERTICAL) {
				x += drawableElement.getPaddingLeft();
				y += currentLayoutPosition;
			}

			updatePositionAndSizeOfChild(x, y, w, h, child);
			
			childrenRequiredPeripheralSpace = getChildrenRequiredPeripheralSpace(drawableElement);
			requiredSpaces = getChildrenRequiredSpaces(drawableElement);
			
			currentLayoutPosition += requiredSpaces[i];
		}
		
		int requiredWidth = 0;
		int requiredHeight = 0;
		
		if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
			requiredWidth = currentLayoutPosition + drawableElement.getPaddingRight();
			requiredHeight = childrenRequiredPeripheralSpace + drawableElement.getPaddingTop() + drawableElement.getPaddingBottom();
		} if (layoutType == LAYOUT_TYPE_VERTICAL) {
			requiredWidth = childrenRequiredPeripheralSpace + drawableElement.getPaddingLeft() + drawableElement.getPaddingRight();
			requiredHeight = currentLayoutPosition + drawableElement.getPaddingBottom();
		}
		
		setFinalSize(drawableElement, requiredWidth, requiredHeight);
	}
	
	private void updatePositionAndSizeOfChild(int x, int y, int w, int h, DrawableElement child) {
		child.setPosition(x + child.getPivotX(), y + child.getPivotY());
		child.setSize(w, h);
	}
	
	private void setFinalSize(DrawableElement drawableElement, int requiredWidth, int requiredHeight) {
		int finalWidth = drawableElement.getWidth();
		int finalHeight = drawableElement.getHeight();
		if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H) != 0) {
			finalWidth = requiredWidth;
		} else if ((fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H) != 0) {
			finalWidth = requiredWidth > drawableElement.getWidth() ? requiredWidth : drawableElement.getWidth();
		}
		if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V) != 0) {
			finalHeight = requiredHeight;
		} else if ((fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V) != 0) {
			finalHeight = requiredHeight > drawableElement.getHeight() ? requiredHeight : drawableElement.getHeight();
		}
		if (finalWidth != drawableElement.getWidth() || finalHeight != drawableElement.getHeight()) {
			drawableElement.setSize(finalWidth, finalHeight);
		}
	}
	
	private boolean mustIgnore(DrawableElement drawableElement) {
		return hasLayoutFlags(drawableElement, IGNORE_LAYOUT);
	}
	
	/**
	 * Returns the peripheral space required to acomodate the children.
	 */
	private int getChildrenRequiredPeripheralSpace(DrawableElement drawableElement) {
		int requiredPeripheralSpace = getRequiredPeripheralSpace(drawableElement);
		int availablePeripheralSpace = getAvailablePeripheralSpace(drawableElement);
		int finalPeripheralSpace = 0;
		if (layoutType == LAYOUT_TYPE_VERTICAL) {
			if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H) != 0) {
				finalPeripheralSpace =requiredPeripheralSpace;
			} else if ((fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H) != 0) {
				if (availablePeripheralSpace < requiredPeripheralSpace) {
					finalPeripheralSpace = requiredPeripheralSpace;
				} else {
					finalPeripheralSpace = availablePeripheralSpace;
				}
			} else {
				finalPeripheralSpace = availablePeripheralSpace;
			}
		} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
			if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V) != 0) {
				finalPeripheralSpace = requiredPeripheralSpace;
			} else if ((fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V) != 0) {
				if (availablePeripheralSpace < requiredPeripheralSpace) {
					finalPeripheralSpace = requiredPeripheralSpace;
				} else {
					finalPeripheralSpace = availablePeripheralSpace;
				}
			} else {
				finalPeripheralSpace = availablePeripheralSpace;
			}
		}
		return finalPeripheralSpace;
	}
	
	private int getRequiredPeripheralSpace(DrawableElement drawableElement) {
		int requiredPeripheralSpace = 0;
		if (layoutType == LAYOUT_TYPE_VERTICAL) {
			if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H) != 0 || (fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H) != 0) {
				for (int i=0; i<drawableElement.getChildrenCount(); i++) {
					DrawableElement child = drawableElement.getChild(i);
					if (!mustIgnore(child)) {
						int childPeripheralSpace = child.getWidth() + child.getMarginLeft() + child.getMarginRight();
						if (childPeripheralSpace > requiredPeripheralSpace) {
							requiredPeripheralSpace = childPeripheralSpace;
						}
					}
				}
			}
		} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
			if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V) != 0 || (fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V) != 0) {
				for (int i=0; i<drawableElement.getChildrenCount(); i++) {
					DrawableElement child = drawableElement.getChild(i);
					if (!mustIgnore(child)) {
						int childPeripheralSpace = child.getHeight() + child.getMarginTop() + child.getMarginBottom();
						if (childPeripheralSpace > requiredPeripheralSpace) {
							requiredPeripheralSpace = childPeripheralSpace;
						}
					}
				}
			}
		}
		return requiredPeripheralSpace;
	}
	
	private int getAvailablePeripheralSpace(DrawableElement drawableElement) {
		int availablePeripheralSpace = 0;
		if (layoutType == LAYOUT_TYPE_VERTICAL) {
			availablePeripheralSpace = drawableElement.getWidth() - drawableElement.getPaddingLeft() - drawableElement.getPaddingRight();
		} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
			availablePeripheralSpace = drawableElement.getHeight() - drawableElement.getPaddingTop() - drawableElement.getPaddingBottom();
		}
		return availablePeripheralSpace;
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
				int requiredSpace = getChildSpace(child);
				totalRequiredSpace += requiredSpace;
				requiredSpaces[i] = requiredSpace;
			}
		}
		
		int availableSpace = 0;
		if (layoutType == LAYOUT_TYPE_VERTICAL) {
			availableSpace = drawableElement.getHeight() - drawableElement.getPaddingTop() - drawableElement.getPaddingBottom();
			if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V) != 0) {
				availableSpace = totalRequiredSpace;
			} else if ((fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V) != 0) {
				if (availableSpace < totalRequiredSpace) {
					availableSpace = totalRequiredSpace;
				}
			}
		} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
			availableSpace = drawableElement.getWidth() - drawableElement.getPaddingLeft() - drawableElement.getPaddingRight();
			if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_H) != 0) {
				availableSpace = totalRequiredSpace;
			} else if ((fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_H) != 0) {
				if (availableSpace < totalRequiredSpace) {
					availableSpace = totalRequiredSpace;
				}
			}
		}
		
		if (availableSpace != totalRequiredSpace) {
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
	private int getChildSpace(DrawableElement child) {
		switch(layoutType) {
			case LAYOUT_TYPE_VERTICAL:
				return child.getHeight() + child.getMarginTop() + child.getMarginBottom();
			case LAYOUT_TYPE_HORIZONTAL:
				return child.getWidth() + child.getMarginLeft() + child.getMarginRight();
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
		if (!mustIgnore(child)) {
			applyFix(drawableElement);
		}
	}

	public void onChildAdded(DrawableElement drawableElement, DrawableElement child) {
		if (!mustIgnore(child)) {
			applyFix(drawableElement);
		}
	}

	public void onChildRemoved(DrawableElement drawableElement, DrawableElement child) {
		boolean mustIgnore = mustIgnore(child);
		for (int i=0; i<layoutFlags.size(); i++) {
			if (((ChildLayoutFlags)layoutFlags.elementAt(i)).drawableElement == child) {
				layoutFlags.removeElementAt(i);
				break;
			}
		}
		if (!mustIgnore) {
			applyFix(drawableElement);
		}
	}

	public byte getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(byte layoutType) {
		this.layoutType = layoutType;
	}
	
	public void setLayoutFlags(DrawableElement child, int flags) {
		for (int i=0; i<layoutFlags.size(); i++) {
			if (((ChildLayoutFlags)layoutFlags.elementAt(i)).drawableElement == child) {
				layoutFlags.removeElementAt(i);
				break;
			}
		}
		ChildLayoutFlags childLayoutFlags = new ChildLayoutFlags();
		childLayoutFlags.drawableElement = child;
		childLayoutFlags.layoutFlags = flags;
		layoutFlags.addElement(childLayoutFlags);
	}
	
	public int getLayoutFlags(DrawableElement child) {
		int childFlags = 0;
		for (int i=0; i<layoutFlags.size(); i++) {
			if (((ChildLayoutFlags)layoutFlags.elementAt(i)).drawableElement == child) {
				childFlags = ((ChildLayoutFlags)layoutFlags.elementAt(i)).layoutFlags;
				break;
			}
		}
		return childFlags;
	}
	
	public boolean hasLayoutFlags(DrawableElement child, int flags) {
		int elementFlags = getLayoutFlags(child);
		return ((elementFlags & flags) != 0);
	}

	/**
	 * @deprecated use getFitPolicy() == FIT_POLICY_ALWAYS_FIT_TO_CHILDREN.
	 */
	public boolean mustFitToChildren() {
		return fitPolicy == FIT_POLICY_ALWAYS_FIT_TO_CHILDREN;
	}

	/**
	 * @deprecated use setFitPolicy(FIT_POLICY_ALWAYS_FIT_TO_CHILDREN).
	 */
	public void setFitToChildren(boolean fitToChildren) {
		if (fitToChildren) {
			this.fitPolicy = FIT_POLICY_ALWAYS_FIT_TO_CHILDREN;
		} else {
			this.fitPolicy = FIT_POLICY_DONT_FIT_TO_CHILDREN;
		}
	}

	public void setFitPolicy(int fitPolicy) {
		this.fitPolicy = fitPolicy;
	}

	public int getFitPolicy() {
		return fitPolicy;
	}
}
