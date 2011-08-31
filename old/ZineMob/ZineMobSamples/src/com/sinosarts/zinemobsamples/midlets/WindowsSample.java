package com.sinosarts.zinemobsamples.midlets;

import com.sinosarts.zinemob.core.ZineMIDlet;
import com.sinosarts.zinemob.windows.Cursor;
import com.sinosarts.zinemob.windows.Window;
import com.sinosarts.zinemob.windows.WindowsModule;
import com.sinosarts.zinemobsamples.windows.MainWindowExample;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class WindowsSample extends ZineMIDlet {

	public void run() {

		Window window = new MainWindowExample();

		WindowsModule windowsModule = new WindowsModule();
		windowsModule.setCursor(initCursor());
		windowsModule.showWindow(window);
		windowsModule.run();

		notifyDestroyed();
	}

	private Cursor initCursor() {
		Cursor cursor = new Cursor();
		cursor.setMovementAnimationSteps(5);
		try {
			cursor.setLayerElement(new Sprite(Image.createImage("/com/sinosarts/zinemobsamples/resources/cursor.png")));
		}
		catch (Exception ex) {
		}
		return cursor;
	}
	
}
