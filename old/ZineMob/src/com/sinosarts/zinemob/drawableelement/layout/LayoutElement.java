package com.sinosarts.zinemob.drawableelement.layout;

import com.sinosarts.zinemob.drawableelement.DrawableElement;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

/**
 * Elemento que permite organizar os elementos filhos obedecendo � regras de layout.
 * A disposi��o dos elementos apenas � atualizada quando o m�todo refresh � chamado,
 * ao menos que se habilite a atualiza��o autom�tica atrav�s do m�todo
 * setAutoRefresh(true).
 */
public class LayoutElement extends DrawableElement {

	/**
	 * Indica que os itens adicionados ao layout s�o posicionados lado a lado.
	 */
	public static final byte LAYOUT_HORIZONTAL = 1;

	/**
	 * Indica que os itens adicionados ao layout s�o posicionados um abaixo do
	 * outro.
	 */
	public static final byte LAYOUT_VERTICAL = 2;

	// por padr�o alinha � esquerda e acima, por isso estas constantes s�o 0

	/**
	 * Alinha o elemento � esquerda do seu espa�o.
	 */
	public static final int ALIGN_LEFT = 0;

	/**
	 * Alinha o elemento no topo do seu espa�o.
	 */
	public static final int ALIGN_TOP = 0;

	/**
	 * Alinha o elemento � direita do seu espa�o.
	 */
	public static final int ALIGN_RIGHT = 1;

	/**
	 * Alinha o elemento na parte de baixo do seu espa�o.
	 */
	public static final int ALIGN_BOTTOM = 2;

	/**
	 * Centraliza o elemento verticalmente no seu espa�o.
	 */
	public static final int CENTER_V = 4;

	/**
	 * Centraliza o elemento horizontalmente no seu espa�o.
	 */
	public static final int CENTER_H = 8;

	/**
	 * Centraliza o elemento verticalmente e horizontalmente no seu espa�o.
	 */
	public static final int CENTER = CENTER_V | CENTER_H;

	/**
	 * Estica o elemento horizontalmente dentro do seu espa�o.
	 */
	public static final int STRETCH_H = 16;

	/**
	 * Estica o elemento verticalmente dentro do seu espa�o.
	 */
	public static final int STRETCH_V = 32;

	/**
	 * Estica o elemento horizontalmente e verticalmente dentro do seu espa�o.
	 */
	public static final int STRETCH = STRETCH_H | STRETCH_V;

	private byte type = LAYOUT_VERTICAL;
	private boolean autoFitToElements = false;
	private boolean showLayoutLines = false;
	private boolean autoRefresh = false;
	private Vector layoutItens = new Vector();
	private Border defaultBorder = new Border(0);

	private boolean isRefreshing = false;

	private static class LayoutItem {

		DrawableElement renderElement;
		int constraints = 0;
		boolean stretchable = false;
		Border border;
		int spaceSize = 0;

		LayoutItem(DrawableElement renderElement, Border border, int constraints, boolean stretchable) {
			this.renderElement = renderElement;
			this.border = border;
			this.constraints = constraints;
			this.stretchable = stretchable;
		}

	}

	/**
	 * Construtor padr�o. Inicia um layout VERTICAL e que se adapta ao tamanho dos
	 * itens adicionados ao layout.
	 */
	public LayoutElement() {
	}

	/**
	 * Inicia um layout do tipo especificado.
	 * @param type o tipo de layout (VERTICAL ou HORIZONTAL)
	 * @param autoFitToElements ver m�todo setAutoFitToElements
	 */
	public LayoutElement(byte type, boolean autoFitToElements) {
		this.type = type;
		this.autoFitToElements = autoFitToElements;
	}

	protected void drawElement(Graphics graphics) {
		super.drawElement(graphics);

		if (showLayoutLines) {
			graphics.setColor(0xffff0000);
			int position = 0;
			for (int i=0; i<layoutItens.size(); i++) {
				LayoutItem item = (LayoutItem)layoutItens.elementAt(i);
				if (getLayoutType() == LAYOUT_HORIZONTAL)
					graphics.drawRect(position, 0, item.spaceSize, getHeight());
				if (getLayoutType() == LAYOUT_VERTICAL)
					graphics.drawRect(0, position, getWidth(), item.spaceSize);

				position += item.spaceSize;
			}
		}
	}

	public void setSize(int w, int h) {
		super.setSize(w, h);
		autoRefresh();
	}

	/**
	 * Habilita ou desabilita o desenho das linhas do layout em vermelho, para
	 * efeitos de visualiza��o para debug.
	 * @param show true para habilitar o desenho das linhas, false para desabilitar
	 */
	public void setShowLayoutLines(boolean show) {
		this.showLayoutLines = show;
	}

