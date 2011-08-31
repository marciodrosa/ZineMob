package com.zine.zinemob.drawableelement;

/**
 * Classe que armazena informações de cor e permite acessar individualmente informações
 * de cada componente.
 */
public class Color {

	private int components;

	/**
	 * Construtor.
	 * @param components componentes da cor no formato 0xAARRGGBB
	 */
	public Color(int components) {
		setComponents(components);
	}

	/**
	 * Construtor.
	 * @param a o componente A
	 * @param r o componente R
	 * @param g o componente G
	 * @param b o componente B
	 */
	public Color(int a, int r, int g, int b) {
		setComponents(a, r, g, b);
	}

	/**
	 * Define os componentes da cor.
	 * @param components componentes da cor no formato 0xAARRGGBB
	 */
	public void setComponents(int components) {
		this.components = components;
	}

	/**
	 * Define os componentes da cor.
	 * @param a o componente A
	 * @param r o componente R
	 * @param g o componente G
	 * @param b o componente B
	 */
	public void setComponents(int a, int r, int g, int b) {
		components = (a << 24) | (r << 16) | (g << 8) | b;
	}

	/**
	 * Retorna os componentes da cor no formato 0xAARRGGBB.
	 * @return os componentes da cor no formato 0xAARRGGBB
	 */
	public int getComponents() {
		return components;
	}

	/**
	 * Retorna o componente vermelho da cor.
	 * @return o componente vermelho da cor
	 */
	public int getRedComponent() {
		return (components >> 16) & 0x000000ff;
	}

	/**
	 * Retorna o componente verde da cor.
	 * @return o componente verde da cor
	 */
	public int getGreenComponent() {
		return (components >> 8) & 0x000000ff;
	}

	/**
	 * Retorna o componente azul da cor.
	 * @return o componente azul da cor
	 */
	public int getBlueComponent() {
		return components & 0x000000ff;
	}

	/**
	 * Retorna o componente alfa da cor.
	 * @return o componente alfa da cor
	 */
	public int getAlphaComponent() {
		return (components >> 24) & 0x000000ff;
	}

}
