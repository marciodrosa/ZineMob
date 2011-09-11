package com.zine.zinemob.gui;

import java.util.Hashtable;

public class GuiEvent {
	
	private int id;
	
	private Hashtable values = new Hashtable();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Object get(String name) {
		return values.get(name);
	}
	
	public void set(String name, Object value) {
		values.put(name, value);
	}
}
