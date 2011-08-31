package com.sinosarts.zinemobsamples.windows;

import com.sinosarts.zinemob.renderelements.RenderableText;
import com.sinosarts.zinemob.windows.FooterCommand;
import com.sinosarts.zinemob.windows.events.CommandEvent;

public class MainWindowExample extends WindowBaseExample {

	private static final byte MESSAGE1_ID = 1;
	private static final byte MESSAGE2_ID = 2;
	private static final byte COLOR_ID = 3;
	private static final byte CLOSE_ID = 4;

	private ColorContainer colorContainer;

	public MainWindowExample() {

		colorContainer = new ColorContainer(COLOR_ID);
		
		addTextWidgetToLayout("Message", MESSAGE1_ID, null, 0, true);
		addTextWidgetToLayout("Message 2", MESSAGE2_ID, null, 0, true);
		addContainerToLayout(colorContainer, null, 0, true);
		addTextWidgetToLayout("Close", CLOSE_ID, null, 0, true);

		centerToScreen();

		FooterCommand footerCommand = new FooterCommand();
		footerCommand.setRenderElement(new RenderableText("Close", getDefaultFont(), true));
		footerCommand.setId(CLOSE_ID);

		setLeftFooterCommand(footerCommand);
	}

	public void onCommandEvent(CommandEvent event) {
		switch(event.getCommandId()) {
			case MESSAGE1_ID:
				getManager().showWindow(new MessageWindowExample("This is a message!"));
				break;
			case MESSAGE2_ID:
				getManager().showWindow(new MessageWindowExample("This is another message!"));
				break;
			case COLOR_ID:
				switch(colorContainer.getColorId()) {
					case ColorContainer.RED:
						getReflectiveBackground().setColor(0xffff0000);
						break;
					case ColorContainer.GREEN:
						getReflectiveBackground().setColor(0xff00ff00);
						break;
					case ColorContainer.BLUE:
						getReflectiveBackground().setColor(0xff0000ff);
						break;
					case ColorContainer.GRAY:
						getReflectiveBackground().setColor(0xff303030);
						break;
				}
				break;
			case CLOSE_ID:
				animateWindowAndClose(HIDE_TO_RIGHT);
				break;
		}

		requestRepaint();
	}

}
