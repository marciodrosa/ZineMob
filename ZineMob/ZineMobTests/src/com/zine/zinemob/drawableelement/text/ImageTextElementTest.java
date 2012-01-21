package com.zine.zinemob.drawableelement.text;

import com.zine.zinemob.i18n.I18n;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class ImageTextElementTest extends TestCase {
	
	public ImageTextElementTest() {
	}

	public ImageTextElementTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new ImageTextElementTest("testShouldTranslateText", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldTranslateText(); } } ));

		testSuite.addTest(new ImageTextElementTest("testShouldNotTranslateText", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldNotTranslateText(); } } ));

		testSuite.addTest(new ImageTextElementTest("testShouldTranslateTextUsingGlobalConfiguration", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldTranslateTextUsingGlobalConfiguration(); } } ));

		testSuite.addTest(new ImageTextElementTest("testShouldNotTranslateTextUsingGlobalConfiguration", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldNotTranslateTextUsingGlobalConfiguration(); } } ));

		testSuite.addTest(new ImageTextElementTest("testShouldTranslateTextOnContructor", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldTranslateTextOnContructor(); } } ));

		testSuite.addTest(new ImageTextElementTest("testShouldNotTranslateTextOnContructor", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldNotTranslateTextOnContructor(); } } ));

		testSuite.addTest(new ImageTextElementTest("testShouldTranslateTextUsingGlobalConfigurationOnContructor", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldTranslateTextUsingGlobalConfigurationOnContructor(); } } ));

		testSuite.addTest(new ImageTextElementTest("testShouldNotTranslateTextUsingGlobalConfigurationOnContructor", new TestMethod()
		{ public void run(TestCase tc) {((ImageTextElementTest)tc).testShouldNotTranslateTextUsingGlobalConfigurationOnContructor(); } } ));
		
		return testSuite;
	}
	
	private ImageTextElementFont createFont() {
		return new ImageTextElementFont("/com/zine/zinemob/res/imageFont.png", "/com/zine/zinemob/res/imageFont.dat");
	}

	private void testShouldTranslateText() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		
		// when:
		ImageTextElement textElement = new ImageTextElement("", createFont());
		textElement.setText("$key", true);
		
		// then:
		assertEquals("The text is not the expected.", "value of the key", textElement.getText());
	}

	private void testShouldNotTranslateText() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		
		// when:
		ImageTextElement textElement = new ImageTextElement("", createFont());
		textElement.setText("$key", false);
		
		// then:
		assertEquals("The text is not the expected.", "$key", textElement.getText());
	}

	private void testShouldTranslateTextUsingGlobalConfiguration() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		ImageTextElement.setTranslateNewObjectsWithI18n(true);
		
		// when:
		ImageTextElement textElement = new ImageTextElement("", createFont());
		textElement.setText("$key");
		
		// then:
		assertEquals("The text is not the expected.", "value of the key", textElement.getText());
	}

	private void testShouldNotTranslateTextUsingGlobalConfiguration() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		ImageTextElement.setTranslateNewObjectsWithI18n(false);
		
		// when:
		ImageTextElement textElement = new ImageTextElement("", createFont());
		textElement.setText("$key");
		
		// then:
		assertEquals("The text is not the expected.", "$key", textElement.getText());
	}

	private void testShouldTranslateTextOnContructor() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		
		// when:
		ImageTextElement textElement = new ImageTextElement("$key", createFont(), false, true);
		
		// then:
		assertEquals("The text is not the expected.", "value of the key", textElement.getText());
	}

	private void testShouldNotTranslateTextOnContructor() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		
		// when:
		ImageTextElement textElement = new ImageTextElement("$key", createFont(), false, false);
		
		// then:
		assertEquals("The text is not the expected.", "$key", textElement.getText());
	}

	private void testShouldTranslateTextUsingGlobalConfigurationOnContructor() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		ImageTextElement.setTranslateNewObjectsWithI18n(true);
		
		// when:
		ImageTextElement textElement = new ImageTextElement("$key", createFont());
		
		// then:
		assertEquals("The text is not the expected.", "value of the key", textElement.getText());
	}

	private void testShouldNotTranslateTextUsingGlobalConfigurationOnContructor() {
		// given:
		I18n.getProperties().add("key", "value of the key");
		ImageTextElement.setTranslateNewObjectsWithI18n(false);
		
		// when:
		ImageTextElement textElement = new ImageTextElement("$key", createFont());
		
		// then:
		assertEquals("The text is not the expected.", "$key", textElement.getText());
	}
	
}
