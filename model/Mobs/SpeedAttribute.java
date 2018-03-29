package model.Mobs;

import controller.ControllerMain;

public enum SpeedAttribute {
	NORMAL (ControllerMain.GUI_SIZE / 500); // 500 is arbitrary. The point is just that its some fraction of the GUI size.
	
	private final double doubleSpeed;
	
	SpeedAttribute(double scale) {
		this.doubleSpeed = scale;
	}
	
	public double toDouble() {
		return doubleSpeed;
	}

}
