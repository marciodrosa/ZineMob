package com.sinosarts.uzine.events;

/**
 * Evento de entrada genérico. Utilizado para tratamento de eventos de entradas
 * em alto nível; isto é, ao invés de gerenciar eventos de entrada utilizando
 * diretamente as teclas do teclado ou o toque do apontador, utiliza ID de ações
 * específicas.
 */
public class InputEvent {

	/**
	 * Evento que indica que o usuário solicitou que o item seguinte ao item atualmente
	 * em foco seja selecionado.
	 */
	public static final byte EVT_SELECTNEXT = 1;
	
	/**
	 * Evento que indica que o usuário solicitou que o item anterior ao item atualmente
	 * em foco seja selecionado.
	 */
	public static final byte EVT_SELECTPREVIOUS = 2;

	/**
	 * Evento que indica que o usuário realizou a ação do item atualmente em foco.
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
