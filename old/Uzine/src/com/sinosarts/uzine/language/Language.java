package com.sinosarts.uzine.language;

import com.sinosarts.zinemob.core.Properties;
import java.io.IOException;

/**
 * Classe utilizada para a obten��o das express�es literais utilizadas no sistema.
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
	 * Carrega o dicion�rio de express�es utilizando o resource de propriedades
	 * especificado. Ver a classe Properties do ZineMob para mais detalhes.
	 * @param resourceName o nome do resource
	 * @throws IOException se ocorrer algum problema ao carregar o resource
	 */
	public void loadFromResource(String resourceName) throws IOException {
		properties = new Properties();
		properties.loadFromResource(resourceName);
	}

	/**
	 * Adiciona ou substitui uma express�o no dicion�rio.
	 * @param key a chave da express�o
	 * @param value o valor da express�o
	 */
	public void setExpression(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * Retorna a express�o vinculada � chave.
	 * @param key a chave da express�o
	 * @return a express�o vinculada � chave. Se n�o houver, retornar� a pr�pria
	 * chave envolta a interroga��es (???chave???).
	 */
	public String getExpression(String key) {
		return properties.getProperty(key, "???" + key + "???");
	}

	/**
	 * Verifica se a String value � uma chave de express�es. Para isso, deve iniciar
	 * com o caractere $. Se isto for verdadeiro, ent�o � buscado a express�o vinculada
	 * � String value (sem o caractere inicial $). Se isto n�o for verdadeiro,
	 * ent�o value � retornado.
	 *
	 * Caso a String inicia com dois caracteres $$, isso significa que, embora
	 * inicia com $, n�o � uma chave de express�es. Ent�o o primeiro caractere $
	 * � cortado e o restante � retornado.
	 *
	 * @param value o valor, que pode ser chave ou um valor literal
	 * @return o valor da chave, caso value seja uma chave, ou o pr�prio value se
	 * for detectado que n�o � uma chave
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
	 * @param key key a chave da express�o
	 * @returna express�o vinculada � chave
	 */
	public static String get(String key) {
		return getInstance().getExpression(key);
	}

}
