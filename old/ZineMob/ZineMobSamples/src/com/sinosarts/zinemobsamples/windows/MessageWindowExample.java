package com.sinosarts.zinemobsamples.windows;

import com.sinosarts.zinemob.renderelements.RenderableText;
import com.sinosarts.zinemob.windows.events.CommandEvent;

public class MessageWindowExample extends WindowBaseExample {

	public MessageWindowExample(String message) {

		RenderableText messageText = new RenderableText(message, getDefaultFont(), true, 150);

		addToLayout(messageText, null, CENTER, false);

		addTextWidgetToLayout("Close", (byte)1, null, 0, false);

		centerToScreen();
	}

	public void onCommandEvent(CommandEvent event) {
		animateWindowAndClose(HIDE_TO_RIGHT);
	}

}
