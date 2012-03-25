package com.zine.zinemob.scene;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.Controller;

/**
 * Asset is a pair of DrawableElement and a Controller that can be simultaneously
 * attached to a Scene. By default, the DrawableElement and the Controller are null
 * and can be setted by the "sets" methods or by the constructor.
 */
public class Asset {
	
	private DrawableElement drawableElement;
	private Controller controller;
	
	public Asset() {
	}
	
	/**
	 * @param drawableElement the DrawableElement
	 * @param controller the Controller
	 */
	public Asset(DrawableElement drawableElement, Controller controller) {
		this.drawableElement = drawableElement;
		this.controller = controller;
	}

	public final DrawableElement getDrawableElement() {
		return drawableElement;
	}

	public void setDrawableElement(DrawableElement drawableElement) {
		this.drawableElement = drawableElement;
	}

	public final Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Attaches the Asset to a Scene. It calls attachDrawableElementToScene and
	 * attachControllerToScene, respectively. This method can be overrided to create
	 * another behavior, or you can override the attachDrawableElementToScene and
	 * attachControllerToScene methods.
	 * @param scene the Scene to attach the Asset
	 */
	public void attachToScene(Scene scene) {
		attachDrawableElementToScene(scene);
		attachControllerToScene(scene);
	}
	
	/**
	 * Attaches the DrawableElement (if not null) to the Scene. This method is
	 * called by attachToScene method. Can be overrided to create another behavior.
	 * @param scene the Scene to attach the DrawableElement
	 */
	protected void attachDrawableElementToScene(Scene scene) {
		if (drawableElement != null) {
			scene.getScreenElement().addChild(drawableElement);
		}
	}
	
	/**
	 * Attaches the Controller (if not null) to the Scene. This method is
	 * called by attachToScene method. Can be overrided to create another behavior.
	 * @param scene the Scene to attach the Controller
	 */
	protected void attachControllerToScene(Scene scene) {
		if (controller != null) {
			scene.addController(controller);
		}
	}
	
}
