package model.Mobs;



public enum ArmorAttribute {
  
	//DEMO is used only for testing, to show a full resistance to any shot
	DEMO_ARMOR(0), NONE(0), LIGHT_ARMOR(.25), MEDIUM_ARMOR(.33), HEAVY_ARMOR(.5);
	
	private double armor;
	
	private ArmorAttribute(double value){
		armor=value;
	}
	
	public double getArmor() {
		return armor;
	}

}
