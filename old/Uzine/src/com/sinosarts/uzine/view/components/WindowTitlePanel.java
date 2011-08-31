package com.sinosarts.uzine.view.components;

import com.sinosarts.uzine.view.theme.Theme;
import com.sinosarts.zinemob.renderelements.Rectangle;
import com.sinosarts.zinemob.renderelements.RenderableText;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.renderelements.layout.LayoutElement;

public class WindowTitlePanel extends LayoutElement {

	private RenderableText title;
	private UZReflectivePanel panel;
	private Rectangle bottomPanel;

	public WindowTitlePanel() {

		setLayoutType(LAYOUT_HORIZONTAL);
		setAutoFitToElements(false);

		panel = new UZReflectivePanel(0, 0, 1, 1,
				Theme.getCurrentTheme().getColorPallete().getTitleBarColor());

		addChild(panel);

		bottomPanel = new Rectangle(0, 0, 1, 1, Theme.getCurrentTheme().getColorPallete().getTitleBarColor());
		bottomPanel.setVisible(false);
		panel.addChild(bottomPanel);

		title = new RenderableText("", Theme.getCurrentTheme().getFontsManager().getWindowTitleFont(), true);

		Border defaultBorder = Theme.getCurrentTheme().getLayoutManager().createTitleBarInternalContentBorder();
		addToLayout(title, defaultBorder, ALIGN_LEFT | CENTER_V, true);

		// inicialização:
		setSize(10, Theme.getCurrentTheme().getLayoutManager().getTitleBarHeight());
		refresh();
	}

	public void setRoundedCorner(int radius) {
		panel.setRoundedCorner(radius);
		if (radius > 0) {
			bottomPanel.setVisible(true);
			bottomPanel.setSize(bottomPanel.getWidth(), radius);
		}
		else {
			bottomPanel.setVisible(false);
		}
	}

	public void setSize(int w, int h) {
		super.setSize(w, h);

		if (panel != null) {
			panel.setSize(w, h);
			bottomPanel.setSize(w, bottomPanel.getHeight());
			bottomPanel.setPosition(0, panel.getHeight() - bottomPanel.getHeight());
		}
	}

	public void setTitle(String title) {
		this.title.setText(title);
		refresh();
	}

	public String getTitle() {
		return new String(title.getText());
	}

}
