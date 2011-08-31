package com.sinosarts.zinemob.core;

import java.util.Hashtable;
import java.util.Vector;

public class MultiLanguages extends ScriptLoader
{
	private Hashtable table = null;
	private static MultiLanguages singleton = null;
	
	private MultiLanguages()
	{
		table = new Hashtable();
	}
	
	public void loadLanguage (String file) throws Exception {
		super.load(file);
	}
	
	public static synchronized MultiLanguages getSingleton()
	{
		if (singleton==null) singleton = new MultiLanguages();
		return singleton;
	}
	
	protected void command (String name, Vector args) throws Exception
	{
		String key = name;
		key = key.substring (1, key.length()-1);
		String value = new String();
		
		for (int i=0; i<args.size(); i++)
		{
			value += (String)args.elementAt(i);
			if (i<args.size()-1)
				value += '\n';
		}
		
		table.put (key, value.trim());
	}
	
	public String getString (String key)
	{
		if (table.containsKey(key))
			return (String)table.get(key);
		else
			return "";
	}
	
	/**
	 * M�todo est�tico que tem a mesma fun��o do m�todo n�o-est�tico getString(String).
	 * Existe apenas por ser mais f�cil de usar, visto que este automaticamente
	 * acessa o singleton da classe.
	 * @param key a palavra-chave da tradu��o.
	 * @return a string na linguagem associada.
	 */
	public static String localize (String key)
	{
		return getSingleton().getString(key);
	}

}
