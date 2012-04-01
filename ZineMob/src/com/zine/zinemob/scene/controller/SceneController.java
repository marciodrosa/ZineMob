package com.zine.zinemob.scene.controller;

import com.zine.zinemob.scene.Scene;

/**
 * Controlls some part of the Scene. The method init() is called when it is added
 * to a Scene. Overrides this method to initiate the logic. To update the logic at
 * each frame, implements the interface Updateble. To access the input, implements
 * the interfaces KeyboardListener or PointerListener.
 */
public class SceneController {
	
	private Scene scene;

	/**
	 * Returns the Scene associated with this Controller. It is null
	 * if the Controller is not attached to a scene.
	 */
	public Scene getScene() {
		return scene;
	}

	/**
	 * Sets the Scene associated with this Controller. It is automatically
	 * done by the Scene when the controller is added to it. When the Controller
	 * is removed from the Scene, this method is called with a null argument.
	 */
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	/**
	 * Convenience method to remove the controller from the scene.
	 */
	public void finish() {
		if (scene != null) {
			scene.removeController(this);
		}
	}
	
	/**
	 * Called when the controller is attached to a Scene. At this moment, te Controller
	 * can access the SceneController class. By default, it does nothing.
	 */
	public void init() {
	}
	
	/**
	 * Called when the controller is removed from the scene. It don't happen necessarily
	 * immediately after the finish method call, can have a latency. By default, it does nothing.
	 */
	public void onFinish() {
	}

}
