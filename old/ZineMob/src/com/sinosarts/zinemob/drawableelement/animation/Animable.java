package com.sinosarts.zinemob.drawableelement.animation;

/**
 * Interface para objetos que possuem anima��o.
 */
public interface Animable {

	/**
	 * Retorna se a anima��o j� chegou ao fim.
	 * @return true se a anima��o j� chegou ao fim, false se n�o
	 */
	public boolean isAnimationCompleted();
}
