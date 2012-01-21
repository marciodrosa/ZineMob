package com.zine.zinemob.properties;

import com.zine.zinemob.util.TextUtils;
import java.util.Hashtable;
import java.util.Vector;

public class Properties {

	private Hashtable hashtable = new Hashtable();

	public String get(String key) {
		return (String) hashtable.get(key);
	}

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
	
	public void add(String key, String value) {
		hashtable.put(key, value);
	}
	
	public void remove(String key) {
		hashtable.remove(key);
	}
	
	public int size() {
		return hashtable.size();
	}
	
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
