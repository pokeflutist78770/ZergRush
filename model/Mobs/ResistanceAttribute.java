package model.Mobs;


public enum ResistanceAttribute {
	
	//DEMO is used only for testing, to show a full resistance to any shot
	DEMO_RESISTANCE(1), FIRE_RESISTANCE(.33), ICE_RESISTANCE(.33), POISON_RESISTANCE(.5);
	
	private double resistance;
	
	ResistanceAttribute(double value) {
		resistance=value;
	}
	
	public double getResistance() {
		return resistance;
	}
}
