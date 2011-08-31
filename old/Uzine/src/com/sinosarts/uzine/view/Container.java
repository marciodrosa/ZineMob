package com.sinosarts.uzine.view;

import com.sinosarts.uzine.events.InputEvent;
import com.sinosarts.uzine.view.components.TextComponent;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.renderelements.RenderElement;
import com.sinosarts.zinemob.renderelements.RenderableTextFont;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;

/**
 * Componente que permite agrupar componentes e outros Containers dentro do seu
 * espa�o.
 */
public class Container implements GuiElement {

	WindowsManager windowsManager;
	Container parentContainer;

	private LayoutElement layout = new LayoutElement();
	private RenderableTextFont defaultFont, defaultFocusFont;

	private Vector components = new Vector(); // <GuiElement>
	private Component currentSelection = null;

	private boolean selectingNextComponentMode = true;

	public Container() {
		layout.setAutoRefresh(true);
	}

	/**
	 * Retorna o �ndice do componente atualmente selecionado. Mesmo que o
	 * container n�o esteja em foco, retornar� o �ndice do �ltimo componente em
	 * foco filho deste container.
	 * @return o �ndice do componente selecionado, -1 se n�o houver
	 */
	public int getSelectedComponentIndex() {
		if (currentSelection == null)
			return -1;
		else
			return components.indexOf(currentSelection);
	}

	public RenderElement getRenderElement() {
		return layout;
	}

	public void requestFocus() {
		if (windowsManager != null)
			windowsManager.requestFocus(this);
	}

	public void requestRepaint() {
		if (windowsManager != null)
			windowsManager.requestRepaint();
	}

	/**
	 * Faz o tratamento para os eventos gen�ricos de entrada para o componente.
	 * Por padr�o, trata apenas os eventos EVT_SELECTNEXT e EVT_SELECTPREVIOUS,
	 * alterando, com isso, o foco dos componentes filhos.
	 * 
	 * Os outros tipos de eventos s�o passados imediatamente para o elemento pai
	 * deste, atrav�s da chamada do mesmo m�todo onInputEvent.
	 * 
	 * @param inputEvent o evento de entrada a ser processado
	 */
	public void onInputEvent(InputEvent inputEvent) {

		requestRepaint();

		switch (inputEvent.getEventType()) {
			case InputEvent.EVT_SELECTNEXT:
				selectNext();
				return;
			case InputEvent.EVT_SELECTPREVIOUS:
				selectPrevious();
				return;
		}

		// se n�o foi processado o evento, manda para o componente pai processar:
		if (parentContainer != null)
			parentContainer.onInputEvent(inputEvent);
	}

	public void onFocus(boolean focus) {
		if (focus)
			setSelection(verifyChildComponentToFocus());
		requestRepaint();
	}

	public GuiElement getParentGuiElement() {
		return parentContainer;
	}

	public int getCursorPositionPointX() {
		return layout.getWidth();
	}

	public int getCursorPositionPointY() {
		return layout.getHeight();
	}

