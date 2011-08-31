package com.zine.zinemob.drawableelement;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import javax.microedition.lcdui.Image;

public class SpriteElementTest extends TestCase {

	private Image spriteImage;
	private SpriteElement spriteElement;

	public SpriteElementTest() {

	}

	public SpriteElementTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() throws Exception {
		spriteImage = Image.createImage("/com/zine/zinemob/res/sprite.png");
		spriteElement = new SpriteElement(spriteImage, 40, 40);
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new SpriteElementTest("testSetFrameSequence", new TestMethod()
		{ public void run(TestCase tc) {((SpriteElementTest)tc).testSetFrameSequence(); } } ));

		testSuite.addTest(new SpriteElementTest("testSetFrameSequenceWithFramesRepeat", new TestMethod()
		{ public void run(TestCase tc) {((SpriteElementTest)tc).testSetFrameSequenceWithFramesRepeat(); } } ));

		testSuite.addTest(new SpriteElementTest("testSetFrameSequenceWithInverseSequence", new TestMethod()
		{ public void run(TestCase tc) {((SpriteElementTest)tc).testSetFrameSequenceWithInverseSequence(); } } ));

		testSuite.addTest(new SpriteElementTest("testSetFrameSequenceWithInvalidFramesRepeat", new TestMethod()
		{ public void run(TestCase tc) {((SpriteElementTest)tc).testSetFrameSequenceWithInvalidFramesRepeat(); } } ));

		return testSuite;
	}

	public void testSetFrameSequence() {
		spriteElement.setFrameSequence(1, 3);
		assertEquals("O tamanho da sequência do sprite deveria ser 3 (frames 1, 2 e 3).", 3, spriteElement.getSprite().getFrameSequenceLength());
	}

	public void testSetFrameSequenceWithFramesRepeat() {
		spriteElement.setFrameSequence(1, 3, 3);
		assertEquals("O tamanho da sequência do sprite deveria ser 9 (frames 1, 1, 1, 2, 2, 2, 3, 3 e 3).", 9, spriteElement.getSprite().getFrameSequenceLength());
	}

	public void testSetFrameSequenceWithInverseSequence() {
		spriteElement.setFrameSequence(3, 1, 2);
		assertEquals("O tamanho da sequência do sprite deveria ser 6 (frames 3, 3, 2, 2, 1 e 1).", 6, spriteElement.getSprite().getFrameSequenceLength());
	}

	public void testSetFrameSequenceWithInvalidFramesRepeat() {
		try {
			spriteElement.setFrameSequence(1, 3, 0);
			fail("Deveria ter lançado uma IllegalArgumentException porque foi definido um valor menor que 1 para a repetição de frames.");
		}
		catch(IllegalArgumentException ex) {
		}
	}

}
