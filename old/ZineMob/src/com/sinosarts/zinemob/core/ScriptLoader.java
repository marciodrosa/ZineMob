package com.sinosarts.zinemob.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


/**
 * ScriptLoader é utilizado para carregar e interpretar scripts. O formato do script
 * é o seguinte:
 * <nome do comando>
 * argumento 1
 * argumento 2
 * ...
 * argumento n
 * 
 * <outro comando>
 * argumento
 * ...
 * //linha de comentï¿½rio
 * 
 * As únicas obrigatoriedades sao que cada linha possua apenas um comando/argumento e
 * que o nome do comando esteja entre < e >.
 */
public abstract class ScriptLoader
{
	protected void load (String fileName) throws Exception
	{
		InputStream instr = getClass().getResourceAsStream(fileName);
		
		if (instr==null) {
			throw new IOException ("Script file " + fileName + " does not exists.");
		}
		
		load (instr);
	}

	protected void load (InputStream instr) throws Exception
	{		
		DataInputStream reader = new DataInputStream (instr);
		instr = null;
		//String buffer;
		String name = null;
		Vector args = new Vector();
		
		try
		{
			for (String buffer=readLine(reader); buffer!=null; buffer=readLine(reader))
			{
				if (!buffer.startsWith("//") && buffer.length()!=0)
				{
					if (buffer.endsWith(">") && buffer.startsWith("<")) //novo comando <>
					{
						if (name!=null) command (name, args);
						name = buffer;
						args.removeAllElements();
					}
					else
						args.addElement (buffer);
					buffer = new String();
				}
				System.gc();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		if (name!=null) command (name, args);
		System.gc();
		
		reader.close();
		reader = null;
		System.gc();
	}
	
	protected abstract void command (String name, Vector args) throws Exception;
	
	public static String readLine (DataInputStream reader) throws IOException
	{
		String buffer = new String();
		
		int data;
		
		while (true)
		{
			data = reader.read();
			if (data==-1)
			{
				if (buffer.length()==0) return null;
				return buffer;
			}
			else if (data=='\r')
			{
				int data2 = reader.read();
				if (data2=='\n') break;
				else
				{
					buffer += (char)data;
					buffer += (char)data2;
				}
			}
			else
				buffer += (char)data;
		}
		return buffer;
	}

	public static boolean stringContains (String s1, String s2)
	{
		for (int i=0; i<s1.length(); i++)
			if (s1.regionMatches(true, i, s2, 0, s2.length())) return true;
		
		return false;
	}
	
	public static Vector stringTokenizer (String s, char t)
	{
		Vector strings = new Vector();
		int begin = 0;
		
		for (int i=0; i<s.length(); i++)
		{
			if (s.charAt(i)==t) {
				if (i!=begin) strings.addElement(new String(s.substring(begin, i)));
				begin = i+1;
			}
		}
		
		return strings;
	}
}
