package model.Mobs;


public enum DefenseAttribute {
  DEMO_DEFENSE(75), SMALL(50), MEDIUM(100), LARGE(200);
	
	
  private double hp;
  
  private DefenseAttribute(double hp) {
	  this.hp=hp;
  }

  public double getDefense() {
	  return hp;
  }
}
