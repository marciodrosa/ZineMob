package com.zine.zinemob.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

/**
 * Utilities to handle texts and Strings.
 */
public class TextUtils {
	
	/**
	 * Loads the resource and returns the text into using the default encoding.
	 * @param resourceName the resource name
	 * @return the text, or an empty String if it fails
	 */
	public static String readTextFromResource(String resourceName) {
		InputStream inputStream = TextUtils.class.getResourceAsStream(resourceName);
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			int b;
			while ((b=inputStream.read()) != -1) {
				byteStream.write(b);
			}
			return new String(byteStream.toByteArray());
		} catch (Exception ex) {
			System.out.print("Error reading properties resource " + resourceName + " " + ex.toString());
			return "";
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * Splits a String and returns the parts
	 * @param originalText the text to be splited
	 * @param separators the separators
	 * @return the parts of the String separated by the separators
	 */
	public static Vector split(String originalText, String[] separators) {
		Vector parts = new Vector();
		StringBuffer currentPart = new StringBuffer();
		for (int i=0; i<originalText.length(); i++) {
			boolean isSeparator = false;
			for (int j=0; j<separators.length; j++) {
				if (originalText.indexOf(separators[j], i) == i) {
					i += separators[j].length() - 1;
					isSeparator = true;
					parts.addElement(currentPart.toString());
					currentPart = new StringBuffer();
					break;
				}
			}
			if (!isSeparator) {
				currentPart.append(originalText.charAt(i));
			}
		}
		parts.addElement(currentPart.toString());
		return parts;
	}

	/**
	 * Returns if the character in a specified position is escaped by the escape
	 * character. This method verifies if the escape character is also escaped.
	 * @param text the text to be verified
	 * @param index the index of the character
	 * @param escapeChar the escape character
	 * @return true if the character at index is escaped by the escape character
	 */
	public static boolean isCharEscapedAt(String text, int index, char escapeChar) {
		return (index > 0 && text.charAt(index-1) == escapeChar && !isCharEscapedAt(text, index-1, escapeChar));
	}
}
