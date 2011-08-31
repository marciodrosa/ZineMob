package com.zine.zinemob.scene;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Graphics;

public class SceneModuleTest extends TestCase {

	private Scene sceneModule;

	public SceneModuleTest() {

	}

	public SceneModuleTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		sceneModule = new Scene();
	}

	public void tearDown() {

	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new SceneModuleTest("testGetDefaultFrameRate", new TestMethod()
		{ public void run(TestCase tc) {((SceneModuleTest)tc).testGetDefaultFrameRate(); } } ));

		testSuite.addTest(new SceneModuleTest("testGetAndSetFrameRate", new TestMethod()
		{ public void run(TestCase tc) {((SceneModuleTest)tc).testGetAndSetFrameRate(); } } ));

		testSuite.addTest(new SceneModuleTest("testUpdateFrame", new TestMethod()
		{ public void run(TestCase tc) {((SceneModuleTest)tc).testUpdateFrame(); } } ));

		return testSuite;
	}

	public void testGetDefaultFrameRate() {

//		- Dado um novo SceneModule
//		- Ent�o o frame rate padr�o � 30 frames por segundo

		assertEquals("O frame rate padr�o deveria ser 30 frames por segundo!", 30, sceneModule.getFrameRate());
	}

	public void testGetAndSetFrameRate() {

//		- Dado um SceneModule e uma nova taxa de frames por segundo
//		- Quando � definido este novo frame rate
//		- Ent�o o frame rate a ser utilizado � o novo frame rate definido

		// dado:
		int newFPS = 123;

		// quando:
		sceneModule.setFrameRate(newFPS);

		// ent�o:
		assertEquals("A nova taxa de frames por segundo n�o � a que foi definida!", newFPS, sceneModule.getFrameRate());
	}

	public void testUpdateFrame() {

//		- Dado um SceneModule
//		- Quando a cena tem o seu frame atualizado
//		- Ent�o as seguintes rotinas s�o executadas, em ordem: verifica��o dos
//		eventos de entrada, atualiza��o da l�gica e desenho da cena

		// dado:
		sceneModule = new Scene() {

			private boolean wasInputEventsVerified = false;
			private boolean wasSceneUpdated = false;
			private boolean wasSceneDrawn = false;

			protected void verifyInputEvents() {
				wasInputEventsVerified = true;
				assertTrue("A cena foi atualizada antes da verifica��o dos eventos de entrada.", !wasSceneUpdated);
				assertTrue("A cena foi desenhada antes da verifica��o dos eventos de entrada.", !wasSceneDrawn);
				super.verifyInputEvents();
			}

			protected void updateScene() {
				wasSceneUpdated = true;
				assertTrue("Os eventos de entrada n�o foram verificados antes da atualiza��o da cena.", wasInputEventsVerified);
				assertTrue("A cena foi desenhada antes da atualiza��o da cena.", !wasSceneDrawn);
				super.verifyInputEvents();
			}

			protected void drawScene() {
				assertTrue("Os eventos de entrada n�o foram verificados antes do desenho da cena.", wasInputEventsVerified);
				assertTrue("A cena n�o foi atualizada antes do desenho da cena.", wasSceneUpdated);
				wasSceneDrawn = true;
				super.drawScene();
			}

			public void updateFrame() {
				super.updateFrame();
				assertTrue("Os eventos de entrada n�o foram processados na atualiza��o do frame.", wasInputEventsVerified);
				assertTrue("A cena n�o foi atualizada na atualiza��o do frame.", wasSceneUpdated);
				assertTrue("A cena n�o foi desenhada na atualiza��o do frame.", wasSceneDrawn);
			}

		};

		// quando:
		sceneModule.updateFrame();
	}

}
