package com.zine.zinemob.scene.controller;

/**
 * Interface para um controlador que deve ser atualizado a cada itera��o do la�o
 * da cena. Se uma inst�ncia de SceneElementController implementar esta interface
 * e for adicionada a uma cena, ent�o o seu m�todo update � chamado a cada itera��o
 * do la�o, antes da rotina de desenho.
 */
public interface Updateble {

	/**
	 * M�todo que deve ser implementado para a rotina de atualiza��o do controle.
	 */
	public void update();

}
