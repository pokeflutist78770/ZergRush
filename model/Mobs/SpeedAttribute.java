package model.Mobs;

import controller.ControllerMain;

public enum SpeedAttribute {
	// 500 is arbitrary. The point is just that its some fraction of the GUI size.
	
	NORMAL (ControllerMain.GUI_SIZE / 500), VERY_FAST(4*ControllerMain.GUI_SIZE/ 500),
	PROJECTILE_SPEED(4*ControllerMain.GUI_SIZE / 500); 
	
	private final double doubleSpeed;
	
	SpeedAttribute(double scale) {
		this.doubleSpeed = scale;
	}
	
	public double toDouble() {
		return doubleSpeed;
	}

}
