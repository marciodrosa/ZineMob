package com.zine.zinemob.text.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Stack;
import org.kxml2.io.KXmlParser;

/**
 * Reads a XML and calls the readXmlTag method of the root XmlResource object.
 */
public class XmlParser {
	
	/**
	 * Parses the XML readed from the given resource.
	 * @param resourceName the resource name
	 * @param root the root resource
	 */
	public void parseResource(String resourceName, XmlResource root) {
		InputStream inputStream = getClass().getResourceAsStream(resourceName);
		parseInputStream(inputStream, root);
		try {
			inputStream.close();
		} catch (IOException ex) {
		}
	}
	
	/**
	 * Parses the XML from the String.
	 * @param xml the XML String
	 * @param root the root resource
	 */
	public void parseString(String xml, XmlResource root) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
		parseInputStream(inputStream, root);
		try {
			inputStream.close();
		} catch (IOException ex) {
		}
	}
	
	/**
	 * Parses the XML readed from the input stream.
	 * @param inputStream the input stream; this object is not closed at the end.
	 * @param root the root resource
	 */
	public void parseInputStream(InputStream inputStream, XmlResource root) {
		KXmlParser xmlParser = new KXmlParser();
		try {
			xmlParser.setInput(new InputStreamReader(inputStream));
			
			Stack resourceStack = new Stack();
			XmlResource currentResource = root;
			
			while (xmlParser.getEventType() != KXmlParser.END_DOCUMENT) {
				if (xmlParser.getEventType() == KXmlParser.START_TAG) {
					if (currentResource == null) {
						resourceStack.push(currentResource);
						xmlParser.next();
					} else {
						String name = xmlParser.getName();
						XmlAttributes attributes = readAttributes(xmlParser);
						String text = "";
						xmlParser.next();
						if (xmlParser.getEventType() == KXmlParser.TEXT) {
							text = xmlParser.getText();
							xmlParser.next();
						}
						resourceStack.push(currentResource);
						currentResource = currentResource.readXmlTag(name, text, attributes);
					}
				} else if (xmlParser.getEventType() == KXmlParser.END_TAG) {
					currentResource = (XmlResource) resourceStack.pop();
					xmlParser.next();
				} else {
					xmlParser.next();
				}
			}
			
		} catch (Exception ex) {
			System.out.println("Error reading XML: " + ex.toString());
		}
	}
	
	private XmlAttributes readAttributes(KXmlParser xmlParser) {
		XmlAttributes attributes = new XmlAttributes();
		for (int i=0; i<xmlParser.getAttributeCount(); i++) {
			attributes.add(xmlParser.getAttributeName(i), xmlParser.getAttributeValue(i));
		}
		return attributes;
	}
}
