package com.zine.zinemob.text.xml;

import java.util.Hashtable;

/**
 * Contains the attributes of one XML tag.
 */
public class XmlAttributes {
	
	private Hashtable attributes = new Hashtable();
	
	public String get(String name) {
		return (String) attributes.get(name);
	}
	
	public String get(String name, String defaultValue) {
		String value = get(name);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}
	
	void add(String name, String value) {
		attributes.put(name, value);
	}
	
}
