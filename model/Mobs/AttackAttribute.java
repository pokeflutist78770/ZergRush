package model.Mobs;

public enum AttackAttribute {
	DEMO_ATTACK(50);
	
	private double attack;
	
	private AttackAttribute(double attack) {
		this.attack=attack;
	}
	
	public double getAttack() {
		return attack;
	}
}
