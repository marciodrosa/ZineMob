package com.zine.zinemob.gui;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.LinearLayoutElement;
import com.zine.zinemob.drawableelement.layoutfixer.LinearLayoutFixer;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;

/**
 * Container is a component that contains many fields or other components.
 */
public class Container extends Component {
	
	private LinearLayoutElement componentsLinearLayoutElement = new LinearLayoutElement();
	
	private Vector children = new Vector(); // <Component>
	private int focusIndex = -1;
	private boolean hasFocus = false;
	Component parentComponent;
	
	public Container() {
		componentsLinearLayoutElement.setFitToChildren(true);
	}
	
	public Container(byte layoutType) {
		this();
		componentsLinearLayoutElement.setLayoutType(layoutType);
	}
	
	public Container(byte layoutType, boolean fitToChildren) {
		this();
		componentsLinearLayoutElement.setLayoutType(layoutType);
		componentsLinearLayoutElement.setFitToChildren(fitToChildren);
	}
	
	public void add(DrawableElement drawableElement) {
		add(drawableElement, 0);
	}
	
	public void add(DrawableElement drawableElement, int layoutFlags) {
		componentsLinearLayoutElement.addChildAndLayout(drawableElement, layoutFlags);
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
		component.setGuiSceneController(getGuiSceneController());
		children.addElement(component);
		if (children.size() == 1 && hasFocus) {
			setFocusTo(0);
		}
	}

	public void setGuiSceneController(GuiSceneController guiSceneController) {
		super.setGuiSceneController(guiSceneController);
		for (int i=0; i<children.size(); i++) {
			((Component)children.elementAt(i)).setGuiSceneController(guiSceneController);
		}
	}

	public DrawableElement getDrawableElement() {
		return componentsLinearLayoutElement;
	}

	public void onFocus(boolean focus) {
		hasFocus = focus;
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
		
		if (componentsLinearLayoutElement.getLayoutType() == LinearLayoutFixer.LAYOUT_TYPE_HORIZONTAL) {
			
			if (gameAction == Canvas.RIGHT) {
				focusNext();
				handled = true;
			} else if (gameAction == Canvas.LEFT) {
				focusPrevious();
				handled = true;
			}
			
		} else if (componentsLinearLayoutElement.getLayoutType() == LinearLayoutFixer.LAYOUT_TYPE_VERTICAL) {
			
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
	
	public void setFocusedChildComponent(Component component) {
		setFocusTo(children.indexOf(component));
	}
	
	/**
	 * Returns the component at x and y point. It can returns this, a child, any
	 * child of some child or null.
	 */
	public Component getComponentAt(int x, int y) {
		Component componentAt = null;
		for (int i=0; i<children.size(); i++) {
			Component c = (Component) children.elementAt(i);
			if (c instanceof Container) {
				componentAt = ((Container)c).getComponentAt(x, y);
				if (componentAt != null) {
					break;
				}
			} else {
				if (c.getDrawableElement().collidesWith(x, y, false, false)) {
					componentAt = c;
					break;
				}
			}
		}
		
		if (componentAt == null && this.getDrawableElement().collidesWith(x, y, false, false)) {
			componentAt = this;
		}
		
		return componentAt;
	}
	
}
