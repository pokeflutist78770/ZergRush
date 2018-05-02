package model;

/**
 * Element attributes represent the elemental type of a Tower's projectiles.  Different Mobs have different resistances
 * to a given element.
 * 
 * @author Ben Walters
 *
 */
public enum ElementalAttribute {  
	DEMO_ELEMENT(2), NONE(1), FIRE(1.25), ICE(1.75), POISON(1.25), AIR(1.25);
	
	private double multiplier;
	
	private ElementalAttribute(double multiplier) {
		this.multiplier = multiplier;
	}

	public double getElementalMultiplier() {
		return multiplier;
	}
}
