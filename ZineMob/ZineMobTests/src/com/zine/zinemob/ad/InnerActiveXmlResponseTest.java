package com.zine.zinemob.ad;

import com.zine.zinemob.text.xml.XmlParser;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class InnerActiveXmlResponseTest extends TestCase {
	
	private XmlParser xmlParser;
	
	public InnerActiveXmlResponseTest() {
	}

	public InnerActiveXmlResponseTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		xmlParser = new XmlParser();
	}

	public void tearDown() {
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new InnerActiveXmlResponseTest("testShouldParseXml", new TestMethod()
		{ public void run(TestCase tc) {((InnerActiveXmlResponseTest)tc).testShouldParseXml(); } } ));

		return testSuite;
	}

	private void testShouldParseXml() {
		// given:
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tns:Response xmlns:tns=\"http://www.inner-active.com/SimpleM2M/M2MResponse\"");
		xml.append("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
		xml.append("xsi:schemaLocation=\"http://www.inner-active.com/SimpleM2M/M2MResponseM2MResponse.xsd \"");
		xml.append("Error =\"OK\">");
		xml.append("<tns:Client Id=\"The client id\"/>");
		xml.append("<tns:Ad>");
		xml.append("<tns:Text>tns:Text</tns:Text>");
		xml.append("<tns:URL>tns:URL</tns:URL>");
		xml.append("<tns:Image>tns:ImageSource</tns:Image>");
		xml.append("</tns:Ad>");
		xml.append("</tns:Response>");
		
		InnerActiveResponse innerActiveXmlResponse = new InnerActiveResponse();
		
		// when:
		xmlParser.parseString(xml.toString(), innerActiveXmlResponse);
		
		// then:
		assertTrue("The response should be Ok.", innerActiveXmlResponse.isOk());
		assertEquals("The client id is not the expected.", "The client id", innerActiveXmlResponse.getClientId());
		assertEquals("The text is not the expected.", "tns:Text", innerActiveXmlResponse.getAd().getText());
		assertEquals("The url is not the expected.", "tns:URL", innerActiveXmlResponse.getAd().getUrl());
		assertEquals("The image source is not the expected.", "tns:ImageSource", innerActiveXmlResponse.getAd().getImage());
	}
	
}
