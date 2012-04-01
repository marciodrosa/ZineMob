package com.zine.zinemob.scene;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.SceneController;

/**
 * Asset is a pair of DrawableElement and a Controller that can be simultaneously
 * attached to a Scene. By default, the DrawableElement and the Controller are null
 * and can be setted by the "sets" methods or by the constructor.
 */
public class Asset {
	
	private DrawableElement drawableElement;
	private SceneController controller;
	
	public Asset() {
	}
	
	/**
	 * @param drawableElement the DrawableElement
	 * @param controller the Controller
	 */
	public Asset(DrawableElement drawableElement, SceneController controller) {
		this.drawableElement = drawableElement;
		this.controller = controller;
	}

	/**
	 * Returns the DrawableElement.
	 */
	public final DrawableElement getDrawableElement() {
		return drawableElement;
	}

	/**
	 * Sets the DrawableElement.
	 */
	public void setDrawableElement(DrawableElement drawableElement) {
		this.drawableElement = drawableElement;
	}

	/**
	 * Returns the Controller.
	 */
	public final SceneController getController() {
		return controller;
	}

	/**
	 * Sets the Controller.
	 */
	public void setController(SceneController controller) {
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
	
	/**
	 * Removes the Asset from the previous attached Scene. It calls removeDrawableElementFromScene and
	 * removeControllerFromScene, respectively. This method can be overrided to create
	 * another behavior, or you can override the attachDrawableElementToScene and
	 * attachControllerToScene methods.
	 */
	public void removeFromScene() {
		removeDrawableElementFromScene();
		removeControllerFromScene();
	}
	
	/**
	 * Removes the DrawableElement (if not null) from the previous attached Scene.
	 * This method is called by removeFromScene method. Can be overrided to create another behavior.
	 */
	protected void removeDrawableElementFromScene() {
		if (drawableElement != null) {
			DrawableElement parent = drawableElement.getParent();
			if (parent != null) {
				parent.removeChild(drawableElement);
			}
		}
	}
	
	/**
	 * Removes the Controller (if not null) from the previous attached Scene.
	 * This method is called by removeFromScene method. Can be overrided to create another behavior.
	 */
	protected void removeControllerFromScene() {
		if (controller != null) {
			controller.finish();
		}
	}
}
