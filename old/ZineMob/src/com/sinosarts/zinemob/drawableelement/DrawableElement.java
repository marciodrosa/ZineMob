package com.sinosarts.zinemob.drawableelement;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import com.sinosarts.zinemob.core.ZineManager;

/**
 * Classe principal para elementos que possuem habilidade de desenho. � semelhante
 * � classe Layer do MIDP, mas possui alguns atrativos extras, como a possibilidade
 * de organizar hierarquicamente elementos filhos e sobreescrever m�todos como getX
 * e getWidth.
 */
public class DrawableElement
{
	/**
	 * Constante para indicar que deve ser utilizado o tamanho padr�o nos m�todos
	 * setWidth e Height. Este valor n�o � utilizado diretamente pela classe
	 * DrawElement, mas pode ser �til para as classes derivadas. O valor real �
	 * 0.
	 */
	public static final int DEFAULT_SIZE = 0;

	private DrawableElement parent = null;
	private Vector children = null;
	private int x, y, w = DEFAULT_SIZE, h = DEFAULT_SIZE;
	private boolean visible = true;

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
	 * Desenha o RenderElement e os seus filhos. Se o elemento estiver invis�vel,
	 * nem o elemento e nem os seus filhos s�o desenhados.
	 * @param graphics o contexto gr�fico onde o elemento ser� desenhado
	 */
	public void draw(Graphics graphics)
	{
		if(isVisible()) {
			int tx = getX();
			int ty = getY();
			graphics.translate (tx, ty);
			drawElement (graphics);
			drawChildren (graphics);
			graphics.translate (-tx, -ty);
		}
	}

