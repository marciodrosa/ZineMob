package com.zine.zinemob.text.xml;

import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class XmlParserTest extends TestCase {
	
	private XmlParser xmlParser;
	
	public XmlParserTest() {
	}

	public XmlParserTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		xmlParser = new XmlParser();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new XmlParserTest("testParseXmlShouldSetResourceAttributes", new TestMethod()
		{ public void run(TestCase tc) {((XmlParserTest)tc).testParseXmlShouldSetResourceAttributes(); } } ));

		return testSuite;
	}

	private void testParseXmlShouldSetResourceAttributes() {
		// given:
		XmlResourceFixture xmlResource = new XmlResourceFixture();
		
		// when:
		xmlParser.parseResource("/com/zine/zinemob/res/testXml.xml", xmlResource);
		
		// then:
		assertNotNull("The 'someTag' object should not be null.", xmlResource.someTag);
		assertEquals("The 'someAttribute' of the 'someTag' object is not the expected.", "the attribute", xmlResource.someTag.someAttribute);
		assertEquals("The 'otherAttribute' of the 'someTag' object is not the expected.", "other attribute", xmlResource.someTag.otherAttribute);
		assertEquals("Should have 2 words in 'someTag'.", 2, xmlResource.someTag.words.size());
		assertEquals("The first word of 'someTag' is not the expected.", "word one", xmlResource.someTag.words.elementAt(0));
		assertEquals("The second word of 'someTag' is not the expected.", "word two", xmlResource.someTag.words.elementAt(1));
		assertEquals("The 'attribute1' is not the expected.", "1", xmlResource.attribute1);
		assertEquals("The 'attribute2' is not the expected.", "2", xmlResource.attribute2);
		assertEquals("The 'otherTag' is not the expected.", "This is the text of the other tag.", xmlResource.otherTag);
	}
	
}
