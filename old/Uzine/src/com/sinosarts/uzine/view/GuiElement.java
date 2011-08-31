package com.sinosarts.uzine.view;

import com.sinosarts.uzine.events.InputEvent;
import com.sinosarts.zinemob.core.KeyListener;
import com.sinosarts.zinemob.renderelements.RenderElement;

public interface GuiElement extends KeyListener/*, PointerListener*/ {

	/**
	 * Retorna o elemento desenhável do elemento.
	 * @return o elemento desenhável do elemento
	 */
	public RenderElement getRenderElement();

	/**
	 * Solicita que o foco atual seja neste componente. O método onFocus(true)
	 * será chamado para este componente.
	 */
	public void requestFocus();

	/**
	 * Solicita que o gerenciador de janelas redesenhe a tela.
	 */
	public void requestRepaint();

	/**
	 * Método chamado para que o elemento faça o tratamento adequado para o evento
	 * de entrada genérico representado pelo InputEvent.
	 * @param inputEvent o evento que ocorreu
	 */
	public void onInputEvent(InputEvent inputEvent);

	/**
	 * Método chamado quando o componente ganha ou perde o foco.
	 * @param focus true se o componente ganhou foco, false se perdeu
	 */
	public void onFocus(boolean focus);

	/*
	 * TODO:
	 * Método chamado quando o componente é pressionado.
	 * @param pressed true se o componente foi pressionado, false se não está mais
	 * pressionado
	 */
	//public void onPressed(boolean pressed);

	/**
	 * Retorna o elemento pai deste. Pode retornar null se não houver elemento
	 * pai.
	 * @return o elemento pai
	 */
	public GuiElement getParentGuiElement();

	/**
	 * Retorna a coordenada X da posição que o cursor deve apontar quando o componente
	 * está em foco. Esta posição é relativa à posição do elemento renderizável
	 * deste componente.
	 * @return a coordenada X da posição que o cursor deve apontar quando o componente
	 * está em foco
	 */
	public int getCursorPositionPointX();

	/**
	 * Retorna a coordenada Y da posição que o cursor deve apontar quando o componente
	 * está em foco. Esta posição é relativa à posição do elemento renderizável
	 * deste componente.
	 * @return a coordenada Y da posição que o cursor deve apontar quando o componente
	 * está em foco
	 */
	public int getCursorPositionPointY();

}
