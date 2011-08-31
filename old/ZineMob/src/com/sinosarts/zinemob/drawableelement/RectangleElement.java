package com.sinosarts.zinemob.drawableelement;

import javax.microedition.lcdui.Graphics;

/**
 * Elemento que desenha um retângulo na tela.
 */
public class RectangleElement extends DrawableElement
{
	public static final byte MODE_FILL = 0;
	public static final byte MODE_WIRE = 1;
	public static final byte MODE_FILL_AND_WIRE = 2;

	private int color, wireColor;
	private int corner = 0;
	private byte mode = MODE_FILL;
	private int wireSize = 1;

	/**
	 * Cria um novo retângulo preto e de tamanho (1,1).
	 */
	public RectangleElement() {
		super();
		setSize(1, 1);
	}

	/**
	 * Cria um novo retângulo.
	 * @param x a posição X inicial
	 * @param y a posição Y inicial
	 * @param width a largura inicial
	 * @param height a altura inicial
	 * @param color a cor do retângulo
	 */
	public RectangleElement (int x, int y, int width, int height, int color){
		super();
		setPosition(x, y);
		setSize(width, height);
		this.color = color;
	}

	/**
	 * Retorna a cor do retângulo.
	 * @return a cor do retângulo
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Define a cor do retângulo.
	 * @param color a cor do retângulo
	 */
	public void setColor (int color) {
		this.color = color;
	}

	/**
	 * Retorna a cor de desenho do contorno.
	 * @return a cor de desenho do contorno
	 */
	public int getWireColor() {
		return wireColor;
	}

	/**
	 * Define a cor de desenho do contorno.
	 * @param color a cor de desenho do contorno
	 */
	public void setWireColor (int color) {
		this.wireColor = color;
	}

	/**
	 * Retorna o modo de desenho do retângulo (ver setDrawMode).
	 * @return o modo de desenho do retângulo
	 */
	public byte getDrawMode() {
		return mode;
	}
	
	/**
	 * Define o modo de desenho do retângulo.
	 * @param mode o modo de desenho do retângulo (MODE_FILL para ser um retângulo
	 * que preeenche a área com a sua cor, MODE_WIRE para desenhar apenas o contorno
	 * ou MODE_FILL_AND_WIRE para preencher a área E desenhar o contorno)
	 */
	public void setDrawMode (byte mode) {
		this.mode = mode;
	}

	/**
	 * Retorna a quantidade de pixels do canto arredondado do retângulo.
	 * @return a quantidade de pixels do canto arredondado do retângulo, 0 se não
	 * houver canto arredondado
	 */
	public int getRoundedCorner() {
		return corner;
	}

	/**
	 * Define se o retângulo tem cantos arredondados.
	 * @param corner a quantidade de pixels do tamanho da área arredondada (0 para
	 * não haver arredondamento)
	 */
	public void setRoundedCorner (int corner) {
		this.corner = corner;
	}
	
	protected void drawElement (Graphics graphics)
	{
		int w = getWidth();
		int h = getHeight();
		
		if (mode == MODE_WIRE) {
			graphics.setColor(wireColor);
			for (int i = 0; i < wireSize; i++) {
				if (corner == 0)
					graphics.drawRect(i, i, w-i, h-i);
				else
					graphics.drawRoundRect(i, i, w-i, h-i, corner, corner);
			}
		}
		else if (mode == MODE_FILL) {
			graphics.setColor(color);
			if (corner == 0)
				graphics.fillRect(0, 0, w, h);
			else
				graphics.fillRoundRect(0, 0, w, h, corner, corner);
		}
		else if (mode == MODE_FILL_AND_WIRE) {
			graphics.setColor(wireColor);
			if (corner == 0)
				graphics.fillRect(0, 0, w, h);
			else
				graphics.fillRoundRect(0, 0, w, h, corner, corner);

			graphics.setColor(color);
			if (corner == 0)
				graphics.fillRect(wireSize, wireSize, w-(wireSize*2), h-(wireSize*2));
			else
				graphics.fillRoundRect(wireSize, wireSize, w-(wireSize*2), h-(wireSize*2), corner, corner);
		}

		super.drawElement(graphics);
	}
	
}

/*
 * Versão 01.05: métodos setMode e getMode renomeados para setDrawMode e getDrawMode
 * 
 * Versão 01.04: removidos métodos setSize, getWidth e getHeight (post ZM_20100417)
 * 
 * Versão 01.01: alteração no modo de desenho do retângulo, corrigindo falhas
 * encontradas no modo MODE_FILL_AND_WIRE quando utiliza-se bordas arredondadas
 * (post MT_20100403_2)
 */
