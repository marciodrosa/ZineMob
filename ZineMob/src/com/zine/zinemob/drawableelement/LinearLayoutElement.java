package com.zine.zinemob.drawableelement;

import com.zine.zinemob.drawableelement.layoutfixer.LinearLayoutFixer;
import com.zine.zinemob.drawableelement.layoutfixer.LinearLayoutHandler;

/**
 * A DrawableElement with a LinearLayoutFixer and some convenience methods.
 */
public class LinearLayoutElement extends DrawableElement implements LinearLayoutHandler {
	
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

	public void setFitPolicy(byte fitPolicy) {
		linearLayoutFixer.setFitPolicy(fitPolicy);
	}

	public byte getFitPolicy() {
		return linearLayoutFixer.getFitPolicy();
	}
}
