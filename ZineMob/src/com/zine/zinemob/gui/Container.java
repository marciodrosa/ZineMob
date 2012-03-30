package com.zine.zinemob.gui;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.LinearLayoutElement;
import com.zine.zinemob.drawableelement.layout.LinearLayout;
import com.zine.zinemob.drawableelement.layout.LinearLayoutHandler;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;

/**
 * Container is a component that contains many fields or other components.
 */
public class Container extends Component implements LinearLayoutHandler {
	
	private LinearLayoutElement linearLayoutElement = new LinearLayoutElement();
	
	private Vector children = new Vector(); // <Component>
	private int focusIndex = -1;
	private boolean hasFocus = false;
	private Component parentComponent;
	
	public Container() {
		linearLayoutElement.setFitPolicy(LinearLayoutElement.FIT_POLICY_ALWAYS_FIT_TO_CHILDREN);
	}
	
	public Container(byte layoutType) {
		this();
		linearLayoutElement.setLayoutType(layoutType);
	}
	
	/**
	 * Returns the LinearLayoutElement object of this container. This is the same
	 * object returned by getDrawableElement.
	 */
	public LinearLayoutElement getLinearLayoutElement() {
		return linearLayoutElement;
	}
	
	public void add(DrawableElement drawableElement) {
		add(drawableElement, 0);
	}
	
	public void add(DrawableElement drawableElement, int layoutFlags) {
		linearLayoutElement.addChildAndLayout(drawableElement, layoutFlags);
	}
	
	public void add(Field field) {
		field.parentComponent = this;
		addComponent(field);
	}
	
	public void add(Field field, int layoutFlags) {
		field.setLayout(layoutFlags);
		add(field);
	}
	
	public void add(Container container) {
		container.parentComponent = this;
		addComponent(container);
	}
	
	public void add(Container container, int layoutFlags) {
		container.setLayout(layoutFlags);
		add(container);
	}
	
	private void addComponent(Component component) {
		add(component.getDrawableElement(), component.getLayout());
		component.setGuiSceneController(getGuiSceneController());
		children.addElement(component);
		if (focusIndex == -1 && hasFocus && component.isFocusable()) {
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
		return linearLayoutElement;
	}

	public void onFocus(boolean focus) {
		hasFocus = focus;
		if (focus) {
			int firstFocusIndex = getFirstFocusIndex();
			if (firstFocusIndex >= 0) {
				setFocusTo(firstFocusIndex);
			}
		} else {
			removeFocusFromCurrentFocused();
		}
	}
	
	private int getFirstFocusIndex() {
		for (int i=0; i<children.size(); i++) {
			if (((Component)children.elementAt(i)).isFocusable()) {
				return i;
			}
		}
		return -1;
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
		
		if (linearLayoutElement.getLayoutType() == LinearLayout.LAYOUT_TYPE_HORIZONTAL) {
			
			if (gameAction == Canvas.RIGHT) {
				focusNext();
				handled = true;
			} else if (gameAction == Canvas.LEFT) {
				focusPrevious();
				handled = true;
			}
			
		} else if (linearLayoutElement.getLayoutType() == LinearLayout.LAYOUT_TYPE_VERTICAL) {
			
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
		int nextIndex = getNextFocusableIndex(focusIndex);
		if (nextIndex != -1) {
			setFocusTo(nextIndex);
		}
	}
	
	private void focusPrevious() {
		int nextIndex = getPreviousFocusableIndex(focusIndex);
		if (nextIndex != -1) {
			setFocusTo(nextIndex);
		}
	}
	
	private int getNextFocusableIndex(int index) {
		for (int i=index+1; i<children.size(); i++) {
			if (((Component)children.elementAt(i)).isFocusable()) {
				return i;
			}
		}
		for (int i=0; i<index; i++) {
			if (((Component)children.elementAt(i)).isFocusable()) {
				return i;
			}
		}
		return -1;
	}
	
	private int getPreviousFocusableIndex(int index) {
		for (int i=index-1; i>=0; i--) {
			if (((Component)children.elementAt(i)).isFocusable()) {
				return i;
			}
		}
		for (int i=children.size()-1; i>index; i--) {
			if (((Component)children.elementAt(i)).isFocusable()) {
				return i;
			}
		}
		return -1;
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
			focusedComponent.onFocus(false);
		} catch (ArrayIndexOutOfBoundsException ex) {
		}
		this.focusIndex = -1;
	}

	public Component getFocusedComponent() {
		try {
			return ((Component) children.elementAt(this.focusIndex)).getFocusedComponent();
		} catch (ArrayIndexOutOfBoundsException ex) {
			return this;
		}
	}
	
	public void setFocusToComponent(Component component) {
		setFocusTo(children.indexOf(component));
	}
	
	/**
	 * Returns the component at x and y point. It can returns this, a child, any
	 * child of some child or null.
	 */
	public Component getComponentAt(int x, int y) {
		Component componentAt = null;
		for (int i=children.size()-1; i>=0; i--) {
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

	public byte getLayoutType() {
		return linearLayoutElement.getLayoutType();
	}

	public void setLayoutType(byte layoutType) {
		linearLayoutElement.setLayoutType(layoutType);
	}

	public void setLayoutFlags(DrawableElement child, int flags) {
		linearLayoutElement.setLayoutFlags(child, flags);
	}

	public int getLayoutFlags(DrawableElement child) {
		return linearLayoutElement.getLayoutFlags(child);
	}

	public boolean hasLayoutFlags(DrawableElement child, int flags) {
		return linearLayoutElement.hasLayoutFlags(child, flags);
	}

	public void setFitPolicy(int fitPolicy) {
		linearLayoutElement.setFitPolicy(fitPolicy);
	}

	public int getFitPolicy() {
		return linearLayoutElement.getFitPolicy();
	}
	
	/**
	 * Returns the background of the linear layout.
	 */
	public DrawableElement getBackground() {
		return linearLayoutElement.getBackground();
	}

	/**
	 * Sets the background of the linear layout.
	 */
	public void setBackground(DrawableElement background) {
		linearLayoutElement.setBackground(background);
	}
}
