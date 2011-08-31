package com.sinosarts.uzine.events;

/**
 * Eventos gerados para serem tratados exclusivamente por um objeto Window.
 */
public class WindowEvent {

	/**
	 * Evento lan�ado quando a janela ser� exibida.
	 */
	public static final byte EVT_SHOW = 1;

	/**
	 * Evento lan�ado quando a janela perde o foco para outra janela que � exibida
	 * SOBRE esta. Este evento n�o � lan�ado quando a janela perde o foco por ser
	 * fechada. Neste caso, o evento EVT_CLOSING � utilizado.
	 */
	public static final byte EVT_LOST_FOCUS_TO_ANOTHER_WINDOW = 2;

	/**
	 * Evento lan�ado quando a janela ganha o foco novamente, ap�s a janela que
	 * est� acima � removida da pilha de janelas e a janela volta a ser a janela
	 * atual. Este evento n�o � lan�ado quando a janela ganha o foco quando �
	 * exibida pela primeira vez, j� no topo da pilha; neste caso, o evento EVT_SHOW
	 * � utilizado.
	 */
	public static final byte EVT_FOCUS_RETURNED = 3;

	/**
	 * Evento lan�ado antes da janela ser fechada, no momento que perde o foco.
	 */
	public static final byte EVT_CLOSING = 4;

	private byte eventType;

	/**
	 * Construtor.
	 * @param eventType o ID do evento
	 */
	public WindowEvent(byte eventType) {
		this.eventType = eventType;
	}

	/**
	 * Retorna o ID do evento.
	 * @return o ID do evento (EVT_SHOW, EVT_LOST_FOCUS_TO_ANOTHER_WINDOW, EVT_FOCUS_RETURNED
	 * ou EVT_CLOSING)
	 */
	public byte getEventType() {
		return eventType;
	}
	
}
