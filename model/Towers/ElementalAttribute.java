package model.Towers;

public enum ElementalAttribute {
	DEMO_ELEMENT(2), NONE(1), FIRE(1.25), WATER(1.25), EARTH(1.25), AIR(1.25);
	
	private double multiplier;
	
	private ElementalAttribute(double multiplier) {
		this.multiplier = multiplier;
	}

	public double getElementalMultiplier() {
		return multiplier;
	}
}
