package com.zine.zinemob.scene.controller;

/**
 * Interface para um controlador que deve ser atualizado a cada iteração do laço
 * da cena. Se uma instância de SceneElementController implementar esta interface
 * e for adicionada a uma cena, então o seu método update é chamado a cada iteração
 * do laço, antes da rotina de desenho.
 */
public interface Updateble {

	/**
	 * Método que deve ser implementado para a rotina de atualização do controle.
	 */
	public void update();

}
