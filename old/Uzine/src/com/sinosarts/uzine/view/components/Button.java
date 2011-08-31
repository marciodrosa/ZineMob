package com.sinosarts.uzine.view.components;

import com.sinosarts.uzine.language.Language;
import com.sinosarts.uzine.view.Component;
import com.sinosarts.uzine.view.theme.Theme;
import com.sinosarts.zinemob.renderelements.Color;
import com.sinosarts.zinemob.renderelements.ReflectivePanel;
import com.sinosarts.zinemob.renderelements.RenderElement;
import com.sinosarts.zinemob.renderelements.RenderableText;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;

/**
 * Componente botão para ser adicionado aos Containers.
 */
public class Button extends Component {

	private LayoutElement layout = new LayoutElement(LayoutElement.LAYOUT_VERTICAL, false);
	private ReflectivePanel panel = new UZReflectivePanel();
	private RenderableText label;
	private int color;

	/**
	 * Construtor.
	 * @param id o Id do componente
	 * @param label o rótulo do botão
	 */
	public Button(int id, String label) {
		super(id);
		init(label);
	}

	/**
	 * Construtor.
	 * @param label o rótulo do botão
	 */
	public Button(String label) {
		init(label);
	}

	private void init(String label) {
		this.label = new RenderableText("", Theme.getCurrentTheme().getFontsManager().getButtonsFont(), true);

		layout.setAutoRefresh(true);

		color = Theme.getCurrentTheme().getColorPallete().getDefaultButtonColor();
		panel.setColor(color);
		panel.setWireColor(Theme.getCurrentTheme().getColorPallete().getButtonBorderColor());
		panel.setDrawMode(UZReflectivePanel.MODE_FILL_AND_WIRE);
		panel.setRoundedCorner(Theme.getCurrentTheme().getLayoutManager().getButtonsRoundedCorners());
		panel.setReflectionDistanceFromBorder(2);

		layout.addToLayout(this.label, Theme.getCurrentTheme().getLayoutManager().createButtonsInternalContentBorder(), LayoutElement.CENTER, true);

		setLabel(label);
		layout.refreshAndFit();

		panel.setSize(layout.getWidth(), layout.getHeight());
		panel.attachFitElement(layout);
	}

	public void setLabel(String label) {
		this.label.setText(Language.getInstance().translate(label));
	}

	public String getLabel() {
		return new String(label.getText());
	}

	public void onFocus(boolean focus) {
		super.onFocus(focus);
		if (focus) {
			Color c = new Color(color);

			int luminosityIndex = Theme.getCurrentTheme().getLayoutManager().getFocusLuminosityIndex();

			int r = c.getRedComponent() + luminosityIndex;
			int g = c.getGreenComponent() + luminosityIndex;
			int b = c.getBlueComponent() + luminosityIndex;

			if (r < 0)
				r = 0;
			else if (r > 255)
				r = 255;
			if (g < 0)
				g = 0;
			else if (g > 255)
				g = 255;
			if (b < 0)
				b = 0;
			else if (b > 255)
				b = 255;

			c.setComponents(r, g, b, 255);

			panel.setColor(c.getComponents());
		}
		else {
			panel.setColor(color);
		}
	}

	public int getCursorPositionPointX() {
		return getRenderElement().getWidth() - 10;
	}

	public RenderElement getRenderElement() {
		return panel;
	}

}
