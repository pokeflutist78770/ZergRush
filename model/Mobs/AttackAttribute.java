package model.Mobs;

/**
 * Attack Attribute enums represent the damage a mob deals to the play if it reaches the end-zone.
 * @author Ben Walters
 *
 */
public enum AttackAttribute {
	DEMO_ATTACK(50), WEAK_ATTACK(50), MEDIUM_ATTACK(100),
	STRONG_ATTACK(200);
	
	private double attack;
	
	private AttackAttribute(double attack) {
		this.attack=attack;
	}
	
	public double getAttack() {
		return attack;
	}
}
