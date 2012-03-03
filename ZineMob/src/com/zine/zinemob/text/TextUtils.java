package com.zine.zinemob.text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 * Utilities to handle texts and Strings.
 */
public class TextUtils {
	
	/**
	 * Loads the resource and returns the text.
	 * @param resourceName the resource name
	 * @param enc the encoding, if it is null or invalid, then the default encoding
	 * is used
	 * @return the text, or an empty String if it fails
	 */
	public static String readTextFromResource(String resourceName, String enc) {
		InputStream inputStream = TextUtils.class.getResourceAsStream(resourceName);
		return readTextFromInputStream(inputStream, enc);
	}
	
	/**
	 * Reads and returns the text in the input stream using the default encoding.
	 * @param inputStream the input stream; it will be closed in the end of the process
	 * @param enc the encoding, if it is null or invalid, then the default encoding
	 * is used
	 * @return the text, or an empty String if it fails
	 */
	public static String readTextFromInputStream(InputStream inputStream, String enc) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			int b;
			while ((b=inputStream.read()) != -1) {
				byteStream.write(b);
			}
			if (enc == null) {
				return new String(byteStream.toByteArray());
			} else {
				try {
					return new String(byteStream.toByteArray(), enc);
				} catch (UnsupportedEncodingException ex) {
					return new String(byteStream.toByteArray());
				}
			}
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
	
	/**
	 * Encodes the String using the URL encode. Original code from article
	 * http://www.developer.nokia.com/Community/Wiki/How_to_encode_URL_in_Java_ME
	 * @param s the original String
	 * @return the encoded String.
	 */
	public static String encodeUrl(String s) {
		StringBuffer sbuf = new StringBuffer();
		int len = s.length();
		for (int i = 0; i < len; i++) {
			int ch = s.charAt(i);
			if ('A' <= ch && ch <= 'Z') { // 'A'..'Z'
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') { // 'a'..'z'
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') { // '0'..'9'
				sbuf.append((char) ch);
			} else if (ch == ' ') { // space
				sbuf.append('+');
			} else if (ch == '-' || ch == '_' //these characters don't need encoding
					|| ch == '.' || ch == '*') {
				sbuf.append((char) ch);
			} else if (ch <= 0x007f) { // other ASCII
				sbuf.append(hex(ch));
			} else if (ch <= 0x07FF) { // non-ASCII <= 0x7FF
				sbuf.append(hex(0xc0 | (ch >> 6)));
				sbuf.append(hex(0x80 | (ch & 0x3F)));
			} else { // 0x7FF < ch <= 0xFFFF
				sbuf.append(hex(0xe0 | (ch >> 12)));
				sbuf.append(hex(0x80 | ((ch >> 6) & 0x3F)));
				sbuf.append(hex(0x80 | (ch & 0x3F)));
			}
		}
		return sbuf.toString();
	}
	
	/**
	 * Decodes the URL-encoded String. Original code from Google Groups:
	 * http://groups.google.com/group/Google-Accounts-API/msg/6c1b5e7e5a2226be?pli=1
	 * @param s the original String
	 * @return the encoded String.
	 */
	public static String decodeUrl(String s) {
		StringBuffer sbuf = new StringBuffer();
		int length = s.length();
		for (int i=0; i<length; i++) {
			char ch = s.charAt(i);
			if (ch == '%' && i+2 < length) {
				char c1 = s.charAt(i + 1);
				char c2 = s.charAt(i + 2);
				if (isHexit(c1) && isHexit(c2)) {
					sbuf.append((char) (hexit(c1) * 16 + hexit(c2)));
					i+= 2;
				} else {
					sbuf.append(ch);
				}
			} else {
				sbuf.append(ch);
			}
		}
		
		return sbuf.toString();
	}

	private static boolean isHexit(char c) {
		String legalChars = "0123456789abcdefABCDEF";
		return (legalChars.indexOf(c) != -1);
	}

	private static int hexit(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		if (c >= 'A' && c <= 'F') {
			return c - 'A' + 10;
		}
		return 0;       // shouldn't happen, we're guarded by isHexit()
	}

	//get the encoded value of a single symbol, each return value is 3 characters long
	private static String hex(int sym) {
		return (hex.substring(sym * 3, sym * 3 + 3));
	}

	// Hex constants concatenated into a string, messy but efficient
	final static String hex = "%00%01%02%03%04%05%06%07%08%09%0a%0b%0c%0d%0e%0f%10%11%12%13%14%15%16%17%18%19%1a%1b%1c%1d%1e%1f%20%21%22%23%24%25%26%27%28%29%2a%2b%2c%2d%2e%2f%30%31%32%33%34%35%36%37%38%39%3a%3b%3c%3d%3e%3f%40%41%42%43%44%45%46%47%48%49%4a%4b%4c%4d%4e%4f%50%51%52%53%54%55%56%57%58%59%5a%5b%5c%5d%5e%5f%60%61%62%63%64%65%66%67%68%69%6a%6b%6c%6d%6e%6f%70%71%72%73%74%75%76%77%78%79%7a%7b%7c%7d%7e%7f%80%81%82%83%84%85%86%87%88%89%8a%8b%8c%8d%8e%8f%90%91%92%93%94%95%96%97%98%99%9a%9b%9c%9d%9e%9f%a0%a1%a2%a3%a4%a5%a6%a7%a8%a9%aa%ab%ac%ad%ae%af%b0%b1%b2%b3%b4%b5%b6%b7%b8%b9%ba%bb%bc%bd%be%bf%c0%c1%c2%c3%c4%c5%c6%c7%c8%c9%ca%cb%cc%cd%ce%cf%d0%d1%d2%d3%d4%d5%d6%d7%d8%d9%da%db%dc%dd%de%df%e0%e1%e2%e3%e4%e5%e6%e7%e8%e9%ea%eb%ec%ed%ee%ef%f0%f1%f2%f3%f4%f5%f6%f7%f8%f9%fa%fb%fc%fd%fe%ff";
}