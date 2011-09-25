package com.zine.zinemob.animation;

/**
 * Base class of animations controlled by frames.
 */
public abstract class FramesAnimationController extends AnimationController {
	
	/**
	 * Defines an infinite quantity of loops.
	 */
	public static int INFINITE_LOOPS = -1;
	
	private int loops = 0;
	private boolean pingPong = false;
	private boolean rewind = false;
	private int stepsBeforeStart = 0;
	private int stepsBetweenLoops = 0;
	private int stepsBetweenFrames = 0;
	private int length = 0;
	private boolean finishAfterExecuteAllFrames = true;
	
	private int pauseReverseCount = 0;
	private int currentFrame = 0;
	private int currentLoop = 0;
	private int frameInc = 1;
	private boolean finished = false;
	
	public void update() {
		super.update();
		
		if (!finished) {
			if (pauseReverseCount <= 0) {

				pauseReverseCount = getStepsBetweenFrames();

				updateFrame(currentFrame);

				currentFrame += frameInc;

				if (currentFrame >= getLength() || currentFrame < 0) {

					currentLoop++;

					if (currentLoop <= getLoops() || getLoops() == INFINITE_LOOPS) {

						pauseReverseCount = getStepsBetweenLoops();

						if (isPingPong()) {
							currentFrame -= frameInc;
							frameInc *= -1;
							currentFrame += frameInc;
						} else {
							currentFrame = 0;
						}

					} else {
						if (mustRewind()) {
							updateFrame(0);
						}
						if (mustFinishAfterExecuteAllFrames()) {
							finish();
						} else {
							animationFinish();
						}
						finished = true;
					}
				}
			} else {
				pauseReverseCount--;
			}
		}
	}
	
	/**
	 * Resets the animation (current frame and loop), so the animation will be
	 * at begining at next frame update.
	 */
	public void resetAnimation() {
		pauseReverseCount = getStepsBeforeStart();
		currentFrame = 0;
		currentLoop = 0;
		frameInc = 1;
		finished = false;
	}
	
	/**
	 * Updates the animation.
	 * @param frame the current frame
	 */
	public abstract void updateFrame(int frame);

	/**
	 * Returns que quantity of loops before the animation ends.
	 */
	public int getLoops() {
		return loops;
	}

	/**
	 * Sets que quantity of loops before the animation ends.
	 */
	public void setLoops(int loops) {
		this.loops = loops;
	}

	/**
	 * Returns if the loops executes in ping-pong mode.
	 */
	public boolean isPingPong() {
		return pingPong;
	}

	/**
	 * Sets if the loops executes in ping-pong mode.
	 */
	public void setPingPong(boolean pingPong) {
		this.pingPong = pingPong;
	}

	/**
	 * Returns if the animation must rewind to the initial state (frame 0) when it finishes.
	 */
	public boolean mustRewind() {
		return rewind;
	}

	/**
	 * Sets if the animation must rewind to the initial state (frame 0) when it finishes.
	 */
	public void setRewind(boolean rewind) {
		this.rewind = rewind;
	}

	/**
	 * Returns the quantity of pause steps between each loop iteration.
	 */
	public int getStepsBetweenLoops() {
		return stepsBetweenLoops;
	}

	/**
	 * Sets the quantity of pause steps between each loop iteration.
	 */
	public void setStepsBetweenLoops(int stepsBetweenLoops) {
		this.stepsBetweenLoops = stepsBetweenLoops;
	}

	/**
	 * Returns the quantity of pause steps between each frame.
	 */
	public int getStepsBetweenFrames() {
		return stepsBetweenFrames;
	}

	/**
	 * Sets the quantity of pause steps between each frame.
	 */
	public void setStepsBetweenFrames(int stepsBetweenFrames) {
		this.stepsBetweenFrames = stepsBetweenFrames;
	}

	/**
	 * Returns the quantity of frames of the animation.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the quantity of frames of the animation.
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Returns the quantity of pause steps before start the animation.
	 */
	public int getStepsBeforeStart() {
		return stepsBeforeStart;
	}

	/**
	 * Sets the quantity of pause steps before start the animation.
	 */
	public void setStepsBeforeStart(int stepsBeforeStart) {
		this.stepsBeforeStart = stepsBeforeStart;
		if (currentFrame == 0) {
			pauseReverseCount = stepsBeforeStart;
		}
	}

	/**
	 * Returns if the animation must finish itself after execute all frames. By
	 * default, the value is true.
	 */
	public boolean mustFinishAfterExecuteAllFrames() {
		return finishAfterExecuteAllFrames;
	}

	/**
	 * Sets if the animation must finish itself after execute all frames. By
	 * default, the value is true.
	 */
	public void setFinishAfterExecuteAllFrames(boolean finishAfterExecuteAllFrames) {
		this.finishAfterExecuteAllFrames = finishAfterExecuteAllFrames;
	}
}
