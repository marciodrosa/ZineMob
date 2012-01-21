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

		testSuite.addTest(new PropertiesTest("testShouldLoadFromResource", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testShouldLoadFromResource(); } } ));

		testSuite.addTest(new PropertiesTest("testLoadFromResourceShouldRemoveAllCurrentKeys", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testLoadFromResourceShouldRemoveAllCurrentKeys(); } } ));

		testSuite.addTest(new PropertiesTest("testErrorWhileLoadFromResourceShouldRemoveAllCurrentKeys", new TestMethod()
		{ public void run(TestCase tc) {((PropertiesTest)tc).testErrorWhileLoadFromResourceShouldRemoveAllCurrentKeys(); } } ));

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

	private void testShouldLoadFromResource() {
		// when:
		properties.loadFromResource("/com/zine/zinemob/res/testProperties.properties");
		
		// then:
		assertEquals("The loaded keys count is not the expected.", 6, properties.size());
		assertEquals("The value of the key 'key' is not the expected.", "value", properties.get("key"));
		assertEquals("The value of the key 'key with other separator' is not the expected.", "other value", properties.get("key with other separator"));
		assertEquals("The value of the key ' blank spaces ' is not the expected.", "  some blank spaces  ", properties.get(" blank spaces "));
		assertEquals("The value of the key 'value with endline' is not the expected.", "this is a\nnew line.", properties.get("value with endline"));
		assertEquals("The value of the key 'value with escaped endline' is not the expected.", "this is not a\\nnew line.", properties.get("value with escaped endline"));
		assertEquals("The value of the key 'key with multiple separators' is not the expected.", "value with the = separators : characters", properties.get("key with multiple separators"));
	}

	private void testLoadFromResourceShouldRemoveAllCurrentKeys() {
		// given:
		properties.add("aaa", "a");
		properties.add("bbb", "b");
		
		// when:
		properties.loadFromResource("/com/zine/zinemob/res/testProperties.properties");
		
		// then:
		assertEquals("The loaded keys count is not the expected, should be only the new loaded keys.", 6, properties.size());
		assertNull("The old key 'aaa' should be removed.", properties.get("aaa"));
		assertNull("The old key 'bbb' should be removed.", properties.get("bbb"));
	}

	private void testErrorWhileLoadFromResourceShouldRemoveAllCurrentKeys() {
		// given:
		properties.add("aaa", "a");
		properties.add("bbb", "b");
		
		// when:
		properties.loadFromResource("/com/zine/zinemob/res/inexistingPropertiesFile.properties");
		
		// then:
		assertEquals("The loaded keys count is not the expected, should remove all when load a resource.", 0, properties.size());
		assertNull("The old key 'aaa' should be removed.", properties.get("aaa"));
		assertNull("The old key 'bbb' should be removed.", properties.get("bbb"));
	}
	
}
