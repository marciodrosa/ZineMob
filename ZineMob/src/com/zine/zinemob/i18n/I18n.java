package com.zine.zinemob.i18n;

import com.zine.zinemob.text.Properties;
import com.zine.zinemob.text.TextUtils;
import java.util.Vector;

/**
 * Global class to manage the language internacionalization.
 */
public class I18n {
	
	private static Properties properties = new Properties();
	
	/**
	 * Sets the properties that contains the idiom.
	 */
	public static void setProperties(Properties properties) {
		I18n.properties = properties;
	}
	
	/**
	 * Returns the properties that contains the idiom.
	 */
	public static Properties getProperties() {
		return properties;
	}
	
	/**
	 * Loads the idiom from a resource properties file.
	 */
	public static void loadFromResource(String resourceName) {
		properties.loadFromResource(resourceName);
	}
	
	/**
	 * Returns the expression by the key. If the key is invalid, a String ???key???
	 * is returned.
	 */
	public static String get(String key) {
		return properties.get(key, getUnknowValue(key));
	}
	
	/**
	 * Returns the expression by the key. If the key is invalid, a String ???key???
	 * is returned. Each arg is placed into the final String, replacing the {i}
	 * token, where i is the arg number (starting by 0).
	 */
	public static String get(String key, Object[] args) {
		String value = properties.get(key);
		if (value == null) {
			return getUnknowValue(key);
		} else {
			for (int i=0; i<args.length; i++) {
				String argToken = new StringBuffer("{").append(i).append('}').toString();
				value = TextUtils.replace(value, argToken, args[i] == null ? "null" : args[i].toString());
			}
			return value;
		}
	}
	
	/**
	 * Translates the value, that can be a key or not. If starts with $, then the
	 * rest of the String is a key (use $$ to escape). To use arguments, use the
	 * format "$key,arg0,arg1,arg2".
	 */
	public static String translate(String valueToTranslate) {
		if (valueToTranslate.startsWith("$$")) {
			return valueToTranslate.substring(1);
		} else if (valueToTranslate.startsWith("$")) {
			return translateKeyWithArgs(valueToTranslate.substring(1));
		} else {
			return valueToTranslate;
		}
	}
	
	private static String translateKeyWithArgs(String keyWithArgs) {
		Vector parts = TextUtils.split(keyWithArgs, new String[]{","});
		String[] args = new String[parts.size() - 1];
		String key = (String) parts.elementAt(0);
		for (int i=0; i<args.length; i++) {
			args[i] = (String) parts.elementAt(i + 1);
		}
		return get(key, args);
	}
	
	private static String getUnknowValue(String key) {
		return new StringBuffer(key.length() + 6).append("???").append(key).append("???").toString();
	}
}
