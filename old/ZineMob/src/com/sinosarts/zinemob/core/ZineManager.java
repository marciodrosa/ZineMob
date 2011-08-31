package com.sinosarts.zinemob.core;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;


/**
 * Classe que disponibiliza o controle sobre os aspectos prim�rios da aplica��o,
 * como imagem e controle. Traz novos recursos em rela��o � classe GameCanvas.
 */
public class ZineManager extends GameCanvas
{
	private static ZineManager instance;
	private KeyListener keyListener;
	private Graphics graphics;
	private int screenW = -1, screenH = -1;

	private ZineManager() {
		super (false);
		graphics = super.getGraphics();
	}

	/**
	 * Retorna a inst�ncia singleton da classe.
	 * @return a inst�ncia da classe GameManager
	 */
	public synchronized static ZineManager getInstance()
	{
		if (instance==null)
			instance = new ZineManager();
		return instance;
	}

	/**
	 * Retorna o contexto gr�fico utilizado pela aplica��o. Ao contr�rio do m�todo
	 * disponibilizado na classe GameCanvas, este pode ser chamado livremente em
	 * qualquer parte da aplica��o sem perda de desempenho, pois n�o cria novos
	 * objetos a cada chamada.
	 * @return o contexto gr�fico da aplica��o
	 */
	public Graphics getGraphics() {
		return graphics;
	}

	/**
	 * Limpa a tela, deixando-a totalmente preta.
	 */
	public void clearScreen() {
		graphics.setColor (0, 0, 0);
		graphics.fillRect (0, 0, getRealWidth(), getRealHeight());
	}

	/**
	 * Retorna a largura da tela. Se uma nova largura foi definida atrav�s do m�todo
	 * setCustomScreenArea, retorna este valor. Caso contr�rio, � retornado o tamanho
	 * real da tela do dispositivo.
	 * @return a largura da tela
	 */
	public int getWidth() {
		if (screenW == -1)
			return super.getWidth();
		return screenW;
	}

	/**
	 * Retorna a altura da tela. Se uma nova altura foi definida atrav�s do m�todo
	 * setCustomScreenArea, retorna este valor. Caso contr�rio, � retornado o tamanho
	 * real da tela do dispositivo.
	 * @return a altura da tela
	 */
	public int getHeight() {
		if (screenH == -1)
			return super.getHeight();
		return screenH;
	}

	/**
	 * Retorna o tamanho real da tela do dispositivo, ignorando o valor usado na
	 * chamada para o m�todo setCustomScreenArea. Este m�todo � o equivalente �
	 * chamada getWidth da classe GameCanvas.
	 * @return a largura da tela do disposivito
	 */
	public int getRealWidth() {
		return super.getWidth();
	}

	/**
	 * Retorna o tamanho real da tela do dispositivo, ignorando o valor usado na
	 * chamada para o m�todo setCustomScreenArea. Este m�todo � o equivalente �
	 * chamada getHeight da classe GameCanvas.
	 * @return a altura da tela do disposivito
	 */
	public int getRealHeight() {
		return super.getHeight();
	}

	/**
	 * Define o objeto KeyListener que ser� avisado sobre eventos relacionados �s
	 * teclas do dispositivo
	 * @param kl o KeyListener que ser� avisado dos eventos de teclas
	 */
	public void setKeyListener (KeyListener kl) {
		keyListener = kl;
	}

	/**
	 * Retorna o objeto KeyListener definido em setKeyListener.
	 * @return o objeto KeyListener definido em setKeyListener
	 */
	public KeyListener getCurrentKeyListener() {
		return keyListener;
	}

	protected void keyPressed (int keyCode) {
		if (keyListener!=null)
			keyListener.keyPressed(keyCode);
	}

	protected void keyReleased (int keyCode) {
		if (keyListener!=null)
			keyListener.keyReleased(keyCode);
	}

	protected void keyRepeated (int keyCode) {
		if (keyListener!=null)
			keyListener.keyRepeated(keyCode);
	}

}

/*
 * Versao 01.05: removidos m�todos deprecated
 */
