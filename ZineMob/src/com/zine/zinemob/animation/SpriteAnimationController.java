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
		spriteElement.getSprite().setFrameSequence(frameSequence);
		setLength(frameSequence.length);
		setLoops(INFINITE_LOOPS);
	}
	
	public void updateFrame(int frame) {
		spriteElement.getSprite().setFrame(frame);
	}
	
}
