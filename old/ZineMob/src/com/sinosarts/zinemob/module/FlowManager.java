package com.sinosarts.zinemob.module;

/**
 * Gerenciador de fluxo, utilizado pela classe GameFlow. � recomendado que esta
 * classe seja reimplementada pela aplica��o, transformando-se em um singleton.
 * Tamb�m recomenda-se que nesta reimplementa��o sejam definidas as constantes
 * com os identificadores para cada fluxo.
 */
public class FlowManager {
	
	private byte flow = -1;

	/**
	 * Define o fluxo atual.
	 * @param id o identificador do fluxo atual
	 */
	public void setFlow(byte id) {
		flow = id;
	}

	/**
	 * Retorna o fluxo atual, definido em setFlow.
	 * @return o identificador do fluxo atual
	 */
	public byte getFlow() {
		return flow;
	}

}
