package com.zine.zinemob.scene.controller;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Classe que controla um elemento da cena.
 */
public class Controller {
	
	/**
	 * The controller of the Scene.
	 */
	public static interface SceneController {
		
		/**
		 * Add the controller to the Scene. If the Controller contains a DrawableElement
		 * without parent, then this DrawableElement is added to the screenElement.
		 * 
		 * If the controller implements Updatable or some input listener, it will
		 * be automatically added to the Scene too.
		 */
		void addController(Controller controller);
		
		/**
		 * Removes the controller from the Scene. If the DrawableElement of the
		 * Controller is a child of the screenElement, then this DrawableElement
		 * is also removed.
		 */
		void removeController(Controller controller);
		
		/**
		 * Returns the screen DrawableElement of the Scene.
		 */
		DrawableElement getScreenElement();
		
		/**
		 * Finishes the execution of the Scene.
		 */
		void finishExecution();
		
		/**
		 * Calls the run method of the object at the end of the main loop.
		 */
		void callAfter(Runnable runnable);
	}

	private DrawableElement drawableElement;
	
	private SceneController sceneController;

	/**
	 * Retorna o elemento a ser desenhado. Um controlador pode ter apenas um
	 * elemento de desenho, porém mais elementos podem ser utilizados como filhos
	 * deste.
	 * @return o elemento a ser desenhado, pode ser null
	 */
	public DrawableElement getDrawableElement() {
		return drawableElement;
	}

	/**
	 * Define o elemento a ser desenhado. Um controlador pode ter apenas um
	 * elemento de desenho, porém uma alternativa para esta limitação é utilizar
	 * um elemento com vários elementos filhos.
	 * @param drawableElement o elemento a ser desenhado, pode ser null
	 */
	public void setDrawableElement(DrawableElement drawableElement) {
		this.drawableElement = drawableElement;
	}

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

}
