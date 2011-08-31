package com.sinosarts.uzine.events;

/**
 * Eventos gerados para serem tratados exclusivamente por um objeto Window.
 */
public class WindowEvent {

	/**
	 * Evento lançado quando a janela será exibida.
	 */
	public static final byte EVT_SHOW = 1;

	/**
	 * Evento lançado quando a janela perde o foco para outra janela que é exibida
	 * SOBRE esta. Este evento não é lançado quando a janela perde o foco por ser
	 * fechada. Neste caso, o evento EVT_CLOSING é utilizado.
	 */
	public static final byte EVT_LOST_FOCUS_TO_ANOTHER_WINDOW = 2;

	/**
	 * Evento lançado quando a janela ganha o foco novamente, após a janela que
	 * está acima é removida da pilha de janelas e a janela volta a ser a janela
	 * atual. Este evento não é lançado quando a janela ganha o foco quando é
	 * exibida pela primeira vez, já no topo da pilha; neste caso, o evento EVT_SHOW
	 * é utilizado.
	 */
	public static final byte EVT_FOCUS_RETURNED = 3;

	/**
	 * Evento lançado antes da janela ser fechada, no momento que perde o foco.
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
