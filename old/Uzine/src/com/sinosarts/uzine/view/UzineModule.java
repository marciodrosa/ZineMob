package com.sinosarts.uzine.view;

import com.sinosarts.uzine.controller.Controller;
import com.sinosarts.uzine.events.InputEvent;
import com.sinosarts.uzine.view.theme.Theme;
import com.sinosarts.zinemob.core.SynchronizedFrame;
import com.sinosarts.zinemob.core.ZineManager;
import com.sinosarts.zinemob.core.flow.Module;
import com.sinosarts.zinemob.renderelements.Rectangle;
import com.sinosarts.zinemob.renderelements.RenderElement;
import com.sinosarts.zinemob.windows.Cursor;
import java.util.Hashtable;
import javax.microedition.lcdui.Graphics;


/**
 * M�dulo de janelas do UZINE. Deve ser executado atrav�s do m�todo run para exibir
 * e controlar as janelas.
 */
public class UzineModule extends Module implements WindowsManager {

	private Hashtable controllers = new Hashtable(); // key = Window, value = Controller

	/**
	 * Contrutor.
	 */
	public UzineModule() {
		configure();
	}

	/**
	 * Contrutor.
	 * @param initialController o controlador de janelas que ser� imediatamente
	 * inicializado atrav�s do m�todo setController
	 */
	public UzineModule(Controller initialController) {
		configure();
		setController(initialController);
	}

	private void configure() {
		Cursor cursor = Theme.getCurrentTheme().getCursor();
		setCursor(cursor);

		RenderElement background = Theme.getCurrentTheme().getBackground();
		if (background == null)
			background = new Rectangle(0, 0, ZineManager.getInstance().getWidth(),
					ZineManager.getInstance().getHeight(), Theme.getCurrentTheme().getColorPallete().getBackgroundColor());
		setBackground(background);
	}

	/**
	 * Define o controle atual. O m�todo createWindow do controle � imediatamente
	 * chamado para que a janela seja exibida pelo m�dulo.
	 * @param controller o controle a ser definido como o atual
	 */
	public void setController(final Controller controller) {

		final Window window = controller.createWindow();

		if (window == null)
			return;

		controllers.put(window, controller);

		new WindowControllerRelationship(window, controller);

		showWindow(window);
	}
	
	/**
	 * Retorna o controle que controla a janela atual, null se n�o houver.
	 * @return o controle que controla a janela atual, null se n�o houver
	 */
	public Controller getController() {
		Window window = getCurrentWindow();

		if (window == null)
			return null;

		return (Controller)controllers.get(window);
	}

	public void init() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void run() {
		Graphics graphics = ZineManager.getInstance().getGraphics();
		SynchronizedFrame frame = new SynchronizedFrame(30);
		frame.clearFrameAfterFlush(true);

		while (!end) {
			synchronized(this) {

				if (background!=null)
					background.draw(graphics);

				for (int i=0; i<windows.size(); i++)
					((Window)windows.elementAt(i)).getRenderElement().draw(graphics);

				if (cursor != null)
					cursor.draw(graphics);

				if (currentWindowLeftFooterCommand != null)
					currentWindowLeftFooterCommand.draw(graphics);
				if (currentWindowRightFooterCommand != null)
					currentWindowRightFooterCommand.draw(graphics);

				frame.update();

				if (!repaintRequested) {
					try {
						repaintRequested = false;
						wait();
					}
					catch(Exception ex) {
					}
				}
			}
		}
	}

	public void showWindow(Window w) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void removeWindow(Window window) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void requestRepaint() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void requestFocus(GuiElement element) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void pushCommandEvent(CommandEvent event) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void pushInputEvent(InputEvent event) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	private class WindowControllerRelationship implements UZCommandListener {

		private Window window;
		private Controller controller;

		WindowControllerRelationship(Window window, Controller controller) {
			this.window = window;
			this.controller = controller;

			window.setUZCommandListener(this);
		}

		public void onCommandEvent(CommandEvent event) {
			Controller newController = controller.onCommand(event, window);
			if (newController != null)
				UzineModule.this.setController(newController);
		}

	}

}
