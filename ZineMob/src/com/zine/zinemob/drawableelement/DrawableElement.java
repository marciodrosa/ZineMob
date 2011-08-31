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
	 * Desenha os filhos deste elemento. Este m�todo � chamado pelo m�todo draw
	 * ap�s o pr�prio elemento ser desenhado atrav�s do m�todo drawElement.
	 * @param graphics o contexto gr�fico onde os elementos ser�o desenhados
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
	 * Posiciona o elemento na coordenada x e y. Se o elemento tiver pai, esta posi��o
	 * � relativa � posi��o do pai.
	 * @param x a coordenada x
	 * @param y a coordenada y
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;

		notifyAreaFixersOnPositionChanged();
	}

	/**
	 * Retorna a coordenada x do elemento. Se o elemento tiver pai, esta posi��o
	 * � relativa � posi��o do pai.
	 * @return a coordenada x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retorna a coordenada y do elemento. Se o elemento tiver pai, esta posi��o
	 * � relativa � posi��o do pai.
	 * @return a coordenada y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Posiciona o elemento na coordenada x e y, globalmente (n�o � em rela��o ao
	 * pai). Este m�todo n�o de t�o veloz execu��o quanto o m�todo setPosition,
	 * pois a posi��o global deve ser calculada.
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
	 * Retorna a coordenada x global do elemento (n�o � relativa ao pai). Este
	 * m�todo n�o � de t�o r�pida execu��o quanto o m�todo getX, pois � necess�rio
	 * varrer toda a hierarquia para calcular a posi��o global do elemento.
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
	 * Retorna a coordenada y global do elemento (n�o � relativa ao pai). Este
	 * m�todo n�o � de t�o r�pida execu��o quanto o m�todo getY, pois � necess�rio
	 * varrer toda a hierarquia para calcular a posi��o global do elemento.
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
	 * Define o pivo do objeto, isto �, a coordenada X e Y da origem do elemento.
	 * Por padr�o, � (0, 0), o que significa que a origem do elemento � o canto
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
	 * Centraliza o pivo do objeto, de acordo com sua largura e altura. Por padr�o,
	 * o pivo � inicializado no canto superior esquerdo (coordenadas 0, 0).
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
	 * Define a posi��o onde os filhos deste elemento s�o desenhados. Esta posi��o
	 * � relativa a este elemento. Por padr�o, X e Y s�o 0.
	 * @param x a coordenada X da posi��o
	 * @param y a coordenada Y da posi��o
	 */
	public void setChildrenViewPosition(int x, int y) {
		this.childrenViewPositionX = x;
		this.childrenViewPositionY = y;

		notifyAreaFixersOnPositionChanged();
	}

	/**
	 * Retorna a coordenada X da posi��o onde os filhos s�o desenhados.
	 * @return a coordenada X da posi��o onde os filhos s�o desenhados
	 */
	public int getChildrenViewPositionX() {
		return this.childrenViewPositionX;
	}

	/**
	 * Retorna a coordenada Y da posi��o onde os filhos s�o desenhados.
	 * @return a coordenada Y da posi��o onde os filhos s�o desenhados
	 */
	public int getChildrenViewPositionY() {
		return this.childrenViewPositionY;
	}

	/**
	 * Adiciona um elemento filho. N�o faz nada se j� for seu filho. Se o elemento
	 * j� possuir pai, este pai deixar� de ter o elemento como filho.
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
	 * Define que o elemento n�o deve ser renderizado, assim como seus filhos.
	 * @param visible true para desligar o desenho do elemento e seus filhos
	 */
	public void setVisible (boolean visible) {
		this.visible = visible;
	}
	
	/**
	 * Retorna se o elemento est� invis�vel e n�o deve ser desenhado.
	 * @return true se o elemento est� invis�vel, false se n�o est�
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
	 * Adiciona um AreaFixer para gerenciar a posi��o e tamanho do elemento.
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
	 * Remove o AreaFixer previamente adicionado atrav�s do m�todo addAreaFixer.
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
	 * Retorna a quantidade de AreaFixers adicionados atrav�s do m�todo addAreaFixer.
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
	 * Retorna o AreaFixer, previamente adicionado atrav�s do m�todo addAreaFixer.
	 * @param index o �ndice do AreaFixer. Pode lan�ar uma exce��o ArrayIndexOutOfBoundsException
	 * se o �ndice for inv�lido.
	 * @return o AreaFixer do �ndice especificado
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
