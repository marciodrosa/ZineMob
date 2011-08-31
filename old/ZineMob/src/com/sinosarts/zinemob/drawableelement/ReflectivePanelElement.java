package com.sinosarts.zinemob.drawableelement;

import com.sinosarts.zinemob.drawableelement.layout.Border;
import com.sinosarts.zinemob.drawableelement.layout.LayoutElement;
import javax.microedition.lcdui.Graphics;

/**
 * Retângulo que possui outro retângulo no seu interior para passar a impressão
 * de uma superfície reflexiva. Quando a cor do retângulo é definida, a cor do
 * reflexo á automaticamente alterada também, sendo que esta cor será a cor do
 * retângulo somada ao índice de reflexão definido.
 *
 * Para alterar apenas a cor do retângulo OU apenas a cor do reflexo, deve-se
 * utilizar os métodos setSolidColor ou setReflectionColor.
 */
public class ReflectivePanelElement extends RectangleElement {

	private int reflectionIndex = 40;
	private RectangleElement reflectionRectangle;
	private Border reflectionBorder;
	private LayoutElement layout;

	/**
	 * Construtor.
	 */
	public ReflectivePanelElement() {
		super();
		init();
	}

	/**
	 * Construtor.
	 * @param x a coordenada X da posição do elemento
	 * @param y a coordenada Y da posição do elemento
	 * @param width a largura do retângulo
	 * @param height a altura do retângulo
	 * @param color a cor da superfície
	 */
	public ReflectivePanelElement (int x, int y, int width, int height, int color){
		super(x, y, width, height, color);
		init();
	}

	private void init() {
		LayoutElement l = getLayout();
		l.setAutoFitToElements(false);
		l.addToLayout(getReflectionRectangle(), getReflectionBorder(), LayoutElement.STRETCH, true);
		l.refresh();

		updateReflectionColor();
	}

	public void setSize(int w, int h) {
		super.setSize(w, h);

		LayoutElement l = getLayout();
		l.setSize(w, h/2);
		l.refresh();
	}

	public void setRoundedCorner (int roundedCorner) {
		super.setRoundedCorner(roundedCorner);

		int reflectionRoundedCorner = roundedCorner - getReflectionDistanceFromBorder();
		if (reflectionRoundedCorner < 0)
			reflectionRoundedCorner = 0;

		getReflectionRectangle().setRoundedCorner(reflectionRoundedCorner);
	}

	/**
	 * Define a quantidade de pixels entre o reflexo e a borda do retângulo. Por
	 * padrão há 1 pixel de distância.
	 * @param distance a quantidade de pixels entre o reflexo e a borda do retângulo
	 */
	public void setReflectionDistanceFromBorder(int distance) {
		Border b = getReflectionBorder();
		b.setBottom(0);
		b.setLeft(distance);
		b.setRight(distance);
		b.setTop(distance);
		
		getLayout().refresh();
	}

	/**
	 * Retorna a quantidade de pixels entre o reflexo e a borda do retângulo. Por
	 * padrão há 1 pixel de distância.
	 * @return a quantidade de pixels entre o reflexo e a borda do retângulo
	 */
	public int getReflectionDistanceFromBorder() {
		// retorna qualquer lado da borda, pois são sempre iguais (menos a de baixo):
		return getReflectionBorder().getTop();
	}

	/**
	 * Define a cor do retângulo. A cor do reflexo também é automaticamente alterada,
	 * somando ao valor da cor do retângulo o índice de reflexão.
	 * @param color a cor a ser definida
	 */
	public void setColor(int color) {
		super.setColor(color);
		updateReflectionColor();
	}

	private void updateReflectionColor() {
		Color reflectionColor = new Color(getColor());

		int r = reflectionColor.getRedComponent() + reflectionIndex;
		int g = reflectionColor.getGreenComponent() + reflectionIndex;
		int b = reflectionColor.getBlueComponent() + reflectionIndex;

		if (r < 0)
			r = 0;
		else if (r > 255)
			r = 255;
		if (g < 0)
			g = 0;
		else if (g > 255)
			g = 255;
		if (b < 0)
			b = 0;
		else if (b > 255)
			b = 255;

		reflectionColor.setComponents(r, g, b, 255);
		setReflectionColor(reflectionColor.getComponents());
	}

	/**
	 * Define a cor sólida do retângulo. Apenas a cor do retângulo é alterada,
	 * a cor do reflexo permanece a mesma.
	 * @param color a cor da parte sólida do retângulo
	 */
	public void setSolidColor(int color) {
		super.setColor(color);
	}

	/**
	 * Retorna a cor sólida do retângulo. Este método retorna o mesmo que getColor.
	 * @return a cor da parte sólida do retângulo
	 */
	public int getSolidColor() {
		return getColor();
	}

	/**
	 * Define a cor da parte reflexiva do retângulo. Esta cor pode ser automaticamente
	 * definida se o método setColor for utilizado.
	 * @param color a cor da parte reflexiva do retângulo
	 */
	public void setReflectionColor(int color) {
		getReflectionRectangle().setColor(color);
	}

	/**
	 * Retorna a cor da parte reflexiva do retângulo.
	 * @return a cor da parte reflexiva do retângulo
	 */
	public int getReflectionColor() {
		return getReflectionRectangle().getColor();
	}

	/**
	 * Retorna o índice de reflexão do retângulo, definido em setReflectionIndex.
	 * Quando a cor do retângulo é definida em setColor, a cor da parte reflexiva
	 * será esta mesma cor somada ao índice de reflexão.
	 * @return o índice de reflexão do retângulo
	 */
	public int getReflectionIndex() {
		return reflectionIndex;
	}

	/**
	 * Define o índice de reflexão do retângulo. Quando a cor do retângulo é
	 * definida em setColor, a cor da parte reflexiva será esta mesma cor somada
	 * ao índice de reflexão.
	 * @param reflectionIndex o índice de reflexão do retângulo
	 */
	public void setReflectionIndex(int reflectionIndex) {
		this.reflectionIndex = reflectionIndex;
		updateReflectionColor();
	}

	private RectangleElement getReflectionRectangle() {
		if (reflectionRectangle == null)
			reflectionRectangle = new RectangleElement();
		return reflectionRectangle;
	}

	private LayoutElement getLayout() {
		if (layout == null) {
			layout = new LayoutElement();
			layout.setAutoFitToElements(false);
		}
		return layout;
	}

	private Border getReflectionBorder() {
		if (reflectionBorder == null)
			reflectionBorder = new Border(1, 1, 1, 0);
		return reflectionBorder;
	}

	protected void drawElement(Graphics graphics) {

		super.drawElement(graphics);
		LayoutElement layoutElement = getLayout();
		layoutElement.draw(graphics);

	}

}

/*
 * Versão 01.05: criação da classe (post ZM_20100418)
 */