	/**
	 * Retorna o tipo de layout.
	 * @return o tipo de Layout (HORIZONTAL ou VERTICAL)
	 */
	public byte getLayoutType() {
		return type;
	}

	/**
	 * Define o tipo de layout.
	 * @param type o tipo de Layout (HORIZONTAL ou VERTICAL)
	 */
	public void setLayoutType(byte type) {
		this.type = type;

		autoRefresh();
	}

	/**
	 * Retorna se o layout deve adaptar o seu tamanho para os itens adicionados ao
	 * layout.
	 * @return true para que o tamanho do layout se adapte aos itens, false para
	 * manter o espa�o do layout fixo
	 */
	public boolean isAutoFitToElements() {
		return autoFitToElements;
	}

	/**
	 * Indica se o layout deve adaptar o seu tamanho para os itens adicionados ao
	 * layout.
	 * @param fitToElements true para que o tamanho do layout se adapte aos itens,
	 * false para manter o espa�o do layout fixo (conforme o que for especificado
	 * atrav�s do m�todo setSize)
	 */
	public void setAutoFitToElements(boolean fitToElements) {
		this.autoFitToElements = fitToElements;
	}

	/**
	 * Retorna se a organiza��o dos elementos deve ocorrer automaticamente ou apenas
	 * na chamada do m�todo refresh.
	 * @return true se a organiza��o dos elementos ocorre automaticamente, false
	 * se apenas ocorre quando � chamado o m�todo refresh
	 */
	public boolean isAutoRefresh() {
		return autoRefresh;
	}

	/**
	 * Define se a organiza��o dos elementos deve ocorrer automaticamente ou apenas
	 * na chamada do m�todo refresh (sendo esta segunda op��o a mais otimizada).
	 * @param autoRefresh true para que a organiza��o dos elementos ocorra automaticamente,
	 * false para ocorrer apenas quando � chamado o m�todo refresh
	 */
	public void setAutoRefresh(boolean autoRefresh) {
		this.autoRefresh = autoRefresh;
	}

	/**
	 * Define a borda padr�o dos elementos que s�o adicionados ao layout sem utilizar
	 * uma borda pr�pria. A borda retornada neste m�todo pode ser alterada, mas
	 * o efeito s� � percebido ap�s a chamada do m�todo refresh.
	 * @return a borda padr�o do layout
	 */
	public Border getDefaultBorder() {
		return defaultBorder;
	}

	private synchronized void autoRefresh() {
		if (isAutoRefresh() && !isRefreshing)
			refresh();
	}

	/**
	 * Adiciona um elemento como filho deste e organiza no layout.
	 * @param element o elemento a ser adicionado ao layout
	 * @param border a borda a ser utilizada, pode ser null
	 * @param constraints flags de configura��o (ALIGN_LEFT, ALIGN_TOP, ALIGN_RIGHT,
	 * ALIGN_BOTTOM, CENTER_V, CENTER_H, CENTER, STRETCH_H, STRETCH_V, STRETCH)
	 * @param stretchable true para indicar que o espa�o utilizado pelo elemento
	 * � male�vel, false para indicar que � fixo (n�o estica nem achata o espa�o
	 * do elemento para se adaptar ao tamanho do LayoutElement)
	 */
	public void addToLayout(DrawableElement element, Border border, int constraints, boolean stretchable) {
		addToLayout(element, border, constraints, stretchable, layoutItens.size());
	}

	/**
	 * Adiciona um elemento como filho deste e organiza no layout.
	 * @param element o elemento a ser adicionado ao layout
	 * @param border a borda a ser utilizada, pode ser null (neste caso, ser� utilizada
	 * a borda padr�o)
	 * @param constraints flags de configura��o (ALIGN_LEFT, ALIGN_TOP, ALIGN_RIGHT,
	 * ALIGN_BOTTOM, CENTER_V, CENTER_H, CENTER, STRETCH_H, STRETCH_V, STRETCH)
	 * @param stretchable true para indicar que o espa�o utilizado pelo elemento
	 * � male�vel, false para indicar que � fixo (n�o estica nem achata o espa�o
	 * do elemento para se adaptar ao tamanho do LayoutElement)
	 * @param index o �ndice que indica a ordena��o do elemento em rela��o aos
	 * demais elementos adicionados ao layout
	 *
	 * @throws ArrayIndexOutOfBoundsException se index for menor que 0 ou extrapolar
	 * a quantidade de elementos no layout
	 */
	public void addToLayout(DrawableElement element, Border border, int constraints, boolean stretchable, int index) {
		if (getElementIndex(element) == -1) {

			if (border == null)
				border = defaultBorder;
			
			LayoutItem item = new LayoutItem(element, border, constraints, stretchable);

			layoutItens.insertElementAt(item, index);
			addChild(element);

			autoRefresh();
		}
	}

