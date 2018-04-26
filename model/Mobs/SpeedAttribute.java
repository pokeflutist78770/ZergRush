package model.Mobs;

import controller.ControllerMain;

/**
 * Speed Attribute represent the rate at which mobs move about the map.
 * 
 * @author Ben Walters
 *
 */
public enum SpeedAttribute {
	// 500 is arbitrary. The point is just that its some fraction of the GUI size.
	
	SLOW(ControllerMain.GUI_SIZE / 1000 ), // 50%
	NORMAL(ControllerMain.GUI_SIZE / 500),  //1.25
	FAST(ControllerMain.GUI_SIZE / 500+1.5),  //1.5
	VERY_FAST(4*ControllerMain.GUI_SIZE/ 500+1.75),
	PROJECTILE_SPEED(4*ControllerMain.GUI_SIZE / 500); 
	
	private final double doubleSpeed;
	
	SpeedAttribute(double scale) {
		this.doubleSpeed = scale;
	}
	
	public double getSpeed() {
		return doubleSpeed;
	}
}
