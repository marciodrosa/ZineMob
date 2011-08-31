package com.sinosarts.zinemob.drawableelement;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.Image;

/**
 * Fonte de caracteres bitmap utilizados no RenderableText.
 */
public class RenderableTextFont {
	
	private byte metrics[] = new byte[256]; // largura de cada caractere
	private Image fontImage;

	private boolean useFontImageColor = true;
	private int fontColor = 0xff000000;
	
	/**
	 * Cria um RenderableTextFont, carregando os dados atrav�s dos resources
	 * especificados nos par�metros.
	 * @param resource o nome do resource que cont�m a imagem dos caracteres
	 * @param resourceMetrics o nome do resource que cont�m o espa�amento, em
	 * pixels, de cada um dos 256 caracteres suportados (cada byte equivale a um
	 * caractere)
	 */
	public RenderableTextFont (String resource, String resourceMetrics) {

		// carrega as m�tricas
		try {
			InputStream is = getClass().getResourceAsStream(resourceMetrics);
			if (is == null) {
				System.out.println ("Font metrics " + resourceMetrics + " not found.");
				return;
			}
			DataInputStream in = new DataInputStream (is);
			in.read(metrics);
		}
		catch (IOException e) {
			System.out.println("Error while reading font metrics " + resourceMetrics + ": " + e.toString());
			e.printStackTrace();
		}

		// carrega a imagem:
		try {
			fontImage = Image.createImage(resource);
		}
		catch (IOException e) {
			System.out.println("Error while reading font image " + resource + ": " + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Construtor.
	 * @param fontImage a imagem utilizada pela fonte, n�o pode ser null
	 * @param fontMetrics o array de m�tricas da fonte, deve ter um tamanho m�nimo
	 * de 256 bytes
	 */
	public RenderableTextFont(Image fontImage, byte[] fontMetrics) {

		if (fontImage == null)
			throw new IllegalArgumentException("Font image can't be null.");
		if (fontMetrics == null)
			throw new IllegalArgumentException("Font metrics can't be null.");
		else if (fontMetrics.length < 256)
			throw new IllegalArgumentException("Font metrics array length must be greater or equal to 256.");

		this.fontImage = fontImage;
		this.metrics = fontMetrics;
	}

	/**
	 * Define uma cor para a fonte no formato AARRGGBB. Esta cor s� � efetivamente
	 * utilizada se o RenderableText for pr�-processado.
	 * @param color a cor da fonte
	 */
	public void setFontColor(int color) {
		fontColor = color;
		useFontImageColor = false;
	}

	/**
	 * Retorna a cor da fonte especificada em setFontColor.
	 * @return a cor da fonte
	 */
	public int getFontColor() {
		return fontColor;
	}

	/**
	 * Define que a cor da fonte ser� a cor da imagem com os caracteres.
	 * @param useFontImageColor true para usar as cores da imagem, false para
	 * utilizar a cor definida em setFontColor
	 */
	public void setUsingFontImageColor(boolean useFontImageColor) {
		this.useFontImageColor = useFontImageColor;
	}

	/**
	 * Retorna se o texto que utiliza esta fonte � renderizado utilizando as cores
	 * da imagem da fonte ou a cor definida em setFontColor.
	 * @return true se o texto que utiliza esta fonte � renderizado utilizando
	 * as cores da imagem da fonte ou false se � utilizada a a cor definida em
	 * setFontColor
	 */
	public boolean isUsingFontImageColor() {
		return useFontImageColor;
	}

	/**
	 * Retorna a imagem utilizada pela fonte.
	 * @return a imagem utilizada pela fonte
	 */
	public Image getFontImage() {
		return fontImage;
	}

	/**
	 * Retorna as m�tricas dos caracteres.
	 * @return array de 256 posi��es, cada qual com o espa�amento, em pixels, de
	 * cada caractere
	 */
	public byte[] getFontMetrics() {
		return metrics;
	}

}

/*
 * Vers�o 01.07: novos contrutores, melhor tratamento de erro e op��o de alterar
 * a cor da fonte (post ZM_20100425_4)
 */
