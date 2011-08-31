package com.sinosarts.zinemob.core;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Mapa de propriedades representados por Strings. Semelhante à classe Properties
 * do J2SE.
 */
public class Properties extends Hashtable {
	
	public Properties() {
	}

	/**
	 * Retorna o valor da propriedade vinculada à chave.
	 * @param key a chave procurada
	 * @return o valor vinculado a esta chave, null se não existir esta chave
	 * nas propriedades
	 */
	public String getProperty(String key) {
		return (String)get(key);
	}

	/**
	 * Retorna o valor da propriedade vinculada à chave.
	 * @param key a chave procurada
	 * @param defaultValue o valor padrão, retornado caso não seja encontrado nenhum
	 * valor vinculado à chave
	 * @return o valor vinculado a esta chave, ou o valor do parâmetro defaultValue
	 * se não existir esta chave nas propriedades
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
	 * uma propriedade, onde chave e valor são separados pelo caractere =. Os
	 * espaços em branco nas extremidades das Strings são eliminados. Expressões
	 * que inicial com o caractere # são comentários e, por isso, são eliminados.
	 * @param resourceName o nome do resource
	 * @throws IOException caso ocorra alguma exceção na leitura do resource
	 */
	public void loadFromResource(String resourceName) throws IOException {

		InputStream is = getClass().getResourceAsStream(resourceName);
		DataInputStream dis = new DataInputStream(is);

		for (String line=readLine(dis); line!=null; line=readLine(dis)) {
			line = line.trim();
			if (line.startsWith("#")) // comentário
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
 * Versão 01.09: criação da classe (post UZ_20100509)
 */
