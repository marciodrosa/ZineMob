package com.sinosarts.zinemobsamples.midlets;

import com.sinosarts.zinemob.core.ZineMIDlet;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.renderelements.ReflectivePanel;
import javax.microedition.lcdui.Graphics;

public class ReflectivePanelSample  extends ZineMIDlet {

	public void run() {

		ReflectivePanel panel = new ReflectivePanel(10, 10, 130, 80, 0xff000000);
		panel.setReflectionDistanceFromBorder(3);
		panel.setRoundedCorner(15);
		panel.setWireColor(0xffffffff);
		panel.setDrawMode(ReflectivePanel.MODE_FILL_AND_WIRE);

		Graphics graphics = ZineManager.getInstance().getGraphics();
		panel.draw(graphics);

		ZineManager.getInstance().flushGraphics();

	}

}
