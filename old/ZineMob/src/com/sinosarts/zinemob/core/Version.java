package com.sinosarts.zinemob.core;

public class Version {

	public static final String VERSION = "01.09";

}

/*
 * Vers�o 01.09: (post UZ_20100509)
 * - nova classe Properties
 * 
 * Vers�o 01.08:
 * - corre��o de bugs da classe Container, onde o foco inicial se dava no �ltimo
 * widget ao inv�s de ocorrer no primeiro e, em alguns casos, o foco n�o retornava
 * para o primeiro widget ap�s selecionar o elemento seguinte ao �ltimo
 * - novo m�todo getCurrentWindow na classw WindowsModule
 * - altera��es nos nomes de m�todos da classe AnimatedWindow e possibilidade de
 * definir uma anima��o padr�o para o fechamento da janela
 * 
 * Vers�o 01.07: (post ZM_20100425_4)
 * - possibilidade de escolher a cor da fonte de RenderableTexts
 * - altera��es e novo m�todo refreshAndFit no LayoutElement
 * - novo m�todo attachedFitElements na classe RenderElement
 * - reformula��o da classe AutoMovableElement
 * 
 * Vers�o 01.06: (post ZM_20100425)
 * - classe Cursor agora permite movimenta��o suave
 * - refatora��es nas classes ValuesInterpolation e AutoMovableElement
 * 
 * Vers�o 01.05: (post ZM_20100418)
 * - altera��es na classe LayoutElement: aceita borda nula, inserir elementos
 * em qualquer posi��o no layout (n�o mais apenas ao final) e auto-refresh
 * - reformula��o total nas classes de GUI, cria��o do pacote windows
 * - nova classe ReflectivePanel
 *
 * Vers�o 01.04: (post ZM_20100417)
 * - novo pacote layout
 * - classe RenderElement possui um novo m�todo setSize
 *
 * Vers�o 01.03: (post MT_20100411)
 * - novos m�todos getWidgetsCount e getWidget na classe Window
 * - RenderableText ignora caracteres com valor acima de 255
 *
 * Vers�o 01.02: (post MT_20100406_2)
 * - classe Widget agora possibilita que o seu ID seja alterado ap�s a constru��o
 * atrav�s do novo m�todo setId
 * - corrigido bug no m�todo removeChildren da classe RenderElement, que ocasionava
 * exce��o se o elemento n�o possu�sse nenhum filho
 * - classe RenderableText n�o modifica mais o conte�do interno do texto quando
 * for�a uma largura m�xima para o desenho
 * 
 * Vers�o 01.01: (post MT_20100403_2)
 * - novo m�todo onCharacterPut na classe RenderableText e documenta��o refeita
 * para esta classe
 * - novo construtor na classe RenderableText que recebe a largura m�xima do texto
 * - altera��o no algoritmo de desenho da classe Rectangle
 * 
 * Vers�o 01.00: post MT_20100403
 */
