package com.zine.zinemob;

import j2meunit.midletui.TestRunner;

public class ZineMobTests extends TestRunner {

	public ZineMobTests() {
		start(new String[] {
			"com.zine.zinemob.drawableelement.ColorTest",
			"com.zine.zinemob.drawableelement.DrawableElementTest",
			"com.zine.zinemob.drawableelement.SpriteElementTest",
			"com.zine.zinemob.drawableelement.ViewportElementTest",

			"com.zine.zinemob.drawableelement.layoutfixer.LayoutFixerTest",
			"com.zine.zinemob.drawableelement.layoutfixer.FitToChildrenLayoutFixerTest",
			"com.zine.zinemob.drawableelement.layoutfixer.StretchToParentLayoutFixerTest",
			
			"com.zine.zinemob.scene.SceneModuleTest",
			"com.zine.zinemob.scene.SceneModuleCanvasTest",
		});
	}

}
