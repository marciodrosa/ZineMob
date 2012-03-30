package com.zine.zinemob.drawableelement;

import com.zine.zinemob.drawableelement.layout.LinearLayout;
import com.zine.zinemob.drawableelement.layout.LinearLayoutHandler;
import javax.microedition.lcdui.Graphics;

/**
 * A DrawableElement with a LinearLayoutFixer and some convenience methods.
 */
public class LinearLayoutElement extends DrawableElement implements LinearLayoutHandler {
	
	private LinearLayout linearLayoutFixer;
	private DrawableElement background;
	
	public LinearLayoutElement() {
		linearLayoutFixer = new LinearLayout();
		addLayout(linearLayoutFixer);
	}
	
	public LinearLayoutElement(byte layoutType) {
		this();
		linearLayoutFixer.setLayoutType(layoutType);
	}

	public void setSize(int w, int h) {
		super.setSize(w, h);
		updateBackground();
	}

	protected void drawElement(Graphics graphics) {
		if (background != null) {
			background.draw(graphics);
		}
		super.drawElement(graphics);
		
//		graphics.setColor(0xff0000);
//		graphics.drawRect(0, 0, getWidth(), getHeight());
	}
	
	private void updateBackground() {
		if (background != null) {
			background.setSize(getWidth(), getHeight());
		}
	}
	
	public byte getLayoutType() {
		return linearLayoutFixer.getLayoutType();
	}
	
	public void setLayoutType(byte layoutType) {
		linearLayoutFixer.setLayoutType(layoutType);
	}
	
	/**
	 * @deprecated use getFitPolicy() == FIT_POLICY_ALWAYS_FIT_TO_CHILDREN.
	 */
	public boolean mustFitToChildren() {
		return linearLayoutFixer.mustFitToChildren();
	}
	
	/**
	 * @deprecated use setFitPolicy(FIT_POLICY_ALWAYS_FIT_TO_CHILDREN).
	 */
	public void setFitToChildren(boolean fitToChildren) {
		linearLayoutFixer.setFitToChildren(fitToChildren);
	}
	
	public void addChildAndLayout(DrawableElement child, int layoutFlags) {
		linearLayoutFixer.setLayoutFlags(child, layoutFlags);
		super.addChild(child);
	}
	
	public void addChildAndLayout(DrawableElement child, int index, int layoutFlags) {
		linearLayoutFixer.setLayoutFlags(child, layoutFlags);
		super.addChild(child, index);
	}
	
	public void setLayoutFlags(DrawableElement child, int layoutFlags) {
		linearLayoutFixer.setLayoutFlags(child, layoutFlags);
	}

	public int getLayoutFlags(DrawableElement child) {
		return linearLayoutFixer.getLayoutFlags(child);
	}

	public boolean hasLayoutFlags(DrawableElement child, int flags) {
		return linearLayoutFixer.hasLayoutFlags(child, flags);
	}

	public void setFitPolicy(int fitPolicy) {
		linearLayoutFixer.setFitPolicy(fitPolicy);
	}

	public int getFitPolicy() {
		return linearLayoutFixer.getFitPolicy();
	}

	/**
	 * Returns the background.
	 */
	public DrawableElement getBackground() {
		return background;
	}

	/**
	 * Sets the background. The size of this element is always updated to fit to
	 * the size of the container. Can be null.
	 */
	public void setBackground(DrawableElement background) {
		this.background = background;
		updateBackground();
	}
}
