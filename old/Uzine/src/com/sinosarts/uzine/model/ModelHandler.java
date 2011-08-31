package com.sinosarts.uzine.model;

import java.util.Hashtable;

public class ModelHandler {

	private Hashtable objects = new Hashtable();
	private Hashtable lists = new Hashtable();

	public void set(String key, Object object) {
		objects.put(key, object);
	}

	public void setList(String key, Object[] list) {
		lists.put(key, list);
	}

	public Object get(String key) {
		return objects.get(key);
	}

	public Object[] getList(String key) {
		return (Object[])lists.get(key);
	}
}
