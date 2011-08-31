package com.sinosarts.uzine.events;

/**
 * Evento de entrada gen�rico. Utilizado para tratamento de eventos de entradas
 * em alto n�vel; isto �, ao inv�s de gerenciar eventos de entrada utilizando
 * diretamente as teclas do teclado ou o toque do apontador, utiliza ID de a��es
 * espec�ficas.
 */
public class InputEvent {

	/**
	 * Evento que indica que o usu�rio solicitou que o item seguinte ao item atualmente
	 * em foco seja selecionado.
	 */
	public static final byte EVT_SELECTNEXT = 1;
	
	/**
	 * Evento que indica que o usu�rio solicitou que o item anterior ao item atualmente
	 * em foco seja selecionado.
	 */
	public static final byte EVT_SELECTPREVIOUS = 2;

	/**
	 * Evento que indica que o usu�rio realizou a a��o do item atualmente em foco.
	 */
	public static final byte EVT_ACTION = 3;

	private byte eventType;

	/**
	 * Construtor.
	 * @param eventType o ID do evento
	 */
	public InputEvent(byte eventType) {
		this.eventType = eventType;
	}

	/**
	 * Retorna o ID do evento.
	 * @return o ID do evento (EVT_SELECTNEXT, EVT_SELECTPREVIOUS ou EVT_ACTION)
	 */
	public byte getEventType() {
		return eventType;
	}
	
}
