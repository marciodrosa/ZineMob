package com.zine.zinemob.drawableelement;

import com.zine.zinemob.drawableelement.layoutfixer.LinearLayoutFixer;

/**
 * A DrawableElement with a LinearLayoutFixer and some convenience methods.
 */
public class LinearLayoutElement extends DrawableElement {
	
	private LinearLayoutFixer linearLayoutFixer;
	
	public LinearLayoutElement() {
		linearLayoutFixer = new LinearLayoutFixer();
		addLayoutFixer(linearLayoutFixer);
	}
	
	public LinearLayoutElement(byte layoutType) {
		this();
		linearLayoutFixer.setLayoutType(layoutType);
	}
	
	public byte getLayoutType() {
		return linearLayoutFixer.getLayoutType();
	}
	
	public void setLayoutType(byte layoutType) {
		linearLayoutFixer.setLayoutType(layoutType);
	}
	
	public boolean mustFitToChildren() {
		return linearLayoutFixer.mustFitToChildren();
	}
	
	public void setFitToChildren(boolean fitToChildren) {
		linearLayoutFixer.setFitToChildren(fitToChildren);
	}
	
	public void addChildAndLayout(DrawableElement child, int layoutFlags) {
		super.addChild(child);
		linearLayoutFixer.setLayoutFlags(child, layoutFlags);
	}
	
	public void addChildAndLayout(DrawableElement child, int index, int layoutFlags) {
		super.addChild(child, index);
		linearLayoutFixer.setLayoutFlags(child, layoutFlags);
	}
}
