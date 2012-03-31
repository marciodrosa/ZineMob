package com.zine.zinemob.drawableelement;

import com.zine.zinemob.drawableelement.layout.LinearLayout;
import com.zine.zinemob.drawableelement.layout.LinearLayoutInterface;
import com.zine.zinemob.drawableelement.layout.LinearLayoutParams;
import javax.microedition.lcdui.Graphics;

/**
 * A DrawableElement with a LinearLayoutFixer and some convenience methods.
 */
public class LinearLayoutElement extends DrawableElement implements LinearLayoutInterface {
	
	private LinearLayout linearLayout;
	private DrawableElement background;
	
	public LinearLayoutElement() {
		linearLayout = new LinearLayout(this);
		addLayout(linearLayout);
	}
	
	public LinearLayoutElement(byte layoutType) {
		this();
		linearLayout.setLayoutType(layoutType);
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
		return linearLayout.getLayoutType();
	}
	
	public void setLayoutType(byte layoutType) {
		linearLayout.setLayoutType(layoutType);
	}
	
	/**
	 * @deprecated use getFitPolicy() == FIT_POLICY_ALWAYS_FIT_TO_CHILDREN.
	 */
	public boolean mustFitToChildren() {
		return linearLayout.mustFitToChildren();
	}
	
	/**
	 * @deprecated use setFitPolicy(FIT_POLICY_ALWAYS_FIT_TO_CHILDREN).
	 */
	public void setFitToChildren(boolean fitToChildren) {
		linearLayout.setFitToChildren(fitToChildren);
	}
	
	public void addChildAndLayout(DrawableElement child, int layoutFlags) {
		linearLayout.setLayoutFlags(child, layoutFlags);
		super.addChild(child);
	}
	
	public void addChildAndLayout(DrawableElement child, int index, int layoutFlags) {
		linearLayout.setLayoutFlags(child, layoutFlags);
		super.addChild(child, index);
	}
	
	public void setLayoutFlags(DrawableElement child, int layoutFlags) {
		linearLayout.setLayoutFlags(child, layoutFlags);
	}

	public int getLayoutFlags(DrawableElement child) {
		return linearLayout.getLayoutFlags(child);
	}

	public void setFitPolicy(int fitPolicy) {
		linearLayout.setFitPolicy(fitPolicy);
	}

	public int getFitPolicy() {
		return linearLayout.getFitPolicy();
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

	public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
		linearLayout.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
	}

	public void setPadding(int padding) {
		linearLayout.setPadding(padding);
	}

	public int getPaddingLeft() {
		return linearLayout.getPaddingLeft();
	}

	public int getPaddingTop() {
		return linearLayout.getPaddingTop();
	}

	public int getPaddingRight() {
		return linearLayout.getPaddingRight();
	}

	public int getPaddingBottom() {
		return linearLayout.getPaddingBottom();
	}

	public void setParams(DrawableElement child, LinearLayoutParams params) {
		linearLayout.setParams(child, params);
	}

	public LinearLayoutParams getParams(DrawableElement child) {
		return linearLayout.getParams(child);
	}

	public void setMargin(DrawableElement child, int margin) {
		linearLayout.setMargin(child, margin);
	}

	public void setMargin(DrawableElement child, int marginLeft, int marginTop, int marginRight, int marginBottom) {
		linearLayout.setMargin(child, marginLeft, marginTop, marginRight, marginBottom);
	}

	public int getMarginLeft(DrawableElement child) {
		return linearLayout.getMarginLeft(child);
	}

	public int getMarginTop(DrawableElement child) {
		return linearLayout.getMarginTop(child);
	}

	public int getMarginRight(DrawableElement child) {
		return linearLayout.getMarginRight(child);
	}

	public int getMarginBottom(DrawableElement child) {
		return linearLayout.getMarginBottom(child);
	}
}
