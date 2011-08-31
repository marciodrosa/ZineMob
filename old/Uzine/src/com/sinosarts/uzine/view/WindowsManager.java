package com.sinosarts.uzine.view;

import com.sinosarts.uzine.events.InputEvent;

public interface WindowsManager {

	/**
	 * Inicia e põe a janela como a janela ativa do módulo. Não faz nada se a janela
	 * já está sendo exibida em algum outro módulo ou neste mesmo módulo.
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
	 * Requisita que o elemento seja colocado em foco. O método onFocus do elemento
	 * é chamado, passando true como parâmetro. Antes disso, se havia outro elemento
	 * em foco, o método onFocus deste elemento é chamado, passando false como
	 * parâmetro.
	 * @param element o elemento a ser posto em foco, podendo ser null
	 */
	public void requestFocus(GuiElement element);

	/**
	 * Adiciona um evento de comando à pilha de eventos.
	 * @param event o evento a ser adicionado
	 */
	public void pushCommandEvent(CommandEvent event);

	/**
	 * Adiciona um evento genérico de input à pilha de eventos.
	 * @param event o evento a ser adicionado
	 */
	public void pushInputEvent(InputEvent event);


}
