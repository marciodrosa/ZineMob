package com.sinosarts.zinemob.drawableelement;

import com.sinosarts.zinemob.drawableelement.layout.Border;
import com.sinosarts.zinemob.drawableelement.layout.LayoutElement;
import javax.microedition.lcdui.Graphics;

/**
 * Ret�ngulo que possui outro ret�ngulo no seu interior para passar a impress�o
 * de uma superf�cie reflexiva. Quando a cor do ret�ngulo � definida, a cor do
 * reflexo � automaticamente alterada tamb�m, sendo que esta cor ser� a cor do
 * ret�ngulo somada ao �ndice de reflex�o definido.
 *
 * Para alterar apenas a cor do ret�ngulo OU apenas a cor do reflexo, deve-se
 * utilizar os m�todos setSolidColor ou setReflectionColor.
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
	 * @param x a coordenada X da posi��o do elemento
	 * @param y a coordenada Y da posi��o do elemento
	 * @param width a largura do ret�ngulo
	 * @param height a altura do ret�ngulo
	 * @param color a cor da superf�cie
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
	 * Define a quantidade de pixels entre o reflexo e a borda do ret�ngulo. Por
	 * padr�o h� 1 pixel de dist�ncia.
	 * @param distance a quantidade de pixels entre o reflexo e a borda do ret�ngulo
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
	 * Retorna a quantidade de pixels entre o reflexo e a borda do ret�ngulo. Por
	 * padr�o h� 1 pixel de dist�ncia.
	 * @return a quantidade de pixels entre o reflexo e a borda do ret�ngulo
	 */
	public int getReflectionDistanceFromBorder() {
		// retorna qualquer lado da borda, pois s�o sempre iguais (menos a de baixo):
		return getReflectionBorder().getTop();
	}

	/**
	 * Define a cor do ret�ngulo. A cor do reflexo tamb�m � automaticamente alterada,
	 * somando ao valor da cor do ret�ngulo o �ndice de reflex�o.
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
	 * Define a cor s�lida do ret�ngulo. Apenas a cor do ret�ngulo � alterada,
	 * a cor do reflexo permanece a mesma.
	 * @param color a cor da parte s�lida do ret�ngulo
	 */
	public void setSolidColor(int color) {
		super.setColor(color);
	}

	/**
	 * Retorna a cor s�lida do ret�ngulo. Este m�todo retorna o mesmo que getColor.
	 * @return a cor da parte s�lida do ret�ngulo
	 */
	public int getSolidColor() {
		return getColor();
	}

	/**
	 * Define a cor da parte reflexiva do ret�ngulo. Esta cor pode ser automaticamente
	 * definida se o m�todo setColor for utilizado.
	 * @param color a cor da parte reflexiva do ret�ngulo
	 */
	public void setReflectionColor(int color) {
		getReflectionRectangle().setColor(color);
	}

	/**
	 * Retorna a cor da parte reflexiva do ret�ngulo.
	 * @return a cor da parte reflexiva do ret�ngulo
	 */
	public int getReflectionColor() {
		return getReflectionRectangle().getColor();
	}

	/**
	 * Retorna o �ndice de reflex�o do ret�ngulo, definido em setReflectionIndex.
	 * Quando a cor do ret�ngulo � definida em setColor, a cor da parte reflexiva
	 * ser� esta mesma cor somada ao �ndice de reflex�o.
	 * @return o �ndice de reflex�o do ret�ngulo
	 */
	public int getReflectionIndex() {
		return reflectionIndex;
	}

	/**
	 * Define o �ndice de reflex�o do ret�ngulo. Quando a cor do ret�ngulo �
	 * definida em setColor, a cor da parte reflexiva ser� esta mesma cor somada
	 * ao �ndice de reflex�o.
	 * @param reflectionIndex o �ndice de reflex�o do ret�ngulo
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
 * Vers�o 01.05: cria��o da classe (post ZM_20100418)
 */
