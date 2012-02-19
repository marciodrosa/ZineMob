package com.zine.zinemob.drawableelement.text;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.i18n.I18n;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * A text that is rendered using an image as font.
 */
public class ImageTextElement extends DrawableElement {
	
	public static final int NO_TYPE_WRITER_EFFECT = -1;
	
	private static boolean translateNewObjectsWithI18n = true;
	private int lineWidth = 0;
	
	/**
	 * Sets the default configuration for translate the texts. By default,
	 * new objects are configured to use the I18n class to translate the texts.
	 * So, you can use the format "$key" to translate the value or use a literal
	 * String (without $ at first character).
	 */
	public static boolean translateNewObjectsWithI18n() {
		return translateNewObjectsWithI18n;
	}

	/**
	 * Sets the default configuration for translate the texts. By default,
	 * new objects are configured to use the I18n class to translate the texts.
	 * So, you can use the format "$key" to translate the value or use a literal
	 * String (without $ at first character).
	 */
	public static void setTranslateNewObjectsWithI18n(boolean aTranslateWithI18n) {
		translateNewObjectsWithI18n = aTranslateWithI18n;
	}
	
	private boolean translateWithI18n = translateNewObjectsWithI18n;
	private String untranslatedText;
	private char[] textCharacters;
	private ImageTextElementFont font;
	private boolean lineWrap = false;
	private int typeWriterEffectSpeed = NO_TYPE_WRITER_EFFECT;
	private int currentTextLength = 0;
	
	private byte textAlign = TEXT_ALIGN_LEFT;
	public static final byte TEXT_ALIGN_LEFT = 0;
	public static final byte TEXT_ALIGN_CENTER = 1;

	private Hashtable newLineIndexesSet = new Hashtable();
	private Vector linesWidth = new Vector();
	
	public ImageTextElement(String text, ImageTextElementFont font) {
		this(text, font, false);
	}

	public ImageTextElement(String text, ImageTextElementFont font, boolean lineWrap) {
		this(text, font, lineWrap, translateNewObjectsWithI18n);
	}
	
	public ImageTextElement(String text, ImageTextElementFont font, boolean lineWrap, boolean translateWithI18n) {
		this.font = font;
		this.lineWrap = lineWrap;
		setText(text, translateWithI18n);
	}

	/**
	 * This method sets only the width, the height is automatically calculated by the text content
	 * and font.
	 */
	public synchronized void setSize(int w, int h) {
		if (lineWrap) {
			preProcessText(w);
		} else {
			super.setSize(w, getHeight());
		}
	}
	
	/**
	 * Enables the typewriter effect.
	 * @param speed the speed of the effect, or NO_TYPE_WRITER_EFFECT to disable
	 * the effect.
	 */
	public final void setTypeWriterEffect(int speed)
	{
		typeWriterEffectSpeed = speed;
		currentTextLength = 0;
	}
	
	/**
	 * Returns the text as an array of characteres.
	 */
	public final char[] getCharacters() {
		return textCharacters;
	}
	
	/**
	 * Returns the text.
	 */
	public final String getText() {
		return new String(textCharacters);
	}
	
	/**
	 * Sets the text.
	 */
	public final void setText(String text) {
		setText(text, translateWithI18n);
	}
	
	/**
	 * Sets the text.
	 * @param text the text
	 * @param translateWithI18n true to call I18n.translate to translate the text
	 */
	public final void setText(String text, boolean translateWithI18n) {
		this.translateWithI18n = translateWithI18n;
		this.untranslatedText = text;
		if (translateWithI18n) {
			text = I18n.translate(text);
		}
		textCharacters = text.toCharArray();
		preProcessText(getWidth());
	}
	
	/**
	 * Updates the current text if it can be translated with I18n class and
	 */
	public final void updateTranslation() {
		setText(untranslatedText);
	}
	
	/**
	 * Returns if the text must be translated using I18n class. It can be setted
	 * on constructor, on setText method or setting by the static method setTranslateNewObjectsWithI18n.
	 */
	public final boolean mustTranslateWithI18n() {
		return translateWithI18n;
	}
	
	/**
	 * Returns the font.
	 */
	public final ImageTextElementFont getFont() {
		return font;
	}
	
	/**
	 * Sets the font.
	 */
	public final void setFont(ImageTextElementFont font) {
		this.font = font;
		preProcessText(getWidth());
	}

	protected void drawElement(Graphics graphics) {
		
		int[] metrics = font.getMetrics();
		Image fontImage = font.getImage();
		
		int posCursor = getInitialCursorPositionByLine(0);
		int posLine = 0, lineCount = 0;
		char character = 0;
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
			if (character > 255) {
				continue;
			}

			if (newLineIndexesSet.contains(new Integer(i)) || character == '\n') {
				posLine += areaY;
				lineCount++;
				posCursor = getInitialCursorPositionByLine(lineCount);
			}

			int characterWidth = metrics[character];
			int characterHeight = areaY;

			graphics.drawRegion(fontImage, (character%16)*areaX,
					(character/16)*areaY, characterWidth, characterHeight, 0,
					posCursor, posLine, 0);
			
			posCursor += metrics[character];
		}
	}
	
	private int getInitialCursorPositionByLine(int line) {
		if (textAlign == TEXT_ALIGN_CENTER) {
			int currentLineWidth = ((Integer)linesWidth.elementAt(line)).intValue();
			return (getWidth() - currentLineWidth) / 2;
		} else {
			return 0;
		}
	}

	/**
	 * Pre processes the text, calculating the width, height and line wrapers.
	 */
	private synchronized void preProcessText(int maxWidth) {
		int height = 0;
		this.lineWidth = maxWidth;
		newLineIndexesSet = new Hashtable();
		linesWidth = new Vector();

		int areaY = font.getImage().getHeight()/16;

		for (int i = 0; i < textCharacters.length; i = processLine(i)) {
			height += areaY;
		}
		if (lineWidth < maxWidth) {
			lineWidth = maxWidth;
		}
		
		super.setSize(this.lineWidth, height);
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

			if (textCharacters[i] == ' ' || textCharacters[i] == '	') {
				lastBlankSpaceIndex = i;
			} else if (textCharacters[i] == '\n') {
				break;
			}

			if (lineWrap && x > lineWidth) { // extrapolou a largura máxima, volta até o último espaço em branco, onde será posto uma nova linha artificial
				if (lastBlankSpaceIndex > 0) {
					newLineIndexesSet.put(new Integer(lastBlankSpaceIndex), new Integer(lastBlankSpaceIndex));

					// volta os caracteres percorridos, diminuindo a variável X:
					for (; i>lastBlankSpaceIndex; i--) {
						x -= metrics[textCharacters[i]];
					}
				}
				else {// não há espaço em branco na linha, o que indica que apenas uma palavra extrapolou toda a largura da linha; é necessário cortar a palavra ao meio
					newLineIndexesSet.put(new Integer(i), new Integer(i));
				}

				break;
			}
		}
		
		if (!lineWrap && x > lineWidth) {
			lineWidth = x;
		}
		
		linesWidth.addElement(new Integer(x));

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

	/**
	 * Returns the text alignment.
	 */
	public byte getTextAlign() {
		return textAlign;
	}

	/**
	 * Sets the text alignment (TEXT_ALIGN_LEFT or TEXT_ALIGN_CENTER).
	 */
	public void setTextAlign(byte textAlign) {
		this.textAlign = textAlign;
	}

}
