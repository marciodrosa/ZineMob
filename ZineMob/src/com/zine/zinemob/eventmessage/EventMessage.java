package com.zine.zinemob.eventmessage;

import java.util.Hashtable;

/**
 * Generic object used to share data and informations from one point of the application
 * to another. It is used when GUI events are propagated or with the FlowController class.
 */
public class EventMessage {
	
	private int id;
	
	private Hashtable values = new Hashtable();

	/**
	 * Returns the ID of the event.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the ID of the event.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the object identified by its name. The object should be setted with
	 * the set method by the emitter of the event.
	 * @param name the name of the object
	 * @return the object idenfied by the name, or null if there is no object identified
	 * by the name
	 */
	public Object get(String name) {
		return values.get(name);
	}
	
	/**
	 * Returns the object identified by its name. The object should be setted with
	 * the set method by the emitter of the event.
	 * @param name the name of the object
	 * @param defaultValue the default value to return if there is no object identified
	 * by the name
	 * @return the object idenfied by the name, or the default value if there is no object identified
	 * by the name
	 */
	public Object get(String name, Object defaultValue) {
		Object object = get(name);
		if (object == null) {
			return defaultValue;
		} else {
			return object;
		}
	}
	
	/**
	 * Sets an object to the event message. This method is usually called by the
	 * emitter of the event, so the object that receives the event message can
	 * retrive the object by the name using the get method. It does nothing if
	 * the object or name are null.
	 * @param name the name of the object
	 * @param value the object
	 */
	public void set(String name, Object value) {
		try {
			values.put(name, value);
		} catch (Exception ex) { // to avoid null pointer exception if the name or value are null
		}
	}
}
