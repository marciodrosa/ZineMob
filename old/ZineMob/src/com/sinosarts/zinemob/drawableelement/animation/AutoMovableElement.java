package com.sinosarts.zinemob.drawableelement.animation;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Layer;
import com.sinosarts.zinemob.core.ValuesInterpolation;
import com.sinosarts.zinemob.drawableelement.DrawableElement;

/**
 * Elemento que se move de um ponto a outro em uma anima��o suave. A anima��o do
 * movimento � atualizada sempre que o elemento � desenhado. O m�todo setPosition,
 * ao inv�s de definir a posi��o imediata do elemento, define a posi��o de destino,
 * para onde o elemento ir� transladar em uma anima��o suave (desde que a quantidade
 * de frames da anima��o for definida como maior que 1).
 */
public class AutoMovableElement extends DrawableElement {

	private ValuesInterpolation movementX = new ValuesInterpolation(0,0,0);
	private ValuesInterpolation movementY = new ValuesInterpolation(0,0,0);

	/**
	 * Construtor.
	 */
	public AutoMovableElement() {
	}

	/**
	 * Define o frame (step) da anima��o.
	 * @param step o frame da anima��o
	 */
	public void setStep(int step) {
		movementX.setStep(step);
		movementY.setStep(step);
	}

	/**
	 * Define a quantidade de frames utilizada para a anima��o do movimento do elemento.
	 * @param steps a quantidade de frames utilizada para a anima��o do movimento do elemento
	 */
	public void setMovementAnimationSteps(int steps) {
		movementX.setSteps(steps);
		movementY.setSteps(steps);
	}

	/**
	 * Retorna a quantidade de frames utilizada para a anima��o do movimento do elemento.
	 * @return a quantidade de frames utilizada para a anima��o do movimento do elemento
	 */
	public int getMovementAnimationSteps() {
		return movementX.getSteps();
	}

	/**
	 * Define o modo como a anima��o do movimento acontece.
	 * @param mode o modo da movimenta��o. Deve ser uma das constantes da classe
	 * ValuesInterpolation.
	 */
	public void setAnimationCurveMode(byte mode) {
		movementX.setCurveMode(mode);
		movementY.setCurveMode(mode);
	}

	/**
	 * Retorna o modo como a anima��o do movimento acontece, definido em setAnimationCurveMode.
	 * @return o modo como a anima��o do movimento acontece
	 */
	public byte getAnimationCurveMode() {
		return movementX.getCurveMode();
	}

	public void setPosition(int x, int y) {
		if (getMovementAnimationSteps() < 1)
			super.setPosition(x, y);

		if (getDestPositionX() != x) {
			movementX.setInitialValue(movementX.getCurrentValue());
			movementX.setStep(0);
			movementX.setFinalValue(x);
		}

		if (getDestPositionY() != y) {
			movementY.setInitialValue(movementY.getCurrentValue());
			movementY.setStep(0);
			movementY.setFinalValue(y);
		}
	}

	/**
	 * Retorna a coordenada X da posi��o de destino do elemento.
	 * @return a coordenada X da posi��o de destino do elemento
	 */
	public int getDestPositionX() {
		return movementX.getFinalValue();
	}

	/**
	 * Retorna a coordenada Y da posi��o de destino do elemento.
	 * @return a coordenada Y da posi��o de destino do elemento
	 */
	public int getDestPositionY() {
		return movementY.getFinalValue();
	}

	public void draw(Graphics g) {
		super.setPosition(movementX.getCurrentValue(), movementY.getCurrentValue());

		super.draw(g);

		movementX.nextStep();
		movementY.nextStep();
	}

	/**
	 * Retorna se a anima��o da �ltima movimenta��o j� foi completada.
	 * @return true se a movimenta��o j� foi completada, false se ainda n�o
	 */
	public boolean isMovementCompleted() {
		return movementX.getCurrentStep() >= movementX.getSteps()-1 &&
				movementY.getCurrentStep() >= movementY.getSteps()-1;
	}

}

/*
 * Vers�o 01.07:
 * - renomeado de AutoMoveble para AutoMovable
 * - m�todos alterados, agora � o m�todo setPosition que define a anima��o
 *
 * Vers�o 01.06: refatora��es e documenta��o na classe (post ZM_20100425)
 */
