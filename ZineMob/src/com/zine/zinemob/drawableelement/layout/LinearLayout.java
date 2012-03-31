package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Layout that organizes the children of the element side-by-side (if it is horizontal)
 * or each child below the other (if it is vertical). The layout is vertical by default.
 */
public class LinearLayout implements Layout, LinearLayoutInterface {
	
	private Vector childrenLayoutParams = new Vector(); // <LinearLayoutParams>
	private byte layoutType = LAYOUT_TYPE_VERTICAL;
	private int fitPolicy = FIT_POLICY_DONT_FIT_TO_CHILDREN;
	private Hashtable onGoingElementsSet = new Hashtable();
	private Hashtable pendingElementsSet = new Hashtable();
	private DrawableElement drawableElement;
	private int paddingLeft, paddingTop, paddingRight, paddingBottom;
	
	public LinearLayout(DrawableElement drawableElement) {
		this.drawableElement = drawableElement;
	}
	
	public synchronized void apply() {
		if (onGoingElementsSet.containsKey(drawableElement)) {
			pendingElementsSet.put(drawableElement, drawableElement);
		} else {
			onGoingElementsSet.put(drawableElement, drawableElement);
			applyLinearLayout();
			if (pendingElementsSet.containsKey(drawableElement)) {
				applyLinearLayout();
			}
			pendingElementsSet.remove(drawableElement);
			onGoingElementsSet.remove(drawableElement);
		}
	}
	
	private void applyLinearLayout() {
		int childrenRequiredPeripheralSpace = getChildrenRequiredPeripheralSpace(drawableElement);
		int[] requiredSpaces = getChildrenRequiredSpaces(drawableElement);
		
		int currentLayoutPosition = 0;
		
		if (getLayoutType() == LAYOUT_TYPE_VERTICAL) {
			currentLayoutPosition = paddingTop;
		} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
			currentLayoutPosition = paddingLeft;
		}

