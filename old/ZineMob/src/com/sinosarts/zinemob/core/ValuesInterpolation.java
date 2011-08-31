package com.sinosarts.zinemob.core;

/**
 * Classe que calcula um valor entre dois outros valores.
 */
public class ValuesInterpolation
{
	public static final byte LINEAR=0, ACCELERATION=1, DESACCELERATION=2;
	
	// constante de precis�o: multiplica as vari�veis e divide o resultado final,
	// evitando imprecis�o dos inteiros.
	private static final int PRECISION = 1000;
	
	private int currentStep = 0;
	private boolean loop = false;
	private byte mode = LINEAR;
	
	private int initialValue, finalValue, steps;
	
	// utilizados para c�lculo da equa��o quadr�tica de acelera��o ou desacelera��o.
	private int a=0, b=0;

	/**
	 * Construtor.
	 * @param initialValue o valor inicial
	 * @param finalValue o valor final
	 * @param steps a quantidade de valores entre o valor inicial e o final
	 */
	public ValuesInterpolation (int initialValue, int finalValue, int steps) {
		this.initialValue = initialValue;
		this.finalValue = finalValue;
		this.steps = steps;
		calculateAandB();
	}

	/**
	 * Construtor.
	 * @param initialValue o valor inicial
	 * @param finalValue o valor final
	 * @param steps a quantidade de valores entre o valor inicial e o final
	 * @param mode o modo de interpola��o (LINEAR, ACCELERATION, DESACCELERATION)
	 */
	public ValuesInterpolation (int initialValue, int finalValue, int steps, byte mode) {
		this.initialValue = initialValue;
		this.finalValue = finalValue;
		this.steps = steps;
		this.mode = mode;
		calculateAandB();
	}

	/**
	 * Define o modo da curva que calcula o valor interpolado entre os valores
	 * inicial e final.
	 * @param m o modo da curva (LINEAR, ACCELERATION, DESACCELERATION)
	 */
	public void setCurveMode (byte m) {
		mode = m;
		calculateAandB();
	}

	/**
	 * Retorna o modo da curva que calcula o valor interpolado entre os valores
	 * inicial e final.
	 * @return o modo da curva (LINEAR, ACCELERATION, DESACCELERATION)
	 */
	public byte getCurveMode() {
		return mode;
	}
	
	private boolean isExpanding() {
		if (steps > Math.abs(finalValue-initialValue))
			return true;
		else
			return false;
	}
	
	private void calculateAandB() {
		if (mode==LINEAR)
			return;
		
		int finalStep = (steps-1);
		a = ((finalValue-initialValue)*PRECISION)/(finalStep*finalStep);
		
		if (mode==DESACCELERATION) {
			a = -a;
			b = -(finalStep*2)*a;
		}
	}

	/**
	 * Retorna o valor interpolado entre o valor inicial e o final.
	 * @param step o passo de onde o valor ser� calculado
	 * @return o valor calculado (ser� o valor final se step for maior ou igual
	 * a quantidade de passos; e ser� o valor inicial se step for menor ou igual
	 * a zero).
	 */
	public int getValueAt (int step) {
		if (step >= steps-1)
			return finalValue;
		if (step <= 0)
			return initialValue;
		
		switch (mode) {
			case LINEAR:
				if (isExpanding())
					return step*(finalValue-initialValue+1)/(steps-1) + initialValue;
				else
					return step*(finalValue-initialValue)/(steps-1) + initialValue;
			case ACCELERATION:
				return (a*(step*step))/PRECISION + initialValue;
			case DESACCELERATION:
				return (a*(step*step) + b*step)/PRECISION + initialValue;
		}
		
		return 0;
	}

	/**
	 * Calcula o valor atual de acordo com o step atual, definido em setStep.
	 * @return o valor atual de acordo com o step atual
	 */
	public int getCurrentValue() {
		return getValueAt (currentStep);
	}

	/**
	 * Avan�a para o pr�ximo step.
	 */
	public void nextStep () {
		setStep(currentStep+1);
	}

	/**
	 * Define o step atual, utilizado para calcular o valor atual, retornado em
	 * getCurrentValue. Se for maior que a quantidade de steps, definir� o step
	 * como zero se estiver no modo de loop ou, caso contr�rio, definir� o step
	 * como o �ltimo poss�vel dentro do limite. Se for menor que zero, ser�
	 * definido como zero.
	 * @param i o step atual
	 */
	public void setStep (int i) {
		currentStep = i;
		
		if (currentStep >= steps) {
			if (loop)
				currentStep = 0;
			else
				currentStep = steps-1;
		}
		else if (currentStep<0)
			currentStep = 0;
	}

	/**
	 * Retorna o step atual, definido em setStep ou incrementado em nextStep.
	 * @return o step atual
	 */
	public int getCurrentStep() {
		return currentStep;
	}

	/**
	 * Ativa ou desativa o modo loop. Neste modo, quando � definido um step maior
	 * que a quantidade m�xima de steps, ent�o o step voltar� a ser zero.
	 * @param l true para ativar o modo loop, false para desativar (por padr�o
	 * est� desativado)
	 */
	public void setLoopMode (boolean l) {
		loop = l;
	}

	/**
	 * Retorna se est� no modo loop, ativado ou desativado atrav�s do m�todo setLoopMode.
	 * @return true se o modo loop est� ativado, false se n�o est�
	 */
	public boolean isLoopMode() {
		return loop;
	}
	
	public void setInitialValue (int i) {
		initialValue = i;
		calculateAandB();
	}
	
	public void setFinalValue (int i) {
		finalValue = i;
		calculateAandB();
	}
	
	public void setSteps (int i) {
		steps = i;
		calculateAandB();
	}
	
	public int getInitialValue() {
		return initialValue;
	}
	
	public int getFinalValue() {
		return finalValue;
	}
	
	public int getSteps() {
		return steps;
	}
}

/*
 * Vers�o 01.06: efetuadas algumas refatora��es e removido m�todo end()
 */
