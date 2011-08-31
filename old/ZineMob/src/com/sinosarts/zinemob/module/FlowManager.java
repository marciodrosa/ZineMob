package com.sinosarts.zinemob.module;

/**
 * Gerenciador de fluxo, utilizado pela classe GameFlow. É recomendado que esta
 * classe seja reimplementada pela aplicação, transformando-se em um singleton.
 * Também recomenda-se que nesta reimplementação sejam definidas as constantes
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
