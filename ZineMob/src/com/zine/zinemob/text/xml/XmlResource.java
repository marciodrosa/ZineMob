package com.zine.zinemob.text.xml;

/**
 * Base class for objects that can be loaded from a XML.
 */
public abstract class XmlResource {
	
	/**
	 * Reads the XML tag.
	 * @param name the name of the tag
	 * @param text the text of the tag
	 * @param attributes the attributes of the tag
	 * @return if the tag creates a new instance of some XmlResource, it should
	 * return this object. Return this if the children of the tag will be processed
	 * by this object. Otherwise, it should return null (if the tag represents a
	 * primitive, like String, Integer or any other object that isn't a XmlResource).
	 */
	public abstract XmlResource readXmlTag(String name, String text, XmlAttributes attributes);
}
