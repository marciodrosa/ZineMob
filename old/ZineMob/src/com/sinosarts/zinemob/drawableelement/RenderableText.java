package com.sinosarts.zinemob.drawableelement;

import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * RenderableText renderiza uma imagem contendo algum texto a partir de uma
 * fonte no formato de imagem (carregada e definida com um objeto da classe
 * RenderableTextFont).
 *
 * Sobre o pr�-processamento: se o pr�-processamento for ativado, todo o texto �
 * desenhado uma �nica vez em um buffer e s� � reprocessado se for feita alguma
 * mudan�a. Este m�todo poupa processamento no momento de desenhar m�ltiplas vezes
 * o texto, por�m gasta mais mem�ria. Al�m disso, este m�todo de pr�-processamento
 * n�o permite alpha blending, utilizando apenas uma cor-chave para a transpar�ncia.
 */
public class RenderableText extends DrawableElement
{
	private Image image;
	private char[] text;
	private RenderableTextFont font;
	private int w=0, h=0;
	private int maxWidth = -1;
	private boolean preProcess;
	
	private int typeWriterEffectSpeed = -1;
	private int currentTextLength = 0;

	// <Integer, Integer> (atua como set)
	private Hashtable newLineIndexesSet = new Hashtable();
	
	/**
	 * Construtor.
	 * @param text o texto que ser� desenhado
	 * @param font a fonte utilizada
	 * @param preProcess true para ativar o pr�-processamento, false para desenhar
	 * em tempo real
	 */
	public RenderableText (String text, RenderableTextFont font, boolean preProcess) {
		init(text, font, preProcess, -1);
	}

	/**
	 * Construtor.
	 * @param text o texto que ser� desenhado
	 * @param font a fonte utilizada
	 * @param preProcess true para ativar o pr�-processamento, false para desenhar
	 * em tempo real
	 * @param maxWidth a largura m�xima do texto
	 */
	public RenderableText (String text, RenderableTextFont font, boolean preProcess, int maxWidth) {
		init(text, font, preProcess, maxWidth);
	}

	private void init(String text, RenderableTextFont font, boolean preProcess, int maxWidth) {
		this.preProcess = preProcess;

		setPosition (0,0);
		this.text = text.toCharArray();
		this.font = font;

		typeWriterEffectSpeed = -1;

		if (maxWidth != -1)
			setMaxWidth(maxWidth);
		else {
			if (preProcess)
				refresh();
			else
				processWidthAndHeight();
		}
	}
	
	/**
	 * Ativa o efeito de m�quina de escrever. Assim, o texto ser� desenhado em
	 * partes, ou seja, caracter a caracter. A anima��o � iniciada logo ap�s a
	 * chamada deste m�todo, sendo atualizada quando o texto � desenhado.
	 *
	 * N�o h� efeito se o objeto for pr�-processado.
	 *
	 * @param set true para ativar o efeito, false para desativar
	 * @param speed a velocidade com que os caracteres s�o desenhados. Com o valor
	 * igual a 1, toda vez que o objeto for desenhado uma letra a mais ser�
	 * colocada. Se for igual a 2, duas letras ser�o adicionadas ao final da
	 * String, e assim sucessivamente.
	 */
	public void setTypeWriterEffect (boolean set, int speed)
	{
		if (set)
			typeWriterEffectSpeed = speed;
		else
			typeWriterEffectSpeed = -1;
		
		currentTextLength = 0;
	}
	
	/**
	 * Retorna a string do RenderableText no formato de vetor de char, que � a
	 * maneira utilizada internamente pela classe.
	 * @return o texto no formato de um array de caracteres
	 */
	public char[] getText() {
		return text;
	}
	
	/**
	 * Define o texto. Se o objeto for pr�-processado, o pr�-processamento �
	 * imediatamente realizado.
	 * @param s o texto a ser utilizado
	 */
	public synchronized void setText (String s)
	{
		text = s.toCharArray();
		if (preProcess) refresh();
		else processWidthAndHeight();
	}
	
	/**
	 * Retorna a fonte que est� sendo utilizada.
	 * @return a fonte que est� sendo utilizada
	 */
	public RenderableTextFont getFont() {
		return font;
	}
	
	/**
	 * Define a fonte a ser utilizada. Se o texto for pr�-processado, o pr�-processamento
	 * � realizado imediatamente.
	 * @param f a fonte a ser utilizada para renderizar o texto
	 */
	public void setFont (RenderableTextFont f) {
		font = f;
		if (preProcess)
			refresh();
		else
			processWidthAndHeight();
	}
	
