package com.zine.zinemob.text;

/**
 * A simple pair of key and value Strings.
 *
 */
public class Pair {
	
	private String key, value;
	
	public Pair(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public Pair clone() {
		return new Pair(key, value);
	}
	
	/**
	 * Returns a String representation in format key=value.
	 */
	public String toString() {
		return key + '=' + value;
	}

	public int hashCode() {
		return toString().hashCode();
	}
	
	/**
	 * Two Pairs are equals if the keys and values are equals.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Pair) {
			Pair other = (Pair) obj;
			return other.key.equals(this.key) && other.value.equals(this.value);
		} else {
			return false;
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
