package com.zine.zinemobsamples.midlets;

import com.zine.zinemob.ZineMIDlet;
import com.zine.zinemob.drawableelement.Color;
import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.LinearLayoutElement;
import com.zine.zinemob.drawableelement.RectangleElement;
import com.zine.zinemob.drawableelement.layout.StretchToParentLayout;
import com.zine.zinemob.drawableelement.text.ImageTextElement;
import com.zine.zinemob.drawableelement.text.ImageTextElementFont;
import com.zine.zinemob.eventmessage.EventMessage;
import com.zine.zinemob.gui.ActionField;
import com.zine.zinemob.gui.GuiController;
import com.zine.zinemob.gui.GuiScene;
import com.zine.zinemob.gui.Window;

public class GuiWindows extends ZineMIDlet {
	
	public static final int ID_SHOW_DIALOG = 1;
	public static final int ID_QUIT = 2;
	
	private ImageTextElementFont font = new ImageTextElementFont("/com/zine/zinemobsamples/res/courier12.png", "/com/zine/zinemobsamples/res/courier12.dat");

	public void run() {
		new MyGuiScene().run();
	}

	class MyGuiScene extends GuiScene {
		
		public MyGuiScene() {
			super(new MyGuiController());
		}
	}

	class MyGuiController extends GuiController {

		public void init() {
			getGuiScene().addWindow(new MyMenuWindow());
		}

		public void onEvent(EventMessage eventMessage) {
			switch (eventMessage.getId()) {
				case ID_SHOW_DIALOG:
					getGuiScene().addWindow(new MyWindowWithCustomController());
					break;
				case ID_QUIT:
					getGuiScene().finishExecution();
					break;
			}
		}
	}

	class MyMenuWindow extends Window {
		
		public MyMenuWindow() {
			super(true);
			addChild(new MyButton("Show Dialog", ID_SHOW_DIALOG));
			addChild(new MyButton("Quit", ID_QUIT));
			setHideWhenGoToBackground(true);
		}
	}

	class MyWindowWithCustomController extends Window {
		
		public MyWindowWithCustomController() {
			super(true);
			addChild(new ImageTextElement("Dialog with custom controller.", font));
			addChild(new MyButton("Ok", 0));
			setGuiController(new GuiController() {
				public void onEvent(EventMessage eventMessage) {
					getGuiScene().closeTopWindow();
				}
			});
		}
	}

	class MyButton extends ActionField {
		
		private LinearLayoutElement linearLayoutElement = new LinearLayoutElement();
		private RectangleElement rectangleElement = new RectangleElement();
		
		public MyButton(String label, int id) {
			setActionEventId(id);
			rectangleElement.setFill(false);
			rectangleElement.setColor(new Color(0xffaaaaaa));
			rectangleElement.addLayout(new StretchToParentLayout(rectangleElement));
			ImageTextElement imageTextElement = new ImageTextElement(label, font);
			linearLayoutElement.setPadding(10);
			linearLayoutElement.setFitPolicy(LinearLayoutElement.FIT_POLICY_ALWAYS_FIT_TO_CHILDREN);
			linearLayoutElement.addChildAndLayout(rectangleElement, LinearLayoutElement.IGNORE_LAYOUT);
			linearLayoutElement.addChild(imageTextElement);
		}

		public DrawableElement getDrawableElement() {
			return linearLayoutElement;
		}

		public void onFocus(boolean focus) {
			if (focus) {
				rectangleElement.setColor(new Color(0xffffffff));
			} else {
				rectangleElement.setColor(new Color(0xffaaaaaa));
			}
		}
	}
}