	/**
	 * Faz com que o objeto que cont�m o texto seja obrigatoriamente da largura
	 * especificada. Desta maneira, as quebras de linha da string s�o automaticamente
	 * calculadas.
	 * @param w a largura do buffer do elemento, onde o texto deve caber (com
	 * quebra de linha, se for necess�rio). O valor -1 (padr�o) indica que o
	 * valor da largura deve ser calculado de acordo com a string (logo, n�o h�
	 * quebra autom�tica de linha).
	 */
	public void setMaxWidth (int w)
	{
		maxWidth = w;
		if (preProcess)
			refresh();
		else
			processWidthAndHeight();
	}

	/**
	 * Retorna o valor definido em setMaxWidth. -1 indica que n�o h� largura m�xima
	 * definida.
	 * @return a largura m�xima definida
	 */
	public int getMaxWidth() {
		return maxWidth;
	}
	
	protected synchronized void drawNode (Graphics g)
	{
		if (preProcess)
			g.drawImage (image, 0, 0, Graphics.TOP | Graphics.LEFT);
		else
			drawOnGraphics (g);

		// teste:
//		g.setColor(0xffff0000);
//		g.drawRect(0, 0, w, h);
	}

	// processa o texto a partir do �ndice especificado no par�metro at� a pr�xima
	// quebra de linha do texto ou at� que seja detectado que uma quebra de linha
	// artificial deva ser inserida porque o texto extrapolou a largura m�xima.
	//
	// Retorna o �ndice seguinte do texto ap�s o final da linha processada.
	//
	// Quando � detectada a necessidade de colocar uma quebra de linha artificial,
	// esta quebra � adicionada na hash newLineIndexesSet. A largura do objeto
	// tamb�m � atualizada de acordo com o espa�o ocupado pela linha.
	//
	private int processLine(int charIndex) {

		int lastBlankSpaceIndex = 0;
		int x = 0;
		int i = charIndex;

		byte[] metrics = font.getFontMetrics();
		
		for (i=charIndex; i<text.length; i++) {

			if (text[i] > 255)
				continue;

			x += metrics[text[i]];

			if (text[i] == ' ' || text[i] == '	')
				lastBlankSpaceIndex = i;
			else if (text[i] == '\n')
				break;

			if (maxWidth > 0 && x > maxWidth) { // extrapolou a largura m�xima, volta at� o �ltimo espa�o em branco, onde ser� posto uma nova linha artificial
				if (lastBlankSpaceIndex > 0) {
					newLineIndexesSet.put(new Integer(lastBlankSpaceIndex), new Integer(lastBlankSpaceIndex));

					// volta os caracteres percorridos, diminuindo a vari�vel X:
					for (; i>lastBlankSpaceIndex; i--)
						x -= metrics[text[i]];
				}
				else // n�o h� espa�o em branco na linha, o que indica que apenas uma palavra extrapolou toda a largura da linha; � necess�rio cortar a palavra ao meio
					newLineIndexesSet.put(new Integer(i), new Integer(i));

				break;
			}
		}

		if (x > w)
			w = x;

		return i + 1;
		
	}

	private void processWidthAndHeight()
	{
		w=0;
		h=0;

		newLineIndexesSet = new Hashtable();

		int areaY = font.getFontImage().getHeight()/16;

		for (int i = 0; i < text.length; i = processLine(i)) {
			h += areaY;
		}
	}
	
	private void drawOnGraphics (Graphics g)
	{
		byte[] metrics = font.getFontMetrics();
		Image fontImage = font.getFontImage();
		int posCursor=0, posLine=0, lineCount=0;
		char character=0;
		int areaX = fontImage.getWidth()/16;
		int areaY = fontImage.getHeight()/16;
		
		int length=0;
		
		if (typeWriterEffectSpeed>0)
		{
			length = currentTextLength;
			currentTextLength += typeWriterEffectSpeed;
			if (currentTextLength>text.length)
				typeWriterEffectSpeed = -1; // passar� a tratar como se n�o houvesse o efeito
		}
		else
			length = text.length;
		
		// desenha as letras no graphics: --------------------
		for (int i=0; i<length; i++)
		{
			character = text[i];
			if (character > 255)
				continue;

			if (newLineIndexesSet.contains(new Integer(i)) || character == '\n') {
				posLine += areaY;
				posCursor = 0;
				lineCount++;
			}

			int characterWidth = metrics[character];
			int characterHeight = areaY;

			onCharacterPut(character, i, lineCount, posCursor, posLine, characterWidth, characterHeight);
			
			g.drawRegion(fontImage, (character%16)*areaX,
					(character/16)*areaY, characterWidth, characterHeight, 0,
					posCursor, posLine, 0);
			
			posCursor += metrics[character];
		}//---------------------------------------------------
	}

