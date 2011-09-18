package com.zine.zinemob.drawableelement;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class DrawableElementTest extends TestCase {

	private Graphics graphics;

	public DrawableElementTest() {

	}

	public DrawableElementTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		graphics = new GameCanvasMock().getGraphics();
	}

	public void tearDown() {
		graphics = null;
		System.gc();
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new DrawableElementTest("testDrawVisibleElement", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testDrawVisibleElement(); } } ));

		testSuite.addTest(new DrawableElementTest("testDrawInvisibleElement", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testDrawInvisibleElement(); } } ));

		testSuite.addTest(new DrawableElementTest("testGetWidthAndGetHeight", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testGetWidthAndGetHeight(); } } ));

		testSuite.addTest(new DrawableElementTest("testAddChild", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testAddChild(); } } ));

		testSuite.addTest(new DrawableElementTest("testAddSameChild", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testAddSameChild(); } } ));

		testSuite.addTest(new DrawableElementTest("testAddChildAt", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testAddChildAt(); } } ));

		testSuite.addTest(new DrawableElementTest("testAddChildAtAnIndexLessThanZero", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testAddChildAtAnIndexLessThanZero(); } } ));

		testSuite.addTest(new DrawableElementTest("testAddChildAtAnIndexGreaterThanChildrenCount", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testAddChildAtAnIndexGreaterThanChildrenCount(); } } ));

		testSuite.addTest(new DrawableElementTest("testAddSameChildAt", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testAddSameChildAt(); } } ));

		testSuite.addTest(new DrawableElementTest("testGetParent", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testGetParent(); } } ));

		testSuite.addTest(new DrawableElementTest("testRemoveChild", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testRemoveChild(); } } ));

		testSuite.addTest(new DrawableElementTest("testRemoveChildren", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testRemoveChildren(); } } ));

		testSuite.addTest(new DrawableElementTest("testGetGlobalPosition", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testGetGlobalPosition(); } } ));

		testSuite.addTest(new DrawableElementTest("testSetGlobalPosition", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testSetGlobalPosition(); } } ));

		testSuite.addTest(new DrawableElementTest("testDrawPosition", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testDrawPosition(); } } ));

		testSuite.addTest(new DrawableElementTest("testDrawPositionOfAChildElementDirectly", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testDrawPositionOfAChildElementDirectly(); } } ));

		testSuite.addTest(new DrawableElementTest("testGraphicsTranslationAfterCallDrawMethod", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testGraphicsTranslationAfterCallDrawMethod(); } } ));

		testSuite.addTest(new DrawableElementTest("testGetTopLeftPositionShouldIgnoreThePivotPosition", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testGetTopLeftPositionShouldIgnoreThePivotPosition(); } } ));

		testSuite.addTest(new DrawableElementTest("testGetTopLeftGlobalPositionShouldIgnoreThePivotPosition", new TestMethod()
		{ public void run(TestCase tc) {((DrawableElementTest)tc).testGetTopLeftGlobalPositionShouldIgnoreThePivotPosition(); } } ));

		return testSuite;
	}

	public void testGetGlobalPosition() {

		// given:
		DrawableElement drawableElement = new DrawableElement();
		DrawableElement granParent = new DrawableElement();
		DrawableElement parent = new DrawableElement();

		granParent.setPosition(1, 2);
		granParent.setPivot(10, 20);
		granParent.setChildrenViewPosition(100, 200);
		granParent.addChild(parent);

		parent.setPosition(1000, 2000);
		parent.addChild(drawableElement);

		drawableElement.setPosition(3, 4);
		drawableElement.setPivot(33, 44); // must be ignored
		drawableElement.setChildrenViewPosition(333, 444); // must be ignored

		// when:
		int globalX = drawableElement.getGlobalX(); 
		int globalY = drawableElement.getGlobalY();

		// then:

		// parent.pos - parent.pivo + parent.pivo -parent.viewpos + pos - pivo

		// granparent pos + granparent pivot - granparent children view pos + parent post + pos
		int expectedGlobalX = 914; // 1 + 10 - 100 + 1000 + 3
		int expectedGlobalY = 1826; // 2 + 20 -200 + 2000 + 4

		assertEquals("Unexpected global position X.", expectedGlobalX, globalX);
		assertEquals("Unexpected global position Y.", expectedGlobalY, globalY);
	}

	public void testSetGlobalPosition() {

		// given:
		DrawableElement drawableElement = new DrawableElement();
		DrawableElement granParent = new DrawableElement();
		DrawableElement parent = new DrawableElement();

		granParent.setPosition(1, 2);
		granParent.setPivot(10, 20);
		granParent.setChildrenViewPosition(100, 200);
		granParent.addChild(parent);

		parent.setPosition(1000, 2000);
		parent.addChild(drawableElement);

		drawableElement.setPivot(33, 44); // must be ignored
		drawableElement.setChildrenViewPosition(333, 444); // must be ignored

		// when:
		drawableElement.setGlobalPosition(914, 1826);

		// then:
		// pos = global - granparent pos - granparent pivot + granparent children view pos - parent post
		int expectedX = 3; // 914 - 1 - 10 + 100 - 1000
		int expectedY = 4; // 1826 - 2 - 20 + 200 - 2000

		assertEquals("Unexpected position X.", expectedX, drawableElement.getX());
		assertEquals("Unexpected position Y.", expectedY, drawableElement.getY());
	}

	public void testDrawPosition() {

		// given:
		DrawableElementMock drawableElement = new DrawableElementMock();
		DrawableElement parent = new DrawableElement();

		parent.setPosition(1, 2);
		parent.setPivot(10, 20);
		parent.setChildrenViewPosition(100, 200);
		parent.addChild(drawableElement);

		drawableElement.setPosition(11, 22);
		drawableElement.setPivot(111, 222);
		drawableElement.setChildrenViewPosition(1111, 2222); // must be ignored

		// when:
		parent.draw(graphics);

		// then:
		// global - pivot
		int expectedDrawPositionX = drawableElement.getGlobalX() - 111;
		int expectedDrawPositionY = drawableElement.getGlobalY() - 222;

		assertEquals("Unexpected draw position X.", expectedDrawPositionX, drawableElement.drawPositionX);
		assertEquals("Unexpected draw position Y.", expectedDrawPositionY, drawableElement.drawPositionY);
	}

	public void testDrawPositionOfAChildElementDirectly() {
		
		// given:
		DrawableElementMock drawableElement = new DrawableElementMock();
		DrawableElement parent = new DrawableElement();

		// must ignore parent if the element is painted directly
		parent.setPosition(1, 2);
		parent.setPivot(11, 22);
		parent.setChildrenViewPosition(111, 222);
		parent.addChild(drawableElement);

		drawableElement.setPosition(10, 20);
		drawableElement.setPivot(2, 3);

		// when:
		drawableElement.draw(graphics);

		// then:
		// pos - pivot
		int expectedDrawPositionX = 8;
		int expectedDrawPositionY = 17;

		assertEquals("Unexpected draw position X.", expectedDrawPositionX, drawableElement.drawPositionX);
		assertEquals("Unexpected draw position Y.", expectedDrawPositionY, drawableElement.drawPositionY);
	}

	public void testGraphicsTranslationAfterCallDrawMethod() {

		// given:
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.setPosition(10, 20);
		graphics.translate(100, 200);

		// when:
		drawableElement.draw(graphics);

		// then:
		assertEquals("The translation X of the graphics must be the sabe before call the draw method.", 100, graphics.getTranslateX());
		assertEquals("The translation Y of the graphics must be the sabe before call the draw method.", 200, graphics.getTranslateY());
	}

	public void testDrawVisibleElement() {

//		Dado um elemento que esteja visível
//		Quando o elemento é desenhado
//		Então primeiro o elemento deve ser desenhado
//		E depois os filhos
		
		// dado:
		DrawableElementMock element = new DrawableElementMock();

		// quando:
		element.draw(graphics);

		// então:
		assertTrue("Não desenhou o elemento.", element.wasElementDrawed);
		assertTrue("Não desenhou os filhos do elemento.", element.wasChildrenDrawed);
		assertTrue("O elemento deveria ter sido desenhado antes dos filhos.", element.wasElementDrawedBeforeChildren);
	}

	public void testDrawInvisibleElement() {

//		Dado um elemento que esteja INVISÍVEL
//		Quando o elemento é desenhado
//		Então nem o elemento e nem os seus filhos devem ser desenhados

		// dado:
		DrawableElementMock element = new DrawableElementMock();
		element.setVisible(false);

		// quando:
		element.draw(graphics);

		// então:
		assertTrue("Não deveria ter desenhado o elemento.", !element.wasElementDrawed);
		assertTrue("Não deveria ter desenhado os filhos do elemento.", !element.wasChildrenDrawed);
	}

	public void testGetWidthAndGetHeight() {

		int width = 10;
		int height = 20;

		DrawableElement element = new DrawableElement();
		element.setSize(10, 20);

		assertEquals("Não retornou a largura definida para o elemento.", width, element.getWidth());
		assertEquals("Não retornou a altura definida para o elemento.", height, element.getHeight());
	}

	public void testAddChild() {
//		Dado dois ou mais elementos
//		Quando é adicionado um filho ao elemento
//		Então este filho deve constar na relação de filhos do elemento
//		*E* esta relação deve estar ordenada de acordo com a ordem que os filhos foram adicionados

		// dado:
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();
		DrawableElement element4 = new DrawableElement();
		
		// quando:
		element1.addChild(element2);
		element1.addChild(element3);
		element3.addChild(element4);

		// então:
		assertEquals("O elemento 1 deveria ter 2 filhos.", 2, element1.getChildrenCount());
		assertEquals("O elemento 3 deveria ter 1 filho.", 1, element3.getChildrenCount());
		assertEquals("O primeiro filho do elemento 1 deveria ser o elemento 2", element2, element1.getChild(0));
		assertEquals("O segundo filho do elemento 1 deveria ser o elemento 3", element3, element1.getChild(1));
		assertEquals("O filho do elemento 3 deveria ser o elemento 4", element4, element3.getChild(0));
	}

	public void testAddSameChild() {
//		Dado dois ou mais elementos que possuem parentesco
//		Quando um filho do elemento é adicionado ao pai novamente
//		Então nada deve acontecer
//		*E* o filho deve continuar na relaçao de filhos do pai, na mesma posição de ordenação
		
		// dado:
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();
		element1.addChild(element2);
		element1.addChild(element3);

		// quando:
		element1.addChild(element2);

		// então:
		assertEquals("O elemento 1 deveria ter apenas 2 filhos, pois o terceiro adicionado já era seu filho.", 2, element1.getChildrenCount());
		assertEquals("O primeiro filho do elemento 1 deveria ser o elemento 2", element2, element1.getChild(0));
		assertEquals("O segundo filho do elemento 1 deveria ser o elemento 3", element3, element1.getChild(1));
	}

	public void testAddChildAt() {
//		Dado um elemento com 1 ou mais filhos
//		Quando é adicionado um novo filho ao elemento
//		*E* é definido o índice de ordenação
//		Então este filho deve constar na relação de filhos do elemento, na posição definida pelo índice

		// dado:
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();
		DrawableElement element4 = new DrawableElement();
		DrawableElement element5 = new DrawableElement();
		element1.addChild(element2);
		element1.addChild(element3);
		element1.addChild(element4);

		// quando:
		element1.addChild(element5, 1);

		// então:
		assertEquals("O elemento 1 deveria ter 4 filhos.", 4, element1.getChildrenCount());
		assertEquals("O primeiro filho do elemento 1 deveria ser o elemento 2", element2, element1.getChild(0));
		assertEquals("O segundo filho do elemento 1 deveria ser o elemento 5", element5, element1.getChild(1));
		assertEquals("O terceiro filho do elemento 1 deveria ser o elemento 3", element3, element1.getChild(2));
		assertEquals("O quarto filho do elemento 1 deveria ser o elemento 4", element4, element1.getChild(3));
		assertEquals("O pai do elemento 5 deveria ser o elemento 1.", element1, element5.getParent());
	}

	public void testAddChildAtAnIndexLessThanZero() {
//		Dado um elemento com 1 ou mais filhos
//		Quando é adicionado um novo filho ao elemento
//		*E* é definido um índice de ordenação menor que zero
//		Então este filho deve constar na relação de filhos do elemento, na primeira posição

		// dado:
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();
		DrawableElement element4 = new DrawableElement();
		element1.addChild(element2);
		element1.addChild(element3);

		// quando:
		element1.addChild(element4, -1);

		// então:
		assertEquals("O elemento 1 deveria ter 3 filhos.", 3, element1.getChildrenCount());
		assertEquals("O primeiro filho do elemento 1 deveria ser o elemento 4", element4, element1.getChild(0));
		assertEquals("O segundo filho do elemento 1 deveria ser o elemento 2", element2, element1.getChild(1));
		assertEquals("O terceiro filho do elemento 1 deveria ser o elemento 3", element3, element1.getChild(2));
	}

	public void testAddChildAtAnIndexGreaterThanChildrenCount() {
//		Dado um elemento com 1 ou mais filhos
//		Quando é adicionado um novo filho ao elemento
//		*E* é definido um índice de ordenação maior que a quantidade de filhos que o pai possui
//		Então este filho deve constar na relação de filhos do elemento, na última posição

		// dado:
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();
		DrawableElement element4 = new DrawableElement();
		element1.addChild(element2);
		element1.addChild(element3);

		// quando:
		element1.addChild(element4, 99);

		// então:
		assertEquals("O elemento 1 deveria ter 3 filhos.", 3, element1.getChildrenCount());
		assertEquals("O primeiro filho do elemento 1 deveria ser o elemento 2", element2, element1.getChild(0));
		assertEquals("O segundo filho do elemento 1 deveria ser o elemento 3", element3, element1.getChild(1));
		assertEquals("O terceiro filho do elemento 1 deveria ser o elemento 4", element4, element1.getChild(2));
	}

	public void testAddSameChildAt() {
//		Dado um elemento com 1 ou mais filhos
//		Quando um filho de um elemento é adicionado novamente ao pai
//		*E* é definido o índice de ordenação
//		Então o filho deve ser removido e adicionado novamente da relação, obedecendo ao indice de ordenação

		// dado:
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();
		DrawableElement element4 = new DrawableElement();
		DrawableElement element5 = new DrawableElement();
		element1.addChild(element2);
		element1.addChild(element3);
		element1.addChild(element4);
		element1.addChild(element5);

		// quando:
		element1.addChild(element2, 1);

		// então:
		assertEquals("O elemento 1 deveria ter 4 filhos.", 4, element1.getChildrenCount());
		assertEquals("O primeiro filho do elemento 1 deveria ser o elemento 3", element3, element1.getChild(0));
		assertEquals("O segundo filho do elemento 1 deveria ser o elemento 2", element2, element1.getChild(1));
		assertEquals("O terceiro filho do elemento 1 deveria ser o elemento 4", element4, element1.getChild(2));
		assertEquals("O quarto filho do elemento 1 deveria ser o elemento 5", element5, element1.getChild(3));
		assertEquals("O pai do elemento 2 deveria ser o elemento 1.", element1, element2.getParent());
	}

	public void testGetParent() {
//		Dado dois ou mais elementos
//		Quando um elemento é definido como filho de outro
//		Então seu pai deve ser o outro elemento

		// dado:
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();

		// quando:
		element1.addChild(element2);
		element2.addChild(element3);

		// então:
		assertEquals("O pai do elemento 1 deveria ser nulo.", null, element1.getParent());
		assertEquals("O pai do elemento 2 deveria ser o elemento 1.", element1, element2.getParent());
		assertEquals("O pai do elemento 3 deveria ser o elemento 2.", element2, element3.getParent());
	}

	public void testRemoveChild() {
//		Dado um elemento com um ou mais filhos
//		Quando um filho é removido do pai
//		Então o pai do ex-filho deve ser nulo
//		*E* o ex-filho não deve mais constar na relação de filhos

		// dado:
		DrawableElement parent = new DrawableElement();
		DrawableElement child1 = new DrawableElement();
		DrawableElement child2 = new DrawableElement();
		parent.addChild(child1);
		parent.addChild(child2);

		// quando:
		parent.removeChild(child1);

		// então:
		assertEquals("O pai do elemento deveria ser nulo, pois o parentesco foi desfeito.", null, child1.getParent());
		assertEquals("O pai deveria ter apenas 1 filho agora, pois 1 deles foi removido.", 1, parent.getChildrenCount());
		assertEquals("O filho que permaneceu no pai deveria ser o filho 2.", child2, parent.getChild(0));
	}

	public void testRemoveChildren() {
//		Dado um elemento pai de outros
//		Quando todos os elementos filhos são removidos
//		Então o pai não deve ter mais nenhum filho
//		*E* todos os filhos diretos deste elemento pai terão um pai nulo

		// dado:
		DrawableElement parent = new DrawableElement();
		DrawableElement element1 = new DrawableElement();
		DrawableElement element2 = new DrawableElement();
		DrawableElement element3 = new DrawableElement();

		parent.addChild(element1);
		parent.addChild(element2);
		element1.addChild(element3);

		// quando:
		parent.removeChildren();

		// então:
		assertEquals("O pai não deveria ter mais filhos.", 0, parent.getChildrenCount());
		assertEquals("O pai do element1 deveria ser nulo, pois todos os filhos do pai foram removidos.", null, element1.getParent());
		assertEquals("O pai do element2 deveria ser nulo, pois todos os filhos do pai foram removidos.", null, element2.getParent());
		assertEquals("O pai do element3 deveria ser element1, os filhos do element1 não deviam ser afetados.", element1, element3.getParent());
	}

	private void testGetTopLeftPositionShouldIgnoreThePivotPosition() {
		
		// given:
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.setPosition(10, 20);
		drawableElement.setPivot(1, 2);
		
		// when:
		int leftTopX = drawableElement.getLeftTopX();
		int leftTopY = drawableElement.getLeftTopY();
		
		// then:
		assertEquals("The left top X is not the expected.", 9, leftTopX);
		assertEquals("The left top Y is not the expected.", 18, leftTopY);
	}

	private void testGetTopLeftGlobalPositionShouldIgnoreThePivotPosition() {
		
		// given:
		DrawableElement parent = new DrawableElement();
		parent.setPosition(100, 200);
		
		DrawableElement drawableElement = new DrawableElement();
		drawableElement.setPosition(10, 20);
		drawableElement.setPivot(1, 2);
		
		parent.addChild(drawableElement);
		
		// when:
		int globalLeftTopX = drawableElement.getGlobalLeftTopX();
		int globalLeftTopY = drawableElement.getGlobalLeftTopY();
		
		// then:
		assertEquals("The left top X is not the expected.", 109, globalLeftTopX);
		assertEquals("The left top Y is not the expected.", 218, globalLeftTopY);
	}

	/**
	 * Mock para a classe DrawableElement que armazena atributos com informações
	 * sobre as chamadas para os métodos da classe.
	 */
	public class DrawableElementMock extends DrawableElement {

		boolean wasElementDrawed, wasChildrenDrawed;
		boolean wasElementDrawedBeforeChildren;
		int drawPositionX, drawPositionY;

		public void draw(Graphics graphics) {
			wasElementDrawedBeforeChildren = false;
			wasElementDrawed = false;
			wasChildrenDrawed = false;
			super.draw(graphics);
		}

		public void drawElement(Graphics graphics) {

			drawPositionX = graphics.getTranslateX();
			drawPositionY = graphics.getTranslateY();

			super.drawElement(graphics);

			wasElementDrawed = true;
			if(!wasChildrenDrawed) {
				wasElementDrawedBeforeChildren = true;
			}
		}

		public void drawChildren(Graphics graphics) {
			super.drawChildren(graphics);
			wasChildrenDrawed = true;
		}
	}

	private class GameCanvasMock extends GameCanvas {
		public GameCanvasMock() {
			super(true);
		}

		public Graphics getGraphics() {
			return super.getGraphics();
		}
	}
}
