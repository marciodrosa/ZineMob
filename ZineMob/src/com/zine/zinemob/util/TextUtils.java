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
		return readTextFromInputStream(inputStream);
	}
	
	/**
	 * Reads and returns the text in the input stream using the default encoding.
	 * @param inputStream the input stream; it will be closed in the end of the process
	 * @return the text, or an empty String if it fails
	 */
	public static String readTextFromInputStream(InputStream inputStream) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			int b;
			while ((b=inputStream.read()) != -1) {
				byteStream.write(b);
			}
			return new String(byteStream.toByteArray());
		} catch (Exception ex) {
			System.out.print("Error reading text input stream: " + ex.toString());
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
				if (substringStartsWith(originalText, i, separators[j])) {
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
	
	private static boolean substringStartsWith(String string, int index, String token) {
		try {
			for (int i=0; i<token.length(); i++) {
				if (token.charAt(i) != string.charAt(index+i)) {
					return false;
				}
			}
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
		return true;
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
	
	/**
	 * Replaces the chunks of the original String with the new value.
	 * @param originalString the original String
	 * @param oldValue the substring to be searched
	 * @param newValue the new value of the substring
	 * @return the new String
	 */
	public static String replace(String originalString, String oldValue, String newValue) {
		StringBuffer newString = new StringBuffer();
		for (int i=0; i<originalString.length(); i++) {
			if (substringStartsWith(originalString, i, oldValue)) {
				newString.append(newValue);
				i += oldValue.length() - 1;
			} else {
				newString.append(originalString.charAt(i));
			}
		}
		return newString.toString();
	}
}
