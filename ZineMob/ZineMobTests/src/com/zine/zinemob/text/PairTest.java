package com.zine.zinemob.text;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class PairTest extends TestCase {
	
	public PairTest() {

	}

	public PairTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new PairTest("testConstructorShouldSetKeyAndValue", new TestMethod()
		{ public void run(TestCase tc) {((PairTest)tc).testConstructorShouldSetKeyAndValue(); } } ));

		testSuite.addTest(new PairTest("testCloneShouldCreateAnNewInstanceWithSameKeyAndValue", new TestMethod()
		{ public void run(TestCase tc) {((PairTest)tc).testCloneShouldCreateAnNewInstanceWithSameKeyAndValue(); } } ));

		testSuite.addTest(new PairTest("testToStringShouldFormatKeyAndValue", new TestMethod()
		{ public void run(TestCase tc) {((PairTest)tc).testToStringShouldFormatKeyAndValue(); } } ));

		testSuite.addTest(new PairTest("testEqualsShouldReturnTrueWhenKeyAndValueAreEquals", new TestMethod()
		{ public void run(TestCase tc) {((PairTest)tc).testEqualsShouldReturnTrueWhenKeyAndValueAreEquals(); } } ));

		testSuite.addTest(new PairTest("testEqualsShouldReturnFalseWhenKeysAreDifferent", new TestMethod()
		{ public void run(TestCase tc) {((PairTest)tc).testEqualsShouldReturnFalseWhenKeysAreDifferent(); } } ));

		testSuite.addTest(new PairTest("testEqualsShouldReturnFalseWhenValuesAreDifferent", new TestMethod()
		{ public void run(TestCase tc) {((PairTest)tc).testEqualsShouldReturnFalseWhenValuesAreDifferent(); } } ));

		return testSuite;
	}

	private void testConstructorShouldSetKeyAndValue() {
		// given:
		Pair pair = new Pair("key", "value");
		
		// then:
		assertEquals("The key is not the expected.", "key", pair.getKey());
		assertEquals("The value is not the expected.", "value", pair.getValue());
	}

	private void testCloneShouldCreateAnNewInstanceWithSameKeyAndValue() {
		// given:
		Pair pair = new Pair("key", "value");
		
		// when:
		Pair cloned = pair.clone();
		
		// then:
		assertEquals("The two pairs should be equals.", pair, cloned);
		assertTrue("The objects should be different instances", pair != cloned);
	}

	private void testToStringShouldFormatKeyAndValue() {
		// given:
		Pair pair = new Pair("key", "value");
		
		// when:
		String string = pair.toString();
		
		// then:
		assertEquals("The pair as String is not the expected.", "key=value", string);
	}

	private void testEqualsShouldReturnTrueWhenKeyAndValueAreEquals() {
		// given:
		Pair pair = new Pair("key", "value");
		Pair other = new Pair("key", "value");
		
		// then:
		assertEquals("The two pairs should be equals.", pair, other);
	}

	private void testEqualsShouldReturnFalseWhenKeysAreDifferent() {
		// given:
		Pair pair = new Pair("key", "value");
		Pair other = new Pair("key2", "value");
		
		// then:
		assertTrue("The two pairs should not be equals.", !pair.equals(other));
	}

	private void testEqualsShouldReturnFalseWhenValuesAreDifferent() {
		// given:
		Pair pair = new Pair("key", "value");
		Pair other = new Pair("key", "value2");
		
		// then:
		assertTrue("The two pairs should not be equals.", !pair.equals(other));
	}
	
}
