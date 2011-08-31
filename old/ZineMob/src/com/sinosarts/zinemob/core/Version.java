package com.sinosarts.zinemob.core;

public class Version {

	public static final String VERSION = "01.09";

}

/*
 * Versão 01.09: (post UZ_20100509)
 * - nova classe Properties
 * 
 * Versão 01.08:
 * - correção de bugs da classe Container, onde o foco inicial se dava no último
 * widget ao invés de ocorrer no primeiro e, em alguns casos, o foco não retornava
 * para o primeiro widget após selecionar o elemento seguinte ao último
 * - novo método getCurrentWindow na classw WindowsModule
 * - alterações nos nomes de métodos da classe AnimatedWindow e possibilidade de
 * definir uma animação padrão para o fechamento da janela
 * 
 * Versão 01.07: (post ZM_20100425_4)
 * - possibilidade de escolher a cor da fonte de RenderableTexts
 * - alterações e novo método refreshAndFit no LayoutElement
 * - novo método attachedFitElements na classe RenderElement
 * - reformulação da classe AutoMovableElement
 * 
 * Versão 01.06: (post ZM_20100425)
 * - classe Cursor agora permite movimentação suave
 * - refatorações nas classes ValuesInterpolation e AutoMovableElement
 * 
 * Versão 01.05: (post ZM_20100418)
 * - alterações na classe LayoutElement: aceita borda nula, inserir elementos
 * em qualquer posição no layout (não mais apenas ao final) e auto-refresh
 * - reformulação total nas classes de GUI, criação do pacote windows
 * - nova classe ReflectivePanel
 *
 * Versão 01.04: (post ZM_20100417)
 * - novo pacote layout
 * - classe RenderElement possui um novo método setSize
 *
 * Versão 01.03: (post MT_20100411)
 * - novos métodos getWidgetsCount e getWidget na classe Window
 * - RenderableText ignora caracteres com valor acima de 255
 *
 * Versão 01.02: (post MT_20100406_2)
 * - classe Widget agora possibilita que o seu ID seja alterado após a construção
 * através do novo método setId
 * - corrigido bug no método removeChildren da classe RenderElement, que ocasionava
 * exceção se o elemento não possuísse nenhum filho
 * - classe RenderableText não modifica mais o conteúdo interno do texto quando
 * força uma largura máxima para o desenho
 * 
 * Versão 01.01: (post MT_20100403_2)
 * - novo método onCharacterPut na classe RenderableText e documentação refeita
 * para esta classe
 * - novo construtor na classe RenderableText que recebe a largura máxima do texto
 * - alteração no algoritmo de desenho da classe Rectangle
 * 
 * Versão 01.00: post MT_20100403
 */
