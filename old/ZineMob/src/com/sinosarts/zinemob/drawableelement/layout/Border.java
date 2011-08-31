package com.sinosarts.zinemob.drawableelement.layout;

/**
 * Borda utilizada para adicionar itens ao layout de um LayoutElement.
 */
public class Border {

	private int left, right, top, bottom;

	/**
	 * Construtor que define um tamanho para as quatro bordas.
	 * @param size o tamanho para a borda da esquerda, da direita, de cima e de
	 * baixo
	 */
	public Border(int size) {
		this.left = size;
		this.right = size;
		this.top = size;
		this.bottom = size;
	}

	/**
	 * Construtor que define um tamanho para cada borda.
	 * @param left o tamanho da borda esquerda
	 * @param right o tamanho da borda direita
	 * @param top o tamanho da borda superior
	 * @param bottom o tamanho da borda inferior
	 */
	public Border(int left, int right, int top, int bottom) {
		this.left = left;
		this.right = right;
		this.top = top;
		this.bottom = bottom;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

}

/*
 * Versão 01.04: criação da classe (ZM_20100417)
 */
