package com.sinosarts.uzine.view;

import com.sinosarts.zinemob.core.ValuesInterpolation;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.renderelements.Animable;
import com.sinosarts.zinemob.renderelements.AnimationListener;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;
import javax.microedition.lcdui.Graphics;

public class WindowRenderElement extends LayoutElement implements Animable {

	public static final byte NO_ANIMATION = 0;
	public static final byte HIDE_TO_LEFT = 1;
	public static final byte UNHIDE_FROM_LEFT = 2;
	public static final byte HIDE_TO_RIGHT = 3;
	public static final byte UNHIDE_FROM_RIGHT = 4;
	public static final byte UNHIDE = 5;

	private int animationSize = 5;
	private boolean animMode = false;

	private ValuesInterpolation positionXAnimation = new ValuesInterpolation(0, 0, animationSize);

	private AnimationListener animationListener;

	public WindowRenderElement(AnimationListener animationListener) {
		this.animationListener = animationListener;
	}

	public int getX() {
		return positionXAnimation.getCurrentValue();
	}

	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		positionXAnimation.setInitialValue(x);
		positionXAnimation.setFinalValue(x);
		positionXAnimation.setStep(0);
	}

	private int getHideToLeftFinalPosition() {
		int screenPosX = (ZineManager.getInstance().getWidth() - ZineManager.getInstance().getWidth())/2;
		return screenPosX-getWidth()-1;
	}

	private int getHideToRightFinalPosition() {
		int screenPosX = (ZineManager.getInstance().getWidth() - ZineManager.getInstance().getWidth())/2;
		return screenPosX + ZineManager.getInstance().getWidth();
	}

	/**
	 * Realiza um movimento de animação da janela.
	 * @param animation a animação a ser aplicada na janela. Pode ser HIDE_TO_LEFT,
	 * UNHIDE_FROM_LEFT, HIDE_TO_RIGHT, UNHIDE_FROM_RIGHT ou UNHIDE (neste caso,
	 * volta para a posição original a partir da última posição desde que foi
	 * escondida).
	 */
	public void animateWindow(byte animation) {

		animMode = true;

		switch(animation) {
			case HIDE_TO_LEFT:
				positionXAnimation.setInitialValue(getX());
				positionXAnimation.setFinalValue(getHideToLeftFinalPosition());
				positionXAnimation.setStep(0);
				break;
			case UNHIDE_FROM_LEFT:
				positionXAnimation.setInitialValue(getHideToLeftFinalPosition());
				positionXAnimation.setFinalValue(super.getX());
				positionXAnimation.setStep(0);
				break;
			case HIDE_TO_RIGHT:
				positionXAnimation.setInitialValue(getX());
				positionXAnimation.setFinalValue(getHideToRightFinalPosition());
				positionXAnimation.setStep(0);
				break;
			case UNHIDE_FROM_RIGHT:
				positionXAnimation.setInitialValue(getHideToRightFinalPosition());
				positionXAnimation.setFinalValue(super.getX());
				positionXAnimation.setStep(0);
				break;
			case UNHIDE:
				positionXAnimation.setInitialValue(getX());
				positionXAnimation.setFinalValue(super.getX());
				positionXAnimation.setStep(0);
				break;
		}
	}

	public void draw (Graphics g) {

		if (positionXAnimation.getFinalValue() == positionXAnimation.getCurrentValue() && animMode) {
			animMode = false;
			animationListener.onAnimationEnd(this);
		}
		else
			animationListener.onAnimationUpdate(this);

		super.draw (g);

		positionXAnimation.nextStep();
	}

	public boolean isAnimationCompleted() {
		return !animMode;
	}
	
}
