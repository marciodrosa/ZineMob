package com.sinosarts.uzine.language;

import com.sinosarts.zinemob.core.Properties;
import java.io.IOException;

/**
 * Classe utilizada para a obtenção das expressões literais utilizadas no sistema.
 */
public class Language {

	private static Language instance;

	private Properties properties = new Properties();

	private Language() {
	}

	public synchronized static Language getInstance() {
		if (instance == null)
			instance = new Language();
		return instance;
	}

	/**
	 * Carrega o dicionário de expressões utilizando o resource de propriedades
	 * especificado. Ver a classe Properties do ZineMob para mais detalhes.
	 * @param resourceName o nome do resource
	 * @throws IOException se ocorrer algum problema ao carregar o resource
	 */
	public void loadFromResource(String resourceName) throws IOException {
		properties = new Properties();
		properties.loadFromResource(resourceName);
	}

	/**
	 * Adiciona ou substitui uma expressão no dicionário.
	 * @param key a chave da expressão
	 * @param value o valor da expressão
	 */
	public void setExpression(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * Retorna a expressão vinculada à chave.
	 * @param key a chave da expressão
	 * @return a expressão vinculada à chave. Se não houver, retornará a própria
	 * chave envolta a interrogações (???chave???).
	 */
	public String getExpression(String key) {
		return properties.getProperty(key, "???" + key + "???");
	}

	/**
	 * Verifica se a String value é uma chave de expressões. Para isso, deve iniciar
	 * com o caractere $. Se isto for verdadeiro, então é buscado a expressão vinculada
	 * à String value (sem o caractere inicial $). Se isto não for verdadeiro,
	 * então value é retornado.
	 *
	 * Caso a String inicia com dois caracteres $$, isso significa que, embora
	 * inicia com $, não é uma chave de expressões. Então o primeiro caractere $
	 * é cortado e o restante é retornado.
	 *
	 * @param value o valor, que pode ser chave ou um valor literal
	 * @return o valor da chave, caso value seja uma chave, ou o próprio value se
	 * for detectado que não é uma chave
	 */
	public String translate(String value) {

		if (value == null)
			return null;

		if (value.startsWith("$") && value.length() > 1) {
			value = value.substring(1);
			if (!value.startsWith("$"))
				return getExpression(value);
		}
		return value;
	}

	/**
	 * Retorna o mesmo que getInstance().getExpression().
	 * @param key key a chave da expressão
	 * @returna expressão vinculada à chave
	 */
	public static String get(String key) {
		return getInstance().getExpression(key);
	}

}