	private int getElementIndex(DrawableElement element) {
		for (int i=0; i<layoutItens.size(); i++) {
			if (element == layoutItens.elementAt(i))
				return i;
		}
		return -1;
	}

	public void removeChild (DrawableElement re) {
		super.removeChild(re);
		int elementIndex = getElementIndex(re);
		if (elementIndex != -1) {
			layoutItens.removeElementAt(elementIndex);

			autoRefresh();
		}
	}

	public void removeChildren() {
		super.removeChildren();
		layoutItens = new Vector();

		autoRefresh();
	}

	/**
	 * Atualiza o posicionamento / redimensionamento dos elementos adicionados ao
	 * layout. O tamanho do elemento � atualizado para ser o menor poss�vel englobando
	 * todos os elementos adicionados ao layout.
	 */
	public void refreshAndFit() {
		refresh(true);
	}

	/**
	 * Atualiza o posicionamento / redimensionamento dos elementos adicionados ao
	 * layout.
	 */
	public void refresh() {
		refresh(isAutoFitToElements());
	}

	private void refresh(boolean fit) {
		isRefreshing = true;

		// 1) ajusta tamanho do layout e o espa�o dispon�vel para os itens:
		if (fit)
			adjustLayoutToItensSize();
		else
			adjustItensToLayoutSize();

		// 2) ajusta posicionamento / tamanho de cada item, individualmente:
		adjustItensPositionAndSize();

		isRefreshing = false;
	}

	private void adjustItensPositionAndSize() {
		// verifica o espa�o dispon�vel para cada item, como ele deve ser alinhado e se deve ser esticado:

		int position = 0;

		for (int i=0; i<layoutItens.size(); i++) {
			LayoutItem item = (LayoutItem)layoutItens.elementAt(i);

			// o padr�o � alinhado acima e � esquerda:
			int x = item.border.getLeft();
			int y = item.border.getTop();
			int w = item.renderElement.getWidth();
			int h = item.renderElement.getHeight();

			// configura��es horizontais:
			if ((item.constraints & ALIGN_RIGHT) != 0) {
				if (type == LAYOUT_VERTICAL)
					x = getWidth() - item.renderElement.getWidth() - item.border.getRight();
				else if (type == LAYOUT_HORIZONTAL)
					x = item.spaceSize - item.renderElement.getWidth() - item.border.getRight();
			}
			else if ((item.constraints & CENTER_H) != 0) {
				if (type == LAYOUT_VERTICAL)
					x = (getWidth()/2) - (item.renderElement.getWidth()/2);
				else if (type == LAYOUT_HORIZONTAL)
					x = (item.spaceSize/2) - (item.renderElement.getWidth()/2);
			}
			else if ((item.constraints & STRETCH_H) != 0) {
				if (type == LAYOUT_VERTICAL)
					w = getWidth() - item.border.getLeft() - item.border.getRight();
				else if (type == LAYOUT_HORIZONTAL)
					w = item.spaceSize - item.border.getLeft() - item.border.getRight();
			}

			// configura��es verticais:
			if ((item.constraints & ALIGN_BOTTOM) != 0) {
				if (type == LAYOUT_VERTICAL)
					y = item.spaceSize - item.renderElement.getHeight() - item.border.getBottom();
				else if (type == LAYOUT_HORIZONTAL)
					y = getHeight() - item.renderElement.getHeight() - item.border.getBottom();
			}
			else if ((item.constraints & CENTER_V) != 0) {
				if (type == LAYOUT_VERTICAL)
					y = (item.spaceSize/2) - (item.renderElement.getHeight()/2);
				else if (type == LAYOUT_HORIZONTAL)
					y = (getHeight()/2) - (item.renderElement.getHeight()/2);
			}
			else if ((item.constraints & STRETCH_V) != 0) {
				if (type == LAYOUT_VERTICAL)
					h = item.spaceSize - item.border.getTop() - item.border.getBottom();
				else if (type == LAYOUT_HORIZONTAL)
					h = getHeight() - item.border.getTop() - item.border.getBottom();
			}

			if (type == LAYOUT_HORIZONTAL)
				x += position;
			if (type == LAYOUT_VERTICAL)
				y += position;

			item.renderElement.setPosition(x, y);
			item.renderElement.setSize(w, h);

			position += item.spaceSize;
		}
	}

	private void adjustLayoutToItensSize() {
		// se o layout n�o tem tamanho fixo, ent�o o seu tamanho ser� o menor poss�vel
		// para acomodar os itens dispostos no layout

		int size = 0;
		int peripheralSize = 0;

		for (int i=0; i<layoutItens.size(); i++) {
			LayoutItem item = (LayoutItem)layoutItens.elementAt(i);
			size += item.spaceSize = getItemSpace(item);
			int itemPeripheralSize = getItemPeripheralSpace(item);
			if (itemPeripheralSize > peripheralSize)
				peripheralSize = itemPeripheralSize;
		}

		if (type == LAYOUT_HORIZONTAL)
			setSize(size, peripheralSize);
		else if (type == LAYOUT_VERTICAL)
			setSize(peripheralSize, size);
	}

