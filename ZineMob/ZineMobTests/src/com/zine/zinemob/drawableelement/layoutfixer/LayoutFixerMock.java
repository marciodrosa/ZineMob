package com.zine.zinemob.drawableelement.layoutfixer;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * This mock contains a log of method calls.
 */
public class LayoutFixerMock implements LayoutFixer {

	private StringBuffer log = new StringBuffer();

	/**
	 * Returns a log with the calls of the methods of this class. The calls are
	 * formatted like this: "methodname(drawableelementname);othermethod(drawableelementname,otherDrawableElementName);etc;".
	 */	
	public String getCallLog() {
		return log.toString();
	}

	private void appendMethodToLog(String methodName, DrawableElement arg) {
		appendMethodToLog(methodName, new DrawableElement[]{arg});
	}

	private void appendMethodToLog(String methodName, DrawableElement arg1, DrawableElement arg2) {
		appendMethodToLog(methodName, new DrawableElement[]{arg1, arg2});
	}

	private void appendMethodToLog(String methodName, DrawableElement[] args) {
		log.append(methodName);
		log.append('(');
		for (int i=0; i<args.length; i++) {
			log.append(args[i].getName());
			if (i != args.length - 1) {
				log.append(',');
			}
		}
		log.append(");");
	}

	public void applyFix(DrawableElement drawableElement) {
		appendMethodToLog("applyFix", drawableElement);
	}

	public void onPositionChanged(DrawableElement drawableElement) {
		appendMethodToLog("onPositionChanged", drawableElement);
	}

	public void onSizeChanged(DrawableElement drawableElement) {
		appendMethodToLog("onSizeChanged", drawableElement);
	}

	public void onParentPositionChanged(DrawableElement drawableElement) {
		appendMethodToLog("onParentPositionChanged", drawableElement);
	}

	public void onParentSizeChanged(DrawableElement drawableElement) {
		appendMethodToLog("onParentSizeChanged", drawableElement);
	}

	public void onChildPositionChanged(DrawableElement drawableElement, DrawableElement child) {
		appendMethodToLog("onChildPositionChanged", drawableElement, child);
	}

	public void onChildSizeChanged(DrawableElement drawableElement, DrawableElement child) {
		appendMethodToLog("onChildSizeChanged", drawableElement, child);
	}

	public void onChildAdded(DrawableElement drawableElement, DrawableElement child) {
		appendMethodToLog("onChildAdded", drawableElement, child);
	}

	public void onChildRemoved(DrawableElement drawableElement, DrawableElement child) {
		appendMethodToLog("onChildRemoved", drawableElement, child);
	}

}
