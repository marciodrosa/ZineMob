package com.sinosarts.zinemob.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Mapa de propriedades representados por Strings. Semelhante � classe Properties
 * do J2SE.
 */
public class Properties extends Hashtable {
	
	public Properties() {
	}

	/**
	 * Retorna o valor da propriedade vinculada � chave.
	 * @param key a chave procurada
	 * @return o valor vinculado a esta chave, null se n�o existir esta chave
	 * nas propriedades
	 */
	public String getProperty(String key) {
		return (String)get(key);
	}

	/**
	 * Retorna o valor da propriedade vinculada � chave.
	 * @param key a chave procurada
	 * @param defaultValue o valor padr�o, retornado caso n�o seja encontrado nenhum
	 * valor vinculado � chave
	 * @return o valor vinculado a esta chave, ou o valor do par�metro defaultValue
	 * se n�o existir esta chave nas propriedades
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * Carrega as propriedades do resource. Cada linha do arquivo corresponde a
	 * uma propriedade, onde chave e valor s�o separados pelo caractere =. Os
	 * espa�os em branco nas extremidades das Strings s�o eliminados. Express�es
	 * que inicial com o caractere # s�o coment�rios e, por isso, s�o eliminados.
	 * @param resourceName o nome do resource
	 * @throws IOException caso ocorra alguma exce��o na leitura do resource
	 */
	public void loadFromResource(String resourceName) throws IOException {

		InputStream is = getClass().getResourceAsStream(resourceName);
		DataInputStream dis = new DataInputStream(is);

		for (String line=readLine(dis); line!=null; line=readLine(dis)) {
			line = line.trim();
			if (line.startsWith("#")) // coment�rio
				continue;

			int separatorIndex = line.indexOf('=');

			if (separatorIndex != -1) {
				String key = line.substring(0, separatorIndex).trim();
				String value = "";
				if (separatorIndex < line.length() - 1)
					value = line.substring(separatorIndex + 1).trim();
				put(key, value);
			}
		}
	}

	private String readLine (DataInputStream reader) throws IOException {
		StringBuffer buffer = new StringBuffer();

		int data;

		while (true) {
			data = reader.read();
			if (data == -1) {
				if (buffer.length()==0)
					return null;
				return buffer.toString();
			}
			else if (data=='\n')
				break;
			else
				buffer.append((char)data);
		}
		return buffer.toString();
	}

}

/*
 * Vers�o 01.09: cria��o da classe (post UZ_20100509)
 */
