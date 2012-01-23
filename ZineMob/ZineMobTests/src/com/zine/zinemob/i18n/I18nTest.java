package com.zine.zinemob.i18n;

import com.zine.zinemob.text.Properties;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class I18nTest  extends TestCase {
	
	public I18nTest() {

	}

	public I18nTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		I18n.setProperties(new Properties());
	}

	public void tearDown() {
		I18n.setProperties(new Properties());
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new I18nTest("testShouldReturnKeyValue", new TestMethod()
		{ public void run(TestCase tc) {((I18nTest)tc).testShouldReturnKeyValue(); } } ));

		testSuite.addTest(new I18nTest("testShouldReturnFormatedUnknowKey", new TestMethod()
		{ public void run(TestCase tc) {((I18nTest)tc).testShouldReturnFormatedUnknowKey(); } } ));

		testSuite.addTest(new I18nTest("testShouldReturnKeyValueWithArgs", new TestMethod()
		{ public void run(TestCase tc) {((I18nTest)tc).testShouldReturnKeyValueWithArgs(); } } ));

		testSuite.addTest(new I18nTest("testShouldTranslateKey", new TestMethod()
		{ public void run(TestCase tc) {((I18nTest)tc).testShouldTranslateKey(); } } ));

		testSuite.addTest(new I18nTest("testShouldTranslateLiteral", new TestMethod()
		{ public void run(TestCase tc) {((I18nTest)tc).testShouldTranslateLiteral(); } } ));

		testSuite.addTest(new I18nTest("testShouldTranslateLiteralWithEscapedDollar", new TestMethod()
		{ public void run(TestCase tc) {((I18nTest)tc).testShouldTranslateLiteralWithEscapedDollar(); } } ));

		testSuite.addTest(new I18nTest("testShouldTranslateLiteralWithArgs", new TestMethod()
		{ public void run(TestCase tc) {((I18nTest)tc).testShouldTranslateLiteralWithArgs(); } } ));
		
		return testSuite;
	}

	private void testShouldReturnKeyValue() {
		// given:
		I18n.getProperties().add("message", "some message");
		I18n.getProperties().add("other.message", "other message");
		
		// when:
		String message = I18n.get("other.message");
		
		// then:
		assertEquals("The message is not the expected.", "other message", message);
	}

	private void testShouldReturnFormatedUnknowKey() {
		// given:
		I18n.getProperties().add("message", "some message");
		I18n.getProperties().add("other.message", "other message");
		
		// when:
		String message = I18n.get("inexisting.message");
		
		// then:
		assertEquals("The message is not the expected.", "???inexisting.message???", message);
	}

	private void testShouldReturnKeyValueWithArgs() {
		// given:
		I18n.getProperties().add("message", "some {0} with {1}, this is a {0}.");
		
		// when:
		String message = I18n.get("message", new Object[] {"message", "arguments"});
		
		// then:
		assertEquals("The message is not the expected.", "some message with arguments, this is a message.", message);
	}

	private void testShouldTranslateKey() {
		// given:
		I18n.getProperties().add("message", "this is a message.");
		
		// when:
		String message = I18n.translate("$message");
		
		// then:
		assertEquals("The message is not the expected.", "this is a message.", message);
	}

	private void testShouldTranslateLiteral() {
		// given:
		I18n.getProperties().add("message", "this is a message.");
		
		// when:
		String message = I18n.translate("message");
		
		// then:
		assertEquals("The message is not the expected.", "message", message);
	}

	private void testShouldTranslateLiteralWithEscapedDollar() {
		// given:
		I18n.getProperties().add("message", "this is a message.");
		
		// when:
		String message = I18n.translate("$$message");
		
		// then:
		assertEquals("The message is not the expected.", "$message", message);
	}

	private void testShouldTranslateLiteralWithArgs() {
		// given:
		I18n.getProperties().add("message", "some {0} with {1}, this is a {0}.");
		
		// when:
		String message = I18n.translate("$message,message,arguments");
		
		// then:
		assertEquals("The message is not the expected.", "some message with arguments, this is a message.", message);
	}
	
}
