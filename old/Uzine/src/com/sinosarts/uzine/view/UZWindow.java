package com.sinosarts.uzine.view;

import com.sinosarts.uzine.language.Language;
import com.sinosarts.uzine.view.components.WindowTitlePanel;
import com.sinosarts.uzine.view.components.UZReflectivePanel;
import com.sinosarts.uzine.view.theme.Theme;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.renderelements.Rectangle;
import com.sinosarts.zinemob.renderelements.layout.Border;
import com.sinosarts.zinemob.windows.AnimatedWindow;
import com.sinosarts.zinemob.windows.Container;
import com.sinosarts.zinemob.windows.events.CommandEvent;
import javax.microedition.lcdui.Graphics;

public class UZWindow {//extends AnimatedWindow {

	//public static final int CLOSE_COMMAND = 4;
	//public static final int MAXIMIZE_COMMAND = 8;
	//public static final int RESIZABLE = 64;
//
//	public static final int SHOW_TITLE = 2;
//	public static final int REFLECTIVE_PANEL_BACKGROUND = 16;
//	public static final int FLAT_BACKGROUND = 32;
//
//	private int flags = 0;
//	private Container panel = new Container();
//
//	private String title = "";
//	private WindowTitlePanel titlePanel = new WindowTitlePanel();;
//	private boolean titleOnTop = false;
//
//	private UZCommandListener uzCommandListener;
//
//	public UZWindow() {
//		init();
//	}
//
//	public UZWindow(int flags) {
//		this.flags = flags;
//		init();
//	}
//
//	public UZWindow(String title) {
//		init();
//		setTitle(title);
//	}
//
//	public UZWindow(String title, int flags) {
//		this.flags = flags;
//		init();
//		setTitle(title);
//	}
//
//	private void init() {
//
//		if ((flags & FLAT_BACKGROUND) != 0) {
//			Rectangle flatPanel = new Rectangle();
//			flatPanel.setDrawMode(Rectangle.MODE_FILL_AND_WIRE);
//			flatPanel.setRoundedCorner(Theme.getCurrentTheme().getLayoutManager().getFlatWindowsRoundedCorners());
//			flatPanel.setColor(Theme.getCurrentTheme().getColorPallete().getFlatPanelColor());
//			flatPanel.setWireColor(Theme.getCurrentTheme().getColorPallete().getFlatPanelBorderColor());
//
//			setBackground(flatPanel);
//
//			if ((flags & SHOW_TITLE) != 0) {
//				addToLayout(titlePanel, new Border(1), ALIGN_TOP | STRETCH_H, false);
//				titlePanel.setRoundedCorner(Theme.getCurrentTheme().getLayoutManager().getFlatWindowsRoundedCorners());
//			}
//
//			setDefaultFont(Theme.getCurrentTheme().getFontsManager().getFlatPanelWindowDefaultFont());
//			setDefaultFocusFont(Theme.getCurrentTheme().getFontsManager().getFlatPanelWindowDefaultFocusFont());
//
//			setSize(Theme.getCurrentTheme().getLayoutManager().getFlatWindowsDefaultWidth(),
//					Theme.getCurrentTheme().getLayoutManager().getFlatWindowsDefaultHeight());
//		}
//		else if ((flags & REFLECTIVE_PANEL_BACKGROUND) != 0) {
//			UZReflectivePanel reflectivePanel = new UZReflectivePanel();
//			reflectivePanel.setRoundedCorner(Theme.getCurrentTheme().getLayoutManager().getReflectiveWindowsRoundedCorners());
//			reflectivePanel.setColor(Theme.getCurrentTheme().getColorPallete().getReflectionPanelColor());
//			reflectivePanel.setReflectionDistanceFromBorder(3);
//
//			setBackground(reflectivePanel);
//
//			if ((flags & SHOW_TITLE) != 0)
//				addTitlePanelToTop();
//
//			setDefaultFont(Theme.getCurrentTheme().getFontsManager().getReflectivePanelWindowDefaultFont());
//			setDefaultFocusFont(Theme.getCurrentTheme().getFontsManager().getReflectivePanelWindowDefaulFocustFont());
//
//			setSize(Theme.getCurrentTheme().getLayoutManager().getReflectivePanelWindowsDefaultWidth(),
//					Theme.getCurrentTheme().getLayoutManager().getReflectivePanelWindowsDefaultHeight());
//		}
//		else {
//			if ((flags & SHOW_TITLE) != 0)
//				addTitlePanelToTop();
//
//			setDefaultFont(Theme.getCurrentTheme().getFontsManager().getWindowDefaultFont());
//			setDefaultFocusFont(Theme.getCurrentTheme().getFontsManager().getWindowDefaultFocusFont());
//
//			setSize(Theme.getCurrentTheme().getLayoutManager().getWindowsDefaultWidth(),
//					Theme.getCurrentTheme().getLayoutManager().getWindowsDefaultHeight());
//		}
//
//		addContainerToLayout(panel, null, STRETCH, true);
//
//	}
//
//	private void addTitlePanelToTop() {
//		titleOnTop = true;
//		titlePanel.setPosition(0, 0);
//		titlePanel.setSize(ZineManager.getInstance().getWidth(), titlePanel.getHeight());
//	}
//
//	void setUZCommandListener(UZCommandListener cl) {
//		uzCommandListener = cl;
//	}
//
//	public void onCommandEvent(CommandEvent event) {
//		if (uzCommandListener != null)
//			uzCommandListener.onCommandEvent(event);
//	}
//
//	public void draw(Graphics g) {
//		super.draw(g);
//
//		if (titleOnTop)
//			titlePanel.draw(g);
//	}
//
//	public Container getPanel() {
//		return panel;
//	}
//
//	public void setTitle(String title) {
//		this.title = Language.getInstance().translate(title);
//		titlePanel.setTitle(this.title);
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void centerToScreen() {
//		super.centerToScreen();
//		setPosition(getX(), getY() - Theme.getCurrentTheme().getLayoutManager().getFooterHeight() / 2);
//	}
//
//	public void setMaximized() {
//		setPosition(0, getWindowScreenAreaPositionY());
//		setSize(ZineManager.getInstance().getWidth(),
//				getWindowScreenAreaHeight());
//	}
//
//	private int getWindowScreenAreaPositionY() {
//		if (titleOnTop)
//			return titlePanel.getHeight();
//		else
//			return 0;
//	}
//
//	// retorna a altura do espaço disponível para a janela:
//	private int getWindowScreenAreaHeight() {
//		return ZineManager.getInstance().getHeight() - Theme.getCurrentTheme().getLayoutManager().getFooterHeight() - getWindowScreenAreaPositionY();
//	}

}
