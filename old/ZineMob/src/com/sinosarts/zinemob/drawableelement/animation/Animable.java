package com.sinosarts.zinemob.drawableelement.animation;

/**
 * Interface para objetos que possuem animação.
 */
public interface Animable {

	/**
	 * Retorna se a animação já chegou ao fim.
	 * @return true se a animação já chegou ao fim, false se não
	 */
	public boolean isAnimationCompleted();
}
