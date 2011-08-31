package com.sinosarts.uzine.view;

import com.sinosarts.uzine.events.InputEvent;

public interface WindowsManager {

	/**
	 * Inicia e p�e a janela como a janela ativa do m�dulo. N�o faz nada se a janela
	 * j� est� sendo exibida em algum outro m�dulo ou neste mesmo m�dulo.
	 * @param w a janela a ser exibida e ativada
	 */
	public void showWindow(Window w);

	/**
	 * Remove a janela da pilha de janelas.
	 * @param window a janela a ser removida
	 */
	public void removeWindow(Window window);

	/**
	 * Solicita que a tela seja repintada.
	 */
	public void requestRepaint();

	/**
	 * Requisita que o elemento seja colocado em foco. O m�todo onFocus do elemento
	 * � chamado, passando true como par�metro. Antes disso, se havia outro elemento
	 * em foco, o m�todo onFocus deste elemento � chamado, passando false como
	 * par�metro.
	 * @param element o elemento a ser posto em foco, podendo ser null
	 */
	public void requestFocus(GuiElement element);

	/**
	 * Adiciona um evento de comando � pilha de eventos.
	 * @param event o evento a ser adicionado
	 */
	public void pushCommandEvent(CommandEvent event);

	/**
	 * Adiciona um evento gen�rico de input � pilha de eventos.
	 * @param event o evento a ser adicionado
	 */
	public void pushInputEvent(InputEvent event);


}
