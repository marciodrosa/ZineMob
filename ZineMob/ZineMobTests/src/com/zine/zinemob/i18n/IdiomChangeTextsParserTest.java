package com.zine.zinemob.i18n;

import com.zine.zinemob.drawableelement.DrawableElement;
import com.zine.zinemob.drawableelement.text.ImageTextElement;
import com.zine.zinemob.drawableelement.text.ImageTextElementFont;
import com.zine.zinemob.scene.Scene;
import com.zine.zinemob.text.Properties;
import j2meunit.framework.Test;
import j2meunit.framework.TestCase;
import j2meunit.framework.TestMethod;
import j2meunit.framework.TestSuite;

public class IdiomChangeTextsParserTest extends TestCase {
	
	private ImageTextElementFont font;
	private IdiomChangeTextsParser idiomChangeTextsParser;
	
	public IdiomChangeTextsParserTest() {

	}

	public IdiomChangeTextsParserTest(String testName, TestMethod method) {
		super(testName, method);
	}

	public void setUp() {
		I18n.setProperties(new Properties());
		font = new ImageTextElementFont("/com/zine/zinemob/res/imageFont.png", "/com/zine/zinemob/res/imageFont.dat");
		idiomChangeTextsParser = new IdiomChangeTextsParser();
	}

	public void tearDown() {
		I18n.setProperties(new Properties());
	}

	public Test suite() {

		TestSuite testSuite = new TestSuite();

		testSuite.addTest(new IdiomChangeTextsParserTest("testShouldUpdateValuesOfTranslatedTexts", new TestMethod()
		{ public void run(TestCase tc) {((IdiomChangeTextsParserTest)tc).testShouldUpdateValuesOfTranslatedTexts(); } } ));

		return testSuite;
	}
	
	private void loadPortuguese() {
		Properties properties = new Properties();
		properties.add("thebook", "O livro");
		properties.add("ison", "está na");
		properties.add("table", "mesa!");
		I18n.setProperties(properties);
	}
	
	private void loadEnglish() {
		Properties properties = new Properties();
		properties.add("thebook", "The book");
		properties.add("ison", "is on");
		properties.add("table", "the table!");
		I18n.setProperties(properties);
	}

	private void testShouldUpdateValuesOfTranslatedTexts() {
		// given:
		loadEnglish();
		ImageTextElement imageTextElement1 = new ImageTextElement("$thebook", font, true, true);
		ImageTextElement imageTextElement2 = new ImageTextElement("$ison", font, true, true);
		ImageTextElement imageTextElement3 = new ImageTextElement("$table", font, true, true);
		ImageTextElement imageTextElementNotTranslate1 = new ImageTextElement("thebook", font, true, true);
		ImageTextElement imageTextElementNotTranslate2 = new ImageTextElement("$ison", font, true, false);
		
		Scene scene = new Scene();
		scene.getScreenElement().addChild(imageTextElement1);
		
		DrawableElement other1 = new DrawableElement();
		DrawableElement other2 = new DrawableElement();
		imageTextElement1.addChild(other1);
		imageTextElement1.addChild(other2);
		other2.addChild(imageTextElement2);
		other2.addChild(imageTextElement3);
		other2.addChild(imageTextElementNotTranslate1);
		other2.addChild(imageTextElementNotTranslate2);
		
		// when:
		loadPortuguese();
		idiomChangeTextsParser.updateTexts(scene);
		
		// then:
		assertEquals("The text $thebook was not updated to portuguese.", "O livro", imageTextElement1.getText());
		assertEquals("The text $ison was not updated to portuguese.", "está na", imageTextElement2.getText());
		assertEquals("The text $table was not updated to portuguese.", "mesa!", imageTextElement3.getText());
		assertEquals("The text thebook should not change because is not a translateble value.", "thebook", imageTextElementNotTranslate1.getText());
		assertEquals("The text $ison should not change because is configured to not translate.", "$ison", imageTextElementNotTranslate2.getText());
	}
	
}
