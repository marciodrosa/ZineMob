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
			return defaultValue.toString();
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
			if (dotsCharSeparatorIndex < 0 && equalCharSeparatorIndex < 0) {
				continue;
			}
			String key, value;
			if (dotsCharSeparatorIndex < equalCharSeparatorIndex) {
				key = line.substring(0, dotsCharSeparatorIndex);
				value = line.substring(dotsCharSeparatorIndex + 1);
			} else {
				key = line.substring(0, equalCharSeparatorIndex);
				value = line.substring(equalCharSeparatorIndex + 1);
			}
			add(substituteSlashNByEndLine(key), substituteSlashNByEndLine(value));
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
				i+=2;
			} else {
				result.append(text.charAt(i));
			}
		}
		return result.toString();
	}
}
