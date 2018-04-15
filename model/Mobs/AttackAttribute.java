package model.Mobs;

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
