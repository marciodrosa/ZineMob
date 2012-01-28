package com.zine.zinemob.text.xml;

import java.util.Vector;

/**
 * XML resource that map the testXml.xml file.
 */
public class XmlResourceFixture extends XmlResource {
	
	static class SomeTag extends XmlResource {
		String someAttribute;
		String otherAttribute;
		Vector words = new Vector(); // <String>

		public XmlResource readXmlTag(String name, String text, XmlAttributes attributes) {
			if (name.equals("word")) {
				this.words.addElement(text);
			}
			return null;
		}
	}
	
	SomeTag someTag;
	String otherTag;
	String attribute1;
	String attribute2;

	public XmlResource readXmlTag(String name, String text, XmlAttributes attributes) {
		if (name.equals("root")) {
			return this;
		} else if (name.equals("someTag")) {
			this.someTag = new SomeTag();
			this.someTag.someAttribute = attributes.get("someAttribute");
			this.someTag.otherAttribute = attributes.get("otherAttribute");
			return this.someTag;
		} else if (name.equals("tagWithoutOtherResourceXmlObject")) {
			return this;
		} else if (name.equals("otherTag")) {
			this.otherTag = text;
			return null;
		} else if (name.equals("attributes")) {
			this.attribute1 = attributes.get("attribute1");
			this.attribute2 = attributes.get("attribute2");
			return null;
		} else {
			return null;
		}
	}
}