	/**
	 * Desenha os filhos deste elemento. Este m�todo � chamado pelo m�todo draw
	 * ap�s o pr�prio elemento ser desenhado atrav�s do m�todo drawElement.
	 * @param graphics o contexto gr�fico onde os elementos ser�o desenhados
	 */
	protected void drawChildren(Graphics graphics)
	{
		if (children != null) {
			for (int i=0; i<children.size(); i++)
				((DrawableElement)children.elementAt(i)).draw (graphics);
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
		this.w = w;
		this.h = h;
	}

	/**
	 * Retorna a largura do elemento.
	 * @return a largura do elemento
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * Retorna a altura do elemento.
	 * @return a altura do elemento
	 */
	public int getHeight() {
		return h;
	}

	/**
	 * Posiciona o elemento na coordenada x e y em rela��o ao seu elemento pai
	 * ou em rela��o � tela se o elemento n�o estiver encadeado como filho de
	 * outro elemento.
	 * @param x a coordenada x do canto superior esquerdo do elemento
	 * @param y a coordenada y do canto superior esquerdo do elemento
	 */
	public void setPosition (int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Posiciona o elemento na coordenada x e y em rela��o � tela. Este m�todo n�o
	 * � de t�o veloz execu��o quanto o m�todo setPosition, pois a posi��o global
	 * (e n�o relativa ao pai) deve ser calculada.
	 * @param x a coordenada x do canto superior esquerdo do elemento
	 * @param y a coordenada y do canto superior esquerdo do elemento
	 */
	public void setGlobalPosition (int x, int y) {
		if (parent == null)
			setPosition(x, y);
		else
			setPosition(x - parent.getGlobalX(), y - parent.getGlobalY());
	}

	/**
	 * Posiciona o elemento na coordenada x e y em rela��o ao seu elemento pai
	 * ou em rela��o � tela se o elemento n�o estiver encadeado como filho de
	 * outro elemento.
	 * @param x a coordenada x do centro do elemento
	 * @param y a coordenada y do centro do elemento
	 */
	public void setCenteredPosition (int x, int y) {
		setPosition (x-(getWidth()/2), y-(getHeight()/2));
	}

	/**
	 * Posiciona o elemento na coordenada x e y em rela��o � tela. Este m�todo n�o
	 * � de t�o veloz execu��o quanto o m�todo setCenteredPosition, pois a posi��o
	 * global (e n�o relativa ao pai) deve ser calculada.
	 * @param x a coordenada x do centro do elemento
	 * @param y a coordenada y do centro do elemento
	 */
	public void setGlobalCenteredPosition (int x, int y) {

		if (parent == null)
			setCenteredPosition(x, y);
		else
			setPosition(x - parent.getGlobalX() - (getWidth()/2),
					y - parent.getGlobalY() - (getHeight()/2));
	}

	/**
	 * Retorna a coordenada x do elemento em rela��o ao seu elemento pai
	 * ou em rela��o � tela se o elemento n�o estiver encadeado como filho de
	 * outro elemento.
	 * @return a coordenada x do canto superior esquerdo do elemento
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retorna a coordenada y do elemento em rela��o ao seu elemento pai
	 * ou em rela��o � tela se o elemento n�o estiver encadeado como filho de
	 * outro elemento.
	 * @return a coordenada y do canto superior esquerdo do elemento
	 */
	public int getY() {
		return y;
	}

	/**
	 * Retorna a coordenada x do elemento em rela��o � tela. Este m�todo n�o � de
	 * t�o r�pida execu��o quanto o m�todo getX, pois � necess�rio varrer toda a
	 * hierarquia para calcular a posi��o global do elemento.
	 * @return a coordenada x do canto superior esquerdo do elemento
	 */
	public int getGlobalX() {
		if (parent == null)
			return getX();
		else
			return parent.getGlobalX() + getX();
	}

	/**
	 * Retorna a coordenada y do elemento em rela��o � tela. Este m�todo n�o � de
	 * t�o r�pida execu��o quanto o m�todo getY, pois � necess�rio varrer toda a
	 * hierarquia para calcular a posi��o global do elemento.
	 * @return a coordenada y do canto superior esquerdo do elemento
	 */
	public int getGlobalY() {
		if (parent == null)
			return getY();
		else
			return parent.getGlobalY() + getY();
	}

	/**
	 * Retorna a coordenada x do elemento em rela��o ao seu elemento pai
	 * ou em rela��o � tela se o elemento n�o estiver encadeado como filho de
	 * outro elemento.
	 * @return a coordenada x do centro do elemento
	 */
	public int getCenteredPositionX() {
		return getX()+(getWidth()/2);
	}

	/**
	 * Retorna a coordenada y do elemento em rela��o ao seu elemento pai
	 * ou em rela��o � tela se o elemento n�o estiver encadeado como filho de
	 * outro elemento.
	 * @return a coordenada y do centro do elemento
	 */
	public int getCenteredPositionY() {
		return getY()+(getHeight()/2);
	}

	/**
	 * Retorna a coordenada x do elemento em rela��o � tela. Este m�todo n�o � de
	 * t�o r�pida execu��o quanto o m�todo getCenteredPositionX, pois � necess�rio
	 * varrer toda a hierarquia para calcular a posi��o global do elemento.
	 * @return a coordenada x do centro do elemento
	 */
	public int getGlobalCenteredPositionX() {
		if (parent == null)
			return getCenteredPositionX();
		else
			return parent.getGlobalX() + getCenteredPositionX();
	}

	/**
	 * Retorna a coordenada y do elemento em rela��o � tela. Este m�todo n�o � de
	 * t�o r�pida execu��o quanto o m�todo getCenteredPositionY, pois � necess�rio
	 * varrer toda a hierarquia para calcular a posi��o global do elemento.
	 * @return a coordenada y do centro do elemento
	 */
	public int getGlobalCenteredPositionY() {
		if (parent == null)
			return getCenteredPositionY();
		else
			return parent.getGlobalY() + getCenteredPositionY();
	}

	/**
	 * Reposiciona este elemento, centralizando � tela do dispositivo.
	 */
	public void centerToScreen() {
		setGlobalCenteredPosition(ZineManager.getInstance().getRealWidth()/2,
				ZineManager.getInstance().getRealHeight()/2);
	}

	/**
	 * Alinha o centro deste elemento com o centro de outro elemento.
	 * @param e o elemento com o qual este ter� sua posi��o central alinhada
	 */
	public void alignCenterWith (DrawableElement e) {
		if (e.parent == parent)
			setCenteredPosition(e.getCenteredPositionX(), e.getCenteredPositionY());
		else
			setGlobalCenteredPosition (e.getGlobalCenteredPositionX(), e.getGlobalCenteredPositionY());
	}
	
	/**
	 * Adiciona um elemento filho. N�o faz nada se j� for seu filho. Se o elemento
	 * j� possuir pai, este pai deixar� de ter o elemento como filho.
	 * @param child o elemento filho
	 */
	public void addChild (DrawableElement child) {

		if (child.parent == this)
			return;

		if (child.parent != null)
			child.parent.removeChild(child);

		if (children == null)
			children = new Vector();
		
		children.addElement(child);
		child.parent = this;
	}
	
	/**
	 * Adiciona um elemento filho. Se o elemento j� possuir pai, este pai deixar�
	 * de ter o elemento como filho. Se o pai for este elemento, o objeto primeiramente
	 * ser� removido da hierarquia para depois ser posto novamente, obedecendo o
	 * �ndice do par�metro.
	 * @param child o elemento filho
	 * @param index a ordena��o deste elemento em rela��o aos demais filhos que
	 * este element cont�m
	 */
	public void addChild (DrawableElement child, int index) {

		if (child.parent != null)
			child.parent.removeChild(child);

		if (children == null)
			children = new Vector();
		
		children.insertElementAt (child, index);
		child.parent = this;
	}
	
	/**
	 * Remove o elemento filho.
	 * @param re o elemento filho a ser removido
	 */
	public void removeChild (DrawableElement re) {
		if (children != null) {
			if (children.removeElement(re)) {
				if (children.isEmpty())
					children = null;
				re.parent = null;
			}
		}
	}
	
	/**
	 * Remove todos os elementos filhos.
	 */
	public void removeChildren() {
		children = null;
	}
	
	/**
	 * Verifica se os elementos est�o colididos.
	 * @param drawElement o elemento a ser analisado
	 * @return true se h� colis�o entre os elementos de acordo com os seus posicionamentos
	 * e tamanhos, false se n�o h�
	 */
	public boolean isCollidedWith(DrawableElement drawElement) {
		int x1, x2, y1, y2;
		if (drawElement.parent == parent) {
			x1 = getX();
			y1 = getY();
			x2 = drawElement.getX();
			y2 = drawElement.getY();
		}
		else {
			x1 = getGlobalX();
			y1 = getGlobalY();
			x2 = drawElement.getGlobalX();
			y2 = drawElement.getGlobalY();
		}

		int xmin1, xmax1, ymin1, ymax1, xmin2, xmax2, ymin2, ymax2;
		xmin1 = x1-(getWidth()/2);
		xmax1 = x1+(getWidth()/2);
		ymin1 = y1-(getHeight()/2);
		ymax1 = y1+(getHeight()/2);
		xmin2 = x2-(drawElement.getWidth()/2);
		xmax2 = x2+(drawElement.getWidth()/2);
		ymin2 = y2-(drawElement.getHeight()/2);
		ymax2 = y2+(drawElement.getHeight()/2);
		
		if (xmin1<xmax2 && xmax1>xmin2 && ymin1<ymax2 && ymax1>ymin2)
			return true;
		else
			return false;
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
}

/*
 * Vers�o 01.07:
 * - novo m�todo attachFitElement
 * - corre��o do metodo removeChild
 * 
 * Vers�o 01.04: (post ZM_20100417)
 * - novo m�todo setSize
 * - novo m�todo getParent
 * 
 * Vers�o 01.02: corrigido o bug que causava exce��o se o m�todo removeChildren
 * fosse chamado quando o elemento n�o possui nenhum filho
 */
