package com.zine.zinemob.scene.controller;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.Scene;

/**
 * Classe que controla um elemento da cena.
 */
public class Controller {
	
	/**
	 * The controller of the Scene.
	 */
	public static interface SceneController {
		
		/**
		 * Add the controller to the Scene. 
		 * 
		 * If the controller implements Updatable or some input listener, it will
		 * be automatically added to the Scene too.
		 */
		public void addController(Controller controller);
		
		/**
		 * Removes the controller from the Scene.
		 */
		public void removeController(Controller controller);
		
		/**
		 * Returns the screen DrawableElement of the Scene.
		 */
		public DrawableElement getScreenElement();
		
		/**
		 * Finishes the execution of the Scene.
		 */
		public void finishExecution();
		
		/**
		 * Calls the run method of the object at the end of the main loop.
		 */
		public void callAfter(Runnable runnable);
		
		/**
		 * Method that runs other Scene and returns to this Scene execution when the
		 * other Scene execution comes to the end.
		 */
		public void runOtherScene(Scene scene);
	}
	
	private SceneController sceneController;

	/**
	 * Returns the SceneController associated with this Controller.
	 */
	public SceneController getSceneController() {
		return sceneController;
	}

	/**
	 * Sets the SceneController associated with this Controller. It is automatically
	 * done by the Scene when the controller is added to the Scene.
	 */
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	
	/**
	 * Called when a non-null SceneController is attached to this Controller. By
	 * default, it does nothing.
	 */
	public void onSceneControllerAttached(SceneController sceneController) {
	}
	
	/**
	 * Convenience method to remove the controller from the scene.
	 */
	public void finish() {
		if (sceneController != null) {
			sceneController.removeController(this);
		}
	}

}
