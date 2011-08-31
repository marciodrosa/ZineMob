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
//		- Então o frame rate padrão é 30 frames por segundo

		assertEquals("O frame rate padrão deveria ser 30 frames por segundo!", 30, sceneModule.getFrameRate());
	}

	public void testGetAndSetFrameRate() {

//		- Dado um SceneModule e uma nova taxa de frames por segundo
//		- Quando é definido este novo frame rate
//		- Então o frame rate a ser utilizado é o novo frame rate definido

		// dado:
		int newFPS = 123;

		// quando:
		sceneModule.setFrameRate(newFPS);

		// então:
		assertEquals("A nova taxa de frames por segundo não é a que foi definida!", newFPS, sceneModule.getFrameRate());
	}

	public void testUpdateFrame() {

//		- Dado um SceneModule
//		- Quando a cena tem o seu frame atualizado
//		- Então as seguintes rotinas são executadas, em ordem: verificação dos
//		eventos de entrada, atualização da lógica e desenho da cena

		// dado:
		sceneModule = new Scene() {

			private boolean wasInputEventsVerified = false;
			private boolean wasSceneUpdated = false;
			private boolean wasSceneDrawn = false;

			protected void verifyInputEvents() {
				wasInputEventsVerified = true;
				assertTrue("A cena foi atualizada antes da verificação dos eventos de entrada.", !wasSceneUpdated);
				assertTrue("A cena foi desenhada antes da verificação dos eventos de entrada.", !wasSceneDrawn);
				super.verifyInputEvents();
			}

			protected void updateScene() {
				wasSceneUpdated = true;
				assertTrue("Os eventos de entrada não foram verificados antes da atualização da cena.", wasInputEventsVerified);
				assertTrue("A cena foi desenhada antes da atualização da cena.", !wasSceneDrawn);
				super.verifyInputEvents();
			}

			protected void drawScene() {
				assertTrue("Os eventos de entrada não foram verificados antes do desenho da cena.", wasInputEventsVerified);
				assertTrue("A cena não foi atualizada antes do desenho da cena.", wasSceneUpdated);
				wasSceneDrawn = true;
				super.drawScene();
			}

			public void updateFrame() {
				super.updateFrame();
				assertTrue("Os eventos de entrada não foram processados na atualização do frame.", wasInputEventsVerified);
				assertTrue("A cena não foi atualizada na atualização do frame.", wasSceneUpdated);
				assertTrue("A cena não foi desenhada na atualização do frame.", wasSceneDrawn);
			}

		};

		// quando:
		sceneModule.updateFrame();
	}

}
