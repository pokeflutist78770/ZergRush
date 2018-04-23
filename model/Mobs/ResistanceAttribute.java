package model.Mobs;

/**
 * Resistance enums represent another layer of damage mitigation for mobs, regulating how much damage they take from
 * attacks of a given elemental type.
 * 
 * @author Ben Walters
 *
 */
public enum ResistanceAttribute {
	
	//DEMO is used only for testing, to show a full resistance to any shot
	DEMO(1), NONE(0), FIRE(.33), ICE(.33), POISON(.5), ELECTRIC(.33);
	
	private double resistance;
	
	ResistanceAttribute(double value) {
		resistance=value;
	}
	
	public double getResistance() {
		return resistance;
	}
}
