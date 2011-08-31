package com.zine.zinemob.drawableelement;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class ColorTest extends TestCase {

	public ColorTest() {

	}

	public ColorTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new ColorTest("testGetIndividualComponents", new TestMethod()
		{ public void run(TestCase tc) {((ColorTest)tc).testGetIndividualComponents(); } } ));

		testSuite.addTest(new ColorTest("testGetHexadecimalValue", new TestMethod()
		{ public void run(TestCase tc) {((ColorTest)tc).testGetHexadecimalValue(); } } ));

		return testSuite;
	}

	public void testGetIndividualComponents() {

		Color color = new Color(0x11223344);

		assertEquals("Não retornou o valor correto do componente alfa!", 0x11, color.getAlphaComponent());
		assertEquals("Não retornou o valor correto do componente vermelho!", 0x22, color.getRedComponent());
		assertEquals("Não retornou o valor correto do componente verde!", 0x33, color.getGreenComponent());
		assertEquals("Não retornou o valor correto do componente azul!", 0x44, color.getBlueComponent());
	}

	public void testGetHexadecimalValue() {

		Color color = new Color(0x11, 0x22, 0x33, 0x44);

		assertEquals("Não retornou o valor correto no formato hexadecimal 0xAARRGGBB!", 0x11223344, color.getComponents());
	}

}