	// chamado para repintar o buffer (ao trocar a string ou a fonte)
	// m�todo lento e pesado - mas chamado apenas se preProcess = true
	public synchronized void refresh()
	{
		image = null; // para liberar a imagem atual e, consequentemente, liberar mem�ria para o processo
		System.gc();
		
		// passo 1) calcula o tamanho do buffer -------------
		processWidthAndHeight();
		
		if (w==0)
			w=1; //imagem vazia, de 1 pixel, apenas para evitar bugs
		if (h==0)
			h=1; //imagem vazia, de 1 pixel, apenas para evitar bugs
		
		Image imageBuffer = Image.createImage (w, h);
		Graphics graphics = imageBuffer.getGraphics();
		graphics.setColor (0xff00ffff);
		graphics.fillRect (0, 0, w, h);
		
		drawOnGraphics (graphics);
		
		// pega os bytes da imagem
		int[] byteArray = new int[w*h];
		imageBuffer.getRGB (byteArray, 0, w, 0, 0, w, h);
		imageBuffer = null;
		System.gc();

		if (font.isUsingFontImageColor()) {
			// troca os pixels magenta por transpar�ncia:
			for (int i=0; i<byteArray.length; i++) {
				if (byteArray[i] == 0xff00ffff) 
					byteArray[i] = 0x00000000;
			}
		}
		else {
			// troca os pixels magenta por transpar�ncia; os demais s�o da cor da fonte:
			int color = font.getFontColor();
			for (int i=0; i<byteArray.length; i++) {
				if (byteArray[i] == 0xff00ffff)
					byteArray[i] = 0x00000000;
				else
					byteArray[i] = color;
			}
		}
		
		// atualiza a imagem:
		image = Image.createRGBImage (byteArray, w, h, true);
		
		System.gc();
	}

	public int getHeight() {
		return h;
	}

	public int getWidth() {
		if (maxWidth > 0 && w > maxWidth)
			return maxWidth;
		return w;
	}

	/**
	 * Retorna a imagem contendo o texto (apenas se o RenderableText for do tipo
	 * pre-processado).
	 * @return a imagem contendo o texto, ou null se o RenderableText n�o for do
	 * tipo pr�-processado
	 */
	public Image getPreProcessedImage() {
		return image;
	}

	/**
	 * M�todo chamado internamente quando o texto est� sendo transformado em desenho,
	 * seja pr�-processado ou n�o. Por padr�o este m�todo n�o faz nada, mas pode
	 * ser reimplementado para fazer tratamentos especiais.
	 * @param character o caracter que ser� desenhado
	 * @param characterIndex o �ndice do caracter no array de caracteres que formam
	 * o texto
	 * @param currentLine a linha que est� sendo desenhado o caracter
	 * @param charPositionX a coordenada X da posi��o em que o caracter ser�
	 * desenhado
	 * @param charPositionY a coordenada Y da posi��o em que o caracter ser�
	 * desenhado
	 * @param charWidth a largura do desenho do caracter
	 * @param charHeight a altura do desenho do caracter
	 */
	protected void onCharacterPut(char character, int characterIndex, int currentLine,
			int charPositionX, int charPositionY, int charWidth, int charHeight) {
	}
}

/*
 * Vers�o 01.07: (post ZM_20100425_4)
 * - altera��o nas cores das fontes
 * 
 * Vers�o 01.03: (post MT_20100411)
 * - caracteres com valores acima de 255 s�o ignorados
 *
 * Vers�o 01.02: (post MT_20100406_2)
 * - n�o modifica mais o conte�do interno do texto quando for�a uma largura m�xima
 * para o desenho
 * - m�todos set e getString renomeados para set e getText
 * - m�todo forceWidth renomeado para setMaxWidth
 *
 * Vers�o 01.01: (post MT_20100403_2)
 * - novo m�todo onCharacterPut e redocumenta��o do c�digo
 * - novo construtor que recebe a largura m�xima
 */
