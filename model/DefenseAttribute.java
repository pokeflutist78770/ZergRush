package model;

/**
 * Defense Attribute enums represent another damage mitigated to a mob's HP, separate from armor, defense attributes 
 * mitigate damage against specific kinds of attacks.
 * 
 * @author Ben
 *
 */
public enum DefenseAttribute {
  DEMO_DEFENSE(75), 
  SMALL(50), 
  MEDIUM(100), 
  LARGE(350),
  EXTRA_LARGE(1000);
	
	
  private double hp;
  
  private DefenseAttribute(double hp) {
	  this.hp=hp;
  }

  public double getDefense() {
	  return hp;
  }
}
