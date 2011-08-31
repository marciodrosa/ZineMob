package com.zine.zinemobsamples.spaceship;

import com.zine.zinemob.ZineMIDlet;

public class SpaceShipMIDlet extends ZineMIDlet {

	public void run() {
		SpaceShipScene spaceShipMainModule = new SpaceShipScene();
		spaceShipMainModule.init();
		spaceShipMainModule.run();
	}

}
