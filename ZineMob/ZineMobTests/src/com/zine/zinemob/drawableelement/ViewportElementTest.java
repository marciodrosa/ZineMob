package com.zine.zinemob.drawableelement;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class ViewportElementTest extends TestCase {

	private class GameCanvasMock extends GameCanvas {
		public GameCanvasMock() {
			super(true);
		}

		public Graphics getGraphics() {
			return super.getGraphics();
		}
	}

	private class ViewportElementMock extends ViewportElement {

		int graphicsClipX, graphicsClipY, graphicsClipWidth, graphicsClipHeight;

		protected void drawElement(Graphics graphics) {
			super.drawElement(graphics);

			graphicsClipX = graphics.getClipX();
			graphicsClipY = graphics.getClipY();
			graphicsClipWidth = graphics.getClipWidth();
			graphicsClipHeight = graphics.getClipHeight();
		}
	}

	private Graphics graphics;

	public ViewportElementTest() {

	}

	public ViewportElementTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		graphics = new GameCanvasMock().getGraphics();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new ViewportElementTest("testClipViewportArea", new TestMethod()
		{ public void run(TestCase tc) {((ViewportElementTest)tc).testClipViewportArea(); } } ));

		return testSuite;
	}

	public void testClipViewportArea() {

		// given:
		final int x = 10;
		final int y = 20;
		final int width = 30;
		final int height = 40;

		ViewportElementMock viewportElement = new ViewportElementMock();

		viewportElement.setPosition(x, y);
		viewportElement.setSize(width, height);

		// when:
		viewportElement.draw(graphics);

		// then:
		assertEquals("O clipX do contexto gráfico não é o esperado.", 0, viewportElement.graphicsClipX);
		assertEquals("O clipY do contexto gráfico não é o esperado.", 0, viewportElement.graphicsClipY);
		assertEquals("O clipWidth do contexto gráfico não é o esperado.", width, viewportElement.graphicsClipWidth);
		assertEquals("O clipHeight do contexto gráfico não é o esperado.", height, viewportElement.graphicsClipHeight);
	}

}
