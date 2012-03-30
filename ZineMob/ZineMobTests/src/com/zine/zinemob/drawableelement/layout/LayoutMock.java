package com.zine.zinemob.drawableelement.layout;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * This mock contains a log of method calls.
 */
public class LayoutMock implements Layout {

	private StringBuffer log = new StringBuffer();

	/**
	 * Returns a log with the calls of the methods of this class. The calls are
	 * formatted like this: "methodname(drawableelementname);othermethod(drawableelementname,otherDrawableElementName);etc;".
	 */	
	public String getCallLog() {
		return log.toString();
	}

	private void appendMethodToLog(String methodName) {
		appendMethodToLog(methodName, null);
	}

	private void appendMethodToLog(String methodName, DrawableElement arg) {
		log.append(methodName);
		log.append('(');
		if (arg != null) {
			log.append(arg.getName());
		}
		log.append(");");
	}

	public void apply() {
		appendMethodToLog("apply");
	}

	public void onPositionChanged() {
		appendMethodToLog("onPositionChanged");
	}

	public void onSizeChanged() {
		appendMethodToLog("onSizeChanged");
	}

	public void onParentPositionChanged() {
		appendMethodToLog("onParentPositionChanged");
	}

	public void onParentSizeChanged() {
		appendMethodToLog("onParentSizeChanged");
	}

	public void onChildPositionChanged(DrawableElement child) {
		appendMethodToLog("onChildPositionChanged", child);
	}

	public void onChildSizeChanged(DrawableElement child) {
		appendMethodToLog("onChildSizeChanged", child);
	}

	public void onChildAdded(DrawableElement child) {
		appendMethodToLog("onChildAdded", child);
	}

	public void onChildRemoved(DrawableElement child) {
		appendMethodToLog("onChildRemoved", child);
	}

	public void onParentChanged() {
		appendMethodToLog("onParentChanged");
	}

}
