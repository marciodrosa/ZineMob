package com.sinosarts.zinemob.drawableelement.animation;

import com.sinosarts.zinemob.drawableelement.animation.Animable;

/**
 * Listener para eventos relacionados a objetos anim�veis.
 */
public interface AnimationListener {

	/**
	 * M�todo chamado quando a anima��o do objeto chega ao final.
	 * @param animable o objeto anim�vel cuja anima��o chegou ao final
	 */
	public void onAnimationEnd(Animable animable);

	/**
	 * M�todo chamado toda a vez que a anima��o do objeto � atualizada.
	 * @param animable o objeto anim�vel cuja anima��o foi atualizada
	 */
	public void onAnimationUpdate(Animable animable);
}
