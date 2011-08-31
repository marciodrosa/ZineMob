package com.sinosarts.zinemob.core;

/**
 * SynchronizedFrame � utilizado para fazer a aplica��o rodar no frame rate estipulado.
 * @author M�rcio Daniel da Rosa
 */
public class SynchronizedFrame
{
	private ZineManager game;
	private byte milliseconds;
	private boolean constantFrameRate;	
	private long lastFrameTime;
	
	private boolean clearFrame;
	
	/**
	 * Contrutor padr�o. Inicia o frame sem defini��o de frame rate.
	 */
	public SynchronizedFrame() {
		game = ZineManager.getInstance();
		setFPS (0);
		clearFrameAfterFlush (false);
	}
	
	public SynchronizedFrame (int fps) {
		game = ZineManager.getInstance();
		setFPS (fps);
		clearFrameAfterFlush (false);
	}
	
	public void clearFrameAfterFlush (boolean clear)
	{
		clearFrame = clear;
	}
	
	/**
	 * Define a quantidade de frames por segundo em rela��o �s atualiza��es feitas.
	 * @param fps quandidade de frames por segundo (0 significa a n�o defini��o do
	 * frame rate).
	 */
	public void setFPS (int fps) {
		if (fps<=0)
			constantFrameRate = false;
		else {
			constantFrameRate = true;
			milliseconds = (byte)(1000/fps);
		}		
	}
	
	private long getFrameTime() {
		return System.currentTimeMillis() - lastFrameTime;
	}
	
	private void delay() // aguarda para se confirmar o tempo definido pelo fps
	{
		long frameTimeInMs = getFrameTime();

		if (frameTimeInMs<milliseconds)
		{
			try {
				Thread.sleep (milliseconds-frameTimeInMs);
			} catch (InterruptedException e) {}
		}
		lastFrameTime = System.currentTimeMillis();
	}
	
	/**
	 * Aguarda o momento necess�rio at� transcorrer o tempo do frame. Ap�s isso,
	 * o m�todo flushGraphics da classe Game � automaticamente chamado, atualizando
	 * a tela.
	 */
	public void update()
	{
		if (constantFrameRate)
			delay();

		game.flushGraphics();

		if (clearFrame)
			game.clearScreen();
	}

}
