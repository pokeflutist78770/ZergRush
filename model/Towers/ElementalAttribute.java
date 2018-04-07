package model.Towers;

public enum ElementalAttribute {
	DEMO_ELEMENT(2);
	
	private double multiplier;
	
	private ElementalAttribute(double multiplier) {
		this.multiplier = multiplier;
	}

	public double getElementalMultiplier() {
		return multiplier;
	}
}
