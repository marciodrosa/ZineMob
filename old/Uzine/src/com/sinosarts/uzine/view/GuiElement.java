package com.sinosarts.uzine.view;

import com.sinosarts.uzine.events.InputEvent;
import com.sinosarts.zinemob.core.KeyListener;
import com.sinosarts.zinemob.renderelements.RenderElement;

public interface GuiElement extends KeyListener/*, PointerListener*/ {

	/**
	 * Retorna o elemento desenh�vel do elemento.
	 * @return o elemento desenh�vel do elemento
	 */
	public RenderElement getRenderElement();

	/**
	 * Solicita que o foco atual seja neste componente. O m�todo onFocus(true)
	 * ser� chamado para este componente.
	 */
	public void requestFocus();

	/**
	 * Solicita que o gerenciador de janelas redesenhe a tela.
	 */
	public void requestRepaint();

	/**
	 * M�todo chamado para que o elemento fa�a o tratamento adequado para o evento
	 * de entrada gen�rico representado pelo InputEvent.
	 * @param inputEvent o evento que ocorreu
	 */
	public void onInputEvent(InputEvent inputEvent);

	/**
	 * M�todo chamado quando o componente ganha ou perde o foco.
	 * @param focus true se o componente ganhou foco, false se perdeu
	 */
	public void onFocus(boolean focus);

	/*
	 * TODO:
	 * M�todo chamado quando o componente � pressionado.
	 * @param pressed true se o componente foi pressionado, false se n�o est� mais
	 * pressionado
	 */
	//public void onPressed(boolean pressed);

	/**
	 * Retorna o elemento pai deste. Pode retornar null se n�o houver elemento
	 * pai.
	 * @return o elemento pai
	 */
	public GuiElement getParentGuiElement();

	/**
	 * Retorna a coordenada X da posi��o que o cursor deve apontar quando o componente
	 * est� em foco. Esta posi��o � relativa � posi��o do elemento renderiz�vel
	 * deste componente.
	 * @return a coordenada X da posi��o que o cursor deve apontar quando o componente
	 * est� em foco
	 */
	public int getCursorPositionPointX();

	/**
	 * Retorna a coordenada Y da posi��o que o cursor deve apontar quando o componente
	 * est� em foco. Esta posi��o � relativa � posi��o do elemento renderiz�vel
	 * deste componente.
	 * @return a coordenada Y da posi��o que o cursor deve apontar quando o componente
	 * est� em foco
	 */
	public int getCursorPositionPointY();

}
