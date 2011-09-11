package com.zine.zinemob.gui;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.LinearLayoutElement;
import com.zine.zinemob.drawableelement.layoutfixer.FitToChildrenLayoutFixer;
import com.zine.zinemob.drawableelement.layoutfixer.LinearLayoutFixer;
import com.zine.zinemob.drawableelement.layoutfixer.StretchToParentLayoutFixer;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;

/**
 * Container is a component that contains many fields or other components.
 */
public class Container extends Component {
	
	private DrawableElement background = new DrawableElement();
	private LinearLayoutElement linearLayoutElement = new LinearLayoutElement();
	
	private Vector children = new Vector(); // <Component>
	private int focusIndex = -1;
	Component parentComponent;
	
	public Container() {
		background.addChild(linearLayoutElement);
		linearLayoutElement.addLayoutFixer(new StretchToParentLayoutFixer());
		linearLayoutElement.setFitToChildren(true);
	}
	
	public Container(byte layoutType) {
		this();
		linearLayoutElement.setLayoutType(layoutType);
	}
	
	public Container(byte layoutType, boolean fitToChildren) {
		this();
		linearLayoutElement.setLayoutType(layoutType);
		linearLayoutElement.setFitToChildren(fitToChildren);
	}
	
	/**
	 * Sets the background.
	 */
	public void setBackground(DrawableElement background) {
		this.background = background;
		background.addLayoutFixer(new FitToChildrenLayoutFixer());
		background.addChild(linearLayoutElement);
	}
	
	public void add(DrawableElement drawableElement) {
		add(drawableElement, 0);
	}
	
	public void add(DrawableElement drawableElement, int layoutFlags) {
		linearLayoutElement.addChild(drawableElement, layoutFlags);
	}
	
	public void add(Field field) {
		add(field, 0);
	}
	
	public void add(Field field, int layoutFlags) {
		field.parentComponent = this;
		addComponent(field, layoutFlags);
	}
	
	public void add(Container container) {
		add(container, 0);
	}
	
	public void add(Container container, int layoutFlags) {
		container.parentComponent = this;
		addComponent(container, layoutFlags);
	}
	
	private void addComponent(Component component, int layoutFlags) {
		add(component.getDrawableElement(), layoutFlags);
		children.addElement(component);
		if (children.size() == 1) {
			setFocusTo(0);
		}
	}

	public DrawableElement getDrawableElement() {
		return background;
	}

	public void onFocus(boolean focus) {
		if (focus) {
			setFocusTo(0);
		} else {
			removeFocusFromCurrentFocused();
		}
	}

	public Component getParentComponent() {
		return parentComponent;
	}

	public void onKeyPressed(int keyCode, int gameAction) {
		if (!onChangeFocusWithKeyboard(gameAction)) {
			super.onKeyPressed(keyCode, gameAction);
		}
	}
	
	public void onKeyRepeated(int keyCode, int gameAction) {
		if (!onChangeFocusWithKeyboard(gameAction)) {
			super.onKeyRepeated(keyCode, gameAction);
		}
	}
	
	private boolean onChangeFocusWithKeyboard(int gameAction) {
		
		boolean handled = false;
		
		if (linearLayoutElement.getLayoutType() == LinearLayoutFixer.LAYOUT_TYPE_HORIZONTAL) {
			
			if (gameAction == Canvas.RIGHT) {
				focusNext();
				handled = true;
			} else if (gameAction == Canvas.LEFT) {
				focusPrevious();
				handled = true;
			}
			
		} else if (linearLayoutElement.getLayoutType() == LinearLayoutFixer.LAYOUT_TYPE_VERTICAL) {
			
			if (gameAction == Canvas.DOWN) {
				focusNext();
				handled = true;
			} else if (gameAction == Canvas.UP) {
				focusPrevious();
				handled = true;
			}
		}
		
		return handled;
	}
	
	private void focusNext() {
		
		if (children.size() > 1) {
			setFocusTo(focusIndex + 1);
		}
	}
	
	private void focusPrevious() {
		
		if (children.size() > 1) {
			setFocusTo(focusIndex - 1);
		}
	}
	
	private void setFocusTo(int index) {
		
		removeFocusFromCurrentFocused();
		
		if (index < 0) {
			index = children.size() - 1;
		} else if (index >= children.size()) {
			index = 0;
		}
		
		try {
			Component focusedComponent = (Component) children.elementAt(index);
			
			this.focusIndex = index;
			
			focusedComponent.onFocus(true);
			
		} catch (ArrayIndexOutOfBoundsException ex) {
			
		}
	}
	
	private void removeFocusFromCurrentFocused() {
		try {
			Component focusedComponent = (Component) children.elementAt(this.focusIndex);
			
			this.focusIndex = -1;
			
			focusedComponent.onFocus(false);
			
		} catch (ArrayIndexOutOfBoundsException ex) {
			
		}
	}

	public Component getFocusedComponent() {
		try {
			return ((Component) children.elementAt(this.focusIndex)).getFocusedComponent();
		} catch (ArrayIndexOutOfBoundsException ex) {
			return this;
		}
	}
	
}