		for (int i=0; i<drawableElement.getChildrenCount(); i++) {
			
			DrawableElement child = drawableElement.getChild(i);
			
			if (mustIgnore(child)) {
				continue;
			}

			int x = getMarginLeft(child);
			int y = getMarginTop(child);
			int w = child.getWidth();
			int h = child.getHeight();
			
			// horizontal setup:
			if (hasLayoutFlags(child, ALIGN_RIGHT)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					x = childrenRequiredPeripheralSpace - child.getWidth() - getMarginRight(child);
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					x = requiredSpaces[i] - child.getWidth() - getMarginRight(child);
				}
			} else if (hasLayoutFlags(child, ALIGN_CENTER_H)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					x = (childrenRequiredPeripheralSpace/2) - (child.getWidth()/2);
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					x = (requiredSpaces[i]/2) - (child.getWidth()/2);
				}
			} else if (hasLayoutFlags(child, FIT_H)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					w = childrenRequiredPeripheralSpace - getMarginLeft(child) - getMarginRight(child);
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					w = requiredSpaces[i] - getMarginLeft(child) - getMarginRight(child);
				}
			} else if (hasLayoutFlags(child, STRETCH_H)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					int availableW = childrenRequiredPeripheralSpace - getMarginLeft(child) - getMarginRight(child);
					w = availableW > w ? availableW : w;
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					int availableW = requiredSpaces[i] - getMarginLeft(child) - getMarginRight(child);
					w = availableW > w ? availableW : w;
				}
			}

			// vertical setup:
			if (hasLayoutFlags(child, ALIGN_BOTTOM)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					y = requiredSpaces[i] - child.getHeight() - getMarginBottom(child);
				} else if (getLayoutType() == LAYOUT_TYPE_HORIZONTAL) {
					y = childrenRequiredPeripheralSpace - child.getHeight() - getMarginBottom(child);
				}
			} else if (hasLayoutFlags(child, ALIGN_CENTER_V)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					y = ( (requiredSpaces[i] - getMarginTop(child) - getMarginBottom(child))/2) - (child.getHeight()/2) + getMarginTop(child);
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					y = (childrenRequiredPeripheralSpace/2) - (child.getHeight()/2);
				}
			} else if (hasLayoutFlags(child, FIT_V)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					h = requiredSpaces[i] - getMarginTop(child) - getMarginBottom(child);
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					h = childrenRequiredPeripheralSpace - getMarginTop(child) - getMarginBottom(child);
				}
			} else if (hasLayoutFlags(child, STRETCH_V)) {
				if (layoutType == LAYOUT_TYPE_VERTICAL) {
					int availableH = requiredSpaces[i] - getMarginTop(child) - getMarginBottom(child);
					y = availableH > y ? availableH : y;
				} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
					int availableH = childrenRequiredPeripheralSpace - getMarginTop(child) - getMarginBottom(child);
					y = availableH > y ? availableH : y;
				}
			}

			if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
				x += currentLayoutPosition;
				y += paddingTop;
			} if (layoutType == LAYOUT_TYPE_VERTICAL) {
				x += paddingLeft;
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
			requiredWidth = currentLayoutPosition + paddingRight;
			requiredHeight = childrenRequiredPeripheralSpace + paddingTop + paddingBottom;
		} if (layoutType == LAYOUT_TYPE_VERTICAL) {
			requiredWidth = childrenRequiredPeripheralSpace + paddingLeft + paddingRight;
			requiredHeight = currentLayoutPosition + paddingBottom;
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
						int childPeripheralSpace = child.getWidth() + getMarginLeft(child) + getMarginRight(child);
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
						int childPeripheralSpace = child.getHeight() + getMarginTop(child) + getMarginBottom(child);
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
			availablePeripheralSpace = drawableElement.getWidth() - paddingLeft - paddingRight;
		} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
			availablePeripheralSpace = drawableElement.getHeight() - paddingTop - paddingBottom;
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
				if (hasLayoutFlags(child, FIT_AVAILABLE_SPACE)) {
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
			availableSpace = drawableElement.getHeight() - paddingTop - paddingBottom;
			if ((fitPolicy & FIT_POLICY_ALWAYS_FIT_TO_CHILDREN_V) != 0) {
				availableSpace = totalRequiredSpace;
			} else if ((fitPolicy & FIT_POLICY_FIT_TO_CHILDREN_WHEN_SPACE_IS_SMALLER_V) != 0) {
				if (availableSpace < totalRequiredSpace) {
					availableSpace = totalRequiredSpace;
				}
			}
		} else if (layoutType == LAYOUT_TYPE_HORIZONTAL) {
			availableSpace = drawableElement.getWidth() - paddingLeft - paddingRight;
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
				return child.getHeight() + getMarginTop(child) + getMarginBottom(child);
			case LAYOUT_TYPE_HORIZONTAL:
				return child.getWidth() + getMarginLeft(child) + getMarginRight(child);
			default:
				return 0;
		}
	}
	
	public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
		this.paddingLeft = paddingLeft;
		this.paddingTop = paddingTop;
		this.paddingRight = paddingRight;
		this.paddingBottom = paddingBottom;
	}

	public void setPadding(int padding) {
		this.paddingLeft = padding;
		this.paddingTop = padding;
		this.paddingRight = padding;
		this.paddingBottom = padding;
	}

	public int getPaddingLeft() {
		return paddingLeft;
	}

	public int getPaddingTop() {
		return paddingTop;
	}

	public int getPaddingRight() {
		return paddingRight;
	}

	public int getPaddingBottom() {
		return paddingBottom;
	}

	public void onPositionChanged() {
	}

	public void onSizeChanged() {
		apply();
	}

	public void onParentChanged() {
	}

	public void onParentPositionChanged() {
	}

	public void onParentSizeChanged() {
	}

	public void onChildPositionChanged(DrawableElement child) {
	}

	public void onChildSizeChanged(DrawableElement child) {
		if (!mustIgnore(child)) {
			apply();
		}
	}

	public void onChildAdded(DrawableElement child) {
		if (!mustIgnore(child)) {
			apply();
		}
	}

	public void onChildRemoved(DrawableElement child) {
		boolean mustIgnore = mustIgnore(child);
		setParams(child, null);
		if (!mustIgnore) {
			apply();
		}
	}

	public byte getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(byte layoutType) {
		this.layoutType = layoutType;
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

	public void setParams(DrawableElement child, LinearLayoutParams params) {
		LinearLayoutParams currentParams = getParams(child);
		if (currentParams != null) {
			childrenLayoutParams.removeElement(currentParams);
		}
		if (params != null) {
			params.drawableElement = child;
			childrenLayoutParams.addElement(params);
		}
	}

	public LinearLayoutParams getParams(DrawableElement child) {
		for (int i=0; i<childrenLayoutParams.size(); i++) {
			if (((LinearLayoutParams) childrenLayoutParams.elementAt(i)).drawableElement == child) {
				return (LinearLayoutParams) childrenLayoutParams.elementAt(i);
			}
		}
		return null;
	}
	
	private LinearLayoutParams getOrCreateParams(DrawableElement child) {
		LinearLayoutParams params = getParams(child);
		if (params == null) {
			params = new LinearLayoutParams();
			setParams(child, params);
		}
		return params;
	}
	
	public void setLayoutFlags(DrawableElement child, int flags) {
		LinearLayoutParams linearLayoutParams = getOrCreateParams(child);
		linearLayoutParams.setLayoutFlags(flags);
	}
	
	public int getLayoutFlags(DrawableElement child) {
		LinearLayoutParams linearLayoutParams = getParams(child);
		if (linearLayoutParams == null) {
			return 0;
		} else {
			return linearLayoutParams.getLayoutFlags();
		}
	}

	public void setMargin(DrawableElement child, int margin) {
		LinearLayoutParams linearLayoutParams = getOrCreateParams(child);
		linearLayoutParams.setMargin(margin);
	}

	public void setMargin(DrawableElement child, int marginLeft, int marginTop, int marginRight, int marginBottom) {
		LinearLayoutParams linearLayoutParams = getOrCreateParams(child);
		linearLayoutParams.setMargin(marginLeft, marginTop, marginRight, marginBottom);
	}
	
	public int getMarginLeft(DrawableElement child) {
		LinearLayoutParams linearLayoutParams = getParams(child);
		if (linearLayoutParams != null) {
			return linearLayoutParams.getMarginLeft();
		} else {
			return 0;
		}
	}
	
	public int getMarginRight(DrawableElement child) {
		LinearLayoutParams linearLayoutParams = getParams(child);
		if (linearLayoutParams != null) {
			return linearLayoutParams.getMarginRight();
		} else {
			return 0;
		}
	}
	
	public int getMarginTop(DrawableElement child) {
		LinearLayoutParams linearLayoutParams = getParams(child);
		if (linearLayoutParams != null) {
			return linearLayoutParams.getMarginTop();
		} else {
			return 0;
		}
	}
	
	public int getMarginBottom(DrawableElement child) {
		LinearLayoutParams linearLayoutParams = getParams(child);
		if (linearLayoutParams != null) {
			return linearLayoutParams.getMarginBottom();
		} else {
			return 0;
		}
	}
}
