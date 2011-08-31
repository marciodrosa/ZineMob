/*
 * ScriptBin.java LUMEngine 2.0
 * M�rcio Daniel da Rosa
 */

package com.sinosarts.zinemob.core;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

/**
 * ScriptBin tem a fun��o de binarizar arquivos de texto para que possam ser ser
 * comprimidos em dados menores. A classe possui m�todos para ler arquivos bin�rios,
 * que s�o interpretados pela aplica��o.
 * 
 * Um arquivo bin�rio � composto de v�rios comandos dispostos em sequ�ncia. Cada
 * comando obrigatoriamente � disposto da seguinte maneira: 4 primeiros bytes (int)
 * para o id, 2 pr�ximos bytes (short) para o tamanho dos dados (sem contar o id
 * nem estes 2 bytes) e o restante dos dados do comando ocupando a quantidade de
 * bytes declarados.
 * @author M�rcio Daniel da Rosa
 */
public abstract class ScriptBin
{
	public void load (String fileName) throws IOException
	{
		DataInputStream inputStream = new DataInputStream (getClass().getResourceAsStream(fileName));
		
		boolean eof = false;
		while (!eof)
		{
			int id = -1;
			short dataSize = 0;
			
			try
			{
				id = inputStream.readInt();
				dataSize = inputStream.readShort();
			}
			catch (EOFException e) {
				eof = true;
				break;
			}
			
			byte data[] = new byte[dataSize];
			
			if (inputStream.read(data)!=-1)
			{
				ByteArrayInputStream byteStream = new ByteArrayInputStream (data);
				DataInputStream commandData = new DataInputStream (byteStream);
				binCommand (id, commandData);
				
				commandData.close();
				commandData = null;
				byteStream = null;
			}
			else
				eof = true;
			
			data = null;
			
			System.gc();
		}
		
		inputStream.close();
		inputStream = null;
		
		System.gc();
	}
	
	protected abstract void binCommand (int id, DataInputStream data) throws IOException;
	
}
