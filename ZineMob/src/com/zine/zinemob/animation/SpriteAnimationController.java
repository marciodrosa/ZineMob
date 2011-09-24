package com.zine.zinemob.animation;

import com.zine.zinemob.drawableelement.SpriteElement;

/**
 * AnimationController that animates a Sprite.
 */
public class SpriteAnimationController extends FramesAnimationController {

	private SpriteElement spriteElement;
	
	public SpriteAnimationController(SpriteElement spriteElement) {
		this.spriteElement = spriteElement;
		setDrawableElement(spriteElement);
	}
	
	public SpriteAnimationController(SpriteElement spriteElement, int[] frameSequence) {
		this(spriteElement);
		setFrameSequenceAndReset(frameSequence);
		setLoops(INFINITE_LOOPS);
	}
	
	public SpriteAnimationController(SpriteElement spriteElement, int firstFrame, int lastFrame) {
		this(spriteElement, createFrameSequence(firstFrame, lastFrame));
	}
	
	private static int[] createFrameSequence(int firstFrame, int lastFrame) {
		int[] frameSequence = new int[lastFrame - firstFrame + 1];
		for (int i=0; i<frameSequence.length; i++) {
			frameSequence[i] = firstFrame + i;
		}
		return frameSequence;
	}
	
	/**
	 * Sets the frame sequence. It will automatically resets the animation.
	 */
	public void setFrameSequence(int[] frameSequence) {
		setFrameSequenceAndReset(frameSequence);
	}
	
	/**
	 * Sets the frame sequence. It will automatically resets the animation.
	 */
	public void setFrameSequence(int firstFrame, int lastFrame) {
		setFrameSequence(createFrameSequence(firstFrame, lastFrame));
	}
	
	private void setFrameSequenceAndReset(int[] frameSequence) {
		spriteElement.getSprite().setFrameSequence(frameSequence);
		setLength(frameSequence.length);
		resetAnimation();
	}
	
	public void updateFrame(int frame) {
		spriteElement.getSprite().setFrame(frame);
	}
	
}
