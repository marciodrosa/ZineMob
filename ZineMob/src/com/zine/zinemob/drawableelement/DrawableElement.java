package com.zine.zinemob.drawableelement;

import com.zine.zinemob.drawableelement.layoutfixer.LayoutFixer;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;

/**
 * Classe principal para elementos que possuem habilidade de desenho.
 */
public class DrawableElement
{
	private Vector children = null;
	private DrawableElement parent = null;
	private int x, y, pivotX, pivotY, childrenViewPositionX, childrenViewPositionY, width, height;
	private int paddingLeft, paddingTop, paddingRight, paddingBottom;
	private int marginLeft, marginTop, marginRight, marginBottom;
	private boolean visible = true;

	private Vector areaFixers = null;
	private LayoutFixer areaFixerExecuting;

	private String name = "";

	/**
	 * Construtor padrão.
	 */
	public DrawableElement() {
	}

	/**
	 * Retorna o elemento pai deste.
	 * @return o elemento pai deste, pode ser null se não houver
	 */
	public DrawableElement getParent() {
		return parent;
	}

	/**
	 * Retorna a quantidade de elementos filhos.
	 * @return a quantidade de elementos filhos
	 */
	public int getChildrenCount() {
		int count = 0;
		if(children != null) {
			count = children.size();
		}
		return count;
	}

