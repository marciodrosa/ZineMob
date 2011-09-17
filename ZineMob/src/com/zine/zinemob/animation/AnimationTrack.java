package com.zine.zinemob.animation;

/**
 * An track with initial and final values.
 */
public class AnimationTrack {
	
	private int initialValue, finalValue, delta;

	public int getInitialValue() {
		return initialValue;
	}

	public void setInitialValue(int initialValue) {
		this.initialValue = initialValue;
		calculateDelta();
	}

	public int getFinalValue() {
		return finalValue;
	}

	public void setFinalValue(int finalValue) {
		this.finalValue = finalValue;
		calculateDelta();
	}
	
	private void calculateDelta() {
		delta = finalValue - initialValue;
	}
	
	/**
	 * Returns the value for the current frame.
	 * @param frame the current frame
	 * @param framesCount the total quantity of frames of the animation
	 */
	public int getValueForFrame(int frame, int framesCount) {
		return ((delta*frame) / (framesCount-1)) + initialValue;
	}
	
}