	public void keyPressed(int keyCode) {
		if (parentContainer != null)
			parentContainer.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {
		if (parentContainer != null)
			parentContainer.keyReleased(keyCode);
	}

	public void keyRepeated(int keyCode) {

		requestRepaint();
		
		int gameKeyCode = ZineManager.getInstance().getGameAction(keyCode);

		switch(gameKeyCode) {
			case Canvas.UP:
				if (layout.getLayoutType() == LayoutElement.LAYOUT_VERTICAL) {
					selectPrevious();
					return;
				}
				break;
			case Canvas.DOWN:
				if (layout.getLayoutType() == LayoutElement.LAYOUT_VERTICAL) {
					selectNext();
					return;
				}
				break;
			case Canvas.LEFT:
				if (layout.getLayoutType() == LayoutElement.LAYOUT_HORIZONTAL) {
					selectPrevious();
					return;
				}
				break;
			case Canvas.RIGHT:
				if (layout.getLayoutType() == LayoutElement.LAYOUT_HORIZONTAL) {
					selectNext();
					return;
				}
				break;
		}

		if (parentContainer != null)
			parentContainer.keyRepeated(keyCode);
	}

	private Component verifyChildComponentToFocus() {

		// se n�o h� componentes, retorna null
		if (components.isEmpty()) {
			return null;
		}
		// se o pai possui o mesmo layout, verifica se o foco chegou a este componente quando a sele��o foi incrementada ou decrementada:
		else if (parentContainer != null && ((LayoutElement)parentContainer.getRenderElement()).getLayoutType() == layout.getLayoutType()) {
			if (parentContainer.selectingNextComponentMode)
				return (Component)components.firstElement();
			else
				return (Component)components.lastElement();
		}
		// se j� havia um componente que havia sido selecionado quando o container estava em foco antes, volta a selecionar este componente:
		else if (currentSelection != null && components.contains(currentSelection))
			return currentSelection;
		else // neste caso, ou n�o havia sele��o antes ou o componente selecionado, por algum motivo, n�o existe mais:
			return (Component)components.firstElement();
	}

	private void setSelection(Component c) {
		currentSelection = c;
		if (currentSelection != null)
			currentSelection.requestFocus();
	}

	private void selectNext() {

		selectingNextComponentMode = true;

		// se n�o tem componentes, n�o faz nada:
		if (components.isEmpty())
			return;

		int index;

		// verifica qual � o componente atualmente selecionado para este container e incrementa o �ndice:
		if (currentSelection != null) {
			index = components.indexOf(currentSelection);
			index++;
		}
		else // se, por algum motivo, o componente n�o existe mais, seleciona o primeiro:
			index = 0;

		// se extrapolar (ou seja, a sele��o anterior era a �ltima), manda o evento para o componente pai ou retorna para o primeiro componente:
		if (index >= components.size()) {

			// volta o foco para o componente pai apenas se for do mesmo tipo de layout (vertical ou horizontal):
			if (parentContainer != null && ((LayoutElement)parentContainer.getRenderElement()).getLayoutType() == layout.getLayoutType()) {
				parentContainer.onInputEvent(new InputEvent(InputEvent.EVT_SELECTNEXT));
				return;
			}
			else // n�o h� componente pai ou o seu layout � diferente, ent�o seleciona novamente o primeiro componente:
				index = 0;
		}

		setSelection((Component)components.elementAt(index));
	}

	private void selectPrevious() {

		selectingNextComponentMode = false;

		// se n�o tem componentes, n�o faz nada:
		if (components.isEmpty())
			return;

		int index;

		// verifica qual � o componente atualmente selecionado para este container e decrementa o �ndice:
		if (currentSelection != null) {
			index = components.indexOf(currentSelection);
			index--;
		}
		else // se, por algum motivo, o componente n�o existe mais, seleciona o primeiro:
			index = 0;

		// se extrapolar (ou seja, a sele��o anterior era a primeira), manda o evento para o componente pai ou seleciona o �ltimo componente:
		if (index < 0) {
			// volta o foco para o componente pai apenas se for do mesmo tipo de layout (vertical ou horizontal):
			if (parentContainer != null && ((LayoutElement)parentContainer.getRenderElement()).getLayoutType() == layout.getLayoutType()) {
				parentContainer.onInputEvent(new InputEvent(InputEvent.EVT_SELECTPREVIOUS));
				return;
			}
			else // n�o h� componente pai ou o seu layout � diferente, ent�o seleciona o �ltimo componente:
				index = components.size() - 1;
		}

		setSelection((Component)components.elementAt(index));
	}

	/**
	 * Retorna a fonte padr�o, definida em setDefaultFont. Se n�o houver fonte
	 * definida, busca pela fonte do container pai. Se n�o houver, retorna null
	 * @return a fonte padr�o
	 */
	public RenderableTextFont getDefaultFont() {

		if (defaultFont != null)
			return defaultFont;
		else {
			if (parentContainer != null)
				return parentContainer.getDefaultFont();
			else
				return null;
		}
	}

	/**
	 * Define a fonte padr�o.
	 * @param defaultFont a fonte padr�o
	 */
	public void setDefaultFont(RenderableTextFont defaultFont) {
		this.defaultFont = defaultFont;
	}

	/**
	 * Retorna a fonte padr�o para foco, definida em setDefaultFocusFont. Se n�o
	 * houver fonte definida para o foco mas houver fonte padr�o definida (retorno
	 * do m�todo getDefaultFont), retorna a fonte padr�o. Se tamb�m n�o houver
	 * fonte padr�o definida, ent�o � retornada a fonte padr�o para o foco do container
	 * pai, se houver (se n�o houver, retornar� null).
	 * @return a fonte padr�o para foco
	 */
	public RenderableTextFont getDefaultFocusFont() {
		if (defaultFocusFont != null)
			return defaultFocusFont;
		else if (defaultFont != null)
			return defaultFont;
		else {
			if (parentContainer != null)
				return parentContainer.getDefaultFocusFont();
			else
				return null;
		}
	}

	/**
	 * Define a fonte padr�o para foco.
	 * @param defaultFocusFont a fonte padr�o
	 */
	public void setDefaultFocusFont(RenderableTextFont defaultFocusFont) {
		this.defaultFocusFont = defaultFocusFont;
	}

	/**
	 * Adiciona um container a este container.
	 * @param container o container a ser adicionado a este
	 */
	public void addContainer(Container container) {
		if (!components.contains(container)) {
			addGuiElement(container);
			initContainer(container);
			requestRepaint();
		}
	}

	/**
	 * Adiciona um container ao layout deste container. Ver o m�todo addToLayout
	 * para maiores explica��es sobre os par�metros.
	 * @param container o container a ser adicionado a este
	 * @param border a borda a ser utilizada
	 * @param constraints os par�metros do layout
	 * @param stretchable
	 */
	public void addContainerToLayout(Container container, Border border, int constraints, boolean stretchable) {
		if (!components.contains(container)) {
			addGuiElementToLayout(container, border, constraints, stretchable);
			initContainer(container);
			requestRepaint();
		}
	}

	/**
	 * Adiciona um componente a este container.
	 * @param componente o componente a ser adicionado a este container
	 */
	public void addComponent(Component component) {
		if (!components.contains(component)) {
			addGuiElement(component);
			initComponent(component);
			requestRepaint();
		}
	}

	/**
	 * Adiciona um componente ao layout deste container. Ver o m�todo addToLayout
	 * para maiores explica��es sobre os par�metros.
	 * @param componente o componente a ser adicionado a este container
	 * @param border a borda a ser utilizada
	 * @param constraints os par�metros do layout
	 * @param stretchable
	 */
	public void addComponentToLayout(Component component, Border border, int constraints, boolean stretchable) {

		if (!components.contains(component)) {
			addGuiElementToLayout(component, border, constraints, stretchable);
			initComponent(component);
			requestRepaint();
		}
	}

	/**
	 * M�todo facilitador. Cria um TextWidget e o adiciona ao layout chamando
	 * pelo m�todo addComponentToLayout. A fonte utilizada ser� a fonte padr�o e
	 * a fonte padr�o para foco (esta �ltima apenas se houver). Se n�o houver
	 * fonte padr�o, este m�todo n�o cria nenhum widget e retorna null.
	 * @param id o Id do componente
	 * @param label o texto do componente (pode ser uma chave para a classe Language)
	 * @param border
	 * @param constraints
	 * @param stretchable
	 * @return o widget criado e adicionado ao layou
	 */
	public TextComponent addTextComponentToLayout(int id, String label, Border border, int constraints, boolean stretchable) {

		RenderableTextFont normalFont = getDefaultFont();
		RenderableTextFont focusFont = getDefaultFocusFont();

		if (normalFont == null)
			return null;

		TextComponent textComponent = new TextComponent(id, label, normalFont, focusFont);
		addComponentToLayout(textComponent, border, constraints, stretchable);
		return textComponent;
	}

	/**
	 * Remove o Component ou o Container previamente adicionado a este Container.
	 * @param element o elemento a ser removido
	 */
	public void removeElement(GuiElement element) {
		components.removeElement(element);
		getRenderElement().removeChild(element.getRenderElement());
	}

	private void addGuiElement(GuiElement element) {
		components.addElement(element);
		layout.addChild(element.getRenderElement());
	}

	private void addGuiElementToLayout(GuiElement element, Border border, int constraints, boolean stretchable) {
		components.addElement(element);
		layout.addToLayout(element.getRenderElement(), border, constraints, stretchable);
	}

	private void initComponent(Component component) {
		component.parentElement = this;
		component.windowsManager = this.windowsManager;
	}

	private void initContainer(Container container) {
		container.parentContainer = this;
		container.windowsManager = this.windowsManager;
	}

}
