package com.sinosarts.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * Elemeto que desenha um sprite.
 */
public class SpriteElement extends DrawableElement
{
	private Sprite sprite;

	/**
	 * Construtor.
	 * @param s sprite que ser� usado, pode ser null
	 */
	public SpriteElement (Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Construtor.
	 * @param im imagem a ser utilizada no sprite
	 */
	public SpriteElement (Image im) {
		sprite = new Sprite(im);
	}
	
	/**
	 * Construtor.
	 * @param image imagem a ser utilizada no sprite
	 * @param frameWidth largura do frame
	 * @param frameHeight altura do frame
	 */
	public SpriteElement (Image image, int frameWidth, int frameHeight) {
		this.sprite = new Sprite(image, frameWidth, frameHeight);
	}
	
	/**
	 * M�todo que facilita a cria��o de uma sequ�ncia de anima��o no sprite
	 * do elemento.
	 * @param firstFrame �ndice do primeiro frame
	 * @param lastFrame �ndice do �ltimo frame
	 */
	public void setFrameSequence(int firstFrame, int lastFrame){
		setFrameSequence(firstFrame, lastFrame, 1);
	}
	
	/**
	 * M�todo que facilita a cria��o de uma sequ�ncia de anima��o no sprite
	 * do elemento.
	 * @param firstFrame �ndice do primeiro frame
	 * @param lastFrame �ndice do �ltimo frame
	 * @param interpolationFrames a quantidade de repeti��es de cada frame. Por
	 * exemplo, definindo os par�metros como (1,3,2), os frames da sequ�ncia
	 * ser�o (1,1,2,2,3,3).
	 */
	public void setFrameSequence(int firstFrame, int lastFrame, int interpolationFrames) {
		
		if (firstFrame>lastFrame || interpolationFrames<1)
			throw new IllegalArgumentException ("interpolationFrames must be equal or greater then 1. " +
					"lastFrame must be equal or greater then firstFrame.");
		
		int frames[] = new int [((lastFrame+1)-firstFrame)*interpolationFrames];
		for (int i=0, j=firstFrame; i<frames.length; i+=interpolationFrames, j++) {
			for (int k=0; k<interpolationFrames; k++) {
				frames[i+k] = j;
			}
		}

		if (sprite != null) {
			sprite.setFrameSequence(frames);
		}
	}

	protected void drawElement(Graphics graphics) {
		if(sprite != null) {
			sprite.paint(graphics);
		}
	}

	/**
	 * Retorna a largura do Sprite, ao menos que uma largura diferente de
	 * DEFAULT_SIZE tenha sido definida.
	 * @return a largura do elemento
	 */
	public int getWidth() {
		if(super.getWidth() == DEFAULT_SIZE && sprite != null) {
			return sprite.getWidth();
		}
		return super.getWidth();
	}

	/**
	 * Retorna a altura do Sprite, ao menos que uma altura diferente de
	 * DEFAULT_SIZE tenha sido definida.
	 * @return a altura do elemento
	 */
	public int getHeight() {
		if(super.getHeight() == DEFAULT_SIZE && sprite != null) {
			return sprite.getHeight();
		}
		return super.getHeight();
	}
	
}
