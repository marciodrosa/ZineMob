package com.zine.zinemob.scene.controller;

import com.zine.zinemob.drawableelement.DrawableElement;

/**
 * Classe que controla um elemento da cena.
 */
public class Controller {

	private DrawableElement drawableElement;

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

}
