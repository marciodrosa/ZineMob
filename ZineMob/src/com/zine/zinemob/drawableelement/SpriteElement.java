package com.zine.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * Elemento que desenha um sprite.
 */
public class SpriteElement extends DrawableElement {
	private Sprite sprite;

	/**
	 * Construtor.
	 * @param sprite sprite que será usado, pode ser null
	 */
	public SpriteElement(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Construtor.
	 * @param image imagem a ser utilizada no sprite
	 */
	public SpriteElement(Image image) {
		sprite = new Sprite(image);
	}
	
	/**
	 * Construtor.
	 * @param image imagem a ser utilizada no sprite
	 * @param frameWidth largura do frame
	 * @param frameHeight altura do frame
	 */
	public SpriteElement(Image image, int frameWidth, int frameHeight) {
		this.sprite = new Sprite(image, frameWidth, frameHeight);
	}
	
	/**
	 * Método que facilita a criação de uma sequência de animação no sprite
	 * do elemento. Se o elemento não possuir um Sprite associado (getSprite
	 * retorna null), então este método não fará nada.
	 * @param firstFrame índice do primeiro frame
	 * @param lastFrame índice do último frame
	 * @throws Exception exceção lançada pela classe Sprite no método setFrameSequence
	 */
	public void setFrameSequence(int firstFrame, int lastFrame){
		setFrameSequence(firstFrame, lastFrame, 1);
	}
	
	/**
	 * Método que facilita a criação de uma sequência de animação no sprite
	 * do elemento. Se o elemento não possuir um Sprite associado (getSprite
	 * retorna null), então este método não fará nada.
	 * @param firstFrame índice do primeiro frame
	 * @param lastFrame índice do último frame
	 * @param framesRepeat a quantidade de repetições de cada frame. Por
	 * exemplo, definindo os parâmetros como (1,3,2), os frames da sequência
	 * serão (1,1,2,2,3,3).
	 * @throws IllegalArgumentException se framesRepeat for menor que 1, ou se o
	 * método setFrameSequence da classe Sprite lançar esta exceção
	 */
	public void setFrameSequence(int firstFrame, int lastFrame, int framesRepeat) {

		if(sprite != null) {
			if (framesRepeat < 1) {
				throw new IllegalArgumentException ("framesRepeat must be equal or greater then 1.");
			}

			boolean inverted = false;
			if (firstFrame > lastFrame) {
				inverted = true;
			}

			if(inverted) {
				int frames[] = new int [((firstFrame+1)-lastFrame)*framesRepeat];
				for (int i=lastFrame, k=0; i>=firstFrame; i--) {
					for (int j=0; j<framesRepeat; j++, k++) {
						frames[k] = i;
					}
				}
				sprite.setFrameSequence(frames);
			}
			else {
				int frames[] = new int [((lastFrame+1)-firstFrame)*framesRepeat];
				for (int i=firstFrame, k=0; i<=lastFrame; i++) {
					for (int j=0; j<framesRepeat; j++, k++) {
						frames[k] = i;
					}
				}
				sprite.setFrameSequence(frames);
			}
		}
	}

	protected void drawElement(Graphics graphics) {
		if(getSprite() != null) {
			getSprite().paint(graphics);
		}
	}

	public int getWidth() {
		if (getSprite() != null) {
			return getSprite().getWidth();
		}
		return super.getWidth();
	}

	public int getHeight() {
		if (getSprite() != null) {
			return getSprite().getHeight();
		}
		return super.getHeight();
	}

	/**
	 * Retorna o Sprite do elemento.
	 * @return o Sprite do elemento
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Define o Sprite do elemento.
	 * @param sprite o Sprite do elemento
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
}
