package com.sinosarts.zinemob.drawableelement.animation;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Layer;
import com.sinosarts.zinemob.core.ValuesInterpolation;
import com.sinosarts.zinemob.drawableelement.DrawableElement;

/**
 * Elemento que se move de um ponto a outro em uma animação suave. A animação do
 * movimento é atualizada sempre que o elemento é desenhado. O método setPosition,
 * ao invés de definir a posição imediata do elemento, define a posição de destino,
 * para onde o elemento irá transladar em uma animação suave (desde que a quantidade
 * de frames da animação for definida como maior que 1).
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
	 * Define o frame (step) da animação.
	 * @param step o frame da animação
	 */
	public void setStep(int step) {
		movementX.setStep(step);
		movementY.setStep(step);
	}

	/**
	 * Define a quantidade de frames utilizada para a animação do movimento do elemento.
	 * @param steps a quantidade de frames utilizada para a animação do movimento do elemento
	 */
	public void setMovementAnimationSteps(int steps) {
		movementX.setSteps(steps);
		movementY.setSteps(steps);
	}

	/**
	 * Retorna a quantidade de frames utilizada para a animação do movimento do elemento.
	 * @return a quantidade de frames utilizada para a animação do movimento do elemento
	 */
	public int getMovementAnimationSteps() {
		return movementX.getSteps();
	}

	/**
	 * Define o modo como a animação do movimento acontece.
	 * @param mode o modo da movimentação. Deve ser uma das constantes da classe
	 * ValuesInterpolation.
	 */
	public void setAnimationCurveMode(byte mode) {
		movementX.setCurveMode(mode);
		movementY.setCurveMode(mode);
	}

	/**
	 * Retorna o modo como a animação do movimento acontece, definido em setAnimationCurveMode.
	 * @return o modo como a animação do movimento acontece
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
	 * Retorna a coordenada X da posição de destino do elemento.
	 * @return a coordenada X da posição de destino do elemento
	 */
	public int getDestPositionX() {
		return movementX.getFinalValue();
	}

	/**
	 * Retorna a coordenada Y da posição de destino do elemento.
	 * @return a coordenada Y da posição de destino do elemento
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
	 * Retorna se a animação da última movimentação já foi completada.
	 * @return true se a movimentação já foi completada, false se ainda não
	 */
	public boolean isMovementCompleted() {
		return movementX.getCurrentStep() >= movementX.getSteps()-1 &&
				movementY.getCurrentStep() >= movementY.getSteps()-1;
	}

}

/*
 * Versão 01.07:
 * - renomeado de AutoMoveble para AutoMovable
 * - métodos alterados, agora é o método setPosition que define a animação
 *
 * Versão 01.06: refatorações e documentação na classe (post ZM_20100425)
 */
