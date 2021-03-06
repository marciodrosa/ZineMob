package com.zine.zinemob.gui;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Base class for fields (buttons and other widgets).
 */
public abstract class Field extends Component {
	
	Component parentComponent;

	public void onFocus(boolean focus) {
	}

	public Component getParentComponent() {
		return parentComponent;
	}

	public Component getFocusedComponent() {
		return this;
	}
	
	public void setFocusToComponent(Component component) {
	}
	
	public abstract DrawableElement getDrawableElement();
	
}
