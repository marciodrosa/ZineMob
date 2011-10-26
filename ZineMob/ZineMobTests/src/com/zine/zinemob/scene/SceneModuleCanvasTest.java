package com.zine.zinemob.scene;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Graphics;

public class SceneModuleCanvasTest extends TestCase {

	private Scene.SceneModuleCanvas sceneModuleCanvas;

	public SceneModuleCanvasTest() {

	}

	public SceneModuleCanvasTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		sceneModuleCanvas = new Scene.SceneModuleCanvas();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new SceneModuleCanvasTest("testGetGraphics", new TestMethod()
		{ public void run(TestCase tc) {((SceneModuleCanvasTest)tc).testGetGraphics(); } } ));

		return testSuite;
	}

	public void testGetGraphics() {
//		Dado um SceneModuleCanvas
//		Quando o m�todo getGraphics for chamado mais de uma vez
//		Ent�o o mesmo objeto Graphics deve ser retornado

		Graphics graphics1 = sceneModuleCanvas.getGraphics();
		Graphics graphics2 = sceneModuleCanvas.getGraphics();

		assertTrue("Os dois objetos Graphics retornados n�o s�o a inst�ncia do mesmo objeto.", graphics1 == graphics2);
	}

}
