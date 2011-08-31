package com.sinosarts.zinemobsamples.windows;

import com.sinosarts.zinemob.renderelements.ReflectivePanel;
import com.sinosarts.zinemob.windows.Component;
import com.sinosarts.zinemob.windows.Container;
import com.sinosarts.zinemob.windows.Widget;
import com.sinosarts.zinemob.windows.events.CommandEvent;

public class ColorContainer extends Container {

	public static final byte RED = 1;
	public static final byte GREEN = 2;
	public static final byte BLUE = 3;
	public static final byte GRAY = 4;

	private byte id, colorId;

	private static final int ICONS_SIZE = 20;

	public ColorContainer(byte id) {

		this.id = id;
		
		setLayoutType(Container.LAYOUT_HORIZONTAL);
		getDefaultBorder().setBottom(10);
		getDefaultBorder().setTop(10);
		getDefaultBorder().setLeft(10);
		getDefaultBorder().setRight(10);

		Widget red = new Widget(RED);
		red.setSize(ICONS_SIZE, ICONS_SIZE);
		red.addChild(createColorPanel(0xffff0000));

		Widget green = new Widget(GREEN);
		green.setSize(ICONS_SIZE, ICONS_SIZE);
		green.addChild(createColorPanel(0xff00ff00));

		Widget blue = new Widget(BLUE);
		blue.setSize(ICONS_SIZE, ICONS_SIZE);
		blue.addChild(createColorPanel(0xff0000ff));

		Widget gray = new Widget(GRAY);
		gray.setSize(ICONS_SIZE, ICONS_SIZE);
		gray.addChild(createColorPanel(0xff303030));

		addWidgetToLayout(red, null, 0, true);
		addWidgetToLayout(green, null, 0, true);
		addWidgetToLayout(blue, null, 0, true);
		addWidgetToLayout(gray, null, 0, true);
	}

	private ReflectivePanel createColorPanel(int color) {
		ReflectivePanel panel = new ReflectivePanel(0, 0, ICONS_SIZE, ICONS_SIZE, color);
		panel.setDrawMode(ReflectivePanel.MODE_FILL_AND_WIRE);
		panel.setWireColor(0xffffffff);
		return panel;
	}

	public void onCommandEvent(CommandEvent event) {

		colorId = event.getCommandId();

		Component parent = getParentComponent();
		if(parent != null)
			parent.onCommandEvent(new CommandEvent(id));
	}

	public byte getColorId() {
		return colorId;
	}

}
