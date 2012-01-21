package com.zine.zinemob.properties;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class PropertiesTest extends TestCase {
	
	private Properties properties;
	
	public PropertiesTest() {

	}

	public PropertiesTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		properties = new Properties();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new PropertiesTest("testShouldReturnTheValueOfTheKey", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testShouldReturnTheValueOfTheKey(); } } ));

		testSuite.addTest(new PropertiesTest("testShouldReturnNullWhenTheKeyDoesNotExist", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testShouldReturnNullWhenTheKeyDoesNotExist(); } } ));

		testSuite.addTest(new PropertiesTest("testShouldReturnTheValueOfTheKeyAndIgnoreTheDefaultValue", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testShouldReturnTheValueOfTheKeyAndIgnoreTheDefaultValue(); } } ));

		testSuite.addTest(new PropertiesTest("testShouldReturnTheDefaultValueWhenTheKeyDoesNotExist", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testShouldReturnTheDefaultValueWhenTheKeyDoesNotExist(); } } ));

		testSuite.addTest(new PropertiesTest("testShouldReturnNullWhenTheKeyDoesNotExistAndDefaultValueIsNull", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testShouldReturnNullWhenTheKeyDoesNotExistAndDefaultValueIsNull(); } } ));

		testSuite.addTest(new PropertiesTest("testAddNewExistingKeyShouldReplaceTheOldOne", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testAddNewExistingKeyShouldReplaceTheOldOne(); } } ));

		testSuite.addTest(new PropertiesTest("testShouldRemoveExistingKey", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testShouldRemoveExistingKey(); } } ));

		return testSuite;
	}

	private void testShouldReturnTheValueOfTheKey() {
		// given:
		properties.add("aaa", "value a");
		properties.add("bbb", "value b");
		properties.add("ccc", "value c");
		
		// when:
		String value = properties.get("bbb");
		
		// then:
		assertEquals("The value of the key bbb is not the expected.", "value b", value);
	}

	private void testShouldReturnNullWhenTheKeyDoesNotExist() {
		// given:
		properties.add("aaa", "value a");
		properties.add("bbb", "value b");
		
		// when:
		String value = properties.get("ccc");
		
		// then:
		assertNull("The value of the inexisting key ccc should be null.", value);
	}

	private void testShouldReturnTheValueOfTheKeyAndIgnoreTheDefaultValue() {
		// given:
		properties.add("aaa", "value a");
		properties.add("bbb", "value b");
		properties.add("ccc", "value c");
		
		// when:
		String value = properties.get("bbb", "ignored default value");
		
		// then:
		assertEquals("The value of the key bbb is not the expected.", "value b", value);
	}

	private void testShouldReturnTheDefaultValueWhenTheKeyDoesNotExist() {
		// given:
		properties.add("aaa", "value a");
		properties.add("bbb", "value b");
		
		// when:
		String value = properties.get("ccc", "the default value");
		
		// then:
		assertEquals("The value of the inexisting key ccc should be the default value.", "the default value", value);
	}

	private void testShouldReturnNullWhenTheKeyDoesNotExistAndDefaultValueIsNull() {
		// given:
		properties.add("aaa", "value a");
		properties.add("bbb", "value b");
		
		// when:
		String value = properties.get("ccc", null);
		
		// then:
		assertNull("The value of the inexisting key ccc should be null because the default value is null too.", value);
	}

	private void testAddNewExistingKeyShouldReplaceTheOldOne() {
		// given:
		properties.add("aaa", "value a");
		properties.add("bbb", "value b");
		properties.add("ccc", "value c");
		
		// when:
		properties.add("bbb", "new value");
		
		// then:
		assertEquals("The value of the key bbb is not the expected.", "new value", properties.get("bbb"));
	}

	private void testShouldRemoveExistingKey() {
		// given:
		properties.add("aaa", "value a");
		properties.add("bbb", "value b");
		
		// when:
		properties.remove("bbb");
		
		// then:
		assertEquals("The value of the key aaa is not the expected.", "value a", properties.get("aaa"));
		assertNull("The value of the key bbb should be null, because bbb was removed.", properties.get("bbb"));
	}
	
}
