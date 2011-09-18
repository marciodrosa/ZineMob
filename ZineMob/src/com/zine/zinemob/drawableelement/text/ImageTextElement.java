package com.zine.zinemob.drawableelement.text;

import com.zine.zinemob.drawableelement.DrawableElement;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * A text that is rendered using an image as font.
 */
public class ImageTextElement extends DrawableElement {
	
	public static final int NO_TYPE_WRITER_EFFECT = -1;
	
	private char[] textCharacters;
	private ImageTextElementFont font;
	private boolean lineWrap = false;
	private int typeWriterEffectSpeed = NO_TYPE_WRITER_EFFECT;
	private int currentTextLength = 0;

	private Hashtable newLineIndexesSet = new Hashtable();
	
	private boolean mustPreProcessTextWhenSizeChanges = true;
	
	public ImageTextElement(String text, ImageTextElementFont font) {
		this(text, font, false);
	}

	public ImageTextElement(String text, ImageTextElementFont font, boolean lineWrap) {
		
		this.textCharacters = text.toCharArray();
		this.font = font;
		this.lineWrap = lineWrap;
		preProcessText();
	}

	public void setSize(int w, int h) {
		super.setSize(w, h);
		if (mustPreProcessTextWhenSizeChanges && mustLineWrap()) {
			preProcessText();
		}
	}
	
	/**
	 * Enables the typewriter effect.
	 * @param speed the speed of the effect, or NO_TYPE_WRITER_EFFECT to disable
	 * the effect.
	 */
	public void setTypeWriterEffect(int speed)
	{
		typeWriterEffectSpeed = speed;
		currentTextLength = 0;
	}
	
	/**
	 * Returns the text.
	 */
	public char[] getText() {
		return textCharacters;
	}
	
	/**
	 * Sets the text.
	 */
	public void setText(String s)
	{
		textCharacters = s.toCharArray();
		preProcessText();
	}
	
	/**
	 * Returns the font.
	 */
	public ImageTextElementFont getFont() {
		return font;
	}
	
	/**
	 * Sets the font.
	 */
	public void setFont(ImageTextElementFont font) {
		this.font = font;
		preProcessText();
	}

	protected void drawElement(Graphics graphics) {
		
		int[] metrics = font.getMetrics();
		Image fontImage = font.getImage();
		
		int posCursor=0, posLine=0, lineCount=0;
		char character=0;
		int areaX = fontImage.getWidth()/16;
		int areaY = fontImage.getHeight()/16;
		
		int length=0;
		
		if (typeWriterEffectSpeed != NO_TYPE_WRITER_EFFECT) {
			length = currentTextLength;
			currentTextLength += typeWriterEffectSpeed;
			if (currentTextLength > textCharacters.length) {
				typeWriterEffectSpeed = -1;
			}
		}
		else {
			length = textCharacters.length;
		}
		
		for (int i=0; i<length; i++) {
			character = textCharacters[i];
			if (character > 255)
				continue;

			if (newLineIndexesSet.contains(new Integer(i)) || character == '\n') {
				posLine += areaY;
				posCursor = 0;
				lineCount++;
			}

			int characterWidth = metrics[character];
			int characterHeight = areaY;

			graphics.drawRegion(fontImage, (character%16)*areaX,
					(character/16)*areaY, characterWidth, characterHeight, 0,
					posCursor, posLine, 0);
			
			posCursor += metrics[character];
		}
	}

	/**
	 * Pre processes the text, calculating the width, height and line wrapers.
	 */
	private void preProcessText() {
		
		mustPreProcessTextWhenSizeChanges = false;
		
		int height = 0;

		newLineIndexesSet = new Hashtable();

		int areaY = font.getImage().getHeight()/16;

		for (int i = 0; i < textCharacters.length; i = processLine(i)) {
			height += areaY;
		}
		
		setSize(getWidth(), height);
		
		mustPreProcessTextWhenSizeChanges = true;
	}
	
	private int processLine(int charIndex) {

		int lastBlankSpaceIndex = 0;
		int x = 0;
		int i = charIndex;

		int[] metrics = font.getMetrics();
		
		for (i=charIndex; i<textCharacters.length; i++) {

			if (textCharacters[i] > 255)
				continue;

			x += metrics[textCharacters[i]];

			if (textCharacters[i] == ' ' || textCharacters[i] == '	')
				lastBlankSpaceIndex = i;
			else if (textCharacters[i] == '\n')
				break;

			if (mustLineWrap() && x > getWidth()) { // extrapolou a largura máxima, volta até o último espaço em branco, onde será posto uma nova linha artificial
				if (lastBlankSpaceIndex > 0) {
					newLineIndexesSet.put(new Integer(lastBlankSpaceIndex), new Integer(lastBlankSpaceIndex));

					// volta os caracteres percorridos, diminuindo a variável X:
					for (; i>lastBlankSpaceIndex; i--)
						x -= metrics[textCharacters[i]];
				}
				else // não há espaço em branco na linha, o que indica que apenas uma palavra extrapolou toda a largura da linha; é necessário cortar a palavra ao meio
					newLineIndexesSet.put(new Integer(i), new Integer(i));

				break;
			}
		}
		
		if (!mustLineWrap() && x > getWidth()) {
			setSize(x, getHeight());
		}

		return i + 1;
	}

	/**
	 * Returns if the line must wraps when it reaches the width of the element.
	 */
	public boolean mustLineWrap() {
		return lineWrap;
	}

	/**
	 * Sets if the line must wraps when it reaches the width of the element.
	 */
	public void setLineWrap(boolean lineWrap) {
		this.lineWrap = lineWrap;
	}

}
