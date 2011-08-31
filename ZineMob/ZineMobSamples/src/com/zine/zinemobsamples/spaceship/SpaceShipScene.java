package com.zine.zinemobsamples.spaceship;

import com.zine.zinemob.scene.Scene;
import com.zine.zinemobsamples.spaceship.controller.BackgroundController;
import com.zine.zinemobsamples.spaceship.controller.ShipController;

public class SpaceShipScene extends Scene {

	public void init() {
		addController(new BackgroundController());
		addController(new ShipController(this));
	}

}
