package com.sinosarts.zinemob.core;

import java.util.Hashtable;

public class ResourceManager
{
	private static ResourceManager singleton;
	private Hashtable objects;
	
	public static ResourceManager getSingleton()
	{
		if (singleton==null) singleton = new ResourceManager();
		return singleton;
	}
	
	private ResourceManager()
	{
		objects = new Hashtable();
	}
	
	public void add (String name, Object o)
	{
		objects.put (name, o);
	}
	
	public void free (String name)
	{
		objects.remove(name);
	}
	
	public Object get (String name)
	{
		return objects.get(name);
	}

}