	/**
	 * Retorna o elemento filho de acordo com o índice. Se o índice for inválido
	 * (extrapolar a quantidade de filhos que o elemento realmente possui ou for
	 * menor que 0), então uma exceção ArrayIndexOutOfBoundsException é lançada.
	 * @param index o índice do elemento
	 * @return o elemento correspondente ao índice
	 */
	public DrawableElement getChild(int index) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			return (DrawableElement)children.elementAt(index);
		}
	}

	/**
	 * Desenha o RenderElement e os seus filhos. Se o elemento estiver invisível,
	 * nem o elemento e nem os seus filhos são desenhados.
	 * @param graphics o contexto gráfico onde o elemento será desenhado
	 */
	public void draw(Graphics graphics)
	{
		if(isVisible()) {
			int translationX = getX();
			int translationY = getY();
			int pivotTranslationX = getPivotX();
			int pivotTranslationY = getPivotY();

			graphics.translate(translationX - pivotTranslationX, translationY - pivotTranslationY);
			drawElement(graphics);
			graphics.translate(pivotTranslationX, pivotTranslationY);

			drawChildren(graphics);

			graphics.translate(-translationX, -translationY);
		}
	}

	/**
	 * Desenha os filhos deste elemento. Este método é chamado pelo método draw
	 * após o próprio elemento ser desenhado através do método drawElement.
	 * @param graphics o contexto gráfico onde os elementos serão desenhados
	 */
	protected void drawChildren(Graphics graphics)
	{
		int translationX = -getChildrenViewPositionX() + getPivotX();
		int translationY = -getChildrenViewPositionY() + getPivotY();

		graphics.translate(translationX, translationY);

		if (children != null) {
			for (int i=0; i<children.size(); i++) {
				((DrawableElement)children.elementAt(i)).draw(graphics);
			}
		}

		graphics.translate(-translationX, -translationY);
	}

	/**
	 * Método responsável pelo desenho deste elemento apenas, sem os seus filhos.
	 * É chamado pelo método draw antes da chamada do método drawChildren. Esta
	 * classe não faz nada por padrão. O desenho das classes derivadas deve ser
	 * implementado aqui.
	 * @param graphics o contexto gráfico onde o elemento será desenhado
	 */
	protected void drawElement(Graphics graphics) {
	}

	/**
	 * Define o tamanho do elemento.
	 * @param w a largura do elemento
	 * @param h a altura do elemento
	 */
	public void setSize(int w, int h) {
		width = w;
		height = h;

		notifyAreaFixersOnSizeChanged();
	}

	/**
	 * Retorna a largura do elemento.
	 * @return a largura do elemento
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Retorna a altura do elemento.
	 * @return a altura do elemento
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Posiciona o elemento na coordenada x e y. Se o elemento tiver pai, esta posição
	 * é relativa à posição do pai.
	 * @param x a coordenada x
	 * @param y a coordenada y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;

		notifyAreaFixersOnPositionChanged();
	}

	/**
	 * Retorna a coordenada x do elemento. Se o elemento tiver pai, esta posição
	 * é relativa à posição do pai.
	 * @return a coordenada x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retorna a coordenada y do elemento. Se o elemento tiver pai, esta posição
	 * é relativa à posição do pai.
	 * @return a coordenada y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Posiciona o elemento na coordenada x e y, globalmente (não é em relação ao
	 * pai). Este método não de tão veloz execução quanto o método setPosition,
	 * pois a posição global deve ser calculada.
	 * @param x a coordenada x
	 * @param y a coordenada y
	 */
	public void setGlobalPosition (int x, int y) {
		if (parent == null) {
			setPosition(x, y);
		}
		else {
			setPosition(x - parent.getGlobalX() + parent.getChildrenViewPositionX() + parent.getPivotX(),
					y - parent.getGlobalY() + parent.getChildrenViewPositionY() + parent.getPivotY());
		}
	}

	/**
	 * Retorna a coordenada x global do elemento (não é relativa ao pai). Este
	 * método não é de tão rápida execução quanto o método getX, pois é necessário
	 * varrer toda a hierarquia para calcular a posição global do elemento.
	 * @return a coordenada x
	 */
	public int getGlobalX() {
		if (parent == null) {
			return getX();
		}
		else {
			return parent.getGlobalX() - parent.getChildrenViewPositionX() + parent.getPivotX() + getX();
		}
	}

	/**
	 * Retorna a coordenada y global do elemento (não é relativa ao pai). Este
	 * método não é de tão rápida execução quanto o método getY, pois é necessário
	 * varrer toda a hierarquia para calcular a posição global do elemento.
	 * @return a coordenada y
	 */
	public int getGlobalY() {
		if (parent == null) {
			return getY();
		}
		else {
			return parent.getGlobalY() - parent.getChildrenViewPositionY() + parent.getPivotY() + getY();
		}
	}
	
	/**
	 * Define o pivo do objeto, isto é, a coordenada X e Y da origem do elemento.
	 * Por padrão, é (0, 0), o que significa que a origem do elemento é o canto
	 * superior esquerdo.
	 * @param x a coordenada X do pivo
	 * @param y a coordenada Y do pivo
	 */
	public void setPivot(int x, int y) {
		this.pivotX = x;
		this.pivotY = y;

		notifyAreaFixersOnPositionChanged();
	}

	/**
	 * Centraliza o pivo do objeto, de acordo com sua largura e altura. Por padrão,
	 * o pivo é inicializado no canto superior esquerdo (coordenadas 0, 0).
	 */
	public void centerPivot() {
		setPivot(getWidth() / 2, getHeight() / 2);
	}

	/**
	 * Retorna a coordenada X do pivo do objeto.
	 * @return a coordenada X do pivo do objeto
	 */
	public int getPivotX() {
		return pivotX;
	}

	/**
	 * Retorna a coordenada Y do pivo do objeto.
	 * @return a coordenada Y do pivo do objeto
	 */
	public int getPivotY() {
		return pivotY;
	}

	/**
	 * Define a posição onde os filhos deste elemento são desenhados. Esta posição
	 * é relativa a este elemento. Por padrão, X e Y são 0.
	 * @param x a coordenada X da posição
	 * @param y a coordenada Y da posição
	 */
	public void setChildrenViewPosition(int x, int y) {
		this.childrenViewPositionX = x;
		this.childrenViewPositionY = y;

		notifyAreaFixersOnPositionChanged();
	}

	/**
	 * Retorna a coordenada X da posição onde os filhos são desenhados.
	 * @return a coordenada X da posição onde os filhos são desenhados
	 */
	public int getChildrenViewPositionX() {
		return this.childrenViewPositionX;
	}

	/**
	 * Retorna a coordenada Y da posição onde os filhos são desenhados.
	 * @return a coordenada Y da posição onde os filhos são desenhados
	 */
	public int getChildrenViewPositionY() {
		return this.childrenViewPositionY;
	}

	/**
	 * Adiciona um elemento filho. Não faz nada se já for seu filho. Se o elemento
	 * já possuir pai, este pai deixará de ter o elemento como filho.
	 * @param child o elemento filho
	 */
	public void addChild (DrawableElement child) {

		if (child.parent == this) {
			return;
		}

		if (child.parent != null) {
			child.parent.removeChild(child);
		}

		if (children == null) {
			children = new Vector();
		}
		
		children.addElement(child);
		child.parent = this;

		notifyAreaFixersOnChildAdded(child);
	}
	
	/**
	 * Adiciona um elemento filho. Se o elemento já possuir pai, este pai deixará
	 * de ter o elemento como filho. Se o pai for este elemento, o objeto primeiramente
	 * será removido da hierarquia para depois ser posto novamente, obedecendo o
	 * índice do parâmetro.
	 * @param child o elemento filho
	 * @param index a ordenação deste elemento em relação aos demais filhos que
	 * este element contém. Se for menor que 0, o índice será considerado como 0.
	 * Se for maior que quantidade de filhos atual, então o elemento será colocado
	 * na última posição.
	 */
	public void addChild (DrawableElement child, int index) {

		if (child.parent != null) {
			child.parent.removeChild(child);
		}

		if (children == null) {
			children = new Vector();
		}

		if (index < 0) {
			index = 0;
		}
		else if (index > children.size()) {
			index = children.size();
		}
		
		children.insertElementAt (child, index);
		child.parent = this;

		notifyAreaFixersOnChildAdded(child);
	}
	
	/**
	 * Remove o elemento filho.
	 * @param child o elemento filho a ser removido
	 */
	public void removeChild (DrawableElement child) {
		if (children != null) {
			if (children.removeElement(child)) {
				if (children.isEmpty()) {
					children = null;
				}
				child.parent = null;

				notifyAreaFixersOnChildRemoved(child);
			}
		}
	}
	
	/**
	 * Remove todos os elementos filhos.
	 */
	public void removeChildren() {
		if(children != null) {
			for (int i=0; i<children.size(); i++) {

				DrawableElement child = (DrawableElement) children.elementAt(i);
				
				child.parent = null;

				notifyAreaFixersOnChildRemoved(child);
			}
		}
		children = null;
	}
	
	/**
	 * Define que o elemento não deve ser renderizado, assim como seus filhos.
	 * @param visible true para desligar o desenho do elemento e seus filhos
	 */
	public void setVisible (boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Retorna se o elemento está invisível e não deve ser desenhado.
	 * @return true se o elemento está invisível, false se não está
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * Retorna o nome do elemento.
	 * @return o nome do elemento
	 */
	public String getName() {
		return name;
	}

	/**
	 * Define um nome para identificar o elemento.
	 * @param name o nome para identificar o elemento
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Adiciona um AreaFixer para gerenciar a posição e tamanho do elemento.
	 * @param areaFixer
	 */
	public void addAreaFixer(LayoutFixer areaFixer) {
		if (areaFixers == null) {
			areaFixers = new Vector();
		}
		areaFixers.addElement(areaFixer);
		areaFixer.applyFix(this);
	}

	/**
	 * Remove o AreaFixer previamente adicionado através do método addAreaFixer.
	 * @param areaFixer
	 */
	public void removeAreaFixer(LayoutFixer areaFixer) {
		if (areaFixers != null) {
			areaFixers.removeElement(areaFixer);
			if (areaFixers.isEmpty()) {
				areaFixers = null;
			}
		}
	}

	/**
	 * Retorna a quantidade de AreaFixers adicionados através do método addAreaFixer.
	 * @return
	 */
	public int getAreaFixersCount() {
		if (areaFixers == null) {
			return 0;
		} else {
			return areaFixers.size();
		}
	}

	/**
	 * Retorna o AreaFixer, previamente adicionado através do método addAreaFixer.
	 * @param index o índice do AreaFixer. Pode lançar uma exceção ArrayIndexOutOfBoundsException
	 * se o índice for inválido.
	 * @return o AreaFixer do índice especificado
	 */
	public LayoutFixer getAreaFixer(int index) {
		if (areaFixers == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			return (LayoutFixer)areaFixers.elementAt(index);
		}
	}

	/**
	 * Sets the padding of the element. It is used by the LayoutFixers to define
	 * the space between the element and the children.
	 * @param paddingLeft the space, in pixels, of the left side of the element
	 * @param paddingTop the space, in pixels, of the top of the element
	 * @param paddingRight the space, in pixels, of the right side of the element
	 * @param paddingBottom the space, in pixels, of the bottom of the element
	 */
	public void setPadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
		this.paddingLeft = paddingLeft;
		this.paddingTop = paddingTop;
		this.paddingRight = paddingRight;
		this.paddingBottom = paddingBottom;
	}

	/**
	 * Sets the padding of the element. It is used by the LayoutFixers to define
	 * the space between the element and the children.
	 * @param paddingLeft the space, in pixels, of all sides of the element
	 */
	public void setPadding(int padding) {
		this.paddingLeft = padding;
		this.paddingTop = padding;
		this.paddingRight = padding;
		this.paddingBottom = padding;
	}

	/**
	 * Returns the padding, in pixels, of the left side of the element.
	 * @return the padding, in pixels, of the left side of the element
	 */
	public int getPaddingLeft() {
		return paddingLeft;
	}

	/**
	 * Returns the padding, in pixels, of the top of the element.
	 * @return the padding, in pixels, of the top of the element
	 */
	public int getPaddingTop() {
		return paddingTop;
	}

	/**
	 * Returns the padding, in pixels, of the right side of the element.
	 * @return the padding, in pixels, of the right side of the element
	 */
	public int getPaddingRight() {
		return paddingRight;
	}

	/**
	 * Returns the padding, in pixels, of the bottom of the element.
	 * @return the padding, in pixels, of the bottom of the element
	 */
	public int getPaddingBottom() {
		return paddingBottom;
	}

	/**
	 * Sets the margin of the element. It is used by the LayoutFixers to define
	 * the space between the element and the parent.
	 * @param marginLeft the space, in pixels, of the left side of the element
	 * @param marginTop the space, in pixels, of the top of the element
	 * @param marginRight the space, in pixels, of the right side of the element
	 * @param marginBottom the space, in pixels, of the bottom of the element
	 */
	public void setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom) {
		this.marginLeft = marginLeft;
		this.marginTop = marginTop;
		this.marginRight = marginRight;
		this.marginBottom = marginBottom;
	}

	/**
	 * Sets the margin of the element. It is used by the LayoutFixers to define
	 * the space between the element and the children.
	 * @param margin the space, in pixels, of all sides of the element
	 */
	public void setMargin(int margin) {
		this.marginLeft = margin;
		this.marginTop = margin;
		this.marginRight = margin;
		this.marginBottom = margin;
	}

	/**
	 * Returns the margin, in pixels, of the left side of the element.
	 * @return the margin, in pixels, of the left side of the element
	 */
	public int getMarginLeft() {
		return marginLeft;
	}

	/**
	 * Returns the margin, in pixels, of the top of the element.
	 * @return the margin, in pixels, of the top of the element
	 */
	public int getMarginTop() {
		return marginTop;
	}

	/**
	 * Returns the margin, in pixels, of the right side of the element.
	 * @return the margin, in pixels, of the right side of the element
	 */
	public int getMarginRight() {
		return marginRight;
	}

	/**
	 * Returns the margin, in pixels, of the bottom of the element.
	 * @return the margin, in pixels, of the bottom of the element
	 */
	public int getMarginBottom() {
		return marginBottom;
	}

	private interface AreaFixerMethodCall {
		void callMethod(LayoutFixer areaFixer);
	}

	private void notifyAreaFixersOnPositionChanged() {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onPositionChanged(DrawableElement.this);
			}
		});

		if (children != null) {
			for (int i=0; i<children.size(); i++) {
				((DrawableElement) children.elementAt(i)).notifyAreaFixersOnParentPositionChanged();
			}
		}

		if (parent != null) {
			parent.notifyAreaFixersOnChildPositionChanged(this);
		}
	}

	private void notifyAreaFixersOnChildPositionChanged(final DrawableElement child) {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onChildPositionChanged(DrawableElement.this, child);
			}
		});
	}

	private void notifyAreaFixersOnParentPositionChanged() {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onParentPositionChanged(DrawableElement.this);
			}
		});
	}

	private void notifyAreaFixersOnSizeChanged() {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onSizeChanged(DrawableElement.this);
			}
		});

		if (children != null) {
			for (int i=0; i<children.size(); i++) {
				((DrawableElement) children.elementAt(i)).notifyAreaFixersOnParentSizeChanged();
			}
		}

		if (parent != null) {
			parent.notifyAreaFixersOnChildSizeChanged(this);
		}
	}

	private void notifyAreaFixersOnChildSizeChanged(final DrawableElement child) {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onChildSizeChanged(DrawableElement.this, child);
			}
		});
	}

	private void notifyAreaFixersOnParentSizeChanged() {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onParentSizeChanged(DrawableElement.this);
			}
		});
	}

	private void notifyAreaFixersOnChildAdded(final DrawableElement child) {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onChildAdded(DrawableElement.this, child);
			}
		});
	}

	private void notifyAreaFixersOnChildRemoved(final DrawableElement child) {

		notifyAreaFixersMethod(new AreaFixerMethodCall() {
			public void callMethod(LayoutFixer areaFixer) {
				areaFixer.onChildRemoved(DrawableElement.this, child);
			}
		});
	}

	private void notifyAreaFixersMethod(AreaFixerMethodCall areaFixerMethodCall) {

		if (areaFixers != null) {
			for (int i=0; i<areaFixers.size(); i++) {

				LayoutFixer areaFixer = (LayoutFixer) areaFixers.elementAt(i);

				if (areaFixer != areaFixerExecuting) {
					areaFixerExecuting = areaFixer;
					areaFixerMethodCall.callMethod(areaFixer);
					areaFixerExecuting = null;
				}
			}
		}
	}
}
