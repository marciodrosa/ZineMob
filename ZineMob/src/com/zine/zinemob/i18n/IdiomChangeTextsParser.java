package com.zine.zinemob.i18n;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.text.ImageTextElement;
import com.zine.zinemob.scene.Scene;

/**
 * Utility class to be used when the idiom was changed, to update the texts used
 * on a Scene.
 */
public class IdiomChangeTextsParser {
	
	/**
	 * Parses all DrawableElements of the Scene and updates the texts of ImageTextElements.
	 */
	public void updateTexts(Scene scene) {
		updateTexts(scene.getScreenElement());
	}
	
	/**
	 * Parses the DrawableElement and all children and updates the texts of ImageTextElements.
	 */
	public void updateTexts(DrawableElement drawableElement) {
		if (drawableElement instanceof ImageTextElement) {
			((ImageTextElement)drawableElement).updateTranslation();
		}
		for (int i=0; i<drawableElement.getChildrenCount(); i++) {
			updateTexts(drawableElement.getChild(i));
		}
	}
}
