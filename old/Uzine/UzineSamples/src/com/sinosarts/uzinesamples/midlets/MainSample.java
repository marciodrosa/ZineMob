package com.sinosarts.uzinesamples.midlets;

import com.sinosarts.uzine.language.Language;
import com.sinosarts.uzine.view.UzineModule;
import com.sinosarts.uzinesamples.controller.MenuController;
import com.sinosarts.zinemob.core.ZineMIDlet;
import java.io.IOException;

public class MainSample extends ZineMIDlet {

	public void run() {

		try {
			Language.getInstance().loadFromResource("/com/sinosarts/uzinesamples/res/eng.properties");
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

		UzineModule module = new UzineModule(new MenuController());
		module.run();
	}

}
