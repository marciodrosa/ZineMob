package com.sinosarts.uzine.view.components;

import com.sinosarts.uzine.view.theme.Theme;
import com.sinosarts.zinemob.renderelements.ReflectivePanel;

// classe que estende ReflectivePanel apenas para definir o valor do índice de
// reflexão logo no construtor, de acordo com o valor do LayoutManager
public class UZReflectivePanel extends ReflectivePanel {

	public UZReflectivePanel() {
		init();
	}

	public UZReflectivePanel (int x, int y, int width, int height, int color){
		super(x, y, width, height, color);
		init();
	}

	private void init() {
		setReflectionIndex(Theme.getCurrentTheme().getLayoutManager().getReflectionIndex());
	}

}
