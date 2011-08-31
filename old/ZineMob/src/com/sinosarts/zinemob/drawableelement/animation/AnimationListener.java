package com.sinosarts.zinemob.drawableelement.animation;

import com.sinosarts.zinemob.drawableelement.animation.Animable;

/**
 * Listener para eventos relacionados a objetos animáveis.
 */
public interface AnimationListener {

	/**
	 * Método chamado quando a animação do objeto chega ao final.
	 * @param animable o objeto animável cuja animação chegou ao final
	 */
	public void onAnimationEnd(Animable animable);

	/**
	 * Método chamado toda a vez que a animação do objeto é atualizada.
	 * @param animable o objeto animável cuja animação foi atualizada
	 */
	public void onAnimationUpdate(Animable animable);
}
