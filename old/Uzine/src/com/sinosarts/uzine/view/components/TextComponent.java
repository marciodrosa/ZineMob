package com.sinosarts.uzine.view.components;

import com.sinosarts.uzine.language.Language;
import com.sinosarts.uzine.view.Component;
import com.sinosarts.zinemob.renderelements.RenderElement;
import com.sinosarts.zinemob.renderelements.RenderableText;
import com.sinosarts.zinemob.renderelements.RenderableTextFont;

/**
 * Componente de texto fixo (não-editável) para ser adicionado em Containers. Pode
 * ser utilizado para labels ou como links (botões, listas, etc.).
 */
public class TextComponent extends Component {

	private RenderableText normalText;
	private RenderableText focusText;
	private boolean focus;
	private String text = "";

	/**
	 * Construtor.
	 * @param id o Id do component
	 * @param text o texto para o componente (pode ser uma chave para a classe
	 * Language)
	 * @param font a fonte utilizada para o desenho do texto
	 * @param focusFont a fonte utilizada para o desenho do texto quando está em
	 * foco, pode ser null para não utilizar uma fonte diferente quando estiver
	 * em foco
	 */
	public TextComponent (int id, String text, RenderableTextFont font, RenderableTextFont focusFont) {
		super(id);

		normalText = new RenderableText("", font, true);

		if (focusFont != null && focusFont != font)
			focusText = new RenderableText("", focusFont, true);
		else
			focusText = normalText;

		setText(text);
	}

	public RenderElement getRenderElement() {
		if(focus)
			return focusText;
		else
			return normalText;
	}

	public void onFocus(boolean focus) {
		this.focus = focus;
		requestRepaint();
	}

	/**
	 * Altera o texto do componente.
	 * @param text o texto do componente (pode ser uma chave para a classe Language)
	 */
	public void setText(String text) {
		this.text = text;

		normalText.setText(text);

		if (focusText != normalText)
			focusText.setText(Language.getInstance().translate(text));
	}

	/**
	 * Retorna o texto do componente.
	 * @return o texto do componente
	 */
	public String getText() {
		return text;
	}

}
