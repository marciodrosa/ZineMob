package com.sinosarts.zinemob.drawableelement;

import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * RenderableText renderiza uma imagem contendo algum texto a partir de uma
 * fonte no formato de imagem (carregada e definida com um objeto da classe
 * RenderableTextFont).
 *
 * Sobre o pré-processamento: se o pré-processamento for ativado, todo o texto é
 * desenhado uma única vez em um buffer e só é reprocessado se for feita alguma
 * mudança. Este método poupa processamento no momento de desenhar múltiplas vezes
 * o texto, porém gasta mais memória. Além disso, este método de pré-processamento
 * não permite alpha blending, utilizando apenas uma cor-chave para a transparência.
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
	 * @param text o texto que será desenhado
	 * @param font a fonte utilizada
	 * @param preProcess true para ativar o pré-processamento, false para desenhar
	 * em tempo real
	 */
	public RenderableText (String text, RenderableTextFont font, boolean preProcess) {
		init(text, font, preProcess, -1);
	}

	/**
	 * Construtor.
	 * @param text o texto que será desenhado
	 * @param font a fonte utilizada
	 * @param preProcess true para ativar o pré-processamento, false para desenhar
	 * em tempo real
	 * @param maxWidth a largura máxima do texto
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
	 * Ativa o efeito de máquina de escrever. Assim, o texto será desenhado em
	 * partes, ou seja, caracter a caracter. A animação é iniciada logo após a
	 * chamada deste método, sendo atualizada quando o texto é desenhado.
	 *
	 * Não há efeito se o objeto for pré-processado.
	 *
	 * @param set true para ativar o efeito, false para desativar
	 * @param speed a velocidade com que os caracteres são desenhados. Com o valor
	 * igual a 1, toda vez que o objeto for desenhado uma letra a mais será
	 * colocada. Se for igual a 2, duas letras serão adicionadas ao final da
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
	 * Retorna a string do RenderableText no formato de vetor de char, que é a
	 * maneira utilizada internamente pela classe.
	 * @return o texto no formato de um array de caracteres
	 */
	public char[] getText() {
		return text;
	}
	
	/**
	 * Define o texto. Se o objeto for pré-processado, o pré-processamento é
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
	 * Retorna a fonte que está sendo utilizada.
	 * @return a fonte que está sendo utilizada
	 */
	public RenderableTextFont getFont() {
		return font;
	}
	
	/**
	 * Define a fonte a ser utilizada. Se o texto for pré-processado, o pré-processamento
	 * é realizado imediatamente.
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
	 * Faz com que o objeto que contém o texto seja obrigatoriamente da largura
	 * especificada. Desta maneira, as quebras de linha da string são automaticamente
	 * calculadas.
	 * @param w a largura do buffer do elemento, onde o texto deve caber (com
	 * quebra de linha, se for necessário). O valor -1 (padrão) indica que o
	 * valor da largura deve ser calculado de acordo com a string (logo, não há
	 * quebra automática de linha).
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
	 * Retorna o valor definido em setMaxWidth. -1 indica que não há largura máxima
	 * definida.
	 * @return a largura máxima definida
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

	// processa o texto a partir do índice especificado no parâmetro até a próxima
	// quebra de linha do texto ou até que seja detectado que uma quebra de linha
	// artificial deva ser inserida porque o texto extrapolou a largura máxima.
	//
	// Retorna o índice seguinte do texto após o final da linha processada.
	//
	// Quando é detectada a necessidade de colocar uma quebra de linha artificial,
	// esta quebra é adicionada na hash newLineIndexesSet. A largura do objeto
	// também é atualizada de acordo com o espaço ocupado pela linha.
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

			if (maxWidth > 0 && x > maxWidth) { // extrapolou a largura máxima, volta até o último espaço em branco, onde será posto uma nova linha artificial
				if (lastBlankSpaceIndex > 0) {
					newLineIndexesSet.put(new Integer(lastBlankSpaceIndex), new Integer(lastBlankSpaceIndex));

					// volta os caracteres percorridos, diminuindo a variável X:
					for (; i>lastBlankSpaceIndex; i--)
						x -= metrics[text[i]];
				}
				else // não há espaço em branco na linha, o que indica que apenas uma palavra extrapolou toda a largura da linha; é necessário cortar a palavra ao meio
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
				typeWriterEffectSpeed = -1; // passará a tratar como se não houvesse o efeito
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
	// método lento e pesado - mas chamado apenas se preProcess = true
	public synchronized void refresh()
	{
		image = null; // para liberar a imagem atual e, consequentemente, liberar memória para o processo
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
			// troca os pixels magenta por transparência:
			for (int i=0; i<byteArray.length; i++) {
				if (byteArray[i] == 0xff00ffff) 
					byteArray[i] = 0x00000000;
			}
		}
		else {
			// troca os pixels magenta por transparência; os demais são da cor da fonte:
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
	 * @return a imagem contendo o texto, ou null se o RenderableText não for do
	 * tipo pré-processado
	 */
	public Image getPreProcessedImage() {
		return image;
	}

	/**
	 * Método chamado internamente quando o texto está sendo transformado em desenho,
	 * seja pré-processado ou não. Por padrão este método não faz nada, mas pode
	 * ser reimplementado para fazer tratamentos especiais.
	 * @param character o caracter que será desenhado
	 * @param characterIndex o índice do caracter no array de caracteres que formam
	 * o texto
	 * @param currentLine a linha que está sendo desenhado o caracter
	 * @param charPositionX a coordenada X da posição em que o caracter será
	 * desenhado
	 * @param charPositionY a coordenada Y da posição em que o caracter será
	 * desenhado
	 * @param charWidth a largura do desenho do caracter
	 * @param charHeight a altura do desenho do caracter
	 */
	protected void onCharacterPut(char character, int characterIndex, int currentLine,
			int charPositionX, int charPositionY, int charWidth, int charHeight) {
	}
}

/*
 * Versão 01.07: (post ZM_20100425_4)
 * - alteração nas cores das fontes
 * 
 * Versão 01.03: (post MT_20100411)
 * - caracteres com valores acima de 255 são ignorados
 *
 * Versão 01.02: (post MT_20100406_2)
 * - não modifica mais o conteúdo interno do texto quando força uma largura máxima
 * para o desenho
 * - métodos set e getString renomeados para set e getText
 * - método forceWidth renomeado para setMaxWidth
 *
 * Versão 01.01: (post MT_20100403_2)
 * - novo método onCharacterPut e redocumentação do código
 * - novo construtor que recebe a largura máxima
 */
