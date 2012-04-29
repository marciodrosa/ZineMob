package com.zine.zinemob.text;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;
import java.util.Vector;

public class TextUtilsTest extends TestCase {
	
	public TextUtilsTest() {

	}

	public TextUtilsTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new TextUtilsTest("testReadTextFromResourceShouldReturnTheContentAsString", new TestMethod()
		{ public void run(TestCase tc) {((TextUtilsTest)tc).testReadTextFromResourceShouldReturnTheContentAsString(); } } ));

		testSuite.addTest(new TextUtilsTest("testReadTextFromResourceShouldReturnAnEmptyStringWhenResourceIsInvalid", new TestMethod()
		{ public void run(TestCase tc) {((TextUtilsTest)tc).testReadTextFromResourceShouldReturnAnEmptyStringWhenResourceIsInvalid(); } } ));

		testSuite.addTest(new TextUtilsTest("testSplit", new TestMethod()
		{ public void run(TestCase tc) {((TextUtilsTest)tc).testSplit(); } } ));

		testSuite.addTest(new TextUtilsTest("testIsCharEscapedAt", new TestMethod()
		{ public void run(TestCase tc) {((TextUtilsTest)tc).testIsCharEscapedAt(); } } ));

		testSuite.addTest(new TextUtilsTest("testReplace", new TestMethod()
		{ public void run(TestCase tc) {((TextUtilsTest)tc).testReplace(); } } ));

		return testSuite;
	}

	private void testReadTextFromResourceShouldReturnTheContentAsString() {
		// when:
		String text = TextUtils.readTextFromResource("/com/zine/zinemob/res/text.txt", "UTF-8");
		
		// then:
		assertEquals("The loaded text is not the expected.", "This is a\r\ntext file.", text);
	}

	private void testReadTextFromResourceShouldReturnAnEmptyStringWhenResourceIsInvalid() {
		// when:
		String text = TextUtils.readTextFromResource("/com/zine/zinemob/res/inexistedfile.txt", null);
		
		// then:
		assertEquals("The loaded text should be empty because the file does not exists.", "", text);
	}
	
	private void testSplit() {
		// given:
		String text = "#This ## is a *+*text,, with separators#";
		
		// when:
		Vector parts = TextUtils.split(text, new String[] {"##", "#", "*+*", ","});
		
		// then:
		assertEquals("The quantity of splited parts is not the expected.", 7, parts.size());
		assertEquals("The part 0 is not the expected.", "", parts.elementAt(0));
		assertEquals("The part 1 is not the expected.", "This ", parts.elementAt(1));
		assertEquals("The part 2 is not the expected.", " is a ", parts.elementAt(2));
		assertEquals("The part 3 is not the expected.", "text", parts.elementAt(3));
		assertEquals("The part 4 is not the expected.", "", parts.elementAt(4));
		assertEquals("The part 5 is not the expected.", " with separators", parts.elementAt(5));
		assertEquals("The part 6 is not the expected.", "", parts.elementAt(6));
	}
	
	public void testIsCharEscapedAt() {
		// given:
		String text = "This $is a $$text with $$$escaped characters.";
		
		// when:
		boolean isChar0Escaped = TextUtils.isCharEscapedAt(text, 0, '$');
		boolean isChar1Escaped = TextUtils.isCharEscapedAt(text, 1, '$');
		boolean isChar5Escaped = TextUtils.isCharEscapedAt(text, 5, '$');
		boolean isChar6Escaped = TextUtils.isCharEscapedAt(text, 6, '$');
		boolean isChar11Escaped = TextUtils.isCharEscapedAt(text, 11, '$');
		boolean isChar12Escaped = TextUtils.isCharEscapedAt(text, 12, '$');
		boolean isChar13Escaped = TextUtils.isCharEscapedAt(text, 13, '$');
		boolean isChar23Escaped = TextUtils.isCharEscapedAt(text, 23, '$');
		boolean isChar24Escaped = TextUtils.isCharEscapedAt(text, 24, '$');
		boolean isChar25Escaped = TextUtils.isCharEscapedAt(text, 25, '$');
		boolean isChar26Escaped = TextUtils.isCharEscapedAt(text, 26, '$');
		
		// then:
		assertTrue("The character 0 should not be escaped.", !isChar0Escaped);
		assertTrue("The character 1 should not be escaped.", !isChar1Escaped);
		assertTrue("The character 5 should not be escaped.", !isChar5Escaped);
		assertTrue("The character 6 should be escaped.", isChar6Escaped);
		assertTrue("The character 11 should not be escaped.", !isChar11Escaped);
		assertTrue("The character 12 should be escaped.", isChar12Escaped);
		assertTrue("The character 13 should not be escaped.", !isChar13Escaped);
		assertTrue("The character 23 should not be escaped.", !isChar23Escaped);
		assertTrue("The character 24 should be escaped.", isChar24Escaped);
		assertTrue("The character 25 should not be escaped.", !isChar25Escaped);
		assertTrue("The character 26 should be escaped.", isChar26Escaped);
	}

	private void testReplace() {
		// given:
		String originalString = "ZineMob is an engine.";
		
		// when:
		String newString = TextUtils.replace(originalString, "an", "the");
		
		// then:
		assertEquals("The new string is not the expected.", "ZineMob is the engine.", newString);
	}
	
}
