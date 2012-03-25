package com.zine.zinemob.scene;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.scene.controller.Controller;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class AssetTest extends TestCase {

	public AssetTest() {

	}

	public AssetTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new AssetTest("testDefaultConstructorShouldCreateNullDrawableElementAndController", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testDefaultConstructorShouldCreateNullDrawableElementAndController(); } } ));

		testSuite.addTest(new AssetTest("testConstructorWithArgumentsShouldInitiateTheDrawableElementAndController", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testConstructorWithArgumentsShouldInitiateTheDrawableElementAndController(); } } ));

		testSuite.addTest(new AssetTest("testGetsAndSets", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testGetsAndSets(); } } ));

		testSuite.addTest(new AssetTest("testAttachToSceneShouldAddControllerAndAddDrawableElementToScreen", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testAttachToSceneShouldAddControllerAndAddDrawableElementToScreen(); } } ));

		testSuite.addTest(new AssetTest("testAttachToSceneShouldAddDrawableElementBeforeAddController", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testAttachToSceneShouldAddDrawableElementBeforeAddController(); } } ));

		testSuite.addTest(new AssetTest("testShouldCallOverridedAttachMethods", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testShouldCallOverridedAttachMethods(); } } ));

		testSuite.addTest(new AssetTest("testAttachToSceneWithoutDrawableElementAndControllerShouldDoNothing", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testAttachToSceneWithoutDrawableElementAndControllerShouldDoNothing(); } } ));

		testSuite.addTest(new AssetTest("testAttachToSceneWithDrawableElementAndWithoutControllerShouldAddDrawableElement", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testAttachToSceneWithDrawableElementAndWithoutControllerShouldAddDrawableElement(); } } ));

		testSuite.addTest(new AssetTest("testAttachToSceneWithoutDrawableElementAndWithControllerShouldAddController", new TestMethod()
		{ public void run(TestCase tc) {((AssetTest)tc).testAttachToSceneWithoutDrawableElementAndWithControllerShouldAddController(); } } ));

		return testSuite;
	}
	
	public void testDefaultConstructorShouldCreateNullDrawableElementAndController() {
		// when:
		Asset asset = new Asset();
		
		// then:
		assertNull("The Controller should be null.", asset.getController());
		assertNull("The DrawableElement should be null.", asset.getDrawableElement());
	}

	private void testConstructorWithArgumentsShouldInitiateTheDrawableElementAndController() {
		// given:
		Controller controller = new Controller();
		DrawableElement drawableElement = new DrawableElement();
		
		// when:
		Asset asset = new Asset(drawableElement, controller);
		
		// then:
		assertSame("The Controller is not the expected.", controller, asset.getController());
		assertSame("The DrawableElement is not the expected.", drawableElement, asset.getDrawableElement());
	}

	private void testGetsAndSets() {
		// given:
		Controller controller = new Controller();
		DrawableElement drawableElement = new DrawableElement();
		Asset asset = new Asset();
		
		// when:
		asset.setController(controller);
		asset.setDrawableElement(drawableElement);
		
		// then:
		assertSame("The Controller is not the expected.", controller, asset.getController());
		assertSame("The DrawableElement is not the expected.", drawableElement, asset.getDrawableElement());
	}

	private void testAttachToSceneShouldAddControllerAndAddDrawableElementToScreen() {
		// given:
		Scene scene = new Scene();
		Controller controller = new Controller();
		DrawableElement drawableElement = new DrawableElement();
		Asset asset = new Asset(drawableElement, controller);
		
		// when:
		asset.attachToScene(scene);
		
		// then:
		assertSame("The Controller should be attached to the Scene.", scene, controller.getSceneController());
		assertSame("The DrawableElement should be attached to the screen element.", scene.getScreenElement(), drawableElement.getParent());
	}

	private void testAttachToSceneShouldAddDrawableElementBeforeAddController() {
		// given:
		final Scene scene = new Scene();
		final DrawableElement drawableElement = new DrawableElement();
		
		Controller controller = new Controller() {
			public void init() {
				// then:
				assertEquals("When the Controller is attached, the DrawableElement should already be attached to the scene.", scene.getScreenElement(), drawableElement.getParent());
			}
		};
		
		Asset asset = new Asset(drawableElement, controller);
		
		// when:
		asset.attachToScene(scene);
	}

	private void testShouldCallOverridedAttachMethods() {
		// given:
		final Scene scene = new Scene();
		Controller controller = new Controller();
		DrawableElement drawableElement = new DrawableElement();
		
		Asset asset = new Asset() {
			protected void attachControllerToScene(Scene s) {
				assertSame("The Scene argument passed to attachControllerToScene method is not the expected.", scene, s);
			}
			protected void attachDrawableElementToScene(Scene s) {
				assertSame("The Scene argument passed to attachDrawableElementToScene method is not the expected.", scene, s);
			}
		};
		
		// when:
		asset.attachToScene(scene);
		
		// then:
		assertNull("The controller should not be attached to the scene, because the overrided method does nothing.", controller.getSceneController());
		assertNull("The drawableElement should not be attached to the scene, because the overrided method does nothing.", drawableElement.getParent());
	}

	private void testAttachToSceneWithoutDrawableElementAndControllerShouldDoNothing() {
		// given:
		Scene scene = new Scene();
		Asset asset = new Asset();
		
		// when:
		asset.attachToScene(scene);
		
		// then:
		// nothing to assert
	}

	private void testAttachToSceneWithDrawableElementAndWithoutControllerShouldAddDrawableElement() {
		// given:
		Scene scene = new Scene();
		DrawableElement drawableElement = new DrawableElement();
		Asset asset = new Asset(drawableElement, null);
		
		// when:
		asset.attachToScene(scene);
		
		// then:
		assertSame("The DrawableElement should be attached to the screen element.", scene.getScreenElement(), drawableElement.getParent());
	}

	private void testAttachToSceneWithoutDrawableElementAndWithControllerShouldAddController() {
		// given:
		Scene scene = new Scene();
		Controller controller = new Controller();
		Asset asset = new Asset(null, controller);
		
		// when:
		asset.attachToScene(scene);
		
		// then:
		assertSame("The Controller should be attached to the Scene.", scene, controller.getSceneController());
	}
}