	private void adjustItensToLayoutSize() {
		// se o layout possui um tamanho fixo, ent�o alguns itens podem ser esticados
		// para ocupar o espa�o do layout.
		// 1) � feito a soma do tamanho dos itens
		// 2) se sobrar um espa�o N:
		//    - ent�o os itens estic�veis tem o seu espa�o aumentado
		//    - se h� X itens estic�veis, cada item tem o espa�o aumentado em N / X
		// 3) se faltar um espa�o N:
		//    - ent�o os itens estic�veis tem o seu espa�o diminuido SE nas as constraints constarem STRETCH_H ou STRETCH_W
		//    - se h� X itens estic�veis, cada item tem o espa�o diminu�do em N / X
		//    - o espa�o nunca � diminu�do a ponto de ficar com tamanho menor que zero

		int size = 0;
		if (type == LAYOUT_VERTICAL)
			size = super.getHeight();
		else if (type == LAYOUT_HORIZONTAL)
			size = super.getWidth();

		Vector stretchableElements = new Vector();
		int itensSize = 0;

		for (int i=0; i<layoutItens.size(); i++) {
			LayoutItem item = (LayoutItem)layoutItens.elementAt(i);
			if (item.stretchable)
				stretchableElements.addElement(item);
			itensSize += item.spaceSize = getItemSpace(item);
		}

		if (size > itensSize && stretchableElements.size() > 0) { // sobrou espa�o, alguns itens podem ser esticados

			int itemSpace = (size-itensSize) / stretchableElements.size();

			for (int i=0; i<stretchableElements.size(); i++) {
				LayoutItem item = (LayoutItem)stretchableElements.elementAt(i);
				if (type == LAYOUT_VERTICAL)
					item.spaceSize += itemSpace;
				else if (type == LAYOUT_HORIZONTAL)
					item.spaceSize +=  item.renderElement.getWidth() + itemSpace;
			}
		}
		else if (size < itensSize && stretchableElements.size() > 0) { // faltou espa�o, alguns itens podem ser achatados
			int itemSpace = (itensSize - size) / stretchableElements.size();

			for (int i=0; i<stretchableElements.size(); i++) {
				LayoutItem item = (LayoutItem)stretchableElements.elementAt(i);
				if (type == LAYOUT_VERTICAL) {
					if ((item.constraints & STRETCH_V) != 0) {
						int itemHeight = item.spaceSize - itemSpace;
						if (itemHeight < 0)
							itemHeight = 0;
						item.spaceSize = itemHeight;
					}
				}
				else if (type == LAYOUT_HORIZONTAL) {
					if ((item.constraints & STRETCH_H) != 0) {
						int itemWidth = item.spaceSize - itemSpace;
						if (itemWidth < 0)
							itemWidth = 0;
						item.spaceSize = itemWidth;
					}
				}
			}
		}

	}

	private int getItemSpace(LayoutItem item) {
		// retorna o espa�o m�nimo ocupado pelo item no layout (altura se for um layout vertical, largura se for horizontal)
		if (type == LAYOUT_VERTICAL)
			return item.renderElement.getHeight() + item.border.getTop() + item.border.getBottom();
		else if (type == LAYOUT_HORIZONTAL)
			return item.renderElement.getWidth() + item.border.getLeft() + item.border.getRight();
		return 0;
	}

	private int getItemPeripheralSpace(LayoutItem item) {
		// retorna o espa�o ocupado perifericamente pelo item no layout (largura se for um layout vertical, altura se for horizontal)
		if (type == LAYOUT_VERTICAL)
			return item.renderElement.getWidth() + item.border.getLeft() + item.border.getRight();
		else if (type == LAYOUT_HORIZONTAL)
			return item.renderElement.getHeight() + item.border.getTop() + item.border.getBottom();
		return 0;
	}

}

/*
 * Versao 01.08:
 * - atributo autoFitToElements � inicializado como false
 * 
 * Vers�o 01.07:
 * - adicionado m�todo refreshAndFit
 * - m�todos getFitToElements e setFitToElements renomeados para isAutoFitToElements
 * setAutoFitToElements
 *
 * Vers�o 01.05: (ZM_20100418)
 * - aceita borda nula, usando a borda default
 * - permite inserir um elemento em qualquer posi��o (ordena��o) no layout, n�o
 * mais apenas no final
 * - autoRefresh
 * - renomeados alguns m�todos e atributos
 * - corre��o na funcionalide de stretch
 *
 * Vers�o 01.04: cria��o da classe (ZM_20100417)
 */

