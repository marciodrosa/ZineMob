package com.zine.zinemob.drawableelement;

import com.zine.zinemob.drawableelement.layout.Layout;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;

/**
 * Classe principal para elementos que possuem habilidade de desenho.
 */
public class DrawableElement
{
	private Vector children = null;
	private DrawableElement parent = null;
	private int x, y, pivotX, pivotY, width, height;
	private int paddingLeft, paddingTop, paddingRight, paddingBottom;
	private int marginLeft, marginTop, marginRight, marginBottom;
	private boolean visible = true;
	private Vector layouts = null;
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
		if(visible) {
			graphics.translate(x - pivotX, y - pivotY);
			drawElement(graphics);
			graphics.translate(pivotX, pivotY);
			drawChildren(graphics);
			graphics.translate(-x, -y);
		}
	}

	/**
	 * Desenha os filhos deste elemento. Este método é chamado pelo método draw
	 * após o próprio elemento ser desenhado através do método drawElement.
	 * @param graphics o contexto gráfico onde os elementos serão desenhados
	 */
	protected void drawChildren(Graphics graphics) {
		if (children != null) {
			for (int i=0; i<children.size(); i++) {
				((DrawableElement)children.elementAt(i)).draw(graphics);
			}
		}
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
		if (this.width != w || this.height != h) {
			width = w;
			height = h;
			notifyLayoutsOnSizeChanged();
		}
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
		if (this.x != x || this.y != y) {
			this.x = x;
			this.y = y;
			notifyLayoutsOnPositionChanged();
		}
	}

	/**
	 * Retorna a coordenada x do elemento. Se o elemento tiver pai, esta posição
	 * é relativa à posição do pai.
	 * @return a coordenada x
	 */
	public final int getX() {
		return x;
	}

	/**
	 * Retorna a coordenada y do elemento. Se o elemento tiver pai, esta posição
	 * é relativa à posição do pai.
	 * @return a coordenada y
	 */
	public final int getY() {
		return y;
	}
	
	/**
	 * Returns the position X of the DrawableElement ignoring the pivot position.
	 */
	public final int getLeftTopX() {
		return x - getPivotX();
	}
	
	/**
	 * Returns the position Y of the DrawableElement ignoring the pivot position.
	 */
	public final int getLeftTopY() {
		return y - getPivotY();
	}

	/**
	 * Posiciona o elemento na coordenada x e y, globalmente (não é em relação ao
	 * pai). Este método não de tão veloz execução quanto o método setPosition,
	 * pois a posição global deve ser calculada.
	 * @param x a coordenada x
	 * @param y a coordenada y
	 */
	public final void setGlobalPosition (int x, int y) {
		if (parent == null) {
			setPosition(x, y);
		} else {
			setPosition(x - parent.getGlobalX(), y - parent.getGlobalY());
		}
	}

	/**
	 * Retorna a coordenada x global do elemento (não é relativa ao pai). Este
	 * método não é de tão rápida execução quanto o método getX, pois é necessário
	 * varrer toda a hierarquia para calcular a posição global do elemento.
	 * @return a coordenada x
	 */
	public final int getGlobalX() {
		if (parent == null) {
			return getX();
		} else {
			return parent.getGlobalX() + getX();
		}
	}

	/**
	 * Retorna a coordenada y global do elemento (não é relativa ao pai). Este
	 * método não é de tão rápida execução quanto o método getY, pois é necessário
	 * varrer toda a hierarquia para calcular a posição global do elemento.
	 * @return a coordenada y
	 */
	public final int getGlobalY() {
		if (parent == null) {
			return getY();
		} else {
			return parent.getGlobalY() + getY();
		}
	}
	
	/**
	 * Returns the position X of the DrawableElement ignoring the pivot position.
	 */
	public final int getGlobalLeftTopX() {
		return getGlobalX() - getPivotX();
	}
	
	/**
	 * Returns the position Y of the DrawableElement ignoring the pivot position.
	 */
	public final int getGlobalLeftTopY() {
		return getGlobalY() - getPivotY();
	}
	
	
	/**
	 * Define o pivo do objeto, isto é, a coordenada X e Y da origem do elemento.
	 * Por padrão, é (0, 0), o que significa que a origem do elemento é o canto
	 * superior esquerdo.
	 * @param x a coordenada X do pivo
	 * @param y a coordenada Y do pivo
	 */
	public final void setPivot(int x, int y) {
		if (this.pivotX != x || this.pivotY != y) {
			this.pivotX = x;
			this.pivotY = y;
			notifyLayoutsOnPositionChanged();
		}
	}

	/**
	 * Centraliza o pivo do objeto, de acordo com sua largura e altura. Por padrão,
	 * o pivo é inicializado no canto superior esquerdo (coordenadas 0, 0).
	 */
	public final void centerPivot() {
		setPivot(getWidth() / 2, getHeight() / 2);
	}

	/**
	 * Retorna a coordenada X do pivo do objeto.
	 * @return a coordenada X do pivo do objeto
	 */
	public final int getPivotX() {
		return pivotX;
	}

	/**
	 * Retorna a coordenada Y do pivo do objeto.
	 * @return a coordenada Y do pivo do objeto
	 */
	public final int getPivotY() {
		return pivotY;
	}

	/**
	 * Adiciona um elemento filho ao final da lista de filhos.
	 * @param child o elemento filho
	 */
	public void addChild (DrawableElement child) {
		addChild(child, children == null? 0 : children.size());
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
	public void addChild(DrawableElement child, int index) {
		
		boolean parentChanged;
		if (child.parent == this) {
			parentChanged = false;
		} else {
			parentChanged = true;
		}

		if (child.parent != null) {
			child.parent.removeChild(child);
		}

		if (children == null) {
			children = new Vector();
		}

		if (index < 0) {
			index = 0;
		} else if (index > children.size()) {
			index = children.size();
		}
		
		children.insertElementAt (child, index);
		child.setParent(this);

		if (parentChanged) {
			notifyLayoutsOnChildAdded(child);
		}
	}
	
	/**
	 * Remove o elemento filho.
	 * @param child o elemento filho a ser removido
	 */
	public void removeChild(DrawableElement child) {
		if (children != null) {
			if (children.removeElement(child)) {
				if (children.isEmpty()) {
					children = null;
				}
				child.setParent(null);
				notifyLayoutsOnChildRemoved(child);
			}
		}
	}
	
	/**
	 * Remove todos os elementos filhos.
	 */
	public void removeChildren() {
		while (children != null) {
			removeChild((DrawableElement) children.lastElement());
		}
	}
	
	private void setParent(DrawableElement parent) {
		if (this.parent != parent) {
			this.parent = parent;
			notifyLayoutsOnParentChanged();
		}
	}
	
	/**
	 * Define que o elemento não deve ser renderizado, assim como seus filhos.
	 * @param visible true para desligar o desenho do elemento e seus filhos
	 */
	public final void setVisible (boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Retorna se o elemento está invisível e não deve ser desenhado.
	 * @return true se o elemento está invisível, false se não está
	 */
	public final boolean isVisible() {
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
	 * @param layoutFixer
	 */
	public void addLayout(Layout layoutFixer) {
		if (layouts == null) {
			layouts = new Vector();
		}
		layouts.addElement(layoutFixer);
		layoutFixer.applyFix(this);
	}

	/**
	 * Remove o AreaFixer previamente adicionado através do método addAreaFixer.
	 * @param layoutFixer
	 */
	public void removeLayout(Layout layoutFixer) {
		if (layouts != null) {
			layouts.removeElement(layoutFixer);
			if (layouts.isEmpty()) {
				layouts = null;
			}
		}
	}

	/**
	 * Retorna a quantidade de AreaFixers adicionados através do método addAreaFixer.
	 * @return
	 */
	public int getLayoutsCount() {
		if (layouts == null) {
			return 0;
		} else {
			return layouts.size();
		}
	}

	/**
	 * Retorna o AreaFixer, previamente adicionado através do método addAreaFixer.
	 * @param index o índice do AreaFixer. Pode lançar uma exceção ArrayIndexOutOfBoundsException
	 * se o índice for inválido.
	 * @return o AreaFixer do índice especificado
	 */
	public Layout getLayout(int index) {
		if (layouts == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			return (Layout)layouts.elementAt(index);
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

	private interface LayoutMethodCall {
		void callMethod(Layout areaFixer);
	}

	private void notifyLayoutsOnPositionChanged() {

		notifyLayoutMethod(new LayoutMethodCall() {
			public void callMethod(Layout areaFixer) {
				areaFixer.onPositionChanged(DrawableElement.this);
			}
		});

		if (children != null) {
			for (int i=0; i<children.size(); i++) {
				((DrawableElement) children.elementAt(i)).notifyLayoutsOnParentPositionChanged();
			}
		}

		if (parent != null) {
			parent.notifyLayoutsOnChildPositionChanged(this);
		}
	}

	private void notifyLayoutsOnChildPositionChanged(final DrawableElement child) {
		notifyLayoutMethod(new LayoutMethodCall() {
			public void callMethod(Layout layout) {
				layout.onChildPositionChanged(DrawableElement.this, child);
			}
		});
	}

	private void notifyLayoutsOnParentPositionChanged() {
		notifyLayoutMethod(new LayoutMethodCall() {
			public void callMethod(Layout layout) {
				layout.onParentPositionChanged(DrawableElement.this);
			}
		});
	}

	private void notifyLayoutsOnSizeChanged() {

		notifyLayoutMethod(new LayoutMethodCall() {
			public void callMethod(Layout layout) {
				layout.onSizeChanged(DrawableElement.this);
			}
		});

		if (children != null) {
			for (int i=0; i<children.size(); i++) {
				((DrawableElement) children.elementAt(i)).notifyLayoutsOnParentSizeChanged();
			}
		}

		if (parent != null) {
			parent.notifyLayoutsOnChildSizeChanged(this);
		}
	}

	private void notifyLayoutsOnChildSizeChanged(final DrawableElement child) {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onChildSizeChanged(DrawableElement.this, child);
				}
			});
		}
	}

	private void notifyLayoutsOnParentSizeChanged() {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onParentSizeChanged(DrawableElement.this);
				}
			});
		}
	}

	private void notifyLayoutsOnChildAdded(final DrawableElement child) {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onChildAdded(DrawableElement.this, child);
				}
			});
		}
	}

	private void notifyLayoutsOnChildRemoved(final DrawableElement child) {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onChildRemoved(DrawableElement.this, child);
				}
			});
		}
	}

	private void notifyLayoutsOnParentChanged() {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onParentChanged(DrawableElement.this);
				}
			});
		}
	}

	private synchronized void notifyLayoutMethod(LayoutMethodCall areaFixerMethodCall) {
		if (layouts != null) {
			for (int i=0; i<layouts.size(); i++) {
				Layout layoutFixer = (Layout) layouts.elementAt(i);
				areaFixerMethodCall.callMethod(layoutFixer);
			}
		}
	}
	
	/**
	 * Returns if the point is collided (inside or in the edges) with this DrawableElement.
	 * @param pointX the point X
	 * @param pointY the point Y
	 * @param useMargin true to use consider the margins of this DrawableElement
	 * @param relativeToParent true to indicate that the relative position of the
	 * Drawable element must be consider, otherwise the global position will be used
	 */
	public boolean collidesWith(int pointX, int pointY, boolean useMargin, boolean relativeToParent) {
		
		if (getWidth() < 1 || getHeight() < 1) {
			return false;
		}
		
		int[] drawableElementArea = getDrawableElementCollisionArea(this, useMargin, relativeToParent);
		
		int drawableElementAreaX1 = drawableElementArea[0];
		int drawableElementAreaY1 = drawableElementArea[1];
		int drawableElementAreaX2 = drawableElementAreaX1 + drawableElementArea[2] - 1;
		int drawableElementAreaY2 = drawableElementAreaY1 + drawableElementArea[3] - 1;
		
		return isDotInsideArea(pointX, pointY, drawableElementAreaX1, drawableElementAreaY1, drawableElementAreaX2, drawableElementAreaY2);
	}
	
	/**
	 * Returns if the area is collided with this DrawableElement. The size of the
	 * area must be at least (1, 1).
	 * @param areaX the X position of the area
	 * @param areaY the Y position of the area
	 * @param areaWidth  the width of the area
	 * @param areaHeight the height of the area
	 * @param useMargin true to use consider the margins of this DrawableElement
	 * @param relativeToParent true to indicate that the relative position of the
	 * Drawable element must be consider, otherwise the global position will be used
	 */
	public boolean collidesWith(int areaX, int areaY, int areaWidth, int areaHeight, boolean useMargin, boolean relativeToParent) {
		
		if (areaWidth < 1 || areaHeight < 1 || getWidth() < 1 || getHeight() < 1) {
			return false;
		}
		
		int[] drawableElementArea = getDrawableElementCollisionArea(this, useMargin, relativeToParent);
		
		int drawableElementAreaX1 = drawableElementArea[0];
		int drawableElementAreaY1 = drawableElementArea[1];
		int drawableElementAreaX2 = drawableElementAreaX1 + drawableElementArea[2] - 1;
		int drawableElementAreaY2 = drawableElementAreaY1 + drawableElementArea[3] - 1;
		
		int areaX2 = areaX + areaWidth - 1;
		int areaY2 = areaY + areaHeight - 1;
		
		boolean isSomeVertexOfAreaInsideDrawableElement = isDotInsideArea(areaX, areaY, drawableElementAreaX1, drawableElementAreaY1, drawableElementAreaX2, drawableElementAreaY2)
				|| isDotInsideArea(areaX2, areaY, drawableElementAreaX1, drawableElementAreaY1, drawableElementAreaX2, drawableElementAreaY2)
				|| isDotInsideArea(areaX2, areaY2, drawableElementAreaX1, drawableElementAreaY1, drawableElementAreaX2, drawableElementAreaY2)
				|| isDotInsideArea(areaX, areaY2, drawableElementAreaX1, drawableElementAreaY1, drawableElementAreaX2, drawableElementAreaY2);
		
		if (isSomeVertexOfAreaInsideDrawableElement) {
			return true;
		} else {
			
			boolean isSomeVertexOfDrawableElementInsideArea = isDotInsideArea(drawableElementAreaX1, drawableElementAreaY1, areaX, areaY, areaX2, areaY2)
					|| isDotInsideArea(drawableElementAreaX2, drawableElementAreaY1, areaX, areaY, areaX2, areaY2)
					|| isDotInsideArea(drawableElementAreaX2, drawableElementAreaY2, areaX, areaY, areaX2, areaY2)
					|| isDotInsideArea(drawableElementAreaX1, drawableElementAreaY2, areaX, areaY, areaX2, areaY2);
			
			if (isSomeVertexOfDrawableElementInsideArea) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Returns if the other DrawableElement is collided with this DrawableElement.
	 * @param other the other DrawableElement
	 * @param useMargin true to use consider the margins of this DrawableElement
	 */
	public boolean collidesWith(DrawableElement other, boolean useMargin) {
		
		boolean relative = other.getParent() == this.getParent();
		
		int[] area = getDrawableElementCollisionArea(other, useMargin, relative);
		
		return collidesWith(area[0], area[1], area[2], area[3], useMargin, relative);
	}
	
	private boolean isDotInsideArea(int dotX, int dotY, int areaX1, int areaY1, int areaX2, int areaY2) {
		return (dotX >= areaX1 && dotX <= areaX2) && (dotY >= areaY1 && dotY <= areaY2);
	}
	
	private int[] getDrawableElementCollisionArea(DrawableElement drawableElement, boolean useMargin, boolean relative) {
		int areaX;
		int areaY;
		int areaWidth = drawableElement.getWidth();
		int areaHeight = drawableElement.getHeight();
		
		if (relative) {
			areaX = drawableElement.getLeftTopX();
			areaY = drawableElement.getLeftTopY();
		} else {
			areaX = drawableElement.getGlobalLeftTopX();
			areaY = drawableElement.getGlobalLeftTopY();
		}
		
		if (useMargin) {
			areaX -= drawableElement.getMarginLeft();
			areaY -= drawableElement.getMarginTop();
			areaWidth += drawableElement.getMarginLeft() + drawableElement.getMarginRight();
			areaHeight += drawableElement.getMarginTop() + drawableElement.getMarginBottom();
		}
		
		return new int[] {areaX, areaY, areaWidth, areaHeight};
	}
}
