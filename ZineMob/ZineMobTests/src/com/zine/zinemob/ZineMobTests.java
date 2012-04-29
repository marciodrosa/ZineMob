package com.zine.zinemob;

import j2meunit.midletui.TestRunner;

public class ZineMobTests extends TestRunner {

	public ZineMobTests() {
		start(new String[] {
			"com.zine.zinemob.ad.InnerActiveXmlResponseTest",
			
			"com.zine.zinemob.animation.AnimationTrackTest",
			"com.zine.zinemob.animation.FramesAnimationControllerTest",
			"com.zine.zinemob.animation.ResizeAnimationControllerTest",
			"com.zine.zinemob.animation.TranslationAnimationControllerTest",
			
			"com.zine.zinemob.drawableelement.ColorTest",
			"com.zine.zinemob.drawableelement.DrawableElementTest",
			"com.zine.zinemob.drawableelement.SemiTransparentRectangleTest",
			"com.zine.zinemob.drawableelement.SpriteElementTest",
			"com.zine.zinemob.drawableelement.ViewportElementTest",

			"com.zine.zinemob.drawableelement.layout.FitToChildrenLayoutTest",
			"com.zine.zinemob.drawableelement.layout.LayoutTest",
			"com.zine.zinemob.drawableelement.layout.LinearLayoutTest",
			"com.zine.zinemob.drawableelement.layout.StretchToParentLayoutTest",
			
			"com.zine.zinemob.drawableelement.text.ImageTextElementTest",
			
			"com.zine.zinemob.drawableelement.tilesmap.TilesMapTest",
			
			"com.zine.zinemob.flow.FlowTest",
			
			"com.zine.zinemob.i18n.I18nTest",
			"com.zine.zinemob.i18n.IdiomChangeTextsParserTest",
			
			"com.zine.zinemob.scene.AssetTest",
			"com.zine.zinemob.scene.SceneModuleTest",
			"com.zine.zinemob.scene.SceneModuleCanvasTest",
			
			"com.zine.zinemob.text.PairTest",
			"com.zine.zinemob.text.PropertiesTest",
			"com.zine.zinemob.text.TextUtilsTest",
			
			"com.zine.zinemob.text.xml.XmlParserTest",
			
			"com.zine.zinemob.trigger.TriggerQueueTest",
		});
	}

}
