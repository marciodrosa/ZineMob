package com.zine.zinemob.properties;

import com.zine.zinemob.util.TextUtils;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Object that contains a map of String keys and values.
 */
public class Properties {

	private Hashtable hashtable = new Hashtable();

	/**
	 * Returns the value of the key, or null if the key does not exist.
	 */
	public String get(String key) {
		return (String) hashtable.get(key);
	}

	/**
	 * Returns the value of the key, or the defaultValue as String if the key does not exist.
	 */
	public String get(String key, Object defaultValue) {
		String value = get(key);
		if (value == null) {
			if (defaultValue == null) {
				return null;
			} else {
				return defaultValue.toString();
			}
		} else {
			return value;
		}
	}
	
	/**
	 * Adds a new key/value pair.
	 */
	public void add(String key, String value) {
		hashtable.put(key, value);
	}
	
	/**
	 * Remove the key.
	 */
	public void remove(String key) {
		hashtable.remove(key);
	}
	
	/**
	 * Returns the keys count.
	 */
	public int size() {
		return hashtable.size();
	}
	
	/**
	 * Loads the properties from a resource text file. Each line is an entry. "="
	 * or ":" separates the key and the value. Blank lines, lines without the
	 * separator or lines that the first non-blank character is "#" (comment) are
	 * ignored. "\n" is replaced by end line (uses "\\n" to escape).
	 */
	public void loadFromResource(String resourceName) {
		hashtable = new Hashtable();
		String textResource = TextUtils.readTextFromResource(resourceName);
		Vector lines = TextUtils.split(textResource, new String[] {"\r\n", "\n"});
		for (int i=0; i<lines.size(); i++) {
			String line = (String) lines.elementAt(i);
			if (isLineAComentary(line)) {
				continue;
			}
			int dotsCharSeparatorIndex = line.indexOf(':');
			int equalCharSeparatorIndex = line.indexOf('=');
			if (dotsCharSeparatorIndex >= 0 || equalCharSeparatorIndex >= 0) {
				String key, value;
				int separatorIndex;
				if (equalCharSeparatorIndex < 0) {
					separatorIndex = dotsCharSeparatorIndex;
				} else if (dotsCharSeparatorIndex < 0) {
					separatorIndex = equalCharSeparatorIndex;
				} else if (dotsCharSeparatorIndex < equalCharSeparatorIndex) {
					separatorIndex = dotsCharSeparatorIndex;
				} else {
					separatorIndex = equalCharSeparatorIndex;
				}
				key = line.substring(0, separatorIndex);
				value = line.substring(separatorIndex + 1);
				add(substituteSlashNByEndLine(key), substituteSlashNByEndLine(value));
			}
		}
	}
	
	private boolean isLineAComentary(String line) {
		return line.trim().startsWith("#");
	}
	
	private String substituteSlashNByEndLine(String text) {
		StringBuffer result = new StringBuffer();
		for (int i=0; i<text.length(); i++) {
			if (text.indexOf("\\n", i) == i) {
				if (TextUtils.isCharEscapedAt(text, i, '\\')) {
					result.append('n');
				} else {
					result.append('\n');
				}
				i++;
			} else {
				result.append(text.charAt(i));
			}
		}
		return result.toString();
	}
}
