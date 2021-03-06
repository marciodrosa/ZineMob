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
	private boolean visible = true;
	private Vector layouts = null;
	private String name = "";

	/**
	 * Construtor padr�o.
	 */
	public DrawableElement() {
	}

	/**
	 * Retorna o elemento pai deste.
	 * @return o elemento pai deste, pode ser null se n�o houver
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
	 * Retorna o elemento filho de acordo com o �ndice. Se o �ndice for inv�lido
	 * (extrapolar a quantidade de filhos que o elemento realmente possui ou for
	 * menor que 0), ent�o uma exce��o ArrayIndexOutOfBoundsException � lan�ada.
	 * @param index o �ndice do elemento
	 * @return o elemento correspondente ao �ndice
	 */
	public DrawableElement getChild(int index) {
		if (children == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			return (DrawableElement)children.elementAt(index);
		}
	}
	
	/**
	 * Desenha o RenderElement e os seus filhos. Se o elemento estiver invis�vel,
	 * nem o elemento e nem os seus filhos s�o desenhados.
	 * @param graphics o contexto gr�fico onde o elemento ser� desenhado
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
	 * Desenha os filhos deste elemento. Este m�todo � chamado pelo m�todo draw
	 * ap�s o pr�prio elemento ser desenhado atrav�s do m�todo drawElement.
	 * @param graphics o contexto gr�fico onde os elementos ser�o desenhados
	 */
	protected void drawChildren(Graphics graphics) {
		if (children != null) {
			for (int i=0; i<children.size(); i++) {
				((DrawableElement)children.elementAt(i)).draw(graphics);
			}
		}
	}

	/**
	 * M�todo respons�vel pelo desenho deste elemento apenas, sem os seus filhos.
	 * � chamado pelo m�todo draw antes da chamada do m�todo drawChildren. Esta
	 * classe n�o faz nada por padr�o. O desenho das classes derivadas deve ser
	 * implementado aqui.
	 * @param graphics o contexto gr�fico onde o elemento ser� desenhado
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
	 * Posiciona o elemento na coordenada x e y. Se o elemento tiver pai, esta posi��o
	 * � relativa � posi��o do pai.
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
	 * Retorna a coordenada x do elemento. Se o elemento tiver pai, esta posi��o
	 * � relativa � posi��o do pai.
	 * @return a coordenada x
	 */
	public final int getX() {
		return x;
	}

	/**
	 * Retorna a coordenada y do elemento. Se o elemento tiver pai, esta posi��o
	 * � relativa � posi��o do pai.
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
	 * Posiciona o elemento na coordenada x e y, globalmente (n�o � em rela��o ao
	 * pai). Este m�todo n�o de t�o veloz execu��o quanto o m�todo setPosition,
	 * pois a posi��o global deve ser calculada.
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
	 * Retorna a coordenada x global do elemento (n�o � relativa ao pai). Este
	 * m�todo n�o � de t�o r�pida execu��o quanto o m�todo getX, pois � necess�rio
	 * varrer toda a hierarquia para calcular a posi��o global do elemento.
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
	 * Retorna a coordenada y global do elemento (n�o � relativa ao pai). Este
	 * m�todo n�o � de t�o r�pida execu��o quanto o m�todo getY, pois � necess�rio
	 * varrer toda a hierarquia para calcular a posi��o global do elemento.
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
	 * Define o pivo do objeto, isto �, a coordenada X e Y da origem do elemento.
	 * Por padr�o, � (0, 0), o que significa que a origem do elemento � o canto
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
	 * Centraliza o pivo do objeto, de acordo com sua largura e altura. Por padr�o,
	 * o pivo � inicializado no canto superior esquerdo (coordenadas 0, 0).
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
	 * Adiciona um elemento filho. Se o elemento j� possuir pai, este pai deixar�
	 * de ter o elemento como filho. Se o pai for este elemento, o objeto primeiramente
	 * ser� removido da hierarquia para depois ser posto novamente, obedecendo o
	 * �ndice do par�metro.
	 * @param child o elemento filho
	 * @param index a ordena��o deste elemento em rela��o aos demais filhos que
	 * este element cont�m. Se for menor que 0, o �ndice ser� considerado como 0.
	 * Se for maior que quantidade de filhos atual, ent�o o elemento ser� colocado
	 * na �ltima posi��o.
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
	 * Define que o elemento n�o deve ser renderizado, assim como seus filhos.
	 * @param visible true para desligar o desenho do elemento e seus filhos
	 */
	public final void setVisible (boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Retorna se o elemento est� invis�vel e n�o deve ser desenhado.
	 * @return true se o elemento est� invis�vel, false se n�o est�
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
	 * Add a Layout object to this DrawableElement.
	 */
	public void addLayout(Layout layout) {
		if (layouts == null) {
			layouts = new Vector();
		}
		layouts.addElement(layout);
		layout.apply();
	}

	/**
	 * Deletes the previous added Layout.
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
	 * Returns the quantity of Layouts added to this DrawableElement.
	 */
	public int getLayoutsCount() {
		if (layouts == null) {
			return 0;
		} else {
			return layouts.size();
		}
	}

	/**
	 * Returns the Layout by index. Can throw an ArrayIndexOutOfBoundsException if
	 * the index is invalid. Please validate with getLayoutsCount.
	 */
	public Layout getLayout(int index) {
		if (layouts == null) {
			throw new ArrayIndexOutOfBoundsException(index);
		} else {
			return (Layout)layouts.elementAt(index);
		}
	}

	private interface LayoutMethodCall {
		void callMethod(Layout areaFixer);
	}

	private void notifyLayoutsOnPositionChanged() {

		notifyLayoutMethod(new LayoutMethodCall() {
			public void callMethod(Layout areaFixer) {
				areaFixer.onPositionChanged();
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
				layout.onChildPositionChanged(child);
			}
		});
	}

	private void notifyLayoutsOnParentPositionChanged() {
		notifyLayoutMethod(new LayoutMethodCall() {
			public void callMethod(Layout layout) {
				layout.onParentPositionChanged();
			}
		});
	}

	private void notifyLayoutsOnSizeChanged() {

		notifyLayoutMethod(new LayoutMethodCall() {
			public void callMethod(Layout layout) {
				layout.onSizeChanged();
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
					layout.onChildSizeChanged(child);
				}
			});
		}
	}

	private void notifyLayoutsOnParentSizeChanged() {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onParentSizeChanged();
				}
			});
		}
	}

	private void notifyLayoutsOnChildAdded(final DrawableElement child) {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onChildAdded(child);
				}
			});
		}
	}

	private void notifyLayoutsOnChildRemoved(final DrawableElement child) {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onChildRemoved(child);
				}
			});
		}
	}

	private void notifyLayoutsOnParentChanged() {
		if (layouts != null) {
			notifyLayoutMethod(new LayoutMethodCall() {
				public void callMethod(Layout layout) {
					layout.onParentChanged();
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
	 * @param relativeToParent true to indicate that the relative position of the
	 * Drawable element must be consider, otherwise the global position will be used
	 */
	public boolean collidesWith(int pointX, int pointY, boolean relativeToParent) {
		
		if (getWidth() < 1 || getHeight() < 1) {
			return false;
		}
		
		int[] drawableElementArea = getDrawableElementCollisionArea(this, relativeToParent);
		
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
	 * @param relativeToParent true to indicate that the relative position of the
	 * Drawable element must be consider, otherwise the global position will be used
	 */
	public boolean collidesWith(int areaX, int areaY, int areaWidth, int areaHeight, boolean relativeToParent) {
		
		if (areaWidth < 1 || areaHeight < 1 || getWidth() < 1 || getHeight() < 1) {
			return false;
		}
		
		int[] drawableElementArea = getDrawableElementCollisionArea(this, relativeToParent);
		
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
	 */
	public boolean collidesWith(DrawableElement other) {
		
		boolean relative = other.getParent() == this.getParent();
		
		int[] area = getDrawableElementCollisionArea(other, relative);
		
		return collidesWith(area[0], area[1], area[2], area[3], relative);
	}
	
	private boolean isDotInsideArea(int dotX, int dotY, int areaX1, int areaY1, int areaX2, int areaY2) {
		return (dotX >= areaX1 && dotX <= areaX2) && (dotY >= areaY1 && dotY <= areaY2);
	}
	
	private int[] getDrawableElementCollisionArea(DrawableElement drawableElement, boolean relative) {
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
		
		return new int[] {areaX, areaY, areaWidth, areaHeight};
	}
}
